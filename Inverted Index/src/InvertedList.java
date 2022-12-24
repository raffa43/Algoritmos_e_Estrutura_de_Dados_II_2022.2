import java.util.ArrayList;

public class InvertedList<T> {
    InvertedNode<T> root;
    public InvertedList() {
        root = new InvertedNode<>(" ");
    }

    public void insert(String str){
        str = str.toLowerCase();
        String[] strs = str.split(" ");

        for(int i = 0; i < strs.length; i++) {
            if (!isWord(strs[i])) continue;
            InvertedNode<T> index = root;
            boolean indexed = false;
            while(index.getNextWord() != null){
                if(strs[i].equals(index.getKey())){
                    index.addString(str);
                    indexed = true;
                    break;
                }else{
                    index = index.getNextWord();
                }
            }
            if(!indexed){
                index.setNextWord(new InvertedNode<>(strs[i], str));
            }
        }
    }

    public ArrayList<String> getStrings(String str){
        str = str.toLowerCase();

        InvertedNode<T> index = root;
        while (index.getNextWord() != null){
            if(index.getKey().equals(str)) return index.getStrings();
            index = index.getNextWord();
        }

        return null;
    }

    public void listKeys(){
        InvertedNode<T> index = root;
        while (index.getNextWord() != null){
            index = index.getNextWord();
            System.out.println(index.getKey());
        }
    }

    public void removeKey(String key){
        InvertedNode<T> index = root;
        InvertedNode<T> parent = index;

        while (index.getNextWord() != null){
            if (index.getKey().equals(key.toLowerCase())){
                parent.setNextWord(index.getNextWord());
            }else{
                parent = index;
                index = index.getNextWord();
            }
        }
    }

    public void removeString(String str){
        str = str.toLowerCase();

        InvertedNode<T> index = root;
        while (index.getNextWord() != null){
            if (index.getStrings().contains(str)){
                index.removeString(str);
                return;
            }
            else index = index.getNextWord();
        }

    }

    private boolean isWord(String str){

        switch (str) {
            case "o", "a", "os", "as", "ao", "à", "aos", "às", "do", "da", "dos", "das", "no", "na", "nas", "nos", "pelo",
                    "pela", "pelos", "pelas", "um", "uma", "uns", "umas", "dum", "duma", "duns", "dumas", "num", "numa",
                    "nuns", "numas", "an", "the", "or", "if", "and" -> {
                return false;
            }
            default -> {return true;}
        }

    }
}
