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
        this.chances = 5;
    }

    private List<String> readFile() { // reads phrases from a file and returns as a list
        List<String> phrases = new ArrayList<>();
        try {
            phrases = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println("Error reading phrases file: " + e.getMessage());
        }
        return phrases;
    }

    private String randomPhrase() { // selects a random phrase from the list, avoiding repeats
        // get a random phrase from the list, seperated from the read file part
        Random rand = new Random();
        int r = rand.nextInt(phraseList.size()); // bounded by size of phraseList
        String phrase = phraseList.get(r);
        while (usedPhrases.contains(phrase)) { // added check repeat when fetching phrase
            r = rand.nextInt();
        }
        usedPhrases.add(phrase);
        return phrase;
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
        printInstructions();
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
        System.out.println("Would you like to play another round? Enter 1 for another round");
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
    protected void processGuess(String guess) { // processes a letter guess by player
        char guessedLetter = guess.charAt(0);
        if (pastGuesses.indexOf(guessedLetter) != -1) {
            System.out.println("You've already guessed this one! Try another.");
            return;
        }
        pastGuesses += guessedLetter;
        boolean correct = false;

        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == guessedLetter) {
                secret.setCharAt(i, guessedLetter);
                correct = true;
            }
        }

        if (correct) {
            System.out.println("Correct! Current phrase: " + secret);
        } else {
            chances--;
            System.out.println("Incorrect guess. Chances left: " + chances);
        }
    }


    @Override
    public void printInstructions() { // prints game instructions for user
        System.out.println("<---- Game Start ---->");
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
