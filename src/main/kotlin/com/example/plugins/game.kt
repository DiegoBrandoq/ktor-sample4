package com.example.plugins

import java.sql.Connection
import java.sql.DriverManager

val players = listOf(
    "ziga"
).shuffled()

fun main() {
    players.forEach {
        println("очередь $it")
        var win = 0
        var trys1 = 0
        var a = (1..100).random()
        println("какое число я загадал???")
        var input = readLine()!!.toInt()
        while (input != a) {
            if (input > a) {
                println("моё число меньше")
            } else {
                println("моё число больше")
            }
            trys1 = trys1 + 1
            input = readLine()!!.toInt()
        }
        win = win + 1
        println("ты угадал ,получаешь + $win к своей статистике")

        println("ты потратил попыток $trys1")
        val url = "jdbc:postgresql://localhost:5432/postgres"
        val password = "password"
        val username = "postgres"
        Class.forName("org.postgresql.Driver")
        val connection = DriverManager.getConnection(url, username, password)
        players.forEach {
            connection
                .createStatement()
                .executeUpdate("insert into game values ('$it',$trys1,$win)")

            var t = readLine()!!
            var g = winrate(connection,t)
            var v = g.size / g.sumOf { it }.toFloat() * 100
            println(v)
            connection
                .createStatement()
                .executeUpdate("insert into winrate values ('$it',$v)")

        }
    }
}

fun winrate(connection: Connection, t : String): List<Int> {
    val a = connection
        .createStatement()
        .executeQuery("Select trys from game where name ='$t'  ")
    var result = mutableListOf<Int>()
    while (a.next()) {
        result.add(a.getInt(1))

    }
    return result

}
