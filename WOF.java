import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public abstract class WOF extends Game { // abstract class WOF which extends game
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
    protected int phraseIndex;
    protected int correctGuessNum;

    public WOF() { // constructor
        this.players = new ArrayList<>();
        this.allRecords = new AllGamesRecord();
        this.pastGuesses = "";
        this.chances = 5;
        this.incorrectGuesses = "";
        this.phraseList = readFile();
        this.id = "";
        this.usedPhrases = new ArrayList<>();
        this.phraseIndex = 0;
        this.correctGuessNum = 0;
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

    public String randomPhrase() { // separated from original randomPhrase so that each call gets new phrase without reading file again
        Random rand = new Random();
        int r = rand.nextInt(phraseList.size()); // safer phrase list selection
        return phraseList.get(r);
    }

    public StringBuilder generateHiddenPhrase(String phrase) { // generates the hidden phrase
        StringBuilder secret = new StringBuilder();
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == ' ') {
                secret.append(" ");
            } else {
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
        // returns T/F for whether letter is a match and modifies hidden phrase and counts score
        boolean found = false;
        for (int i = 0; i < phrase.length(); i++) {
            if (phrase.charAt(i) == guess || phrase.charAt(i) == Character.toUpperCase(guess)) {
                secret.setCharAt(i, phrase.charAt(i));
                found = true;
                correctGuessNum++;
            }
        }
        return found;
    }


    // placeholder/demonstration method for main
    private void startGame() {
        printInstructions();
        playGame();
    }


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
