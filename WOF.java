import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public abstract class WOF extends Game{ // abstract class WOF which extends game
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
    private int phraseIndex;

    public WOF() { // constructor
        this.players = players;
        this.allRecords = new AllGamesRecord();
        this.pastGuesses = "";
        this.chances = 5;
        this.incorrectGuesses = "";
        this.phraseList = readFile();
        this.id = id;
        this.usedPhrases = new ArrayList<String>();
        this.phraseIndex = 0;
    }

    public abstract GameRecord play(); // plays one game. To be implemented by subclasses.

    public abstract boolean playNext(); // checks if the next game should be played.

    public List<String> readFile() { // reads phrases from file and returns as a list
        List<String> phrases = new ArrayList<>();
        try {
            phrases = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println("Error reading phrases file: " + e.getMessage());
        }
        return phrases;
    }

    public String randomPhrase() { // seperated from original randomPhrase so that each call gets new phrase without reading file again
        Random rand = new Random();
        int r = rand.nextInt(3); // gets 0, 1, or 2
        String phrase = phraseList.get(r);
        return phrase;
    }

    public StringBuilder generateHiddenPhrase(String phrase) { // generates the hidden phrase
        StringBuilder secret = new StringBuilder();
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == ' ') {
                secret.append(" ");
            }
            else {
                secret.append("*");
            }
        }
        return secret;
    }

    public char getGuess() { // gets guess from user
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter a letter you would like to guess: ");
        String input = in.nextLine();
        char guess = input.charAt(0);
        while (!Character.isLetter(guess)) {
            System.out.println("You entered an invalid guess, please only input letters");
            input = in.nextLine();
            guess = input.charAt(0);
        }
        return guess;
    }

    public Boolean updateGuess(String phrase, StringBuilder secret, char guess) {
        // returns T/F for whether letter is a match and modifies hidden phrase
        boolean found = false;
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == guess || phrase.charAt(i) == Character.toUpperCase(guess)) {
                secret.setCharAt(i, phrase.charAt(i));
                found = true;
            }
        }
        return found;
    }

    public AllGamesRecord playAll() { // checks if human or ai player, then plays a set of games and records results.
        printInstructions();
        this.phrase = randomPhrase();
        this.secret = generateHiddenPhrase(phrase);
        while (!phraseList.isEmpty()) {
            String phrase = randomPhrase();
            this.phrase = phrase;
            this.secret = generateHiddenPhrase(phrase);

            if (players == null || players.isEmpty()) {
                while (playNext()) {
                    GameRecord record = play();
                    allRecords.add(record);
                }
            } else {
                for (WOFPlayer player : players) {
                    player.reset();
                    this.secret = generateHiddenPhrase(phrase);
                    allRecords.add(play());
                }
            }
        }
        return allRecords;
    }

    // placeholder/demonstration method for main
    private void startGame() {
        printInstructions();
        playGame();
    }

    // prints game instructions and the initial hidden phrase
    public abstract void printInstructions();
//        System.out.println("<----Game Start---->");
//        System.out.println("Instructions: This is a hangman-like game but with a sentence.");
//        System.out.println("Enter 1 letter at a time please! Only the first letter will be recognized.");
//        System.out.println("Hidden phrase: " + secret);

    // game execution
    public void playGame() {
        while (!secret.toString().equals(phrase)) {
            char g = getGuess();
            if (pastGuesses.indexOf(g) != -1) {
                System.out.println("You've already guessed this one! Try another");
                continue;
            }
            pastGuesses += g;
            processGuess(g);

            if (chances == 0) {
                gameOver();
                return;
            }
        }
        System.out.println("<----Congrats! All done---->");
    }

    // processes the result of a guess
    protected void processGuess(char g) {
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

    // prints game over message
    protected void gameOver() {
        System.out.println("Oops too many wrong guesses, game over!");
        System.out.println("<----Game Over---->");
    }

}
