/**
 * Made by Joel Amoakoh
 * Student 20096482
 */
package controllers

import models.Football
import persistence.Serializer

class FootballAPI(serializerType: Serializer) {
    private var serializer:Serializer = serializerType
    private var footballs = ArrayList<Football>()

    fun add(football: Football): Boolean {
        return footballs.add(football)
    }

    fun listAllTeams(): String =
        if (footballs.isEmpty()) "No Teams Stored"
    else formatListString(footballs.filter { football: Football -> football.isTeamArchived  })
    fun numberOfTeams(): Int {
        return footballs.size
    }
    fun findTeam(index: Int): Football?{
        return if (isValidListIndex(index, footballs)){
            footballs[index]
        }
        else null
    }
    fun  isValidListIndex(index: Int, list : List <Any>): Boolean{
        return (index >= 0 && index < list.size)
    }
    fun listActiveTeams(): String =
        if (footballs.isEmpty()) "No teams Stored"
    else formatListString(footballs.filter { football: Football -> !football.isTeamArchived  })



    fun listArchivedTeams(): String =
        if(numberOfArchivedTeams() == 0) "No archived teams stored"
    else formatListString(footballs.filter {football: Football -> football.isTeamArchived })

    fun numberOfArchivedTeams(): Int = footballs.count{ football: Football ->football.isTeamArchived  }



    fun numberOfActiveTeams() {
        footballs.stream()
            .filter { footballs: Football -> !footballs.isTeamArchived}
            .count()
            .toInt()
    }


    fun listTeamsBySelectedPriority(priority: Int): String =
        if (footballs.isEmpty()) "No Teams Stored"
       else {
           val listOfTeams = formatListString(footballs.filter { football -> football.teamPosition== priority  })
            if (listOfTeams.equals("")) "No teams with priority: $priority"
            else "${numberOfTeamsByPriority(priority)} teams with priority $priority: $listOfTeams"
        }

    fun numberOfTeamsByPriority(priority: Int): Int = footballs.count {f: Football -> f.teamPosition == priority}
    fun deleteTeam(indexToDelete: Int): Football? {
        return if (isValidListIndex(indexToDelete, footballs)) {
            footballs.removeAt(indexToDelete)
        } else null
    }
    fun updateTeam(indexToUpdate: Int, football:Football?): Boolean {
        val foundTeam = findTeam(indexToUpdate)


        if ((foundTeam != null) && (football != null)) {
            foundTeam.teamName = football.teamName
            foundTeam.teamPosition = football.teamPosition
            foundTeam.League = football.League
            return true
        }


        return false
    }

    fun isValidIndex(index: Int) :Boolean{
        return isValidListIndex(index, footballs);
    }
    @Throws(Exception::class)
    fun load() {
        footballs = serializer.read() as ArrayList<Football>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(footballs)
    }
    fun archiveTeam(indexToArchive: Int): Boolean {
        if (isValidIndex(indexToArchive)) {
            val teamToArchive = footballs[indexToArchive]
            if (!teamToArchive.isTeamArchived) {
                teamToArchive.isTeamArchived = true
                return true
            }
        }
        return false
    }

    fun searchByName(searchString : String) =
        formatListString(footballs.filter { football -> football.teamName.contains(searchString, ignoreCase = true)})


    private fun formatListString(teamsToFormat: List<Football>): String =
        teamsToFormat
            .joinToString(separator = "\n") { football ->
                footballs.indexOf(football).toString() + ": " + football.toString()
            }



}