import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

// concrete implementation of WOF for a human player
public class WOF2 extends GuessingGame {
    protected String phrase;
    protected StringBuilder secret;
    protected String pastGuesses;
    protected int chances;
    protected String incorrectGuesses;
    protected ArrayList<WOFPlayer> players;
    protected List<String> phraseList;
    protected AllGamesRecord allRecords;
    protected ArrayList<String> usedPhrases;
    private String id;


    public WOF2(int maxAttempts) { // constructor initializes phrase list, scanner, and max attempts
        super(maxAttempts);
        this.scanner = new Scanner(System.in);
        this.phraseList = readFile(); // load phrases from file
        this.usedPhrases = new ArrayList<>();
        this.phraseList = readFile();
        this.id = id;
        this.chances = maxAttempts;
        this.pastGuesses = "";
        this.incorrectGuesses = "";

    }

    public List<String> readFile() { // reads phrases from file and returns as a list
        List<String> phrases = new ArrayList<>();
        try {
            phrases = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println("Error reading phrases file: " + e.getMessage());
        }
        return phrases;
    }

    public String randomPhrase() { // separated from original randomPhrase so that each call gets new phrase without reading file again
        Random rand = new Random();
        int r = rand.nextInt(phraseList.size()); // safer phrase list selection
        return phraseList.get(r);
    }

    private StringBuilder generateHiddenPhrase(String phrase) { // generates hidden version of the phrase
        StringBuilder hidden = new StringBuilder();
        for (char c : phrase.toCharArray()) {
            if (c == ' ') {
                hidden.append(" ");
            } else {
                hidden.append("*");
            }
        }
        return hidden;
    }

    @Override
    public GameRecord play() { // plays one game and returns a game record
        playGame();
        return new GameRecord(chances, id);
    }

    // game execution
    public void playGame() {
        while (!secret.toString().equals(phrase)) {
            String g = super.getUserGuess();
            if (pastGuesses.contains(g)) {
                System.out.println("You've already guessed this one! Try another");
                continue;
            }
            pastGuesses += g;
            processGuess(g);
            endGame();
            }
        System.out.println("<----Congrats! All done---->");
    }

    @Override
    public boolean playNext() { // checks if the player wants to play another round
        System.out.println("Enter 1 to start");
        String response = scanner.nextLine();
        if (response.indexOf('1') == -1) {
            System.out.println("Sad to see you go :( Here are your results: ");
            System.out.println(allRecords);
            System.out.println("Exiting...");
            System.exit(0);
        }
        System.out.println("Starting a new round...");
        return true;
    }

    @Override
    protected boolean isGameWon() { // checks if player has guessed the full phrase
        return secret.toString().equals(phrase);
    }

    @Override
    protected void endGame(){
        if (chances == 0) {
            System.out.println("Oops too many wrong guesses, game over!");
            System.out.println("<----Game Over---->");
            System.exit(1);
        }
    }

    @Override
    protected void processGuess(String guess) {
        char g = guess.charAt(0);
        if (updateGuess(phrase, secret, g)) {
            System.out.println("Correct!");
            System.out.println("Current progress: " + secret);
        } else {
            chances--;
            incorrectGuesses += g;
            System.out.println("Try again! You have " + chances + " chances left");
            System.out.println("Your past incorrect guesses: " + incorrectGuesses);
            System.out.println("Current progress: " + secret);
        }
    }

    public Boolean updateGuess(String phrase, StringBuilder secret, char guess) {
        // returns T/F for whether letter is a match and modifies hidden phrase and counts score
        boolean found = false;
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == guess || phrase.charAt(i) == Character.toUpperCase(guess)) {
                secret.setCharAt(i, phrase.charAt(i));
                found = true;
            }
        }
        return found;
    }


    @Override
    public void printInstructions() { // prints game instructions for user
        System.out.println("<---- Game Start ---->");
        phrase = randomPhrase();
        secret = generateHiddenPhrase(phrase);
        System.out.println("Instructions: This is a hangman-like game with a phrase.");
        System.out.println("Guess one letter at a time. Only the first letter will be recognized.");
        System.out.println("Hidden phrase: " + secret);
    }

    // main method to initiate the game
    public static void main(String[] args) {
        WOF2 game = new WOF2(5); // max 5 attempts per game
        game.playAll();
    }
}
