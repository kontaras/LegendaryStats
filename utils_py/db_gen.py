# Generate fake data to populate database

from enum import Enum

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


class Outcomes(Enum):
    WIN = "WIN"
    DRAW = "DRAW"
    LOSS = "LOSS"
    INC = "INCOMPLETE"

    def __init__(self, repr):
        self.db_value = repr


class Players(Enum):
    SOLO = "SOLO"
    ADVANCED = "ADVANCED"
    TWO = "TWO"
    THREE = "THREE"
    FOUR = "FOUR"
    FIVE = "FIVE"


def generate_db(num_plays):
    pass


if __name__ == '__main__':
    generate_db(100)
