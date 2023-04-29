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
}