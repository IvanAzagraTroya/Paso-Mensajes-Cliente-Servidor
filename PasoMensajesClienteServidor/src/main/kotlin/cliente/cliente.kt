package cliente

import java.io.DataInputStream
import java.io.DataOutputStream
import java.net.InetAddress
import java.net.Socket
import kotlin.system.exitProcess

const val PUERTO = 8080

fun main(){
    val direccion: InetAddress
    val server: Socket

    println("Intento conectarme al servidor")
    try {
        direccion = InetAddress.getLocalHost()
        server = Socket(direccion, PUERTO)

        println("Conexión realizada con éxito")

        val datos = DataInputStream(server.getInputStream())
        println(datos.readUTF())

        while(true) {
            val mensaje = DataOutputStream(server.getOutputStream())
            print("Escriba su mensaje:")
            val texto = readln()
            mensaje.writeUTF(texto)

            if(datos.available() != 0){
                println(datos.readUTF())
            }
            if(texto == "salir") {
                break
            }
            if(server.isClosed) exitProcess(0)
        }

        server.close()
        println("Voy a desconectarme ya")
    } catch (e: Exception) {
        println("Error al intentar conextarse al servidor: ${e.message}")
    }
}