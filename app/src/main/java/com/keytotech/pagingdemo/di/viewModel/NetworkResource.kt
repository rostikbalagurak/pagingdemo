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
        fun <T> success(data: T?): NetworkResource<T> {
            return NetworkResource(Status.SUCCESS, data, null)
        }

        fun <T> failed(msg: String?, data: T? = null): NetworkResource<T> {
            return NetworkResource(Status.FAILED, data, msg)
        }

        fun <T> running(data: T? = null): NetworkResource<T> {
            return NetworkResource(Status.RUNNING, data, null)
        }
    }

    fun get(): T {
        return data!!
    }
}