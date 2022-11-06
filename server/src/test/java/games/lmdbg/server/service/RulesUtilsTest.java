package games.lmdbg.server.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import games.lmdbg.rules.model.Outcome;
import games.lmdbg.rules.model.PlayerCount;
import games.lmdbg.rules.set.core.Boards;
import games.lmdbg.rules.set.core.Henchmen;
import games.lmdbg.rules.set.core.Masterminds;
import games.lmdbg.rules.set.core.Schemes;
import games.lmdbg.rules.verifier.CardSetType;
import games.lmdbg.rules.verifier.MissingRecruitSupport;
import games.lmdbg.rules.verifier.PrintableError;
import games.lmdbg.rules.verifier.WrongSetCount;


import org.junit.jupiter.api.Assertions;

public class RulesUtilsTest {
    @Test
    void testServerToRules() {
        final String outcome = Outcome.DRAW.toString();
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
        
        games.lmdbg.server.model.game.Scheme scheme = Mockito.mock(games.lmdbg.server.model.game.Scheme.class);
        Mockito.when(scheme.getId()).thenReturn(schemeId);
        Mockito.when(serverPlay.getScheme()).thenReturn(scheme);
        
        games.lmdbg.server.model.game.Mastermind mastermind = Mockito.mock(games.lmdbg.server.model.game.Mastermind.class);
        Mockito.when(mastermind.getId()).thenReturn(mastermindId);
        Mockito.when(serverPlay.getMastermind()).thenReturn(mastermind);


        games.lmdbg.server.model.game.Hero hero1 = Mockito.mock(games.lmdbg.server.model.game.Hero.class);
        Mockito.when(hero1.getId()).thenReturn(heroId1);
        games.lmdbg.server.model.game.Hero hero2 = Mockito.mock(games.lmdbg.server.model.game.Hero.class);
        Mockito.when(hero2.getId()).thenReturn(heroId2);
        Mockito.when(serverPlay.getHeroes()).thenReturn(Set.of(hero1, hero2));

        games.lmdbg.server.model.game.Villain villain1 = Mockito.mock(games.lmdbg.server.model.game.Villain.class);
        Mockito.when(villain1.getId()).thenReturn(villainId1);
        games.lmdbg.server.model.game.Villain villain2 = Mockito.mock(games.lmdbg.server.model.game.Villain.class);
        Mockito.when(villain2.getId()).thenReturn(villainId2);
        Mockito.when(serverPlay.getVillains()).thenReturn(Set.of(villain1, villain2));

        games.lmdbg.server.model.game.Henchman henchman1 = Mockito.mock(games.lmdbg.server.model.game.Henchman.class);
        Mockito.when(henchman1.getId()).thenReturn(henchmanId1);
        games.lmdbg.server.model.game.Henchman henchman2 = Mockito.mock(games.lmdbg.server.model.game.Henchman.class);
        Mockito.when(henchman2.getId()).thenReturn(henchmanId2);
        Mockito.when(serverPlay.getHenchmen()).thenReturn(Set.of(henchman1, henchman2));

        games.lmdbg.server.model.game.Support support1 = Mockito.mock(games.lmdbg.server.model.game.Support.class);
        Mockito.when(support1.getId()).thenReturn(supportId1);
        games.lmdbg.server.model.game.Support support2 = Mockito.mock(games.lmdbg.server.model.game.Support.class);
        Mockito.when(support2.getId()).thenReturn(supportId2);
        Mockito.when(serverPlay.getSupports()).thenReturn(Set.of(support1, support2));

        games.lmdbg.server.model.game.Starter starter1 = Mockito.mock(games.lmdbg.server.model.game.Starter.class);
        Mockito.when(starter1.getId()).thenReturn(starterId1);
        games.lmdbg.server.model.game.PlayStarter playStarter1 = Mockito.mock(games.lmdbg.server.model.game.PlayStarter.class);
        Mockito.when(playStarter1.getStarter()).thenReturn(starter1);
        Mockito.when(playStarter1.getQuantity()).thenReturn(starterQuantity1);
        games.lmdbg.server.model.game.Starter starter2 = Mockito.mock(games.lmdbg.server.model.game.Starter.class);
        Mockito.when(starter2.getId()).thenReturn(starterId2);
        games.lmdbg.server.model.game.PlayStarter playStarter2 = Mockito.mock(games.lmdbg.server.model.game.PlayStarter.class);
        Mockito.when(playStarter2.getStarter()).thenReturn(starter2);
        Mockito.when(playStarter2.getQuantity()).thenReturn(starterQuantity2);
        Mockito.when(serverPlay.getStarters()).thenReturn(Set.of(playStarter1, playStarter2));

        games.lmdbg.server.model.game.Board board = Mockito.mock(games.lmdbg.server.model.game.Board.class);
        Mockito.when(board.getId()).thenReturn(boardId);
        Mockito.when(serverPlay.getBoard()).thenReturn(board);

        games.lmdbg.rules.model.Play rulesPlay = RulesUtils.serverToRules(serverPlay);

        Assertions.assertEquals(rulesPlay.getOutcome().toString(), outcome);
        Assertions.assertEquals(rulesPlay.getPlayers().toString(), playerCount);
        Assertions.assertEquals(rulesPlay.getScheme(), schemeId);
        Assertions.assertEquals(rulesPlay.getMastermind(), mastermindId);

        Assertions.assertEquals(rulesPlay.getHeroes().size(), 2);
        Assertions.assertTrue(rulesPlay.getHeroes().contains(heroId1));
        Assertions.assertTrue(rulesPlay.getHeroes().contains(heroId2));

        Assertions.assertEquals(rulesPlay.getVillains().size(), 2);
        Assertions.assertTrue(rulesPlay.getVillains().contains(villainId1));
        Assertions.assertTrue(rulesPlay.getVillains().contains(villainId2));

        Assertions.assertEquals(rulesPlay.getHenchmen().size(), 2);
        Assertions.assertTrue(rulesPlay.getHenchmen().contains(henchmanId1));
        Assertions.assertTrue(rulesPlay.getHenchmen().contains(henchmanId2));

        Assertions.assertEquals(rulesPlay.getSupports().size(), 2);
        Assertions.assertTrue(rulesPlay.getSupports().contains(supportId1));
        Assertions.assertTrue(rulesPlay.getSupports().contains(supportId2));

        Assertions.assertEquals(rulesPlay.getStarters().size(), 2);
        Assertions.assertEquals(rulesPlay.getStarters().get(starterId1), starterQuantity1);
        Assertions.assertEquals(rulesPlay.getStarters().get(starterId2), starterQuantity2);

        Assertions.assertEquals(rulesPlay.getBoard(), boardId);
    }

