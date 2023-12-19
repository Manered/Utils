package dev.manere.utils.elo;

/**
 * This class represents the initial ELO ratings of two players.
 *
 * @param <A> The ELO rating for the first player.
 * @param <B> The ELO rating for the second player.
 */
public class Ratings<A, B> {
    private int playerElo1;
    private int playerElo2;

    /**
     * Create a Ratings instance with initial ELO ratings for two players.
     *
     * @param playerElo1 The initial ELO rating of the first player.
     * @param playerElo2 The initial ELO rating of the second player.
     */
    public Ratings(int playerElo1, int playerElo2) {
        this.playerElo1 = playerElo1;
        this.playerElo2 = playerElo2;
    }

    /**
     * Create a Ratings instance with initial ELO ratings for two players.
     *
     * @param playerElo1 The initial ELO rating of the first player.
     * @param playerElo2 The initial ELO rating of the second player.
     */
    public static Ratings<Integer, Integer> of(int playerElo1, int playerElo2) {
        return new Ratings<>(playerElo1, playerElo2);
    }

    /**
     * Returns playerElo1 or playerElo2 via a parameter that can be 1 or 2.
     * @param playerNumber can be 1 or 2.
     * @return playerElo1 or playerElo2 via a parameter that can be 1 or 2.
     */
    public int player(int playerNumber) {
        switch (playerNumber) {
            case 1 -> {
                return playerElo1;
            }
            case 2 -> {
                return playerElo2;
            }
            default -> throw new RuntimeException("The only params you can provide for player(int playerNumber) are 0, 1");
        }
    }

    /**
     * Set the initial ELO rating for the first player.
     *
     * @param playerElo1 The initial ELO rating for the first player.
     * @return The updated Ratings instance.
     */
    public Ratings<A, B> playerElo1(int playerElo1) {
        this.playerElo1 = playerElo1;
        return this;
    }

    /**
     * Set the initial ELO rating for the second player.
     *
     * @param playerElo2 The initial ELO rating for the second player.
     * @return The updated Ratings instance.
     */
    public Ratings<A, B> playerElo2(int playerElo2) {
        this.playerElo2 = playerElo2;
        return this;
    }
}
