package nz.co.codebros.quakesnz.interactor

/**
 * Created by Leandro on 23/11/2017.
 */
sealed class Result<out T> {
    data class Success<T>(val value: T) : Result<T>()
    data class Failure<T>(val throwable: Throwable) : Result<T>()
}