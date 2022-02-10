package com.example.testmvpmovies.utils

import android.view.View
import android.widget.TextView
import kotlinx.coroutines.*

/**
 * Пример использования во ViewModel:
 * delay(viewModelScope, durationInMillis = 400L, onRepeat = false) {
 * }
 *
 * Во Fragment:
 * delay(lifecycleScope, durationInMillis = 400L, onRepeat = false) {
 * }
 *
 * Брать scope у view:
 * view.findViewTreeLifecycleOwner()?.lifecycle?.coroutineScope?.let {
 * delay(it, durationInMillis = 400L, onRepeat = false) {
 * }
 * }
 */

fun delay(
    coroutineScope: CoroutineScope,
    durationInMillis: Long = 500L,
    dispatcher: CoroutineDispatcher = Dispatchers.Main,
    onRepeat: Boolean = true,
    block: () -> Unit
): Job = coroutineScope.launch(dispatcher) {

    //Вызов один раз после задержки
    if (!onRepeat) {
        delay(durationInMillis)
        block()
    }

    //Вызов повторяется с задержкой
    while (onRepeat) {
        delay(durationInMillis)
        block()
    }
}