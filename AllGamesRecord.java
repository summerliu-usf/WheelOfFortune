import java.util.ArrayList;
import java.util.HashMap;

public class AllGamesRecord {
    // keeps all game records and has series of methods
    private HashMap<String, ArrayList<Integer>> records;

    public AllGamesRecord() {
        this.records = new HashMap<>();
    }

    public void add(GameRecord gameRecord) { // adds a GameRecord to the AllGamesRecord
        int score = gameRecord.getScore();
        String id = gameRecord.getPlayerId();
        if (records.containsKey(id)) {
            records.get(id).add(score);
        }
        else {
            records.put(id, new ArrayList<>());
            records.get(id).add(score);
        }
    }

    public int average() {// returns the average score for all games added to the record

    }

    public int average(String id) {// returns the average score for all games of a particular player
        return int ; }
    public ArrayList<Integer> highGameList(n) {}// returns a sorted list of the top n scores including player and score. This method should use the Collections class to sort the game instances.

    public ArrayList<Integer> highGameList(playerId, n) {}// returns a sorted list of the top n scores for the specified player.. This method should use the Collections class to sort the game instances.

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
