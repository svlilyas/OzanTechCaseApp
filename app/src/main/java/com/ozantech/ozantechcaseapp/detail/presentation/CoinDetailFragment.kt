package com.ozantech.ozantechcaseapp.detail.presentation

import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.ozantech.ozantechcaseapp.R
import com.ozantech.ozantechcaseapp.core.model.extension.BooleanExt.safeGet
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinHistoryResponse
import com.ozantech.ozantechcaseapp.core.model.remote.response.CoinResponse
import com.ozantech.ozantechcaseapp.core.uicomponents.binding.BindingFragment
import com.ozantech.ozantechcaseapp.core.uicomponents.extensions.FlowExt.flowWithLifecycle
import com.ozantech.ozantechcaseapp.core.uicomponents.extensions.FragmentExt.getColor
import com.ozantech.ozantechcaseapp.core.uicomponents.extensions.FragmentExt.getDrawable
import com.ozantech.ozantechcaseapp.core.uicomponents.extensions.ViewExt.setOnDebouncedClickListener
import com.ozantech.ozantechcaseapp.databinding.FragmentCoinDetailBinding
import com.ozantech.ozantechcaseapp.detail.domain.CoinDetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import timber.log.Timber

@AndroidEntryPoint
class CoinDetailFragment :
    BindingFragment<FragmentCoinDetailBinding>(R.layout.fragment_coin_detail) {
    private val viewModel: CoinDetailViewModel by viewModels()
    private val args by navArgs<CoinDetailFragmentArgs>()

    private var coinItem: CoinResponse.Coin? = null
    override fun observeData() {
        flowWithLifecycle(
            dispatcher = Dispatchers.Main,
            flowData = viewModel.uiStateFlow,
            repeatedLifecycle = Lifecycle.State.STARTED
        ) { state ->
            state.errorMessage?.let {
                Toast.makeText(requireContext(), it, Toast.LENGTH_SHORT).show()
            }
            Timber.d("DataLog -> ${state.uiState}")
            state.coinHistory?.let {
                showCoinHistory(it)
            }
        }
    }

    override fun getViewData() {
        binding {
            coinItem = args.coinItem
            item = coinItem

            viewModel.fetchCoinHistory(coinId = coinItem?.uuid)
        }
    }

    override fun initClickListeners() {
        binding {
            ivBack.setOnDebouncedClickListener {
                findNavController().popBackStack()
            }

            ivFavorite.setOnDebouncedClickListener {
                coinItem?.let {
                    coinItem = it.copy(isFavorite = !coinItem?.isFavorite.safeGet())
                    item = coinItem
                    viewModel.toggleFavorite(coinItem = coinItem)
                }
            }
        }
    }

    private fun showCoinHistory(coinHistory: CoinHistoryResponse) {
        binding {
            try {
                val entryList: ArrayList<Entry> = ArrayList()
                coinHistory.history?.forEachIndexed { index, item ->
                    entryList.add(
                        Entry(
                            index.toFloat(),
                            item?.price?.toFloat() ?: 0F
                        )
                    )
                    Timber.d("TS: ${item?.timestamp} PR: ${item?.price}")
                }

                val lineDataSet = LineDataSet(entryList, getString(R.string.price_title))

                val datasets: ArrayList<ILineDataSet> = ArrayList()
                datasets.add(lineDataSet)

                lineDataSet.run {
                    setDrawFilled(true)
                    setDrawValues(false)
                    setDrawCircles(false)
                    lineWidth = 3f
                    color = getColor(colorRes = R.color.blue_100)

                    valueTextColor = getColor(colorRes = R.color.white_100)
                    fillDrawable = getDrawable(drawableRes = R.drawable.fade_purple)
                }

                lineChart.axisRight.run {
                    setDrawGridLines(false)
                    setDrawLabels(true)
                    setDrawAxisLine(false)
                    textColor = getColor(colorRes = R.color.white_100)
                }
                lineChart.axisLeft.run {
                    setDrawGridLines(false)
                    setDrawLabels(false)
                    setDrawAxisLine(false)
                }
                lineChart.xAxis.run {
                    setDrawGridLines(false)
                    setDrawLabels(false)
                    setDrawAxisLine(false)
                }
                lineChart.run {
                    description.isEnabled = false
                    legend.isEnabled = false
                    setDrawGridBackground(false)
                    setDrawBorders(false)
                    isScaleYEnabled = false
                    data = LineData(datasets)
                    invalidate()
                }
            } catch (e: NegativeArraySizeException) {
                Timber.e(e)
            }
        }
    }

    override fun onDestroyView() {
        coinItem = null
        super.onDestroyView()
    }
}