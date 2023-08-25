package dev.manere.utils.tierlist.gamemode;

/**
 * Represents all the game-modes in the official tier-list.
 */
public enum GameMode {

    SWORD(0, "Sword"),
    VANILLA(1, "Vanilla"),
    AXE(2, "Axe"),
    POT(3, "Pot"),
    NETH_POT(4, "NethPot"),
    UHC(5, "UHC"),
    SMP(6, "SMP");
    public final int number;
    public final String name;

    GameMode(int number, String name) {
        this.number = number;
        this.name = name;
    }
}