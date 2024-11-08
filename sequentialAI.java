// ai player that guesses letters sequentially from 'a' to 'z'
public class sequentialAI implements WOFPlayer {
    private String playerId;
    private int currentIndex;
    private final char[] alphabet = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    // constructor initializing player ID
    public sequentialAI(String playerId) {
        this.playerId = playerId;
        reset();
    }

    // returns the next letter in alphabetical order
    @Override
    public char nextGuess() {
        if (currentIndex < alphabet.length) {
            return alphabet[currentIndex++];
        }
        return '?'; // fallback if all letters guessed
    }

    // returns the player's ID
    @Override
    public String playerId() {
        return playerId;
    }

    // resets the index to start guessing from 'a' again
    @Override
    public void reset() {
        currentIndex = 0;
    }
}
