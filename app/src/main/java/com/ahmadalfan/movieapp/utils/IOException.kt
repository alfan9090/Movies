package com.ahmadalfan.movieapp.utils

import java.io.IOException

class ApiException(message: String) : IOException(message)
class NoInternetException(message: String) : IOException(message)
class SslExpired(message: String) : IOException(message)
class TokenExpired(message: String) : IOException(message)