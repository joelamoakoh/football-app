import mu.KotlinLogging
import utils.ScannerInput
import java.lang.System.exit
import kotlin.system.exitProcess

private val logger = KotlinLogging.logger {}

fun main(args: Array<String>) {
    runMenu()
}
fun mainMenu() : Int {
    return ScannerInput.readNextInt(""" 
         > ----------------------------------
         > |        Football App         |
         > ----------------------------------
         > | English Teams                  |
         > |   1) Add a Team                |
         > |   2) List all English Teams    |
         > |   3) Update a Team             |
         > |   4) Delete a Team            |
         > ----------------------------------
         > |   0) Exit                      |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
}

fun runMenu() {
    do {
        when (val option = mainMenu()) {
            1  -> addTeams()
            2  -> listTeams()
            3  -> updateTeams()
            4  -> deleteTeams()
            0  -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}
fun addTeams(){
    logger.info { "addTeams() function invoked" }

}

fun listTeams(){
    logger.info { "listTeams() function invoked" }

}

fun updateTeams(){
    logger.info { "updateTeams() function invoked" }

}

fun deleteTeams(){
    logger.info { "deleteTeams() function invoked" }

}

fun exitApp(){
    println("Ill see you lad")
    exitProcess(0)
}
