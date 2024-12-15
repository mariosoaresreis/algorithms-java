import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.math.*;

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

    public int findKthSmallestNumber(int[] nums, int k) {
        final PriorityQueue<Integer> p = new PriorityQueue<>((a,b) -> b-a);

        for ( int i= 0; i < k; i++){
            p.add(nums[i]);
        }

        for (int i=k; i < nums.length; i++){
            if ( nums[i] < p.peek() ){
                p.poll();
                p.add(nums[i]);
            }
        }
        return p.peek();
    }

    public static String sortCharacterByFrequency(String str) {
        final Map<Character, Integer> map = new HashMap<>();
        final PriorityQueue<Map.Entry<Character, Integer>> maxHeap =
                new PriorityQueue<>((a,b)-> a.getValue()-b.getValue());


        for (int i=0; i < str.length(); i++){
            map.put(str.charAt(i), map.getOrDefault(str.charAt(i), 0) + 1);
        }

        maxHeap.addAll(map.entrySet());

        final StringBuilder sb = new StringBuilder();

        while(!maxHeap.isEmpty()){
            Map.Entry<Character, Integer> entry = maxHeap.poll();

            for (int i=1; i <= entry.getValue(); i++ ){
                sb.append(entry.getKey());
            }

        }


        return sb.toString();
    }

    public static List<Point> findClosestPoints(Point[] points, int k) {
        final PriorityQueue<Point> minHeap = new PriorityQueue<>();
        final Map<Integer, Integer> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();



        for (int i=0; i < k; i++){
            minHeap.add(points[i]);
        }


        for (int i=k; i < points.length; i++){
            if ( points[i].distFromOrigin() < minHeap.peek().distFromOrigin()    ){
                minHeap.poll();
                minHeap.add(points[i]);
            }
        }

        final List<Point> list = new ArrayList<>();

        for (int i=1; i<=k; i++){
            list.add(minHeap.poll());
        }

        return list;
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

    public static List<Integer> findClosestElements(int[] arr, int K, int X) {
        final PriorityQueue<Entry> minHeap =
                new PriorityQueue<>( (a,b) -> b.value - a.value == 0? b.key - a.key: b.value - a.value );

        for (int i = 0; i < arr.length; i++){
            minHeap.add(new Entry(arr[i], Math.abs(X - arr[i])));

            if (minHeap.size() > K){
                minHeap.poll();
            }
        }

        final List<Integer> result = new ArrayList<>();

        while(!minHeap.isEmpty()){
            result.add(minHeap.poll().key);
        }

        Collections.sort(result);

        return result;
    }

    public static void main(String[] args){
        System.out.println(findMaximumDistinctElements(new int[]{3, 5, 12, 11, 12}, 2));

    }

    public static boolean exist(char[][] board, String word) {
        for (int i=0; i < board.length; i++){
            for (int j=0; j < board[0].length; j++){
                boolean result = dfs(0, i, j, board, word);

                if (result) {
                    return true;
                }
            }

        }

        return false;
    }

    public static int[] asteroidCollision(int[] asteroids) {
        final Stack<Integer> stack = new Stack<>();

        for (int i =0; i < asteroids.length; i++){
            int currAsteroid = asteroids[i];

            if (stack.isEmpty()){
                stack.add(currAsteroid);
            }else {
                int last = stack.peek();

                if ( (abs(currAsteroid) - abs(last) == 0) || (abs(currAsteroid) > abs(last) && diffSign(currAsteroid, last))){
                    stack.pop();
                }

                if ( !diffSign(last, currAsteroid)  && abs(currAsteroid) > abs(last)   ) {
                    stack.add(currAsteroid);
                }
            }
        }

        int[] result = new int[stack.size()];

        for (int i=result.length-1; i >= 0; i--){
            result[i] = stack.pop();
        }

        return result;
    }

    public static int abs(int a){
        if (a < 0){
            return a*-1;
        }

        return a;
    }

    public static boolean diffSign(int a, int b){
        return (a > 0 && b < 0) || ( a < 0 && b> 0);
    }

    public static int findMaximumDistinctElements(int[] nums, int k) {
        int distinctElementsCount = 0;
        final PriorityQueue<Map.Entry<Integer, Integer>> maxHeap =
                new PriorityQueue<>((a,b) -> a.getValue()-b.getValue());

        Map<Integer, Integer> map = new HashMap<>();

        for (int i=0; i < nums.length; i++){
            map.put(nums[i], map.getOrDefault(nums[i], 0) + 1);
        }

        for ( Map.Entry<Integer, Integer> entry: map.entrySet()  ){
            if (entry.getValue() == 1){
                //distinctElementsCount++;
            }else {
                maxHeap.add(entry);
            }
        }

        while (k > 0 && maxHeap.size() > 0 ){
            int count = maxHeap.peek().getValue();
            boolean found = false;

            while (k > 0 && count > 1){
                found = true;
                count--;
                k--;
            }

            if (!found){
                count = 0;
                k--;
            }
            Map.Entry<Integer, Integer> entry = maxHeap.poll();
            map.put(entry.getKey(), count);
        }

        while (k > 0){
            Map.Entry<Integer, Integer> entry = maxHeap.poll();
            map.put(entry.getKey(), 0);
            k--;
        }

        for ( Map.Entry<Integer, Integer> entry: map.entrySet()  ){
            if (entry.getValue() == 1){
                distinctElementsCount++;
            }
        }


        return distinctElementsCount;
    }

    static boolean dfs(int k, int i, int j, char[][] board, String word){
        if (
                (i > board.length - 1) ||
                        (i < 0) ||
                        (j > board[0].length -1) ||
                        (j < 0) ||
                        (word.charAt(k) != board[i][j])||
                        (board[i][j] == '/') ){
            return false;
        }

        if ( k >= word.length()-1){
            return true;
        }

        char tmp = board[i][j];
        board[i][j] = '/';
        Map.Entry<Integer, Integer> p;
        PriorityQueue<Map.Entry<Integer, Integer>> maxHeap = new PriorityQueue<>((a,b) -> b.getValue()-a.getValue());



        boolean result = dfs(k+1, i+1, j, board, word) ||
                dfs(k+1, i, j+1, board, word) ||
                dfs(k+1, i-1, j, board, word) ||
                dfs(k+1, i, j-1, board, word);

        board[i][j] = tmp;

        return result;

    }
}
