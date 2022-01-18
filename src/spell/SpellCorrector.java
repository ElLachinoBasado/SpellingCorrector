package spell;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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

        return null;
    }
}
