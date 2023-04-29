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
    @Test
    fun `listTeamsBySelectedPriority returns No Teams when ArrayList is empty`() {
        assertEquals(0, emptyTeams!!.numberOfTeams())
        assertTrue(emptyTeams!!.listTeamsBySelectedPriority(1).lowercase().contains("no teams")
        )
    }

    @Test
    fun `listTeamsBySelectedPriority returns no teams when no teams of that priority exist`() {
        assertEquals(5, populatedTeams!!.numberOfTeams())
        val priority2String = populatedTeams!!.listTeamsBySelectedPriority(2).lowercase()
        assertTrue(priority2String.contains("no teams"))
        assertTrue(priority2String.contains("2"))
    }

    @Test
    fun `listTeamsBySelectedPriority returns all teams that match that priority when teams of that position exist`() {
        assertEquals(5, populatedTeams!!.numberOfTeams())
        val priority1String = populatedTeams!!.listTeamsBySelectedPriority(1).lowercase()
        assertTrue(priority1String.contains("1 team"))
        assertTrue(priority1String.contains("priority 1"))
        assertTrue(priority1String.contains("summer holiday"))
        assertFalse(priority1String.contains("swim"))
        assertFalse(priority1String.contains("learning kotlin"))
        assertFalse(priority1String.contains("code app"))
        assertFalse(priority1String.contains("test app"))


        val priority4String = populatedTeams!!.listTeamsBySelectedPriority(4).lowercase(Locale.getDefault())
        assertTrue(priority4String.contains("2 team"))
        assertTrue(priority4String.contains("priority 4"))
        assertFalse(priority4String.contains("swim"))
        assertTrue(priority4String.contains("code app"))
        assertTrue(priority4String.contains("test app"))
        assertFalse(priority4String.contains("learning kotlin"))
        assertFalse(priority4String.contains("summer holiday"))
    }
    @Nested
    inner class DeleteTeams {

        @Test
        fun `deleting a team that does not exist, returns null`() {
            assertNull(emptyTeams!!.deleteTeam(0))
            assertNull(populatedTeams!!.deleteTeam(-1))
            assertNull(populatedTeams!!.deleteTeam(5))
        }

        @Test
        fun `deleting a note that exists delete and returns deleted object`() {
            assertEquals(5, populatedTeams!!.numberOfTeams())
            assertEquals(swim, populatedTeams!!.deleteTeam(4))
            assertEquals(4, populatedTeams!!.numberOfTeams())
            assertEquals(learnKotlin, populatedTeams!!.deleteTeam(0))
            assertEquals(3, populatedTeams!!.numberOfTeams())
        }
    }
    @Nested
    inner class UpdateTeams{
        @Test
        fun `updating a team that does not exist returns false`(){
            assertFalse(populatedTeams!!.updateTeam(6, Football("Updating Note", 2, "Work", false)))
            assertFalse(populatedTeams!!.updateTeam(-1, Football("Updating Note", 2, "Work", false)))
            assertFalse(emptyTeams!!.updateTeam(0, Football("Updating Note", 2, "Work", false)))
        }

        @Test
        fun `updating a team that exists returns true and updates`() {
            assertEquals(swim, populatedTeams!!.findTeam(4))
            assertEquals("Swim - Pool", populatedTeams!!.findTeam(4)!!.teamName)
            assertEquals(3, populatedTeams!!.findTeam(4)!!.teamPosition)
            assertEquals("Hobby", populatedTeams!!.findTeam(4)!!.League)


            assertTrue(populatedTeams!!.updateTeam(4, Football("Updating Note", 2, "College", false)))
            assertEquals("Updating Note", populatedTeams!!.findTeam(4)!!.teamName)
            assertEquals(2, populatedTeams!!.findTeam(4)!!.teamPosition)
            assertEquals("College", populatedTeams!!.findTeam(4)!!.League)
        }
    }


}

