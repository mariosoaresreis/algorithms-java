import java.util.PriorityQueue;

public class MedianFinder {
    private final PriorityQueue<Integer> maxHeap;
    private final PriorityQueue<Integer> minHeap;

    public MedianFinder(){
        maxHeap = new PriorityQueue<>((a, b)-> b - a );
        minHeap = new PriorityQueue<>();
    }

    public void add(Integer number){
        if (minHeap.isEmpty() || minHeap.peek() <= number  ){
            minHeap.add(number);
        }else {
            maxHeap.add(number);
        }

        if ( maxHeap.size() > minHeap.size()  + 1){
            minHeap.offer(maxHeap.poll());
        }else if (minHeap.size() > maxHeap.size()){
            maxHeap.offer(minHeap.poll());
        }
    }

    public double findMedian() {
        if (minHeap.size() == maxHeap.size()){
            return (double)(maxHeap.peek() + minHeap.peek())/2;
        }else {
            return (double) minHeap.peek();
        }
    }

    public static void main(String[] args){
        MedianFinder finder = new MedianFinder();
        finder.maxHeap.add(1);
        finder.maxHeap.add(2);
        System.out.println(finder.maxHeap.peek());
    }
}
