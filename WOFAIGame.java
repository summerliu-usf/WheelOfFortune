import java.util.ArrayList;

public class WOFAIGame extends WOF {
    private ArrayList<WOFPlayer> players;
    private ArrayList<WOFPlayer> allPlayers;
    private WOFPlayer defaultPl = new sequentialAI();
    private int numPlayers;
    private WOFPlayer currentPlayer;
    private ArrayList<WOFPlayer> hasPlayed;
    private int aiScore;

    public WOFAIGame() { // adds default player
        super();
        this.players = new ArrayList<>();
        this.allPlayers = new ArrayList<>();
        this.currentPlayer = defaultPl;
        this.chances = 10;
        players.add(defaultPl);
        allPlayers.add(defaultPl);
        hasPlayed = new ArrayList<>();
        phraseIndex = 0;
    }

    public WOFAIGame(int numPlayers) {
        super();
        this.allPlayers = new ArrayList<>();
        allPlayers.add(new sequentialAI());
        allPlayers.add(new frequencyAI());
        allPlayers.add(new randomAI());
        this.players = new ArrayList<>(allPlayers.subList(0, numPlayers));
        this.chances = 10;
        this.aiScore = 0;
        hasPlayed = new ArrayList<>();
    }

    public WOFAIGame(ArrayList<WOFPlayer> players) { // takes a list of AI players
        super();
        this.allPlayers = new ArrayList<>(players);
        this.players = new ArrayList<>(players);
        this.chances = 10;
        this.aiScore = 0;
        hasPlayed = new ArrayList<>();
    }

    @Override
    public void printInstructions() {
        System.out.println("<---- AI Wheel of Fortune ---->");
        System.out.println("Welcome! In this game, multiple AI players will take turns guessing letters.");
        System.out.println("Each AI player will play a separate game for every phrase loaded from the file.");
        System.out.println("There are " + players.size() + " AI players participating, each with unique guessing strategies.");
        System.out.println("Each round will display the current progress on the hidden phrase as the AI makes guesses.");
        System.out.println("Let's watch the AI try to solve each phrase!\n");
    }

    private WOFPlayer nextPlayer() { // finds next AI player who hasn't played in this round
        WOFPlayer res = null;
        if (hasPlayed.size() == players.size()) {
            hasPlayed = new ArrayList<>();
        }
        for (WOFPlayer player : players) {
            if (!hasPlayed.contains(player)) {
                hasPlayed.add(player);
                res = player;
                break;
            }
        }
        return res;
    }

    @Override
    public GameRecord play() { // plays one game for one player
        currentPlayer = nextPlayer();  // get the next player who hasn't played this phrase
        currentPlayer.reset();
        aiScore = 0;
        chances = 10;

        if (phraseIndex >= phraseList.size()) {
            phraseIndex = 0;
        }

        phrase = phraseList.get(phraseIndex);
        this.secret = generateHiddenPhrase(phrase);
        pastGuesses = "";

        System.out.println("Player " + currentPlayer.playerId() + " is playing the game.");

        while (!secret.toString().equals(phrase) && chances > 0) {
            char g = currentPlayer.nextGuess();
            if (pastGuesses.indexOf(g) != -1) {
                continue;
            }
            pastGuesses += g;
            System.out.println(currentPlayer.playerId() + "'s current guess: " + g);

            if (updateGuess(phrase, secret, g)) {
                aiScore++;
                System.out.println("Correct guess! Current progress: " + secret);
            } else {
                chances--;
                System.out.println("Incorrect guess! Chances left: " + chances);
            }

            System.out.println("Current progress: " + secret);

            if (chances == 0) {
                gameOver();
                System.out.println("Score: " + aiScore);
                GameRecord record = new GameRecord(aiScore, currentPlayer.playerId());
                allRecords.add(record); // Record each game result here
                return record;
            }
        }

        if (secret.toString().equals(phrase)) {
            System.out.println("<---- Congrats! AI Player " + currentPlayer.playerId() + " solved the phrase! ---->");
        }

        GameRecord record = new GameRecord(aiScore, currentPlayer.playerId());
        allRecords.add(record);
        return record;
    }



    @Override
    public boolean playNext() {
        // if all players have completed the current phrase, move to the next phrase
        if (hasPlayed.size() == players.size()) {
            hasPlayed.clear(); // Reset for the next phrase
            phraseIndex++;      // Move to the next phrase
        }
        // continue if there are more phrases to play
        return phraseIndex < phraseList.size();
    }



    public static void main(String[] args) {
        WOFAIGame aiGame = new WOFAIGame(3);
        aiGame.playAll();
        System.out.println(aiGame.allRecords);
        System.out.println("Top 3 scores: " + aiGame.allRecords.highGameList(3));
        System.out.println("Average score across all games: " + aiGame.allRecords.average());
        for (WOFPlayer player : aiGame.allPlayers) {
            System.out.println("Average score for " + player.playerId() +": " + aiGame.allRecords.average(player.playerId()));
        }
    }
}
