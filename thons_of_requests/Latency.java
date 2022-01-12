package thons_of_requests;

public class Latency {

    private static double totalLatency = 0;
    private static int timesCalled = 0;

    private long startTime;
    
    public void start() {
        startTime = System.currentTimeMillis();
    }
    
    public double stop() {
        long stopTime = System.currentTimeMillis();
        double latency = (stopTime - startTime) / 1000.0f;
        addToLatency(latency);

        return latency;
    }

    private static void addToLatency(double latency) {
        totalLatency += latency;
        timesCalled++;
    }

    public static double totalLatencyMedium() {
        return totalLatency / timesCalled;
    }

    public static void reset() {
        totalLatency = 0;
        timesCalled = 0;
    }

}
