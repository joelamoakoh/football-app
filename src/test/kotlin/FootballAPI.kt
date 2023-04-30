package controllers

import models.Football
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import persistence.XMLSerializer
import java.io.File
import java.util.*


class FootballAPITest {

    private var learnKotlin: Football? = null
    private var summerHoliday: Football? = null
    private var codeApp: Football? = null
    private var testApp: Football? = null
    private var swim: Football? = null
    private var populatedTeams: FootballAPI? = FootballAPI(XMLSerializer(File("teams.xml")))
    private var emptyTeams: FootballAPI? = FootballAPI(XMLSerializer(File("teams.xml")))

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
            assertTrue(emptyTeams!!.listAllTeams().lowercase().contains("no teams"))
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
        fun `listActive Teams returns no active teams stored when ArrayList is empty`() {
            assertEquals(0, emptyTeams!!.numberOfActiveTeams())
            assertTrue(
                emptyTeams!!.listActiveTeams().lowercase().contains("no active notes")
            )
        }

        @Test
        fun `listActive Teams returns active teams when ArrayList has active teams stored`() {
            assertEquals(3, populatedTeams!!.numberOfActiveTeams())
            val activeNotesString = populatedTeams!!.listActiveTeams().lowercase()
            assertTrue(activeNotesString.contains("learning kotlin"))
            assertFalse(activeNotesString.contains("code app"))
            assertTrue(activeNotesString.contains("summer holiday"))
            assertTrue(activeNotesString.contains("test app"))
            assertFalse(activeNotesString.contains("swim"))
        }

        @Test
        fun `listArchived Teams returns no archived teams when ArrayList is empty`() {
            assertEquals(0, emptyTeams!!.numberOfArchivedTeams())
            assertTrue(
                emptyTeams!!.listArchivedTeams().lowercase().contains("no archived teams")
            )
        }

        @Test
        fun `listArchived Teams returns archived teams when ArrayList has archived teams stored`() {
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

    @Nested
    inner class PersistenceTests {

        @Test
        fun `saving and loading an empty collection in XML doesn't crash app`() {

            val storingTeams = FootballAPI(XMLSerializer(File("teams.xml")))
            storingTeams.store()


            val loadedTeams = FootballAPI(XMLSerializer(File("teams.xml")))
            loadedTeams.load()


            assertEquals(0, storingTeams.numberOfTeams())
            assertEquals(0, loadedTeams.numberOfTeams())
            assertEquals(storingTeams.numberOfTeams(), loadedTeams.numberOfTeams())
        }

        @Test
        fun `saving and loading an loaded collection in XML doesn't loose data`() {
            val storingTeams = FootballAPI(XMLSerializer(File("teams.xml")))
            storingTeams.add(testApp!!)
            storingTeams.add(swim!!)
            storingTeams.add(summerHoliday!!)
            storingTeams.store()

            //Loading notes.xml into a different collection
            val loadedTeams = FootballAPI(XMLSerializer(File("teams.xml")))
            loadedTeams.load()

            //Comparing the source of the notes (storingNotes) with the XML loaded notes (loadedNotes)
            assertEquals(3, storingTeams.numberOfTeams())
            assertEquals(3, loadedTeams.numberOfTeams())
            assertEquals(storingTeams.numberOfTeams(), loadedTeams.numberOfTeams())
            assertEquals(storingTeams.findTeam(0), loadedTeams.findTeam(0))
            assertEquals(storingTeams.findTeam(1), loadedTeams.findTeam(1))
            assertEquals(storingTeams.findTeam(2), loadedTeams.findTeam(2))
        }
    }
    @Nested
    inner class ArchiveTeams {
        @Test
        fun `archiving a team that does not exist returns false`(){
            assertFalse(populatedTeams!!.archiveTeam(6))
            assertFalse(populatedTeams!!.archiveTeam(-1))
            assertFalse(emptyTeams!!.archiveTeam(0))
        }

        @Test
        fun `archiving an already archived TEAM returns false`(){
            assertTrue(populatedTeams!!.findTeam(2)!!.isTeamArchived)
            assertFalse(populatedTeams!!.archiveTeam(2))
        }

        @Test
        fun `archiving an active team that exists returns true and archives`() {
            assertFalse(populatedTeams!!.findTeam(1)!!.isTeamArchived)
            assertTrue(populatedTeams!!.archiveTeam(1))
            assertTrue(populatedTeams!!.findTeam(1)!!.isTeamArchived)
        }
    }
    @Nested
    inner class CountingMethods {

        @Test
        fun numberOfTeamsCalculatedCorrectly() {
            assertEquals(5, populatedTeams!!.numberOfTeams())
            assertEquals(0, emptyTeams!!.numberOfTeams())
        }

        @Test
        fun numberOfArchivedTeamsCalculatedCorrectly() {
            assertEquals(2, populatedTeams!!.numberOfArchivedTeams())
            assertEquals(0, emptyTeams!!.numberOfArchivedTeams())
        }

        @Test
        fun numberOfActiveTeamsCalculatedCorrectly() {
            assertEquals(3, populatedTeams!!.numberOfActiveTeams())
            assertEquals(0, emptyTeams!!.numberOfActiveTeams())
        }

        @Test
        fun numberOfTeamsByPriorityCalculatedCorrectly() {
            assertEquals(1, populatedTeams!!.numberOfTeamsByPriority(1))
            assertEquals(0, populatedTeams!!.numberOfTeamsByPriority(2))
            assertEquals(1, populatedTeams!!.numberOfTeamsByPriority(3))
            assertEquals(2, populatedTeams!!.numberOfTeamsByPriority(4))
            assertEquals(1, populatedTeams!!.numberOfTeamsByPriority(5))
            assertEquals(0, emptyTeams!!.numberOfTeamsByPriority(1))
        }
    }
    @Nested
    inner class SearchMethods {

        @Test
        fun `search teams by title returns no teams when no teams with that title exist`() {

            assertEquals(5, populatedTeams!!.numberOfTeams())
            val searchResults = populatedTeams!!.searchByName("no results expected")
            assertTrue(searchResults.isEmpty())


            assertEquals(0, emptyTeams!!.numberOfTeams())
            assertTrue(emptyTeams!!.searchByName("").isEmpty())
        }

        @Test
        fun `search notes by title returns notes when teams with that title exist`() {
            assertEquals(5, populatedTeams!!.numberOfTeams())

            var searchResults = populatedTeams!!.searchByName("Code App")
            assertTrue(searchResults.contains("Code App"))
            assertFalse(searchResults.contains("Test App"))


            searchResults = populatedTeams!!.searchByName("App")
            assertTrue(searchResults.contains("Code App"))
            assertTrue(searchResults.contains("Test App"))
            assertFalse(searchResults.contains("Swim - Pool"))


            searchResults = populatedTeams!!.searchByName("aPp")
            assertTrue(searchResults.contains("Code App"))
            assertTrue(searchResults.contains("Test App"))
            assertFalse(searchResults.contains("Swim - Pool"))
        }
    }

}

