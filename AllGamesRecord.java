import java.util.ArrayList;
import java.util.Collections;

public class AllGamesRecord {
    private ArrayList<GameRecord> records;

    public AllGamesRecord() {
        this.records = new ArrayList<>();
    }

    // adds a GameRecord to the AllGamesRecord
    public void add(GameRecord gameRecord) {
        records.add(gameRecord);
    }

    // checks if a user already exists in the records
    public boolean checkUser(String playerId) {
        for (GameRecord record : records) {
            if (record.getPlayerId().equals(playerId)) {
                return true;
            }
        }
        return false;
    }

    // returns the average score for all games added to the record as an integer
    public int average() {
        int totalScore = 0;
        int totalGames = records.size();

        for (GameRecord record : records) {
            totalScore += record.getScore();
        }
        if (totalGames == 0) {
            return 0;
        }
        return totalScore / totalGames;
    }

    // returns the average score for all games of a particular player as an integer
    public int average(String playerId) {
        int totalScore = 0;
        int count = 0;
        for (GameRecord record : records) {
            if (record.getPlayerId().equals(playerId)) {
                totalScore += record.getScore();
                count += 1;
            }
        }
        if (count == 0) {
            return 0;
        }
        return totalScore / count;
    }

    // returns the top n highest scores from all game records
    public ArrayList<Integer> highGameList(int n) {
        ArrayList<GameRecord> sortedRecords = new ArrayList<>(records);
        Collections.sort(sortedRecords, new ScoreComparator());

        int endIndex = Math.min(n, sortedRecords.size());
        ArrayList<Integer> topScores = new ArrayList<>();

        for (int i = 0; i < endIndex; i++) {
            topScores.add(sortedRecords.get(i).getScore());
        }

        return topScores;
    }

    // returns the top n highest scores for the specified player
    public ArrayList<Integer> highGameList(String playerId, int n) {
        ArrayList<GameRecord> playerRecords = new ArrayList<>();

        for (GameRecord record : records) {
            if (record.getPlayerId().equals(playerId)) {
                playerRecords.add(record);
            }
        }
        Collections.sort(playerRecords, new ScoreComparator());
        int endIndex = Math.min(n, playerRecords.size());
        ArrayList<Integer> topScores = new ArrayList<>();

        for (int i = 0; i < endIndex; i++) {
            topScores.add(playerRecords.get(i).getScore());
        }
        return topScores;
    }


    // comparator for sorting by score in descending order
    private static class ScoreComparator implements java.util.Comparator<GameRecord> {
        public int compare(GameRecord r1, GameRecord r2) {
            if (r1.getScore() < r2.getScore()) {
                return 1;
            } else if (r1.getScore() > r2.getScore()) {
                return -1;
            } else {
                return 0;
            }
        }
    }

    // returns a string representation
    @Override
    public String toString() {
        StringBuilder result = new StringBuilder("Results: \n");
        for (GameRecord record : records) {
            result.append(record.toString()).append("\n");
        }
        return result.toString();
    }

    // checks if two AllGamesRecord objects are equal
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AllGamesRecord other = (AllGamesRecord) obj;
        return records.equals(other.records);
    }

}
