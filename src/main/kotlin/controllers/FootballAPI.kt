package controllers

import models.Football

class FootballAPI {
    private var footballs = ArrayList<Football>()

    fun add(football: Football): Boolean {
        return footballs.add(football)
    }

    fun listAllTeams(): String {
        return if (footballs.isEmpty()) {
            "No notes stored"
        } else {
            var listOfTeams = ""
            for (i in footballs.indices) {
                listOfTeams += "${i}: ${footballs[i]} \n"
            }
            listOfTeams
        }
    }
    fun numberOfTeams(): Int {
        return footballs.size
    }
    fun findTeam(index: Int): Football?{
        return if (isValidListIndex(index, footballs)){
            footballs[index]
        }
        else null
    }
    private fun  isValidListIndex(index: Int, list : List <Any>): Boolean{
        return (index >= 0 && index < list.size)
    }
    fun listActiveTeams(): String {
        return if (numberOfActiveTeams() == 0) {
            "No active Teams stored"
        } else {
            var listOfActiveFootballs = ""
            for (football  in footballs) {
                if (!football.isTeamArchived) {
                    listOfActiveFootballs += "${footballs.indexOf(football)}: $football \n"
                }
            }
            listOfActiveFootballs
        }
    }

    fun listArchivedTeams(): String {
        return if (numberOfArchivedTeams() == 0) {
            "No archived teams stored"
        } else {
            var listOfArchivedTeams = ""
            for (football in footballs) {
                if (football.isTeamArchived) {
                    listOfArchivedTeams += "${footballs.indexOf(football)}: $football \n"
                }
            }
            listOfArchivedTeams
        }
    }

    fun numberOfArchivedTeams(): Int {
        var counter = 0
        for (football in footballs) {
            if (football.isTeamArchived) {
                counter++
            }
        }
        return counter
    }

    fun numberOfActiveTeams(): Int {
        var counter = 0
        for (football in footballs) {
            if (!football.isTeamArchived) {
                counter++
            }
        }
        return counter
    }

}