package com.ozantech.ozantechcaseapp.core.network.utils

import com.ozantech.ozantechcaseapp.core.model.extension.StringExt.empty
import com.ozantech.ozantechcaseapp.core.model.remote.network.Resource
import com.ozantech.ozantechcaseapp.core.model.remote.response.BaseApiResponse
import com.ozantech.ozantechcaseapp.core.model.remote.response.ErrorResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.channelFlow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

object NetworkHandler {
    inline fun <T> handleResponse(crossinline request: suspend () -> Response<BaseApiResponse<T>>): Flow<Resource<T>> =
        channelFlow {
            var response: Response<BaseApiResponse<T>>? = null
            try {
                response = request.invoke()

                if (response.isSuccessful) {
                    send(Resource.success(data = response.body()?.data))
                } else {
                    throw HttpException(response)
                }
            } catch (e: UnknownHostException) {
                send(
                    response.defaultServerError()
                )
            } catch (e: HttpException) {
                send(
                    response.defaultServerError()
                )
            } catch (e: IllegalStateException) {
                send(
                    response.defaultServerError()
                )
            } catch (e: Exception) {
                send(
                    response.defaultServerError()
                )
            }
        }.flowOn(Dispatchers.IO)

    fun <T> Response<BaseApiResponse<T>>?.defaultServerError(): Resource<T> {
        val code = this?.code() ?: -1
        val errorString = this?.errorBody()?.string() ?: String.empty

        val errorResponse = ErrorResponse(
            errorCode = code,
            message = errorString
        )

        return Resource.error(error = errorResponse)
    }
}