package com.ozantech.ozantechcaseapp.core.network.interceptor

import com.ozantech.ozantechcaseapp.core.model.utils.AppConstants
import com.ozantech.ozantechcaseapp.core.network.BuildConfig
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import timber.log.Timber
import javax.inject.Inject

class ApiRequestInterceptor @Inject constructor() :
    Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val newRequest = addHeaderToRequest(chain.request())

        return chain.proceed(newRequest)
    }

    private fun addHeaderToRequest(request: Request): Request {
        val newRequest = request.newBuilder()

        runBlocking(Dispatchers.IO) {
            try {
                newRequest.apply {
                    addHeader(AppConstants.API_KEY, BuildConfig.API_KEY)
                }.build()
            } catch (e: Exception) {
                Timber.e(e)
            }
        }

        return newRequest.build()
    }
}
