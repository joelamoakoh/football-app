/**
 * Made by Joel Amoakoh
 * Student 20096482
 */
package controllers

import models.Football
import models.Score
import persistence.Serializer
class ScoresAPI (serializerType: Serializer) {
    private var serializer: Serializer = serializerType
    private var scores = ArrayList<Score>()

    fun input(score: Score): Boolean {
        return scores.add(score)
    }

    fun updateScore(indexToUpdate: Int, score: Score?): Boolean {
        val foundScore = findScore(indexToUpdate)


        if ((foundScore != null) && (score != null)) {
            foundScore.firstTeam = score.firstTeam
            foundScore.secondTeam = score.secondTeam
            foundScore.score1 = score.score1
            foundScore.score2 = score.score2
            return true


        }
        return false
    }

    fun findScore(index: Int): Score? {
        return if (ListIndex(index, scores)) {
            scores[index]
        } else null
    }

    fun ListIndex(index: Int, scores: ArrayList<Score>): Boolean {
        return (index >= 0 && index < scores.size)
    }
    @Throws(Exception::class)
    fun load() {
        scores = serializer.read() as ArrayList<Score>
    }

    @Throws(Exception::class)
    fun store() {
        serializer.write(scores)
    }
    fun listScores(): String =
        if (scores.isEmpty()) "No Scores  Stored"
        else formatListString(scores.filter { score: Score -> score.isScoreArchived  })



    private fun formatListString(teamsToFormat: List<Score>): String =
        teamsToFormat
            .joinToString(separator = "\n") { score ->
                scores.indexOf(score).toString() + ": " + score.toString()
            }
    fun deleteScore(scoresToDelete: Int): Score? {
        return if (ListIndex(scoresToDelete, scores)) {
            scores.removeAt(scoresToDelete)
        } else null
    }
    fun numberOfScores(): Int {
        return scores.size
    }
}




