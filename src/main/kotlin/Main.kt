import controllers.FootballAPI
import models.Football
import mu.KotlinLogging
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.lang.System.exit
import kotlin.system.exitProcess

private val footballAPI = FootballAPI()

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
    // logger.info { "addTeams() function invoked" }
    val teamName = readNextLine("Enter Team Name ")
    val teamPosition = readNextInt("Enter League Position")
    val League = readNextLine("Enter League")
    val isAdded = footballAPI.add(Football(teamName, teamPosition,League, false))

    if (isAdded) {
        println("Added Successfully")
    } else {
        println("Not Added")
    }
}

fun listTeams(){
    println(footballAPI.listAllTeams())
}

fun updateTeams() {
    listTeams()
    if (footballAPI.numberOfTeams() > 0) {
        //only ask the user to choose the note if notes exist
        val indexToUpdate = readNextInt("Enter the index of the team to update: ")
        if (footballAPI.isValidIndex(indexToUpdate)) {
            val teamName = readNextLine("Enter team Name: ")
            val teamPosition= readNextInt("Enter league position")
            val League = readNextLine("Enter League")

            if (footballAPI.updateTeam(indexToUpdate, Football(teamName, teamPosition, League ,false))){
                println("Update Successful")
            } else {
                println("Update Failed")
            }
        } else {
            println("There are no notes for this index number")
        }
    }
}


fun deleteTeams(){
    listTeams()
    if (footballAPI.numberOfTeams() > 0) {
        //only ask the user to choose the note to delete if notes exist
        val indexToDelete = readNextInt("Enter the index of the team to delete: ")
        //pass the index of the note to NoteAPI for deleting and check for success.
        val teamToDelete = footballAPI.deleteTeam(indexToDelete)
        if (teamToDelete != null) {
            println("Delete Successful! Deleted Team: ${teamToDelete.teamName}")
        } else {
            println("Delete NOT Successful")
        }
    }
}


fun exitApp(){
    println("Ill see you lad")
    exitProcess(0)
}
