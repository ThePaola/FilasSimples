import java.util.ArrayList;
import java.util.Arrays;

public class Result {
    public double totalTime;
    public int losses;
    public double[] timeStatistics;

    public Result() {
        totalTime = 0.0;
        losses = 0;
        timeStatistics = new double[Rules.capacity + 1];
    }

    public void ShowResult() {
        System.out.println("==============================================================");
        System.out.println("===================  SIMULATION RESULTS  =====================");
        System.out.println("==============================================================");
        System.out.println("**************************************************************");
        System.out.printf("Queue:   F1 (G/G/%d/%d)\n", Rules.servers, Rules.capacity);
        System.out.printf("Arrival: %.1f ... %.1f\n", Rules.minArrival, Rules.maxArrival);
        System.out.printf("Service: %.1f ... %.1f\n", Rules.minService, Rules.maxService);
        System.out.println("**************************************************************");
        System.out.println("   State        Time       Probability");
        for (int i = 0; i < timeStatistics.length; i++) {
            System.out.printf("      %d        %.4f        %.2f%%\n", i, timeStatistics[i], (timeStatistics[i] / totalTime) * 100);
        }
        System.out.println("\nNumber of losses: " + losses);
        System.out.println("\n==============================================================");
        System.out.println("Simulation average time: " + totalTime);
        System.out.println("==============================================================");
    }
}
