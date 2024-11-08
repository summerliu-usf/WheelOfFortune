import java.util.ArrayList;
import java.util.HashMap;
import java.util.Collections;
import java.util.List;

public class AllGamesRecord {
    // keeps all game records and has series of methods
    private HashMap<String, ArrayList<Integer>> records; // mistakingly thought that the AIs were supposed to play tgt and therefore used a HashMap

    public AllGamesRecord() {
        this.records = new HashMap<>();
    }

    public void add(GameRecord gameRecord) { // adds a GameRecord to the AllGamesRecord
        int score = gameRecord.getScore();
        String id = gameRecord.getPlayerId();
        if (checkUser(id)) {
            records.get(id).add(score);
        } else {
            records.put(id, new ArrayList<Integer>());
            records.get(id).add(score);
        }
    }

    public boolean checkUser(String id) { // checks if a user already exists
        if (records.containsKey(id)) {
            return true;
        }
        return false;
    }



    public int average() {// returns the average score for all games added to the record
        int totalScore = 0;
        int totalGames = 0;
        for (ArrayList<Integer> scoreLst : records.values()) {
            for (Integer score : scoreLst) {
                totalScore += score;
                totalGames++;
            }
        }
        return totalScore / totalGames;
    }

    public int average(String id) {// returns the average score for all games of a particular player
        int totalScore = 0;
        int totalGames = 0;
        ArrayList<Integer> scoreLst = records.get(id);
        for (Integer score : scoreLst) {
            totalScore += score;
            totalGames++;
        }
        return totalScore / totalGames;
    }

    public ArrayList<Integer> highGameList(int n) {// returns a sorted list of the top n scores including player and score. This method should use the Collections class to sort the game instances.
        List<GameRecord> sorted = new ArrayList<>();
        Collections.sort(sorted, Collections.reverseOrder());
        ArrayList<Integer> topScores = new ArrayList<>();
        for (int i = 0; i < Math.min(n, sorted.size()); i++) {
            topScores.add(sorted.get(i).getScore());
        }
        return topScores;
    }

    public ArrayList<Integer> highGameList(String id, Integer n) {// returns a sorted list of the top n scores for the specified player.. This method should use the Collections class to sort the game instances.
        ArrayList<Integer> topScores = new ArrayList<>();
        ArrayList<Integer> scoreLst = records.get(id);
        for (int i = 0; i < Math.min(n, scoreLst.size()); i++) {
            topScores.add(scoreLst.get(i));
        }
        return topScores;
    }

    @Override
    public String toString() {
        return records.toString();
    }

    @Override
    public boolean equals(Object obj) {
        assert (obj instanceof AllGamesRecord);
        return records.equals(((AllGamesRecord) obj).records);
    }
}
