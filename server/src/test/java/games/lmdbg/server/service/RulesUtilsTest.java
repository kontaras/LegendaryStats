package games.lmdbg.server.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import games.lmdbg.rules.model.Outcome;
import games.lmdbg.rules.model.PlayerCount;
import games.lmdbg.rules.set.core.Boards;
import games.lmdbg.rules.set.core.Masterminds;
import games.lmdbg.rules.set.core.Schemes;
import games.lmdbg.rules.verifier.CardSetType;
import games.lmdbg.rules.verifier.MissingRecruitSupport;
import games.lmdbg.rules.verifier.PrintableError;
import games.lmdbg.rules.verifier.WrongSetCount;


import org.junit.jupiter.api.Assertions;

class RulesUtilsTest {
    @Test
    void testServerToRules() {
        final String outcome = Outcome.DRAW_DECK.toString();
        final String playerCount = PlayerCount.TWO.toString();
        final int schemeId = 1;
        final int mastermindId = 2;
        final int heroId1 = 3;
        final int heroId2 = 4;
        final int villainId1 = 5;
        final int villainId2 = 6;
        final int henchmanId1 = 7;
        final int henchmanId2 = 8;
        final int supportId1 = 9;
        final int supportId2 = 10;
        final int starterId1 = 11;
        final int starterQuantity1 = 12;
        final int starterId2 = 13;
        final int starterQuantity2 = 14;
        final int boardId = 15;
        
        games.lmdbg.server.model.Play serverPlay = Mockito.mock(games.lmdbg.server.model.Play.class);
        Mockito.when(serverPlay.getOutcome()).thenReturn(outcome);
        Mockito.when(serverPlay.getPlayers()).thenReturn(playerCount);
        Mockito.when(serverPlay.getScheme()).thenReturn(schemeId);
        Mockito.when(serverPlay.getMastermind()).thenReturn(mastermindId);
        Mockito.when(serverPlay.getHeroes()).thenReturn(Set.of(heroId1, heroId2));
        Mockito.when(serverPlay.getVillains()).thenReturn(Set.of(villainId1, villainId2));
        Mockito.when(serverPlay.getHenchmen()).thenReturn(Set.of(henchmanId1, henchmanId2));
        Mockito.when(serverPlay.getSupports()).thenReturn(Set.of(supportId1, supportId2));

        games.lmdbg.server.model.StarterPlay playStarter1 = Mockito.mock(games.lmdbg.server.model.StarterPlay.class);
        Mockito.when(playStarter1.getStarter()).thenReturn(starterId1);
        Mockito.when(playStarter1.getQuantity()).thenReturn(starterQuantity1);
        games.lmdbg.server.model.StarterPlay playStarter2 = Mockito.mock(games.lmdbg.server.model.StarterPlay.class);
        Mockito.when(playStarter2.getStarter()).thenReturn(starterId2);
        Mockito.when(playStarter2.getQuantity()).thenReturn(starterQuantity2);
        Mockito.when(serverPlay.getStarters()).thenReturn(Set.of(playStarter1, playStarter2));
        
        Mockito.when(serverPlay.getBoard()).thenReturn(boardId);

        games.lmdbg.rules.model.Play rulesPlay = RulesUtils.serverToRules(serverPlay);

        Assertions.assertEquals(rulesPlay.getOutcome().toString(), outcome);
        Assertions.assertEquals(rulesPlay.getPlayers().toString(), playerCount);
        Assertions.assertEquals(rulesPlay.getScheme(), schemeId);
        Assertions.assertEquals(rulesPlay.getMastermind(), mastermindId);

        Assertions.assertEquals(2, rulesPlay.getHeroes().size());
        Assertions.assertTrue(rulesPlay.getHeroes().contains(heroId1));
        Assertions.assertTrue(rulesPlay.getHeroes().contains(heroId2));

        Assertions.assertEquals(2, rulesPlay.getVillains().size());
        Assertions.assertTrue(rulesPlay.getVillains().contains(villainId1));
        Assertions.assertTrue(rulesPlay.getVillains().contains(villainId2));

        Assertions.assertEquals(2, rulesPlay.getHenchmen().size());
        Assertions.assertTrue(rulesPlay.getHenchmen().contains(henchmanId1));
        Assertions.assertTrue(rulesPlay.getHenchmen().contains(henchmanId2));

        Assertions.assertEquals(2, rulesPlay.getSupports().size());
        Assertions.assertTrue(rulesPlay.getSupports().contains(supportId1));
        Assertions.assertTrue(rulesPlay.getSupports().contains(supportId2));

        Assertions.assertEquals(2, rulesPlay.getStarters().size());
        Assertions.assertEquals(rulesPlay.getStarters().get(starterId1), starterQuantity1);
        Assertions.assertEquals(rulesPlay.getStarters().get(starterId2), starterQuantity2);

        Assertions.assertEquals(rulesPlay.getBoard(), boardId);
    }

    @Test
    void testVerify() {
        final String outcome = Outcome.DRAW_DECK.toString();
        final String playerCount = PlayerCount.SOLO.toString();
        final int schemeId = Schemes.INSTANCE.getPORTALS_TO_THE_DARK_DIMENSION().getId();
        final int mastermindId = Masterminds.INSTANCE.getDR_DOOM().getId();
        final int boardId = Boards.INSTANCE.getHQ().getId();
        
        games.lmdbg.server.model.Play serverPlay = Mockito.mock(games.lmdbg.server.model.Play.class);
        Mockito.when(serverPlay.getOutcome()).thenReturn(outcome);
        Mockito.when(serverPlay.getPlayers()).thenReturn(playerCount);Mockito.when(serverPlay.getScheme()).thenReturn(schemeId);
        Mockito.when(serverPlay.getMastermind()).thenReturn(mastermindId);

        Mockito.when(serverPlay.getHeroes()).thenReturn(Collections.emptySet());
        Mockito.when(serverPlay.getVillains()).thenReturn(Collections.emptySet());
        Mockito.when(serverPlay.getHenchmen()).thenReturn(Collections.emptySet());
        Mockito.when(serverPlay.getSupports()).thenReturn(Collections.emptySet());
        Mockito.when(serverPlay.getStarters()).thenReturn(Collections.emptySet());


        Mockito.when(serverPlay.getBoard()).thenReturn(boardId);

        Set<PrintableError> errors = new HashSet<>(RulesUtils.verify(serverPlay));

        Assertions.assertEquals(5, errors.size());
        Assertions.assertTrue(errors.contains(MissingRecruitSupport.INSTANCE));
        Assertions.assertTrue(errors.contains(new WrongSetCount(CardSetType.HENCHMAN, 1, 0)));
        Assertions.assertTrue(errors.contains(new WrongSetCount(CardSetType.STARTER, 1, 0)));
        Assertions.assertTrue(errors.contains(new WrongSetCount(CardSetType.HERO, 3, 0)));
        Assertions.assertTrue(errors.contains(new WrongSetCount(CardSetType.VILLAIN, 1, 0)));
    }
}
