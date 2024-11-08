public class GameRecord implements Comparable<GameRecord>{
    // keeps record of player id and score for one game
    private int score;
    private final String id;

    public GameRecord(int score, String id) { //constructor
        this.score = score;
        this.id = id;
    }

    @Override
    public int compareTo(GameRecord other) { // override compareTo
        return Integer.compare(this.score, other.score);
    }

    @Override
    public String toString() { //override toSTring
        return "Score: " + this.score + " for player " + this.id;
    }

    @Override
    public boolean equals(Object other) { //override equals
        assert other instanceof GameRecord;
        return this.score == ((GameRecord) other).score;
    }

    public int getScore() { //getter
        return score;
    }

    public String getPlayerId() { //setter
        return id;
    }
}