    @Test
    void testVerify() {
        final String outcome = Outcome.DRAW.toString();
        final String playerCount = PlayerCount.SOLO.toString();
        final int schemeId = Schemes.PORTALS_TO_THE_DARK_DIMENSION;
        final int mastermindId = Masterminds.DR_DOOM;
        final int boardId = Boards.HQ;
        
        games.lmdbg.server.model.Play serverPlay = Mockito.mock(games.lmdbg.server.model.Play.class);
        Mockito.when(serverPlay.getOutcome()).thenReturn(outcome);
        Mockito.when(serverPlay.getPlayers()).thenReturn(playerCount);
        
        games.lmdbg.server.model.game.Scheme scheme = Mockito.mock(games.lmdbg.server.model.game.Scheme.class);
        Mockito.when(scheme.getId()).thenReturn(schemeId);
        Mockito.when(serverPlay.getScheme()).thenReturn(scheme);
        
        games.lmdbg.server.model.game.Mastermind mastermind = Mockito.mock(games.lmdbg.server.model.game.Mastermind.class);
        Mockito.when(mastermind.getId()).thenReturn(mastermindId);
        Mockito.when(serverPlay.getMastermind()).thenReturn(mastermind);

        Mockito.when(serverPlay.getHeroes()).thenReturn(Collections.emptySet());
        Mockito.when(serverPlay.getVillains()).thenReturn(Collections.emptySet());
        Mockito.when(serverPlay.getHenchmen()).thenReturn(Collections.emptySet());
        Mockito.when(serverPlay.getSupports()).thenReturn(Collections.emptySet());
        Mockito.when(serverPlay.getStarters()).thenReturn(Collections.emptySet());

        games.lmdbg.server.model.game.Board board = Mockito.mock(games.lmdbg.server.model.game.Board.class);
        Mockito.when(board.getId()).thenReturn(boardId);
        Mockito.when(serverPlay.getBoard()).thenReturn(board);

        Set<PrintableError> errors = new HashSet<>(RulesUtils.verify(serverPlay));

        Assertions.assertEquals(5, errors.size());
        Assertions.assertTrue(errors.contains(MissingRecruitSupport.INSTANCE));
        Assertions.assertTrue(errors.contains(new WrongSetCount(CardSetType.HENCHMAN, 1, 0)));
        Assertions.assertTrue(errors.contains(new WrongSetCount(CardSetType.STARTER, 1, 0)));
        Assertions.assertTrue(errors.contains(new WrongSetCount(CardSetType.HERO, 3, 0)));
        Assertions.assertTrue(errors.contains(new WrongSetCount(CardSetType.VILLAIN, 1, 0)));
    }
}
