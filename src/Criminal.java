import java.util.HashMap;
import java.util.Map;
import java.util.PriorityQueue;

public class Criminal {
    final Map<String, String> criminals = new HashMap<>();
    final PriorityQueue<Integer> result = new PriorityQueue<>();
    final Map<Integer, String> resultMap = new HashMap<>();

    public Criminal(){
        criminals.put("Marylin Maroon", "Joe Carlos, Black Bird, John Space");
        criminals.put("Adam Driver", "adam joe, Marylin Manson, mjay");
        criminals.put("Marylin Manson", "Dead, crow, joe");
    }

    private static boolean matchesExact(String word1, String word2){
        return word1.trim().equalsIgnoreCase(word2.trim());
    }

    private static boolean matchesPartial(String word1, String word2){
        return word1.trim().toUpperCase().contains(word2.trim().toUpperCase());
    }
    
    private String formatName(String key){
        return String.format("Name: %s:%s", getFirstName(key), criminals.get(key));
    }

    private String getFirstName(String key) {
        String[] array = key.split(" ");
        return array[0].trim().toUpperCase();
    }

    private void find(String name){
        if (criminals.containsKey(name)){
           addResult(name, 1);
        }else {
            for (String key: criminals.keySet()) {
                if (matchesExact(key, name)) {
                    addResult(key, 2);
                }

                if (matchesPartial(key, name)) {
                    addResult(key, 3);
                }

                if (matchesExactOneOfAlias(name, key)) {
                    addResult(key, 4);
                }

                if (matchesPartial(criminals.get(key), name)) {
                   addResult(key,5);
                }
            }
        }

        System.out.println(formatName(resultMap.get(result.peek())));
    }

    private void addResult(String key, Integer resultNumber){
        this.result.add(resultNumber);
        this.resultMap.put(resultNumber, key);
    }

    private boolean matchesExactOneOfAlias(String name, String key){
        String aliases = criminals.get(key);
        String[] aliasesArray = aliases.split(" ");

        for (String s: aliasesArray){
            if ( matchesExact(name, s) ){
                return true;
            }
        }

        return false;
    }

    public static String largestPalindromic(String num) {
        final StringBuilder firstHalf = new StringBuilder();
        final StringBuilder secondHalf = new StringBuilder();
        int[] frequency = new int[10];
        StringBuilder center = new StringBuilder();

        for (int i=0; i < num.length(); i++){
            int val = num.charAt(i) - '0';
            frequency[val]++;
        }

        for (int i=0; i < frequency.length; i++){
            if (frequency[i] == 0){
                continue;
            }

            if (frequency[i]%2 != 0){
                if (center.isEmpty()) {
                    while (frequency[i] > 0) {
                        center.append(i);
                        frequency[i]--;
                    }
                }else {
                    while (frequency[i] > 1) {
                        if (frequency[i] % 2 == 0){
                            firstHalf.append(i);
                        }else {
                            secondHalf.append(i);
                        }
                        frequency[i]--;
                    }
                }

                continue;
            }

            while( frequency[i] > 0 ) {
                firstHalf.append(i);
                frequency[i]--;

                if (frequency[i] == 0) {
                    continue;
                }

                secondHalf.append(i);
                frequency[i]--;
            }
        }

        return firstHalf.toString() + center + secondHalf.reverse();
    }

    public static void main(String[] args){
       System.out.println(largestPalindromic("444947137"));
    }
}
