package hangman;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class EvilHangmanGame implements IEvilHangmanGame {

    Set<String> currentWords = new TreeSet<>();
    Map<String, Set<String>> wordMap = new TreeMap<>();
    SortedSet<Character> usedLetters = new TreeSet<>();
    String pattern = "";
    int inputWordLen = 0;

    @Override
    public void startGame(File dictionary, int wordLength) throws EmptyDictionaryException {
        wordMap.clear(); // Make sure we are starting with a clean map if start game is called again
        currentWords.clear(); // Make sure the current words list gets cleared for a new game
        Scanner scan =  null;
        inputWordLen = wordLength;
        pattern = getFirstPattern();
        int numWords = 0;
        // Test to make sure the file is found
        try{
            scan = new Scanner(dictionary);
            // Add all of the words of input length to the currentWords set
            while(scan.hasNext()) {
                String currentWord = scan.next();
                if(currentWord.length() == inputWordLen){
                    currentWords.add(currentWord);
                    numWords++;
                }
            }
        }
        catch(FileNotFoundException error){
            error.printStackTrace();
        } finally {
            if (scan != null) scan.close();
        }
        if(dictionary.length() == 0 || numWords == 0){
            throw new EmptyDictionaryException("Empty dictionary found");
        }
    }

    public String getFirstPattern(){
        return "-".repeat(Math.max(0, inputWordLen));
    }

    @Override
    public Set<String> makeGuess(char guess) throws GuessAlreadyMadeException {
        guess = Character.toLowerCase(guess);
        if(usedLetters.contains(guess)){
            throw new GuessAlreadyMadeException("You have already guessed that letter!");
        }

        usedLetters.add(guess);
        buildMap(guess);
        String key = chooseKey(guess);
        currentWords = wordMap.get(key);
        return currentWords;
    }

    public void buildMap(char guess) {
        wordMap.clear();
        for(String word : currentWords){
            String tempPattern = createPattern(word, guess);
            if(wordMap.containsKey(tempPattern)){
                wordMap.get(tempPattern).add(word);
            }
            else{
                Set<String> newPattern = new TreeSet<>();
                newPattern.add(word);
                wordMap.put(tempPattern, newPattern);
            }
        }
    }

    public String createPattern(String word, char guess){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < word.length(); i++){
            if(word.charAt(i) == guess){
                stringBuilder.append(guess);
            }
            else if(pattern.charAt(i) != '-'){
                stringBuilder.append(pattern.charAt(i));
            }
            else{
                stringBuilder.append('-');
            }
        }
        return stringBuilder.toString();
    }

    public String chooseKey(char guess){
        String key = "";
        filterBySize();
        if(wordMap.size() > 1){
            filterByFrequency(guess);
            if(wordMap.size() > 1){
                filterByPosition();
                for(String s : wordMap.keySet()){
                    key = s;
                }
            }
            else {
                for(String s : wordMap.keySet()){
                    key = s;
                }
            }
        }
        else{
            for(String s : wordMap.keySet()){
                key = s;
            }
        }
        pattern = key;
        return key;
    }

    public void filterBySize(){
        int maxSize = 0;
        for(Map.Entry<String, Set<String>> entry : wordMap.entrySet()){
            if(entry.getValue().size() > maxSize){
                maxSize = entry.getValue().size();
            }
        }
        Set<String> filter = new TreeSet<>();
        for(Map.Entry<String, Set<String>> entry : wordMap.entrySet()){
            if(entry.getValue().size() != maxSize){
                filter.add(entry.getKey());
            }
        }
        wordMap.keySet().removeAll(filter);
    }

    public void filterByFrequency(char guess){
        int least = 1000; // Initialize least to be a large number
        for(Map.Entry<String, Set<String>> entry : wordMap.entrySet()){
            int frequency = 0;
            for(int i = 0; i < entry.getKey().length(); i++){
                if(entry.getKey().charAt(i) == guess){
                    frequency++;
                }
            }
            if(frequency < least){
                least = frequency;
            }
        }

        Set<String> filter = new TreeSet<>();
        for(Map.Entry<String, Set<String>> entry : wordMap.entrySet()){
            int frequency = 0;
            for(int i = 0; i < entry.getKey().length(); i++){
                if(entry.getKey().charAt(i) == guess){
                    frequency++;
                }
            }
            if(frequency != least){
                filter.add(entry.getKey());
            }
        }
        wordMap.keySet().removeAll(filter);
    }

    public void filterByPosition(){
        String comparing = "~";
        for(Map.Entry<String, Set<String>> entry: wordMap.entrySet()){
            String current = entry.getKey();
            if(current.compareTo(comparing) < 0){
                comparing = current;
            }
        }
        Set<String>filter = new TreeSet<>();
        for(Map.Entry<String, Set<String>> entry: wordMap.entrySet()){
            if(!entry.getKey().equals(comparing)){
                filter.add(entry.getKey());
            }
        }
        wordMap.keySet().removeAll(filter);
    }

    @Override
    public SortedSet<Character> getGuessedLetters() {
        return usedLetters;
    }

    public String getPattern() {
        return pattern;
    }
}
