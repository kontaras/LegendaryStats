package games.lmdbg.rules.set

import games.lmdbg.rules.set.core.coreSet

val releases = listOf(coreSet)

//These are mutable lists for testing purposes
val heroesById = releases.flatMap { it.heroes }.associateBy { it.id }.toMutableMap()
val schemesById = releases.flatMap { it.schemes }.associateBy { it.id }.toMutableMap()
val mastermindsById = releases.flatMap { it.masterminds }.associateBy { it.id }.toMutableMap()
val villainsById = releases.flatMap { it.villains }.associateBy { it.id }.toMutableMap()
val henchmanById = releases.flatMap { it.henchmen }.associateBy { it.id }.toMutableMap()
val supportsById = releases.flatMap { it.supports }.associateBy { it.id }.toMutableMap()
val startersById = releases.flatMap { it.starters }.associateBy { it.id }.toMutableMap()
val boardsById = releases.flatMap { it.boards }.associateBy { it.id }.toMutableMap()