import java.util.Scanner;

public class WOFUserGame extends WOF { // concrete implementation of WOF for human player
    private Scanner scanner;
    private String id;

    public WOFUserGame() { // constructor
        super();
        this.scanner = new Scanner(System.in);// Initialize id to an empty string
    }

    @Override
    public boolean playNext() {  // checks if the next game should be played
        System.out.println("Enter 1 to start a new round");
        String response = scanner.nextLine();
        if (response.indexOf('1') == -1) {
            System.out.println("Sad to see you go :( Here are your results: ");
            System.out.println(allRecords);
            System.out.println("Exiting...");
            return false; // Return false instead of System.exit(0) for graceful exit
        }
        System.out.println("Starting a new round...");
        return true;
    }

    @Override
    public GameRecord play() { // plays one game
        super.playGame();
        return new GameRecord(chances, id);
    }

    @Override
    public char getGuess() { // gets a single character guess from the user.
        System.out.print("Please enter a letter you would like to guess: ");
        String input = scanner.nextLine();
        while (input.isEmpty() || input.length() > 1 || !Character.isLetter(input.charAt(0))) { // improved input check mechanism
            System.out.print("Invalid input. Please enter a single letter: ");
            input = scanner.nextLine();
        }
        return Character.toLowerCase(input.trim().charAt(0)); // added trim
    }

    @Override
    public void printInstructions() { // overrode instructions for human player to enter id
        System.out.println("<----Game Start---->");
        System.out.println("Instructions: This is a hangman-like game but with a sentence.");
        System.out.print("Enter your name to keep a record of your score: ");
        id = scanner.nextLine();
        if (allRecords.checkUser(id)) { // UI for existing vs. new users
            System.out.println("Hello again " + id + "! Welcome back.");
        } else {
            System.out.println("Okay " + id + ", let's start!");
        }
        phrase = randomPhrase();
        secret = generateHiddenPhrase(phrase);
        System.out.println("Enter 1 letter at a time please! Only the first letter will be recognized.");
        System.out.println("Hidden phrase: " + secret);
    }

    public static void main(String[] args) {
        WOFUserGame wofUserGame = new WOFUserGame();
        wofUserGame.playAll();
        System.out.println(wofUserGame.allRecords.highGameList(3));
        System.out.println(wofUserGame.allRecords.average());
    }
}
