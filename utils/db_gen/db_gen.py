# Generate fake data to populate database
import random
import sys
from enum import Enum

NUM_PLAYS = 100

STARTING_ID = 0

MIN_HER0 = 101
MAX_HERO = 115

MIN_SCHEME = 101
MAX_SCHEME = 108

MIN_MASTERMIND = 101
MAX_MASTERMIND = 109

MIN_VILLAIN = 101
MAX_VILLAIN = 110

MIN_HENCHMEN = 101
MAX_HENCHMEN = 104

USER_ID = 0
USER_NAME = "system_automation"


class Outcomes(Enum):
    WIN = "WIN_DEFEAT_MASTERMIND"
    DRAW = "DRAW"
    LOSS = "LOSS_SCHEME"
    INC = "INCOMPLETE"

    def __init__(self, represent):
        self.db_value = represent

    @classmethod
    def as_list(cls):
        return list(cls)


class Players(Enum):
    SOLO = "SOLO"
    ADVANCED = "ADVANCED"
    TWO = "TWO"
    THREE = "THREE"
    FOUR = "FOUR"
    FIVE = "FIVE"

    def __init__(self, represent):
        self.db_value = represent

    @classmethod
    def as_list(cls):
        return list(cls)


def generate_db(starting_id, num_plays, out_file):
    generate_user(out_file)
    plays = generate_plays(starting_id, num_plays, out_file)
    generate_heroes(plays, out_file)
    generate_villains(plays, out_file)
    generate_henchmen(plays, out_file)


def generate_user(out_file):
    out_file.write("""INSERT INTO \"user\" (id, user_name)
        VALUES (%d, '%s')
        ON CONFLICT DO NOTHING;\n\n""" % (USER_ID, USER_NAME))


def generate_plays(starting_id, num_plays, out_file):
    plays = {}

    # print start of query
    out_file.write("INSERT INTO play (id, player_id, outcome, mastermind_id, scheme_id, players) VALUES \n")

    for id in range(starting_id, starting_id + num_plays):
        if id > starting_id:
            out_file.write(",\n")

        outcome = random.choice(Outcomes.as_list()).db_value
        mastermind = random.randint(MIN_MASTERMIND, MAX_MASTERMIND)
        scheme = random.randint(MIN_SCHEME, MAX_SCHEME)
        players = random.choice(Players.as_list()).db_value

        value_format = "\t(%d, %d, '%s', %d, %d, '%s')"
        out_file.write(value_format % (id, USER_ID, outcome, mastermind, scheme, players))
        plays[id] = {"scheme": scheme, "players": players}

    out_file.write(";\n\n")

    return plays


def generate_heroes(plays, out_file):
    out_file.write("INSERT INTO play_hero (play_id, hero_id) VALUES\n")
    first = True
    for play_id, play in plays.items():
        players = play["players"]
        if players in [Players.SOLO.db_value, Players.ADVANCED.db_value]:
            num_heroes = 3
        elif players in [Players.TWO.db_value, Players.THREE.db_value, Players.FOUR.db_value]:
            num_heroes = 5
        elif players is Players.FIVE.db_value:
            num_heroes = 6

        heroes = list(range(MIN_HER0, MAX_HERO + 1))
        random.shuffle(heroes)
        for hero_id in heroes[:num_heroes]:
            if not first:
                out_file.write(",\n")
            else:
                first = False
            out_file.write("\t(%d, %d)" % (play_id, hero_id))

    out_file.write(";\n\n")


def generate_villains(plays, out_file):
    out_file.write("INSERT INTO play_villain (play_id, villain_id) VALUES\n")
    first = True
    for play_id, play in plays.items():
        players = play["players"]
        if players in [Players.SOLO.db_value, Players.ADVANCED.db_value]:
            num_villains = 1
        elif players is Players.TWO.db_value:
            num_villains = 2
        elif players in [Players.THREE.db_value, Players.FOUR.db_value]:
            num_villains = 3
        elif players is Players.FIVE.db_value:
            num_villains = 4

        villains = list(range(MIN_VILLAIN, MAX_VILLAIN + 1))
        random.shuffle(villains)
        for villain_id in villains[:num_villains]:
            if not first:
                out_file.write(",\n")
            else:
                first = False
            out_file.write("\t(%d, %d)" % (play_id, villain_id))

    out_file.write(";\n\n")


def generate_henchmen(plays, out_file):
    out_file.write("INSERT INTO play_henchman (play_id, henchman_id) VALUES\n")
    first = True
    for play_id, play in plays.items():
        players = play["players"]
        if players in [Players.SOLO.db_value, Players.ADVANCED.db_value, Players.TWO.db_value, Players.THREE.db_value]:
            num_henchmen = 1
        elif players in [Players.FOUR.db_value, Players.FIVE.db_value]:
            num_henchmen = 2

        henchmen = list(range(MIN_HENCHMEN, MAX_HENCHMEN + 1))
        random.shuffle(henchmen)
        for henchman_id in henchmen[:num_henchmen]:
            if not first:
                out_file.write(",\n")
            else:
                first = False
            out_file.write("\t(%d, %d)" % (play_id, henchman_id))

    out_file.write(";\n\n")


if __name__ == '__main__':
    generate_db(STARTING_ID, NUM_PLAYS, sys.stdout)
