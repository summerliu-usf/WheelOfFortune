import java.util.ArrayList;
public class WOFAIGame extends WOF {
    private ArrayList<WOFPlayer> players;
    private ArrayList<WOFPlayer> allPlayers;
    private WOFPlayer defaultPl;
    private int numPlayers;
    private int chances;
    private WOFPlayer currentPlayer;
    private ArrayList<WOFPlayer> hasPlayed;


    public WOFAIGame() { //adds default player
        super();
        this.players = new ArrayList<>();
        players.add(defaultPl);
    }

    public WOFAIGame(int numPlayers) {
        super();
        this.players = new ArrayList<WOFPlayer>(numPlayers);
        players = new ArrayList<>(allPlayers.subList(0,numPlayers));
    }

    public WOFAIGame(ArrayList<WOFPlayer> players) { // takes a list of AI players
        super();
        this.players = players;
    }

    @Override
    public void printInstructions() {
        System.out.println("<---- AI Wheel of Fortune ---->");
        System.out.println("Welcome! In this game, multiple AI players will take turns guessing letters.");
        System.out.println("Each AI player will play a separate game for every phrase loaded from the file.");
        System.out.println("There are " + numPlayers + " AI players participating, each with unique guessing strategies.");
        System.out.println("Each round will display the current progress on the hidden phrase as the AI makes guesses.");
        System.out.println("Let's watch the AI try to solve each phrase!\n");
    }

    private WOFPlayer nextPlayer() { //finds next AI player who hasn't played in this round
        WOFPlayer res = null;
        if (hasPlayed.size() == players.size()) {
            hasPlayed = new ArrayList<WOFPlayer>();
        }
        for (WOFPlayer player: players) {
            if (!hasPlayed.contains(player)) {
                hasPlayed.add(player);
                res = player;
                break;
            }
        }
        return res;
    }

    @Override
    public GameRecord play(){ // plays one game for one player
        printInstructions();
        currentPlayer = nextPlayer();
        currentPlayer.reset();
        this.secret = generateHiddenPhrase(phrase); // Reset the hidden phrase for each player
        System.out.println("Player " + currentPlayer.playerId() + " is playing the game.");

        while (!secret.toString().equals(phrase)) {
            char g = currentPlayer.nextGuess();
            if (pastGuesses.indexOf(g) != -1) {
                continue;
            }
            pastGuesses += g;
            processGuess(g);

            if (chances == 0) {
                gameOver();
                break;
            }
        }

        if (secret.toString().equals(phrase)) {
            System.out.println("<---- Congrats! AI Player " + currentPlayer.playerId() + " solved the phrase! ---->");
        }
        return new GameRecord(chances, currentPlayer.playerId());
    }

    @Override
    public boolean playNext(){ // AI alwyas plays next game
        return true;
    }

    public static void main(String[] args) {
        WOFAIGame aiGame = new WOFAIGame();
        aiGame.playAll();
        System.out.println(aiGame.allRecords.highGameList(3));
        System.out.println(aiGame.allRecords.average());
        for (WOFPlayer player : aiGame.allPlayers) {
            System.out.println(aiGame.allRecords.average(player.playerId()));
        }
    }
}
