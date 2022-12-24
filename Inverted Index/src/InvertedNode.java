import java.util.ArrayList;

public class InvertedNode<T> {
    ArrayList<String> strings;
    String key;
    InvertedNode<T> nextWord;

    public InvertedNode(String word) {
        this.key = word;
        strings = new ArrayList<>();
    }

    public InvertedNode(String word, String str) {
        this.key = word;
        strings = new ArrayList<>();
        strings.add(str);
    }

    public void addString(String string){
        strings.add(string);
    }

    public void removeString(String string){
        strings.remove(string);
    }

    public ArrayList<String> getStrings() {
        return strings;
    }

    public void setStrings(ArrayList<String> words) {
        this.strings = words;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String index) {
        this.key = index;
    }

    public InvertedNode<T> getNextWord() {
        return nextWord;
    }

    public void setNextWord(InvertedNode<T> nextWord) {
        this.nextWord = nextWord;
    }
}
