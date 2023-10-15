package dev.manere.utils.tierlist.gamemode;

/**
 * Represents all the game-modes in the official tier-list.
 */
public enum GameMode {

    AXE(2, "Axe"),
    NETH_POT(4, "NethPot"),
    POT(3, "Pot"),
    SMP(6, "SMP"),
    SWORD(0, "Sword"),
    UHC(5, "UHC"),
    VANILLA(1, "Vanilla");
    public final int number;
    public final String name;

    GameMode(int number, String name) {
        this.number = number;
        this.name = name;
    }
}