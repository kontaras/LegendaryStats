package games.lmdbg.rules.model

import games.lmdbg.rules.PlayBuilder
import games.lmdbg.rules.set.core.Boards
import kotlin.test.Test
import kotlin.test.assertEquals

internal class ModelsTest {

    /** Test if a play is not changed by being encoded and decoded. */
    @Test
    fun encodeDecodeTest() {
        val play = PlayBuilder().build()
        val encoded = Play.playToString(play)
        assertEquals(play, Play.playFromString(encoded))
    }

    /** Test to make sure that the existing format still works. */
    @Test
    fun regressionStringsTest() {
        val play = "{\n" +
                "  \"scheme\": 108,\n" +
                "  \"mastermind\": 109,\n" +
                "  \"supports\": [101],\n" +
                "  \"starters\": {101: 3},\n" +
                "  \"heroes\": [\n" +
                "    101,\n" +
                "    102,\n" +
                "    103,\n" +
                "    105,\n" +
                "    108\n" +
                "  ],\n" +
                "  \"villains\": [\n" +
                "    107,\n" +
                "    108,\n" +
                "    106\n" +
                "  ],\n" +
                "  \"henchmen\": [\n" +
                "    101\n" +
                "  ],\n" +
                "  \"outcome\": \"WIN_DEFEAT_MASTERMIND\",\n" +
                "  \"players\": \"THREE\"\n" +
                "  \"board\": \"101\"\n" +
                "}"

        assertEquals(
            Play(
                Outcome.WIN_DEFEAT_MASTERMIND,
                PlayerCount.THREE,
                108,
                109,
                mutableSetOf(101, 102, 105, 103, 108),
                mutableSetOf(106, 107, 108),
                mutableSetOf(101),
                mutableSetOf(101),
                mutableMapOf(101 to 3),
                Boards.HQ.id
            ), Play.playFromString(play)
        )
    }
}