package controllers

import models.Football
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import java.util.*


class FootballAPITest {

    private var learnKotlin: Football? = null
    private var summerHoliday: Football? = null
    private var codeApp: Football? = null
    private var testApp: Football? = null
    private var swim: Football? = null
    private var populatedTeams: FootballAPI? = FootballAPI()
    private var emptyTeams: FootballAPI? = FootballAPI()

    @BeforeEach
    fun setup() {
        learnKotlin = Football("Learning Kotlin", 5, "College", false)
        summerHoliday = Football("Summer Holiday to France", 1, "Holiday", false)
        codeApp = Football("Code App", 4, "Work", true)
        testApp = Football("Test App", 4, "Work", false)
        swim = Football("Swim - Pool", 3, "Hobby", true)

        //adding 5 teams to api
        populatedTeams!!.add(learnKotlin!!)
        populatedTeams!!.add(summerHoliday!!)
        populatedTeams!!.add(codeApp!!)
        populatedTeams!!.add(testApp!!)
        populatedTeams!!.add(swim!!)
    }

    @AfterEach
    fun tearDown() {
        learnKotlin = null
        summerHoliday = null
        codeApp = null
        testApp = null
        swim = null
        populatedTeams = null
        emptyTeams = null
    }

    @Nested
    inner class AddTeams {
        @Test
        fun `adding a Football Team to an Arraylist`() {
            val newTeam = Football("Study Lambdas", 1, "College", false)
            assertEquals(5, populatedTeams!!.numberOfTeams())
            assertTrue(populatedTeams!!.add(newTeam))
            assertEquals(6, populatedTeams!!.numberOfTeams())
            assertEquals(newTeam, populatedTeams!!.findTeam(populatedTeams!!.numberOfTeams() - 1))
        }

        @Test
        fun `adding a Team to an empty list adds to ArrayList`() {
            val newTeam = Football("Study Lambdas", 1, "College", false)
            assertEquals(0, emptyTeams!!.numberOfTeams())
            assertTrue(emptyTeams!!.add(newTeam))
            assertEquals(1, emptyTeams!!.numberOfTeams())
            assertEquals(newTeam, emptyTeams!!.findTeam(emptyTeams!!.numberOfTeams() - 1))
        }
    }

    @Nested
    inner class ListTeams {
        @Test
        fun `ListAllTeams returns no teams message if arraylist has nothing`() {
            assertEquals(0, emptyTeams!!.numberOfTeams())
            assertFalse(emptyTeams!!.listAllTeams().lowercase().contains("no teams"))
        }

        @Test
        fun `listAllTeams returns Teams when ArrayList has teams stored`() {
            assertEquals(5, populatedTeams!!.numberOfTeams())
            val footballsString = populatedTeams!!.listAllTeams().lowercase()
            assertTrue(footballsString.contains("learning kotlin"))
            assertTrue(footballsString.contains("code app"))
            assertTrue(footballsString.contains("test app"))
            assertTrue(footballsString.contains("swim"))
            assertTrue(footballsString.contains("summer holiday"))
        }


        @Test
        fun `listActiveTeams returns no active teams stored when ArrayList is empty`() {
            assertEquals(0, emptyTeams!!.numberOfActiveTeams())
            assertFalse(
                emptyTeams!!.listActiveTeams().lowercase().contains("no active notes")
            )
        }

        @Test
        fun `listActiveTeams returns active teams when ArrayList has active teams stored`() {
            assertEquals(3, populatedTeams!!.numberOfActiveTeams())
            val activeNotesString = populatedTeams!!.listActiveTeams().lowercase()
            assertTrue(activeNotesString.contains("learning kotlin"))
            assertFalse(activeNotesString.contains("code app"))
            assertTrue(activeNotesString.contains("summer holiday"))
            assertTrue(activeNotesString.contains("test app"))
            assertFalse(activeNotesString.contains("swim"))
        }

        @Test
        fun `listArchivedTeamsreturns no archived teams when ArrayList is empty`() {
            assertEquals(0, emptyTeams!!.numberOfArchivedTeams())
            assertTrue(
                emptyTeams!!.listArchivedTeams().lowercase().contains("no archived teams")
            )
        }

        @Test
        fun `listArchivedTeams returns archived teams when ArrayList has archived teams stored`() {
            assertEquals(2, populatedTeams!!.numberOfArchivedTeams())
            val archivedNotesString = populatedTeams!!.listArchivedTeams().lowercase(Locale.getDefault())
            assertFalse(archivedNotesString.contains("learning kotlin"))
            assertTrue(archivedNotesString.contains("code app"))
            assertFalse(archivedNotesString.contains("summer holiday"))
            assertFalse(archivedNotesString.contains("test app"))
            assertTrue(archivedNotesString.contains("swim"))
        }

    }
}

