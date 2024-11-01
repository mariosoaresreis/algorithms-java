import java.util.HashMap;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.printf("Hello and welcome!");

        for (int i = 1; i <= 5; i++) {
            //TIP Press <shortcut actionId="Debug"/> to start debugging your code. We have set one <icon src="AllIcons.Debugger.Db_set_breakpoint"/> breakpoint
            // for you, but you can always add more by pressing <shortcut actionId="ToggleLineBreakpoint"/>.
            System.out.println("i = " + i);
        }
    }

    public static String MinWindowSubstring(String[] strArr) {
        // code goes here
        HashMap<Character, Integer> hashCharacters = new HashMap<>();
        HashMap<Character, Integer> hashWord = new HashMap<>();
        String characteres  = strArr[1];
        String word = strArr[0];
        String result = "";
        int left = 0;
        Integer minSize = Integer.MAX_VALUE;
        int match = 0;

        for (int i = 0; i < characteres.length(); i++){
            Character letter = characteres.charAt(i);
            hashCharacters.put(letter, hashCharacters.getOrDefault(letter, 0) + 1);
        }

        for (int right = 0; right < word.length(); right++){
            Character rightChar = word.charAt(right);
            hashWord.put(rightChar,   hashWord.getOrDefault(rightChar, 0) + 1);
            boolean ok = true;

            if (hashWord.size() >= hashCharacters.size() ) {
                ok = validate(hashCharacters, hashWord);

                if (ok && right - left + 1 < minSize){
                    minSize = right - left + 1;
                    StringBuffer sb = new StringBuffer();

                    for (int i=left; i <= right; i++  ){
                        sb.append(word.charAt(i));
                    }

                    result = sb.toString();
                }

                while ( ok && hashWord.size() >= hashCharacters.size() ){
                    Character leftChar = word.charAt(left);

                    if (hashWord.getOrDefault(leftChar, 0) >= 1){
                        hashWord.put(leftChar, hashWord.getOrDefault(leftChar, 0) - 1);

                        if (hashWord.getOrDefault(leftChar, 0).equals(0)){
                            hashWord.remove(leftChar);
                        }
                    }

                    left++;

                    ok = validate(hashCharacters, hashWord);

                    if (ok && right - left + 1 < minSize){
                        minSize = right - left + 1;
                        StringBuffer sb = new StringBuffer();

                        for (int i=left; i <= right; i++  ){
                            sb.append(word.charAt(i));
                        }

                        result = sb.toString();
                    }
                }
            }


        }

        return result;
    }

    public static boolean validate(HashMap<Character, Integer> hashCharacters,  HashMap<Character, Integer> hashWord){
        boolean ok = true;

        for ( Character key : hashCharacters.keySet()){
            if (hashCharacters.getOrDefault(key, 0).intValue() > 0 &&
                    hashWord.getOrDefault(key, 0).intValue() < hashCharacters.getOrDefault(key, 0).intValue()   ){
                ok = false;
                break;
            }
        }

        return ok;
    }
}