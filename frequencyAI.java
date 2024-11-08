import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

// ai player that guesses letters based on english letter frequency
public class frequencyAI implements WOFPlayer {
    private String playerId;
    private List<Character> frequencyOrder;
    private int currentIndex;

    // constructor initializing player ID
    public frequencyAI(String playerId) {
        this.playerId = playerId;
        reset();
    }

    // returns the next letter in frequency order
    @Override
    public char nextGuess() {
        if (currentIndex < frequencyOrder.size()) {
            return frequencyOrder.get(currentIndex++);
        }
        return '?'; // fallback if all letters guessed
    }

    // returns the player's ID
    @Override
    public String playerId() {
        return playerId;
    }

    // resets index and sets letter frequency order
    @Override
    public void reset() {
        frequencyOrder = new ArrayList<>(Arrays.asList(
                'e', 't', 'a', 'o', 'i', 'n', 's', 'h', 'r', 'd', 'l', 'c', 'u', 'm', 'w', 'f', 'g', 'y', 'p', 'b', 'v', 'k', 'j', 'x', 'q', 'z'
        ));
        currentIndex = 0;
    }
}
