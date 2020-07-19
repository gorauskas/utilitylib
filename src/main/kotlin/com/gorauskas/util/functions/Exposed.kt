package com.gorauskas.util.functions

import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.transactions.transaction

fun <T> loggedTransaction(statement: Transaction.() -> T) = transaction {
    addLogger(StdOutSqlLogger)
    statement()
}