package games.lmdbg.rules.set.core

import games.lmdbg.rules.model.*
import games.lmdbg.rules.set.common.Teams

val coreSet = Release(
    "Core Set", "Core Set", "Phase 1",
    heroes = listOf(
        Heroes.BLACK_WIDOW,
        Heroes.CAPTAIN_AMERICA,
        Heroes.CYCLOPS,
        Heroes.DEADPOOL,
        Heroes.EMMA_FROST,
        Heroes.GAMBIT,
        Heroes.HAWKEYE,
        Heroes.HULK,
        Heroes.IRON_MAN,
        Heroes.NICK_FURY,
        Heroes.ROGUE,
        Heroes.SPIDER_MAN,
        Heroes.STORM,
        Heroes.THOR,
        Heroes.WOLVERINE
    ),
    schemes = listOf(
        Schemes.THE_LEGACY_VIRUS,
        Schemes.MIDTOWN_BANK_ROBBERY,
        Schemes.NEGATIVE_ZONE_PRISON_BREAKOUT,
        Schemes.PORTALS_TO_THE_DARK_DIMENSION,
        Schemes.REPLACE_EARTHS_LEADERS_WITH_KILLBOTS,
        Schemes.SECRET_INVASION_OF_THE_SKRULL_SHAPESHIFTERS,
        Schemes.SUPER_HERO_CIVIL_WAR,
        Schemes.UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE,
        Schemes.ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER
    ),
    masterminds = listOf(
        Masterminds.DR_DOOM,
        Masterminds.EPIC_DURISSA_THE_DISPOSSESSED,
        Masterminds.LOKI,
        Masterminds.EPIC_TERRISKAI_TERROR_OF_THE_SKIES,
        Masterminds.MAGNETO,
        Masterminds.EPIC_NAX_LORD_OF_CRIMSON_BOG,
        Masterminds.RED_SKULL,
        Masterminds.EPIC_KELILA_BENDER_OF_WILLS,
        Masterminds.IRON_MONGER
    ),
    villains = listOf(
        Villains.BROTHERHOOD,
        Villains.ENEMIES_OF_ASGARD,
        Villains.HYDRA,
        Villains.MASTERS_OF_EVIL,
        Villains.RADIATION,
        Villains.SKRULLS,
        Villains.SPIDER_FOES,
        Villains.CHITAURI,
        Villains.GAMMA_HUNTERS,
        Villains.IRON_FOES
    ),
    henchmen = listOf(Henchmen.DOOMBOT_LEGION, Henchmen.HAND_NINJAS, Henchmen.SAVAGE_LAND_MUTATES, Henchmen.SENTINEL),
    supports = listOf(Supports.SHIELD_OFFICER),
    starters = listOf(Starters.SHIELD),
    boards = listOf(Boards.HQ)
)

object Heroes {
    val BLACK_WIDOW = Hero(101, "Black Widow", "Steelvara the Light", "Black Widow", listOf(Teams.AVENGERS))
    val CAPTAIN_AMERICA =
        Hero(102, "Captain America", "Lord Cedric of the Citadel", "Captain America", listOf(Teams.AVENGERS))
    val CYCLOPS = Hero(103, "Cyclops", "Disaray the Sufferer", null, listOf(Teams.X_MEN))
    val DEADPOOL = Hero(104, "Deadpool", "Nunchi", null, listOf())
    val EMMA_FROST = Hero(105, "Emma Frost", "Doneya Petalfall", null, listOf(Teams.X_MEN))
    val GAMBIT = Hero(106, "Gambit", "Makea the All-Knowing", null, listOf(Teams.X_MEN))
    val HAWKEYE = Hero(107, "Hawkeye", "Arcillo the Noble Hearted", "Hawkeye", listOf(Teams.AVENGERS))
    val HULK = Hero(108, "Hulk", "Cawr", "Hulk", listOf(Teams.AVENGERS))
    val IRON_MAN = Hero(109, "Iron Man", "Lollycooler", "Iron Man", listOf(Teams.AVENGERS))
    val NICK_FURY = Hero(110, "Nick Fury", "Mal Gravemore", "Nick Fury", listOf(Teams.SHIELD))
    val ROGUE = Hero(111, "Rogue", "Ceridwen", null, listOf(Teams.X_MEN))
    val SPIDER_MAN = Hero(112, "Spider-Man", "Ryuhi", null, listOf(Teams.SPIDER_FRIENDS))
    val STORM = Hero(113, "Storm", "Ordmantil the Shadow", null, listOf(Teams.X_MEN))
    val THOR = Hero(114, "Thor", "\"Hog\" Dryll", "Thor", listOf(Teams.AVENGERS))
    val WOLVERINE = Hero(115, "Wolverine", "Kamina the Curious", null, listOf(Teams.X_MEN))
}

