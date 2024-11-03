import java.util.HashMap;
import java.util.Stack;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        testStack();
    }

    public static void testStack(){
        Stack<String> stack = new Stack<>();
        stack.push("1");
        stack.push("2");
        System.out.println(stack.pop());
        System.out.println(stack.pop());
    }

    public static String BitmapHoles(String[] strArr) {
        int[][] matrix = new int[strArr.length][strArr[0].length()];
        int[][] visitted = new int[strArr.length][strArr[0].length()];
        int countHoles = 0;
        int index = 0;

        // code goes here
        for (String s: strArr){
            for (int j=0; j < s.length(); j++){
                matrix[index][j] = Integer.parseInt("" + s.charAt(j));
            }

            index++;
        }

        for (int i=0; i < matrix.length; i++){
            for (int j=0; j < matrix[0].length; j++){
                if (matrix[i][j] == 0 && visitted[i][j] != 1){
                    countHoles++;
                    markAroundVisited(i, j, matrix);
                }
            }
        }

        return ""+countHoles;
    }

    public static void markAroundVisited(int i, int j, int[][] matrix ){
        matrix[i][j] = 1;

        // up
        if ( (i > 0) && (matrix[i-1][j] == 0) ){
            matrix[i-1][j] = 1;
            markAroundVisited(i-1, j, matrix);
        }

        //down
        if ( i < matrix.length - 1 && matrix[i + 1][j] == 0 ){
            matrix[i + 1][j] = 1;
            markAroundVisited(i+1, j, matrix);
        }

        //right
        if ( j < matrix[0].length - 1 && matrix[i][j+1] == 0 ){
            matrix[i][j+1] = 1;
            markAroundVisited(i, j+1, matrix);
        }

        //left
        if ( j > 0 && matrix[i][j-1] == 0  ){
            matrix[i][j-1] = 1;
            markAroundVisited(i, j-1, matrix);
        }

    }

    static void bubbleSort(int arr[], int n){
        int i, j, temp;
        boolean swapped;
        for (i = 0; i < n - 1; i++) {
            swapped = false;
            for (j = 0; j < n - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {

                    // Swap arr[j] and arr[j+1]
                    temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    swapped = true;
                }
            }

            // If no two elements were
            // swapped by inner loop, then break
            if (swapped == false)
                break;
        }
    }

    public static int Consecutive(int[] arr) {
        bubbleSort(arr, arr.length);
        int sum = 0;

        for (int i=0; i < arr.length-1; i++){
            if ( arr[i] + 1 != arr[i + 1] ){
                sum += arr[i+1] - arr[i] - 1;
            }
        }


        // code goes here
        return sum;
    }

    public static String StringReduction(String str) {
        if (str.length() == 0){
            return "0";
        }

        if (str.length() == 1){
            return "1";
        }

        HashMap<String, Character> dictionary = new HashMap<>();
        dictionary.put("ab", 'c');
        dictionary.put("ba", 'c');
        dictionary.put("bc", 'a');
        dictionary.put("cb", 'a');
        dictionary.put("ac", 'b');
        dictionary.put("ca", 'b');
        int reductions = Integer.MAX_VALUE;
        char[] characters = new char[str.length()];

        for (int i=0; i < characters.length; i++ ){
            characters[i] = str.charAt(i);
        }

        while (reductions > 0 ) {
            reductions = 0;
            String strCharacters = new String(characters);

            for (int i=0; i < strCharacters.length() - 1; i++){
                StringBuilder sb = new StringBuilder();
                sb.append(strCharacters.charAt(i));
                sb.append(strCharacters.charAt(i+1));

                if (dictionary.containsKey(sb.toString())){
                    System.out.println(sb.toString());
                    characters[i] = dictionary.get(sb.toString());
                    characters[i+1] = '@';
                    reductions++;
                    strCharacters = new String(characters);
                    strCharacters = strCharacters.replace("@", "");
                    characters = strCharacters.toCharArray();
                    break;
                }
            }


            //System.out.println(strCharacters);
        }

        int l = new String(characters).length();


        // code goes here
        return (""+l).trim();
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