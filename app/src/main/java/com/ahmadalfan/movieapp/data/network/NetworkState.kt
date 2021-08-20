package com.ahmadalfan.movieapp.data.network

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED,
    NOCONNECTION
}

class NetworkState (val status: Status, val message: String) {
    companion object {
        val LOADING: NetworkState = NetworkState(Status.RUNNING, "Running")
        val LOADED: NetworkState = NetworkState(Status.SUCCESS, "Success")
        val ERROR: NetworkState = NetworkState(Status.FAILED, "Something went wrong")
        val NODATA: NetworkState = NetworkState(Status.FAILED, "NoData")
        val ENDOFLIST: NetworkState = NetworkState(Status.FAILED, "You have reached the end")
        val NOINTERNET: NetworkState = NetworkState(Status.NOCONNECTION, "No internet connection")
    }
}