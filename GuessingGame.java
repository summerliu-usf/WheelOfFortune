import java.util.Scanner;

// superclass for general game functionality, extends game
public abstract class GuessingGame extends Game {
    protected int maxAttempts; // max number of attempts allowed
    protected int attempts; // current number of attempts
    protected Scanner scanner; // scanner for user input
    protected int score;
    protected String id;

    public GuessingGame(int maxAttempts) { // constructor to initialize attempts and scanner
        super();
        this.maxAttempts = maxAttempts;
        this.attempts = 0;
        this.scanner = new Scanner(System.in);
        this.score = score;
        this.id = id;
    }

    // plays one game
    @Override
    public GameRecord play() {
        attempts = 0; // reset attempts for each game
        while (attempts < maxAttempts && !isGameWon()) {
            System.out.println("Attempt " + (attempts + 1) + " of " + maxAttempts);
            String guess = getUserGuess();
            processGuess(guess);
            attempts++;
        }
        return new GameRecord(score, id);
    }

    // checks if the next game should be played, to be overridden by subclasses
    @Override
    public abstract boolean playNext();

    // gets a guess from the user
    protected String getUserGuess() {
        System.out.print("Enter your guess: ");
        return scanner.nextLine().trim().toUpperCase();
    }


    // checks if the game is won
    protected abstract boolean isGameWon();

    // processes a guess
    protected abstract void processGuess(String guess);

    // ends the game and prints game record for the game result
    protected abstract void endGame();

    public abstract void printInstructions();
}
