package nz.co.codebros.quakesnz.core.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch

fun <S, E> MutableStateFlow<S>.reduceIn(
    coroutineScope: CoroutineScope,
    sharedFlow: SharedFlow<E>,
    reduce: suspend (S, E) -> S
) {
    coroutineScope.launch { combine(sharedFlow, reduce).collect(::emit) }
}
