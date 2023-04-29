import java.lang.System.exit
import java.util.*

val scanner = Scanner(System.`in`)

fun main(args: Array<String>) {
    runMenu()
}
fun mainMenu() : Int {
    print(""" 
         > ----------------------------------
         >       Football App      
         > ----------------------------------
         > \\    NOTE MENU            //
         >  \\   1) Add A Team       //
         >   \\  2) League          //                  
         > |  \\ 3) Update A Team  //           
         > |   \\4) Delete a Team //            
         > ----------------------------------
         > |  \\  0) Exit    //                  |
         > ----------------------------------
         > ==>> """.trimMargin(">"))
    return scanner.nextInt()
}
fun runMenu() {
    do {
        val option = mainMenu()
        when (option) {
            1  -> addNote()
            2  -> listNotes()
            3  -> updateNote()
            4  -> deleteNote()
            0  -> exitApp()
            else -> println("Invalid option entered: " + option)
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
    println("Exiting...bye")
    exit(0)
}
