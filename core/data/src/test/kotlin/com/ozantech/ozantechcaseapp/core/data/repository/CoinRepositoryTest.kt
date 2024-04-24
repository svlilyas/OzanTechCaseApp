package com.ozantech.ozantechcaseapp.core.data.repository

import com.ozantech.ozantechcaseapp.core.data.EntityHelper
import com.ozantech.ozantechcaseapp.core.data.TestDispatcherRule
import com.ozantech.ozantechcaseapp.core.model.extension.CollectionExt.safeGet
import com.ozantech.ozantechcaseapp.core.model.local.OrderType
import com.ozantech.ozantechcaseapp.core.model.remote.network.Resource
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.withContext
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoinRepositoryTest {
    @get: Rule
    val dispatcherRule = TestDispatcherRule()

    private lateinit var mockkCoinRepository: CoinRepository

    @Before
    fun setUp() {
        mockkCoinRepository = mockk()
    }

    @Test
    fun `fetch coins expected three items list`() = runTest {
        withContext(Dispatchers.IO) {
            //arrange
            val coinResponse = EntityHelper.generateCoinResponse(coinName = "CoinName")

            coEvery { mockkCoinRepository.fetchCoins(orderBy = OrderType.Price.type) } returns flowOf(
                Resource.success(
                    data = coinResponse
                )
            )

            //action
            val response =
                mockkCoinRepository.fetchCoins(orderBy = OrderType.Price.type).first().data

            //assert
            assert(response != null && response.coins?.size == 3)
            repeat(2) {
                assert(response?.coins.safeGet(it)?.name == "CoinName")
            }
        }
    }

    @Test
    fun `fetch coins expected single item`() = runTest {
        withContext(Dispatchers.IO) {
            //arrange
            val coinResponse = EntityHelper.generateCoinResponse(coinName = "CoinName")

            coEvery { mockkCoinRepository.fetchCoins(orderBy = OrderType.Price.type) } returns flowOf(
                Resource.success(
                    data = coinResponse
                )
            )

            //action
            val response =
                mockkCoinRepository.fetchCoins(orderBy = OrderType.Price.type).first().data

            //assert
            assert(response != null)
            assert(response?.coins.safeGet(0)?.name == "CoinName")
        }
    }
}