package verifier

import kon.foo.model.PlayerCount
import kon.foo.verifier.SetCounts
import org.junit.jupiter.api.Test
import kon.foo.verifier.getPlayerCountRules

import org.junit.jupiter.api.Assertions

internal class VerifierKtTest {

    @Test
    fun verify() {
    }

    @Test
    fun getPlayerCountRulesTest() {
        Assertions.assertEquals(SetCounts(3, 1, 1), getPlayerCountRules(PlayerCount.SOLO))
        Assertions.assertEquals(SetCounts(3, 1, 1), getPlayerCountRules(PlayerCount.ADVANCED))
        Assertions.assertEquals(SetCounts(5, 2, 1), getPlayerCountRules(PlayerCount.TWO))
        Assertions.assertEquals(SetCounts(5, 3, 1), getPlayerCountRules(PlayerCount.THREE))
        Assertions.assertEquals(SetCounts(5, 3, 2), getPlayerCountRules(PlayerCount.FOUR))
        Assertions.assertEquals(SetCounts(6, 4, 2), getPlayerCountRules(PlayerCount.FIVE))
    }
}