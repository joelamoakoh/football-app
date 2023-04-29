import utils.ScannerInput
import java.lang.System.exit


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
            1  -> addNote()
            2  -> listNotes()
            3  -> updateNote()
            4  -> deleteNote()
            0  -> exitApp()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}
fun addNote(){
    println("You chose Add Team")
}

fun listNotes(){
    println("You chose List Teams")
}

fun updateNote(){
    println("You chose Update a team")
}

fun deleteNote(){
    println("You chose Delete a team")
}

fun exitApp(){
    println("Ill see you lad")
    exit(0)
}
