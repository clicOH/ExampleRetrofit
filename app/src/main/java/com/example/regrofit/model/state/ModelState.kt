package com.example.regrofit.model.state

enum class Status {
    INIT,
    SUCCESS,
    FAILED,
    LOADING
}


data class ModelState<out T>(
    val status: Status,
    val data: T? = null,
    val message: String? = "",
    val code: Int? = 0
) {

    companion object {

        fun <T> init(): ModelState<T> {
            return ModelState(Status.INIT)
        }

        // When the call is loading set the state
        // as Loading and rest as null
        fun <T> loading(message: String? = ""): ModelState<T> {
            return ModelState(Status.LOADING, message = message)
        }

        // In case of Success,set status as
        // Success and data as the response
        fun <T> success(data: T? = null, message: String? = ""): ModelState<T> {
            return ModelState(Status.SUCCESS, data = data, message = message)
        }

        // In case of failure ,set state to Error ,
        // add the error message,set data to null
        fun <T> failed(message: String? = "", code: Int? = 0): ModelState<T> {
            return ModelState(Status.FAILED, message = message, code = code)
        }
    }


}
