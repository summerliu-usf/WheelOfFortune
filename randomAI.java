import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// ai player that guesses letters randomly
public class randomAI implements WOFPlayer {
    private String playerId;
    private List<Character> remainingLetters;
    private Random random;

    // constructor initializing player ID and random generator
    public randomAI(String playerId) {
        this.playerId = playerId;
        this.random = new Random();
        reset();
    }

    // returns a random letter that hasn't been guessed yet
    @Override
    public char nextGuess() {
        int index = random.nextInt(remainingLetters.size());
        char guess = remainingLetters.get(index);
        remainingLetters.remove(index);
        return guess;
    }

    // returns the player's ID
    @Override
    public String playerId() {
        return playerId;
    }

    // resets the remaining letters list to include all letters
    @Override
    public void reset() {
        remainingLetters = new ArrayList<>();
        for (char letter = 'a'; letter <= 'z'; letter++) {
            remainingLetters.add(letter);
        }
    }
}
