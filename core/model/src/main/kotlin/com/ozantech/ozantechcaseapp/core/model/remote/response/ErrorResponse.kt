package com.ozantech.ozantechcaseapp.core.model.remote.response

/**
 * @param code A network response code.
 * @param message A network error message.
 */
data class ErrorResponse(
    val errorCode: Int? = null,
    val message: String? = null,
    val errors: List<FieldError>? = null
) {
    data class FieldError(
        val field: String? = null,
        val messages: List<String>? = emptyList()
    )
}
