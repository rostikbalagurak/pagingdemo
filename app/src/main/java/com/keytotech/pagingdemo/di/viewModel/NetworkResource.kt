package com.keytotech.pagingdemo.di.viewModel

/**
 * NetworkResource
 *
 * @author Bogdan Ustyak (bogdan.ustyak@gmail.com)
 */
enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

@Suppress("DataClassPrivateConstructor")
data class NetworkResource<T> private constructor(val status: Status, val data: T? = null, val msg: String? = null) {

    companion object {
        /**
         * Returns NetworkResource with successfully loaded data
         */
        fun <T> success(data: T?): NetworkResource<T> {
            return NetworkResource(Status.SUCCESS, data, null)
        }

        /**
         * Returns NetworkResource with failed status
         */
        fun <T> failed(msg: String?, data: T? = null): NetworkResource<T> {
            return NetworkResource(Status.FAILED, data, msg)
        }

        /**
         * Returns NetworkResource with running status
         */
        fun <T> running(data: T? = null): NetworkResource<T> {
            return NetworkResource(Status.RUNNING, data, null)
        }
    }

    /**
     * Returns non nullable data object
     */
    fun get(): T {
        return data!!
    }
}