package com.ahmadalfan.movieapp.data.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.ahmadalfan.movieapp.utils.ApiException
import com.ahmadalfan.movieapp.utils.NoInternetException
import com.ahmadalfan.movieapp.utils.SslExpired
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.InputStream
import java.security.GeneralSecurityException
import java.security.KeyStore
import java.security.cert.Certificate
import java.security.cert.CertificateFactory
import java.util.*
import javax.net.ssl.SSLContext
import javax.net.ssl.SSLHandshakeException
import javax.net.ssl.TrustManagerFactory
import javax.net.ssl.X509TrustManager

class NetworkConnectionInterceptor(
    context: Context

) : Interceptor {
    private val applicationContext = context.applicationContext
    private var requestBuilder: Request.Builder? = null
    override fun intercept(chain: Interceptor.Chain): Response {
        try {
            if (!isInternetAvailable()) {
                throw NoInternetException("Make sure you have an active data connection")
            }

            val original = chain.request()
            requestBuilder = original.newBuilder()
                .addHeader("Accept", "application/json")
                .addHeader("Content-type", "application/json")
            val request = requestBuilder!!.build()
            return chain.proceed(request)
        } catch (e: ApiException) {
            throw SslExpired("Ssl Expired")
        } catch (ssl: SSLHandshakeException) {
            throw SslExpired("Ssl Expired")
        }
    }

    private fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManager =
            applicationContext.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManager?.let { it ->
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                it.getNetworkCapabilities(connectivityManager.activeNetwork)?.apply {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        else -> false
                    }
                }
            } else {
                it.activeNetworkInfo.also {
                    result = it != null && it.isConnected
                }
            }
        }
        return result
    }

 }