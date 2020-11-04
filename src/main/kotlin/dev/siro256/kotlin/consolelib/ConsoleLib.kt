package dev.siro256.kotlin.consolelib

import kotlinx.coroutines.*
import java.util.*

object ConsoleLib {
    private val coroutine = CoroutineScope(Dispatchers.IO)
    private var initialized = false

    fun initialize() {
        if (initialized) return
        coroutine.launch {
            while (true) {
                print(">")
                val input = Scanner(System.`in`).nextLine()
                if (input != "") coroutine.launch { ConsoleInputEvent(input).call() }
            }
        }
    }

    fun println(message: String) {
        coroutine.launch {
            kotlin.io.print("\u001B[2K]\u001B[0G$message\n>")
        }
    }
}