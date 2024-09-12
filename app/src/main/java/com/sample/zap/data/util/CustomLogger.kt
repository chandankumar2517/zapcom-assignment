package com.sample.zap.data.util

import org.koin.core.logger.Level
import org.koin.core.logger.Logger

class CustomLogger : Logger(Level.DEBUG) {
    override fun log(level: Level, msg: String) {
        when (level) {
            Level.INFO -> println("INFO: $msg")
            Level.DEBUG -> println("DEBUG: $msg")
            Level.ERROR -> println("ERROR: $msg")
            else -> println("UNKNOWN: $msg")
        }
    }
}
