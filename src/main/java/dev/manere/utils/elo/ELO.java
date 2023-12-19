package dev.manere.utils.elo;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * This class provides methods to calculate ELO ratings for two players after a match.
 */
public class ELO {
    /**
     * Calculate the new ELO ratings for two players after a match.
     *
     * @param ratings   The initial ELO ratings of the two players.
     * @param winner    The winner of the match (Winner.ONE or Winner.TWO).
     * @param kFactor   The K-factor, which determines the sensitivity of the ELO change.
     * @return          A List containing the new ELO ratings for the two players.
     */
    public static @NotNull List<Integer> elo(@NotNull Ratings<Integer, Integer> ratings, @NotNull Winner winner, int kFactor) {
        int rating1 = ratings.player(1);
        int rating2 = ratings.player(2);

        double expectedScore1 = 1 / (1 + Math.pow(10, (double) (rating2 - rating1) / 400));
        double expectedScore2 = 1 - expectedScore1;

        int score1 = winner.equals(Winner.ONE) ? 1 : 0;
        int score2 = winner.equals(Winner.TWO) ? 1 : 0;

        int newRating1 = (int) Math.round(rating1 + kFactor * (score1 - expectedScore1));
        int newRating2 = (int) Math.round(rating2 + kFactor * (score2 - expectedScore2));

        if (newRating1 < 0) newRating1 = 0;
        if (newRating2 < 0) newRating2 = 0;

        return List.of(newRating1, newRating2);
    }
}
