package com.ozantech.ozantechcaseapp.core.uicomponents.extensions

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import timber.log.Timber

object FlowExt {
    inline fun <T> LifecycleOwner.flowWithLifecycle(
        dispatcher: CoroutineDispatcher = Dispatchers.IO,
        repeatedLifecycle: Lifecycle.State = Lifecycle.State.STARTED,
        flowData: Flow<T>?,
        crossinline action: suspend (T) -> Unit
    ) {
        /**
         * Start a coroutine in the lifecycle scope
         */
        lifecycleScope.launch(dispatcher) {
            /**
             * repeatOnLifecycle launches the block in a new coroutine every time the
             * lifecycle is in the STARTED state (or above) and cancels it when it's STOPPED.
             */
            repeatOnLifecycle(repeatedLifecycle) {
                /**
                 * Trigger the flow and start listening for values.
                 * Note that this happens when lifecycle is STARTED and stops
                 * collecting when the lifecycle is STOPPED
                 */
                try {
                    flowData?.collectLatest {
                        action.invoke(it)
                    }
                } catch (t: Throwable) {
                    Timber.d(t)
                    cancel()
                }
            }
        }
    }
}




