public abstract class Game { // loops through a set of games and returns the result
    protected AllGamesRecord allRecords;

    public Game() { //constructor
        this.allRecords = new AllGamesRecord();
    }

    public AllGamesRecord playAll() { // plays a set of games and records results.
        while (playNext()) {
            GameRecord gameRecord = play();
            allRecords.add(gameRecord);
        }
        return allRecords;
    }

    public abstract GameRecord play(); // plays one game. To be implemented by subclasses.

    public abstract boolean playNext(); // checks if the next game should be played. To be implemented by subclasses.

}
