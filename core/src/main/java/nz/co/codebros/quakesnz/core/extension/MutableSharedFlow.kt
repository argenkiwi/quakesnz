package nz.co.codebros.quakesnz.core.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

fun <E> MutableSharedFlow<E>.reactIn(coroutineScope: CoroutineScope, react: suspend (E) -> E?) {
    coroutineScope.launch {
        mapNotNull(react)
            .collect { launch { emit(it) } }
    }
}

fun <E, S> MutableSharedFlow<E>.reactIn(
    coroutineScope: CoroutineScope,
    stateFlow: StateFlow<S>,
    react: suspend (E, S) -> E?
) {
    coroutineScope.launch {
        combine(stateFlow, react)
            .mapNotNull { it }
            .collect { launch { emit(it) } }
    }
}
