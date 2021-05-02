package dev.siro256.kotlin.consolelib

import kotlinx.coroutines.*
import java.util.*

/**
 * ライブラリの基本クラス  使用できる関数が全てここにある
 *
 * @author Siro_256
 * @since 1.0.0
 */

@Suppress("ReplaceJavaStaticMethodWithKotlinAnalog", "unused")
object Console {
    /**
     * 処理を実行する[CoroutineScope]
     *
     * @author Siro_256
     * @since 1.0.0
     */

    private val coroutine = CoroutineScope(Dispatchers.IO)

    /**
     * 入力を受け付けるCoroutine
     *
     * @author Siro_256
     * @since 1.1.0
     */

    private var inputCoroutine: Job? = null

    /**
     * すでに初期化されているか否かを判定するための変数
     *
     * @author Siro_256
     * @since 1.0.0
     */

    private var initialized = false

    /**
     * 入力待ちの時に表示される文字列  デフォルトは「>」
     *
     * @author Siro_256
     * @since 1.0.0
     */

    var prefix = ">"

    /**
     * 初期化する関数  プログラムの最初に実行する必要がある
     *
     * @author Siro_256
     * @since 1.0.0
     */

    fun initialize() {
        if (initialized) return
        initialized = true
        inputCoroutine = coroutine.launch {
            while (true) {
                if (!this.isActive) break
                System.out.print(prefix)
                val input = Scanner(System.`in`).nextLine()
                if (input != "") coroutine.launch { ConsoleInputEvent(input).call() }
            }
        }
    }

    /**
     * 改行を出力する
     *
     * @author Siro_256
     * @since 1.0.0
     */

    fun println() {
        coroutine.launch {
            System.out.println()
        }
    }

    /**
     * [Boolean]値を出力し、改行する
     * @param message [Boolean]型のメッセージ
     *
     * @author Siro_256
     * @since 1.0.0
     */

    fun println(message: Boolean) {
        coroutine.launch {
            System.out.print("\u001B[2K]\u001B[0G$message\n$prefix")
        }
    }

    /**
     * [Char]値を出力し、改行する
     * @param message [Char]型のメッセージ
     *
     * @author Siro_256
     * @since 1.0.0
     */

    fun println(message: Char) {
        coroutine.launch {
            System.out.print("\u001B[2K]\u001B[0G$message\n$prefix")
        }
    }

    /**
     * [Int]値を出力し、改行する
     * @param message [Int]型のメッセージ
     *
     * @author Siro_256
     * @since 1.0.0
     */

    fun println(message: Int) {
        coroutine.launch {
            System.out.print("\u001B[2K]\u001B[0G$message\n$prefix")
        }
    }

    /**
     * [Long]値を出力し、改行する
     * @param message [Long]型のメッセージ
     *
     * @author Siro_256
     * @since 1.0.0
     */

    fun println(message: Long) {
        coroutine.launch {
            System.out.print("\u001B[2K]\u001B[0G$message\n$prefix")
        }
    }

    /**
     * [Float]値を出力し、改行する
     * @param message [Float]型のメッセージ
     *
     * @author Siro_256
     * @since 1.0.0
     */

    fun println(message: Float) {
        coroutine.launch {
            System.out.print("\u001B[2K]\u001B[0G$message\n$prefix")
        }
    }

    /**
     * [Double]値を出力し、改行する
     * @param message [Double]型のメッセージ
     *
     * @author Siro_256
     * @since 1.0.0
     */

    fun println(message: Double) {
        coroutine.launch {
            System.out.print("\u001B[2K]\u001B[0G$message\n$prefix")
        }
    }

    /**
     * [CharArray]値を出力し、改行する
     * @param message [CharArray]型のメッセージ
     *
     * @author Siro_256
     * @since 1.0.0
     */

    fun println(message: CharArray) {
        coroutine.launch {
            System.out.print("\u001B[2K]\u001B[0G$message\n$prefix")
        }
    }

    /**
     * [String]値を出力し、改行する
     * @param message [String]型のメッセージ
     *
     * @author Siro_256
     * @since 1.0.0
     */

    fun println(message: String) {
        coroutine.launch {
            System.out.print("\u001B[2K]\u001B[0G$message\n$prefix")
        }
    }

    /**
     * [Any]値を出力し、改行する
     * @param message [Any]型のメッセージ
     *
     * @author Siro_256
     * @since 1.0.0
     */

    fun println(message: Any) {
        coroutine.launch {
            System.out.print("\u001B[2K]\u001B[0G$message\n$prefix")
        }
    }

    /**
     * 通常のコンソールの処理に割り込み、入力した値を取得する
     * @return 入力された値
     * 
     * @author Siro_256
     * @since 1.1.0
     */

    fun readLine(): String? {
        System.out.println("TestMessage1")
        var input: String?

        runBlocking {
            inputCoroutine?.cancelAndJoin()
            System.out.println("TestMessage2")
            input = Scanner(System.`in`).nextLine()
            System.out.println(prefix)
            inputCoroutine?.start()
        }
        System.out.println("TestMessage3")
        return input
    }
}