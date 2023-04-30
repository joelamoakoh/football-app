/**
 * Made by Joel Amoakoh
 * Student 20096482
 */
import controllers.FootballAPI
import controllers.ScoresAPI
import models.Football
import models.Score
import mu.KotlinLogging
import persistence.XMLSerializer
import utils.ScannerInput
import utils.ScannerInput.readNextInt
import utils.ScannerInput.readNextLine
import java.io.File
import java.lang.System.exit
import kotlin.system.exitProcess

private val footballAPI = FootballAPI(XMLSerializer(File("teams.xml")))
private val scoresAPI = ScoresAPI(XMLSerializer(File("scores.xml")))

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
         > |   4) Delete a Team             |
         > |   5) Archive a team            |
         > |   6) Search a team             |
         > |   7) Add Most Memorable Score  |
         > |   8) List Most Memorable Scores|
         > |   9) Delete a Score -.-        |
         > ----------------------------------
         > |   20) Save Teams               |
         > |   21) Load Teams               |
         > |   22) Save Scores              |
         > |   23) Load Scores              |
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
            5  -> archiveTeams()
            6  -> searchTeam()
            7  -> addScore()
            8  -> listScore()
            9  -> deleteScore()
            0  -> exitApp()
            20 -> save()
            21 -> load()
            22 -> save1()
            23 -> load1()
            else -> println("Invalid option entered: $option")
        }
    } while (true)
}


fun listTeams() {
    if (footballAPI.numberOfTeams() > 0) {
        val option = readNextInt(
            """
                  > --------------------------------
                  > |   1) View ALL Teams          |
                  > |   2) View ACTIVE Teams       |
                  > |   3) View ARCHIVED Teams     |
                  > --------------------------------
         > ==>> """.trimMargin(">"))

        when (option) {
            1 -> listAllTeams();
            2 -> listActiveTeams();
            3 -> listArchivedTeams();
            else -> println("Invalid option entered: " + option);
        }
    } else {
        println("Option Invalid - No notes stored");
    }
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
fun listArchivedTeams() {
    println(footballAPI.listArchivedTeams())
}
fun listAllTeams() {
    println(footballAPI.listAllTeams())
}


fun deleteTeams(){
    listTeams()
    if (footballAPI.numberOfTeams() > 0) {
        val indexToDelete = readNextInt("Enter the index of the team to delete: ")
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
fun save() {
    try {
        footballAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}

fun load() {
    try {
        footballAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}
fun listActiveTeams() {
    println(footballAPI.listActiveTeams())
}

fun archiveTeams() {
    listActiveTeams()
    if (footballAPI.numberOfTeams() > 0) {
        val indexToArchive = readNextInt("Enter the index of the note to archive: ")
        //pass the index of the note to NoteAPI for archiving and check for success.
        if (footballAPI.archiveTeam(indexToArchive)) {
            println("Archive Successful!")
        } else {
            println("Archive NOT Successful")
        }
    }
}
fun searchTeam(){
    val searchName = readNextLine("Enter the description of your search: ")
    val searchResult = footballAPI.searchByName(searchName)
    if(searchResult.isEmpty()){
        println("No teams found -.-")
    } else {
        println(searchResult)
    }
}
fun addScore(){

    val firstTeam = readNextLine("Enter First Team Name ")
    val secondTeam = readNextLine("Enter Second Team Name ")
    val score1 = readNextInt("Enter First Team Score")
    val score2 = readNextInt("Enter Second Team Score")
    val scoreAdded = scoresAPI.input(Score(firstTeam, secondTeam,score1, score2,false))

    if (scoreAdded) {
        println("Added Successfully")
    } else {
        println("Not Added")
    }
}
fun addTeams(){

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
fun save1() {
    try {
        scoresAPI.store()
    } catch (e: Exception) {
        System.err.println("Error writing to file: $e")
    }
}
fun load1() {
    try {
        scoresAPI.load()
    } catch (e: Exception) {
        System.err.println("Error reading from file: $e")
    }
}
fun listScore(){
println(scoresAPI.listScores())
}
fun deleteScore(){
    listScore()
    if (scoresAPI.numberOfScores() > 0){
        val scoresToDelete = readNextInt("Enter the index of the score to delete.... ")
        val scoreToDelete = scoresAPI.deleteScore (scoresToDelete)
        if (scoreToDelete != null){
            println("Delete Successful ! Deleted score: ${scoreToDelete.firstTeam}")
        }else{
            println("Delete couldn't happen")
        }
    }
}





