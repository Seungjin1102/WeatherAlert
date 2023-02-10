package com.example.data.exception

class EmptyBodyException(message: String? = "") : Exception(message)

class NetworkFailureException(message: String? = "") : Exception(message)