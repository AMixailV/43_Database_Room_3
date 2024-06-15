package ru.mixail_akulov.a43_database_room_3.utils

import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock

/**
 * Загрузите значение только один раз и кэшируйте его.
 * Загрузчик @param выполняет загрузку значения (не должен генерировать исключения)
 */
class AsyncLoader<T>(
    private val loader: suspend () -> T,
) {

    // Пример реализации с использованием Mutex.
    // Вместо этого можно использовать async() и Deferred<T>.

    private val mutex = Mutex()
    private var value: T? = null

    suspend fun get(): T {
        mutex.withLock {
            if (value == null) {
                value = loader()
            }
        }
        return value!!
    }

}