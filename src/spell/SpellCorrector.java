package spell;

import java.io.File;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;

public class SpellCorrector implements ISpellCorrector {
    Trie dictionary;
    public SpellCorrector() {
         dictionary = new Trie();
    }

    @Override
    public void useDictionary(String dictionaryFileName) throws IOException {
        File text = new File(dictionaryFileName);
        Scanner scnr = new Scanner(text);

        while(scnr.hasNextLine()){
            String line = scnr.next();
            dictionary.add(line);
        }
    }

    @Override
    public String suggestSimilarWord(String inputWord) {
        TrieNode word = (TrieNode) dictionary.find(inputWord);
        if (word != null) return inputWord.toLowerCase();

        Set<String> possibleWords = new TreeSet<>();
        findSimilarWords(inputWord,possibleWords);

        Set<String> wordCandidates = findRealWords(possibleWords);

        if (wordCandidates.size() > 1) return breakTie(wordCandidates);
        else if (wordCandidates.size() == 1) for (String candidate : wordCandidates) return candidate;
        else {
            Set<String> possibleWords2 = new TreeSet<>();
            for (String currentString : possibleWords) findSimilarWords(currentString,possibleWords2);

            wordCandidates = findRealWords(possibleWords2);
            if (wordCandidates.size() > 1) return breakTie(wordCandidates);
            else if (wordCandidates.size() == 1) for (String candidate : wordCandidates) return candidate;
        }

        return null;
    }

    private void findSimilarWords(String inputWord, Set<String> possibleWords) {
        deletionDistance(inputWord,possibleWords);
        transpositionDistance(inputWord,possibleWords);
        alterationDistance(inputWord,possibleWords);
        insertionDistance(inputWord,possibleWords);
    }

    private void deletionDistance(String inputWord, Set<String> possibleWords) {

    }
    private void transpositionDistance(String inputWord, Set<String> possibleWords) {

    }
    private StringBuilder swapCharacter(StringBuilder word, int i, int j) {
        return null;
    }
    private void alterationDistance(String inputWord, Set<String> possibleWords) {

    }
    private void insertionDistance(String inputWord, Set<String> possibleWords) {
            //for (char j = a; j <= z; ++j)
    }

    private Set<String> findRealWords (Set<String> possibleWords) { //return new set that contains values from possibleWords that are also found in dictionary
        return null;
    }

    private String breakTie(Set<String> possibleWords) {
        String similarWord = "";
        return similarWord;
    }
}
