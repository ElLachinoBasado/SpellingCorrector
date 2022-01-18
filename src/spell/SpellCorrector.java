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
        for (int i = 0; i < inputWord.length(); i++) {
            StringBuilder toAdd = new StringBuilder(inputWord);
            toAdd.deleteCharAt(i);
            possibleWords.add(toAdd.toString());
        }
    }
    private void transpositionDistance(String inputWord, Set<String> possibleWords) {
        for (int i = 0; i < inputWord.length() - 1; i++) {
            StringBuilder toAdd = new StringBuilder(inputWord);
            toAdd = swapCharacter(toAdd,i,i+1);
            possibleWords.add(toAdd.toString());
        }
    }

    private StringBuilder swapCharacter(StringBuilder word, int i, int j) {
        char iChar = word.charAt(i);
        char jChar = word.charAt(j);
        word.setCharAt(i,jChar);
        word.setCharAt(j,iChar);
        return word;
    }

    private void alterationDistance(String inputWord, Set<String> possibleWords) {
        for (int i = 0; i < inputWord.length(); i++) {
            for (char j = 'a'; j <= 'z'; j++) {
                StringBuilder toAdd = new StringBuilder(inputWord);
                toAdd.setCharAt(i,j);
                possibleWords.add(toAdd.toString());
            }
        }
    }

    private void insertionDistance(String inputWord, Set<String> possibleWords) {
        for (char c = 'a'; c <= 'z'; c++) {
            StringBuilder toAdd = new StringBuilder(inputWord + c);
            possibleWords.add(toAdd.toString());
            toAdd = new StringBuilder(c + inputWord);
            possibleWords.add(toAdd.toString());
        }

        for (int i = 0; i < inputWord.length()-1; i++) {
            for (char c = 'a'; c <= 'z'; c++) {
                StringBuilder toAdd = new StringBuilder(inputWord.substring(0,i+1) + c + inputWord.substring(i+1,inputWord.length()));
                possibleWords.add(toAdd.toString());
            }
        }
    }


    private Set<String> findRealWords (Set<String> possibleWords) { //return new set that contains values from possibleWords that are also found in dictionary
        Set<String> realWords = new TreeSet<>();
        for (String nextCandidate : possibleWords) {
            if (dictionary.find(nextCandidate) != null) {
                realWords.add(nextCandidate);
            }
        }
        return realWords;
    }

    private String breakTie(Set<String> possibleWords) {
        StringBuilder mostSimilar = new StringBuilder();
        int[] setOfCounts = new int[possibleWords.size()];

        int currentHighest = 0;
        for (String candidate : possibleWords) {
            int currentCandidateCount = dictionary.find(candidate).getValue();
            if (currentCandidateCount > currentHighest) {
                currentHighest = currentCandidateCount;
                mostSimilar = new StringBuilder(candidate);
            }
        }

        return mostSimilar.toString();
    }
}
