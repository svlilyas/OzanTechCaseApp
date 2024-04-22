package com.ozantech.ozantechcaseapp.core.uicomponents.platform.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ozantech.ozantechcaseapp.core.uicomponents.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlin.properties.Delegates

/**
 * Base ViewModel to manage to put all common methods and manage
 * @param ViewState is for storing state of the view
 * @param ViewAction is for storing all actions about the view
 */
abstract class BaseViewModel<ViewState : BaseViewState,
        ViewAction : BaseAction>(initialState: ViewState) :
    ViewModel() {
    /**
     * For storing ViewState
     */
    private val _uiStateFlow = MutableStateFlow(initialState)
    val uiStateFlow = _uiStateFlow.asStateFlow()

    private var stateTimeTravelDebugger: StateTimeTravelDebugger? = null

    init {
        if (BuildConfig.DEBUG) {
            stateTimeTravelDebugger = StateTimeTravelDebugger(this::class.java.simpleName)
        }
    }

    /**
     * Delegate will handle state event deduplication
     * (multiple states of the same type holding the same data
     * will not be dispatched multiple times to LiveData stream)
     */
    protected var state by Delegates.observable(initialState) { _, old, new ->
        viewModelScope.launch {
            _uiStateFlow.emit(new)
        }

        if (new != old) {
            stateTimeTravelDebugger?.apply {
                addStateTransition(old, new)
                logLast()
            }
        }
    }

    fun sendAction(viewAction: ViewAction) {
        stateTimeTravelDebugger?.addAction(viewAction)
        state = onReduceState(viewAction)
    }

    protected abstract fun onReduceState(viewAction: ViewAction): ViewState
}
