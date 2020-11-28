package dev.siro256.kotlin.consolelib

import dev.siro256.kotlin.eventlib.Event

/**
 * コンソールに文字が入力された場合に発火するイベント
 *
 * @author Siro_256
 * @since 1.0.0
 */

class ConsoleInputEvent(val input: String): Event()