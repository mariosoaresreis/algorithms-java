import java.util.*;

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

        for (int i=frequency.length-1; i >= 0; i--){
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

    public static int minMoves(int[] nums) {
        int smallest = Integer.MAX_VALUE;
        int indexSmallest = -1;
        int indexLargest = -1;
        int largest = Integer.MIN_VALUE;

        for (int i=0; i < nums.length; i++){
            if (nums[i] > largest){
                indexLargest = i;
            }

            if (nums[i] < smallest){
                indexSmallest = i;
            }
            largest = max(largest, nums[i]);
            smallest = min(smallest, nums[i]);

        }

        int resultMax = nums.length - max(indexSmallest, indexLargest);

        if (indexSmallest > indexLargest){
            resultMax += indexLargest - indexSmallest;
        }else {
            resultMax += indexSmallest - indexLargest;
        }

        int resultMin =  min(indexLargest, indexSmallest) + 1;

        if (indexLargest > indexSmallest){
            resultMin += indexLargest - indexSmallest;
        }else {
            resultMin += indexSmallest - indexLargest;
        }

        return min(resultMax, resultMin);
    }

    public static int min(int a, int b) {
        if (a < b) {
            return a;
        }

        return b;
    }


    public static int max(int a, int b) {
        if (a > b) {
            return a;
        }

        return b;
    }

    public List<Integer> findKLargestNumbers(int[] nums, int k) {
        PriorityQueue<Integer> p = new PriorityQueue<>((a,b) -> b - a);

        for (int i=0; i < nums.length; i++){
            p.add(nums[i]);
        }

        List<Integer> list = new ArrayList<>();

        for (int i=1; i<= k; i++){
            list.add(p.poll());
        }

        return list;
    }

    public static String sortReverse(String word){
        StringBuilder sb = new StringBuilder();


        for (byte b : word.getBytes()){
            sb.insert(0,  (char)b);
        }

        return sb.toString();
    }

    public static void main(String[] args){
        PriorityQueue<Integer> p = new PriorityQueue<>((a,b)-> b-a);
       System.out.println(sortReverse("apple"));
    }
}
