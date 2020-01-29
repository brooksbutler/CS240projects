package hangman;

public class GuessAlreadyMadeException extends Exception {
    GuessAlreadyMadeException(String s){
        super(s);
    }
}
