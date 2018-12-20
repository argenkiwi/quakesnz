package nz.co.codebros.quakesnz.core.usecase

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Failure<out T>(val throwable: Throwable) : Result<T>()
}
