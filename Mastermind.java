import java.util.Random;

// mastermind game extending guessinggame
public class Mastermind extends GuessingGame {
    private final String[] colors = {"R", "G", "B", "Y", "O", "P"}; // available colors
    private String secretCode; // randomly generated secret code
    private int score; // score based on correct guesses

    public Mastermind(int maxAttempts) { // constructor initializes max attempts and secret code
        super(maxAttempts);
        this.secretCode = generateSecretCode();
        this.score = 0; // initialize score to zero
    }

    @Override
    public void printInstructions() { // brief instructions for mastermind game
        System.out.println("<---- Welcome to Mastermind ---->");
        System.out.println("Guess the 4-color code (R, G, B, Y, O, P).");
        System.out.println("Each guess gets feedback: 'Exact' or 'Partial'.");
        System.out.println("Match all colors and positions to win. Good luck!");
    }

    // generates a random secret code of 4 colors
    private String generateSecretCode() {
        Random rand = new Random();
        StringBuilder code = new StringBuilder();
        for (int i = 0; i < 4; i++) {
            code.append(colors[rand.nextInt(colors.length)]);
        }
        return code.toString();
    }

    @Override
    protected boolean isGameWon() { // checks if the game is won by checking secretCode match
        return score == 4; // winning condition: all colors guessed correctly in exact positions
    }

    @Override
    protected void processGuess(String guess) { // processes a guess, counts exact and partial matches
        int exactMatches = countExactMatches(guess);
        int partialMatches = countPartialMatches(guess);
        score = exactMatches; // update score with exact matches count for current guess
        System.out.println(exactMatches + " exact, " + partialMatches + " partial.");
    }

    // counts exact matches between guess and secret code
    private int countExactMatches(String guess) {
        int exactMatches = 0;
        for (int i = 0; i < 4; i++) {
            if (guess.charAt(i) == secretCode.charAt(i)) {
                exactMatches++;
            }
        }
        return exactMatches;
    }

    // counts partial matches (right color, wrong position) between guess and secret code
    private int countPartialMatches(String guess) {
        int partialMatches = 0;
        StringBuilder tempSecret = new StringBuilder(secretCode);
        StringBuilder tempGuess = new StringBuilder(guess);

        for (int i = 0; i < 4; i++) {
            if (tempGuess.charAt(i) == tempSecret.charAt(i)) {
                tempSecret.setCharAt(i, '-');
                tempGuess.setCharAt(i, '*');
            }
        }

        for (int i = 0; i < 4; i++) {
            if (tempGuess.charAt(i) != '*') {
                int index = tempSecret.indexOf(String.valueOf(tempGuess.charAt(i)));
                if (index != -1) {
                    partialMatches++;
                    tempSecret.setCharAt(index, '-');
                }
            }
        }
        return partialMatches;
    }

    @Override
    protected void endGame() { // prints final message and score
        if (isGameWon()) {
            System.out.println("Congratulations! You've guessed the secret code: " + secretCode);
        } else {
            System.out.println("Game over! The correct code was: " + secretCode);
        }
        System.out.println("Your final score: " + score);
    }

    @Override
    public boolean playNext() { // asks if the player wants to play another round
        System.out.print("Would you like to play another round? Enter 1 as yes ");
        String response = scanner.nextLine().trim().toLowerCase();
        return response.equals("yes");
    }

    // main method to initiate mastermind game
    public static void main(String[] args) {
        Mastermind game = new Mastermind(10);
        game.playAll();
    }
}
