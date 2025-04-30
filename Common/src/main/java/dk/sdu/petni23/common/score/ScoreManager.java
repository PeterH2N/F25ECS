package dk.sdu.petni23.common.score;

import java.util.function.Consumer;

public class ScoreManager {
    private static int totalScore = 0;
    private static Consumer<Integer> onScoreChanged;

    public static void addScore(int score) {
        totalScore += score;
        if (onScoreChanged != null)
            onScoreChanged.accept(totalScore);
    }

    public static int getScore() {
        return totalScore;
    }

    public static void setScoreListener(Consumer<Integer> listener) {
        onScoreChanged = listener;
    }

    public static void reset() {
        totalScore = 0;
        onScoreChanged = null;
    }
}
