package com.example.plugins
import io.ktor.network.sockets.*
import java.sql.Connection
import java.sql.DriverManager

fun main() {
    val url = "jdbc:postgresql://localhost:5432/postgres"
    val password = "password"
    val username = "postgres"
    Class.forName("org.postgresql.Driver")
    val connection = DriverManager.getConnection(url, username, password)

    val userName = readLine()!!
    val userAge = readLine()!!.toInt()
    connection
        .createStatement()
        .executeUpdate("insert into users values ('$userName',$userAge)")

    fun getString(connection: Connection): List<String> {
        val a = connection
            .createStatement()
            .executeQuery("SELECT * From users")
        var result = mutableListOf<String>()
        while (a.next()) {
            result.add(a.getString(1) + " " + a.getInt(2))
        }
        return result
    }
}