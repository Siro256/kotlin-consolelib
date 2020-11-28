package dev.siro256.kotlin.consolelib

import dev.siro256.kotlin.eventlib.Event

/**
 * コンソールに文字が入力された場合に発火するイベント
 */

class ConsoleInputEvent(val input: String): Event()