object Schemes {
    val THE_LEGACY_VIRUS = Scheme(101, "The Legacy Virus", "Bleed 'em White", "Radioactive Palladium Poisoning")
    val MIDTOWN_BANK_ROBBERY =
        Scheme(102, "Midtown Bank Robbery", "Pillage The Countryside", "Destroy the Cities of Earth!")
    val NEGATIVE_ZONE_PRISON_BREAKOUT =
        Scheme(103, "Negative Zone Prison Breakout", "Overwhelming Hordes", "Asgard Under Siege")
    val PORTALS_TO_THE_DARK_DIMENSION =
        Scheme(104, "Portals to the Dark Dimension", "The Growing Darkness", "Invade Asgard")
    val REPLACE_EARTHS_LEADERS_WITH_KILLBOTS = Scheme(
        105,
        "Replace Earth's Leaders with Killbots",
        "The Dead Shall Rise!",
        "Replace Earth's Leaders with HYDRA"
    )
    val SECRET_INVASION_OF_THE_SKRULL_SHAPESHIFTERS =
        Scheme(106, "Secret Invasion of the Skrull Shapeshifters", "The Lure of Voodoo", null)
    val SUPER_HERO_CIVIL_WAR = Scheme(107, "Super Hero Civil War", "No More Heroes", "Super Hero Civil War")
    val UNLEASH_THE_POWER_OF_THE_COSMIC_CUBE =
        Scheme(108, "Unleash the Power of the Cosmic Cube", "Apex of Power", "Unleash the Power of the Cosmic Cube")
    val ENSLAVE_MINDS_WITH_THE_CHITAURI_SCEPTER = Scheme(109, null, null, "Enslave Minds with the Chitauri Scepter")
}

object Masterminds {
    val DR_DOOM = Mastermind(101, "Dr. Doom", "Durissa the Dispossessed", null)
    val EPIC_DURISSA_THE_DISPOSSESSED = Mastermind(102, null, "Epic Durissa the Dispossessed", null)
    val LOKI = Mastermind(103, "Loki", "Terriskai, Terror of the Skies", "Loki")
    val EPIC_TERRISKAI_TERROR_OF_THE_SKIES = Mastermind(104, null, "Epic Terriskai, Terror of the Skies", null)
    val MAGNETO = Mastermind(105, "Magneto", "Nax, Lord of Crimson Bog", null)
    val EPIC_NAX_LORD_OF_CRIMSON_BOG = Mastermind(106, null, "Epic Nax, Lord of Crimson Bog", null)
    val RED_SKULL = Mastermind(107, "Red Skull", "Kelila, Bender of Wills", "Red Skull")
    val EPIC_KELILA_BENDER_OF_WILLS = Mastermind(108, null, "Epic Kelila, Bender of Wills", null)
    val IRON_MONGER = Mastermind(109, null, null, "Iron Monger")
}

object Villains {
    val BROTHERHOOD = Villain(101, "Brotherhood", "Sarco", null)
    val ENEMIES_OF_ASGARD = Villain(102, "Enemies of Asgard", "Nortagem", "Enemies of Asgard")
    val HYDRA = Villain(103, "HYDRA", "Ernak the Lethal", "HYDRA")
    val MASTERS_OF_EVIL = Villain(104, "Masters of Evil", "Void Rider", null)
    val RADIATION = Villain(105, "Radiation", "Force of Gehenna", null)
    val SKRULLS = Villain(106, "Skrulls", "Brigitte Blackbird", null)
    val SPIDER_FOES = Villain(107, "Spider-Foes", "Teuthos", null)
    val CHITAURI = Villain(108, null, null, "Chitauri")
    val GAMMA_HUNTERS = Villain(109, null, null, "Gamma Hunters")
    val IRON_FOES = Villain(110, null, null, "Iron Foes")
}

object Henchmen {
    val DOOMBOT_LEGION = Henchman(101, "Doombot Legion", "Goblin Horde", "Hammer Drone Army")
    val HAND_NINJAS = Henchman(102, "Hand Ninjas", "Leprechauns", "HYDRA Pilots")
    val SAVAGE_LAND_MUTATES = Henchman(103, "Savage Land Mutates", "Books of the Dead", "HYDRA Spies")
    val SENTINEL = Henchman(104, "Sentinel", "Phyllo Assassins", "Ten Rings Fanatics")
}

object Supports {
    val SHIELD_OFFICER = Support(101, "S.H.I.E.L.D. Officer", "Mayor Shufflebottom", "S.H.I.E.L.D. Officer")
}

object Starters {
    val SHIELD = Starter(101, "S.H.I.E.L.D.", "Fixers", "S.H.I.E.L.D.")
}

object Boards {
    val HQ = Board(101, "HQ", "Guild Hall", "HQ")
}
