package server

import kotlinx.coroutines.*
import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.ServerSocket
import java.net.Socket
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit
import kotlin.system.exitProcess

const val PUERTO = 8080

fun main(): Unit = runBlocking {
    val pool = Executors.newFixedThreadPool(1)
    val server: ServerSocket
    var cliente: Socket
    var numCliente = 0
    println(" -- El servidor comienza a escuchar peticiones de conexión --")
    try {
        server = ServerSocket(PUERTO)
        while(true){
            numCliente++
            cliente = server.accept()
            println(" -- LLega el cliente: $numCliente --")

            val ps = DataOutputStream(cliente.getOutputStream())
            ps.writeUTF("{SERVER} -- Cliente número: $numCliente")
            do{
                val into = DataInputStream(cliente.getInputStream())
                if(into.available() != 0) {
                    print("$numCliente -> Mensaje: ")
                    println(into.readUTF())
                    println(" -- Mandando confirmación de mensaje recibido --")
                    ps.writeUTF("{SERVER} -- Mensaje recibido, enhorabuena crack --")
                }
            }while ((cliente.isConnected))
        }
    } catch (e: Exception) {
        println("Error al recibir la petición ${e.message}")
    }
}

/*
suspend fun disconnect():Boolean = coroutineScope {
    val response = async(Dispatchers.IO) {
        readln()
    }
    return@coroutineScope response.await() == "salir"
}*/
