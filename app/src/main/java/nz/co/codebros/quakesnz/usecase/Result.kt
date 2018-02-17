package nz.co.codebros.quakesnz.usecase

/**
 * Created by Leandro on 23/11/2017.
 */
sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    data class Failure<out T>(val throwable: Throwable) : Result<T>()
}