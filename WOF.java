import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public abstract class WOF extends Game{ // abstract class WOF which extends game
    public String phrase;
    public StringBuilder secret;
    public String pastGuesses;
    protected ArrayList<String> usedPhrases;
    private int chances = 5;
    private String incorrectGuesses = "";
    private String id;


    public WOF() { // constructor
        this.phrase = randomPhrase();
        this.secret = generateHiddenPhrase(this.phrase);
        this.pastGuesses = "";
        this.usedPhrases = new ArrayList<String>(); // added used phrase list
        this.chances = chances;
    }

    public abstract GameRecord play(); // plays one game. To be implemented by subclasses.

    public abstract boolean playNext(); // checks if the next game should be played.

    public String randomPhrase() {// Get the phrase from a file of phrases
        List<String> phraseList = null;

        try {
            phraseList = Files.readAllLines(Paths.get("phrases.txt"));
        } catch (IOException e) {
            System.out.println(e);
        }

        // Get a random phrase from the list
        Random rand = new Random();
        int r = rand.nextInt(phraseList.size()); // bounded by size of phraseList
        String phrase = phraseList.get(r);
        while (usedPhrases.contains(phrase)) { // added check repeat when fetching phrase
            r = rand.nextInt();
        }
        usedPhrases.add(phrase);
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

    public AllGamesRecord playAll() { // plays a set of games and records results.
        while (playNext()) {
            GameRecord gameRecord = play();
            allRecords.add(gameRecord);
        }
        return allRecords;
    }

    // Starts the game by printing instructions and running the main game loop
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

    // main game loop
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
    private void processGuess(char g) {
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
    private void gameOver() {
        System.out.println("Oops too many wrong guesses, game over!");
        System.out.println("<----Game Over---->");
    }

    public GameRecord getGameRecord(){
        return new GameRecord(chances, id);
    }

}
