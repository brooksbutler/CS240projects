package hangman;

import java.io.File;
import java.util.Scanner;
import java.util.Set;

public class EvilHangman {

    public static void main(String[] args) {
        String dictionary;
        int wordLength;
        int guesses;
        if(args.length == 3) {
            dictionary = args[0];
            wordLength = Integer.parseInt(args[1]);
            guesses = Integer.parseInt(args[2]);

            EvilHangmanGame game = new EvilHangmanGame();
            try{
                game.startGame(new File(dictionary), wordLength);
                runGame(game, guesses);
            }
            catch (EmptyDictionaryException e){
                e.printStackTrace();
            }
        }
        else{
            System.out.println("Use this format: java [main class name] dictionary wordlength guesses");
        }
    }

    private static void runGame(EvilHangmanGame game, int guesses) {
        Set<Character> guessedLetters = game.getGuessedLetters();
        String pattern = game.getFirstPattern();
        printGame(guesses, guessedLetters,pattern);
        Scanner input = new Scanner(System.in);
        String inputLine;

        while(guesses > 0){
            Set<String> results;
            inputLine = input.nextLine().toLowerCase();
            char guess = 0;
            if(inputLine.length() > 0){
                guess = inputLine.charAt(0);
            }
            while(!Character.isAlphabetic(guess) || inputLine.length() != 1){
                System.out.println("Invalid input.\n");
                printGame(guesses, guessedLetters, pattern);
                inputLine = input.nextLine().toLowerCase();
                if(inputLine.length() > 0){
                    guess = inputLine.charAt(0);
                }
            }

            try {
                results = game.makeGuess(guess);
                pattern = game.getPattern();
                if(pattern.contains(String.valueOf(guess))){
                    int numchars = 0;
                    for(int i = 0; i < pattern.length(); i++){
                        if(pattern.charAt(i) == guess){
                            numchars++;
                        }
                    }
                    printResponse(true, guess, numchars);
                    guesses++;
                }
                else{
                    printResponse(false, guess, 0);
                }

            }
            catch (GuessAlreadyMadeException error){
                System.out.println("You've already guessed that.\n");
                printGame(guesses, guessedLetters, pattern);
                continue;
            }
            int count = 0;
            for(int i = 0; i < pattern.length(); i++){
                if(pattern.charAt(i) != '-'){
                    count++;
                }
            }
            if(count == pattern.length()){
                printWon(results.toString());
                break;
            }
            guesses--;
            if(guesses == 0){
                String word = results.iterator().next();
                printLost(word);
            }
            printGame(guesses, guessedLetters, pattern);
        }
        input.close();
    }

    private static void printGame(int guesses, Set<Character> guessedLetters, String pattern) {
        System.out.println("You have " + guesses + " guesses left");
        StringBuilder stringBuilder = new StringBuilder();
        for (Character c : guessedLetters) {
            stringBuilder.append(c);
            stringBuilder.append(" ");
        }
        System.out.println("Used letters: " + stringBuilder.toString());
        System.out.println("Word: " + pattern);
        System.out.print("Enter guess: ");
    }

    private static void printResponse(boolean contains, Character guess, int numbers) {
        if(contains){
            System.out.println("Yes, there is " + numbers + " " + guess + "\n");
        }
        else{
            System.out.println("Sorry, there are no " + guess + "'s" + "\n");
        }
    }

    private static void printWon(String correct){
        System.out.println("You win! " + correct);
        System.exit(0);
    }

    private static void printLost(String correct){
        System.out.println("You lose!");
        System.out.println("The word was: " + correct);
        System.exit(0);
    }
}
