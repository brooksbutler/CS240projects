package spell;

import java.io.IOException;
import java.io.File;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector {

    Trie Trie = new Trie();

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        String word;
        File inputFile = new File(dictionaryFileName);
        Scanner scan = new Scanner(inputFile);
        while(scan.hasNext()) {
            word = scan.next().toLowerCase();
            Trie.add(word);
        }
        scan.close();
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        Set<String> firstEdit = new TreeSet<>();
        inputWord = inputWord.toLowerCase();

        if (Trie.find(inputWord) != null){
            return inputWord;
        }
        else {
            doDeletion(inputWord, firstEdit);
            doTransposition(inputWord, firstEdit);
            doAlteration(inputWord, firstEdit);
            doInsertion(inputWord, firstEdit);

            String suggestedWord = suggestWord(firstEdit);
            if (suggestedWord == null) {
                Set<String> secondEdit = new TreeSet<>();

                for(String s: firstEdit){
                    doInsertion(s, secondEdit);
                    doDeletion(s, secondEdit);
                    doTransposition(s, secondEdit);
                    doAlteration(s, secondEdit);
                }

                suggestedWord = suggestWord(secondEdit);
            }
            return suggestedWord;
        }
    }

    public String suggestWord(Set<String> editSet) {
        int maxFrequency = 0;
        String suggested = null;
        for(String s : editSet) {
            if(Trie.find(s) != null) {
                INode node =  Trie.find(s);
                if(node.getValue() > maxFrequency){
                    maxFrequency = node.getValue();
                    suggested = s;
                }
            }
        }

        return suggested;
    }

    public void doDeletion(String word, Set<String> editSet) {
        for(int i = 0; i < word.length(); i++) {
            StringBuilder stringBuilder = new StringBuilder(word);
            stringBuilder.deleteCharAt(i);
            String stringBuilderWord = stringBuilder.toString();
            editSet.add(stringBuilderWord);
        }
    }

    public void doTransposition(String word, Set<String> editSet){
        char[] c = word.toCharArray();
        for (int i = 0; i < word.length()-1; i++){
            char temp = c[i];
            c[i] = c[i+1];
            c[i+1] = temp;
            String transposedWord = new String(c);
            editSet.add(transposedWord);
            c = word.toCharArray();
        }
    }

    public void doAlteration(String word, Set<String> editSet){
        char[] alterString = word.toCharArray();
        for(int i = 0; i < word.length(); i++){
            for(char c = 'a'; c <= 'z'; c++){
                alterString[i] = c;
                String alteredWord = new String(alterString);
                editSet.add(alteredWord);
            }
            alterString = word.toCharArray();
        }
    }

    public void doInsertion(String word, Set<String> editSet){
        for(int i = 0; i < word.length() + 1; i++){
            for(char c = 'a'; c <= 'z'; c++){
                StringBuilder stringBuilder = new StringBuilder(word);
                stringBuilder.insert(i, c);
                String stringBuilderWord = stringBuilder.toString();
                editSet.add(stringBuilderWord);
            }
        }
    }



}
