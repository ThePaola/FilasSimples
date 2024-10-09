import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<Result> results = new ArrayList<>();

        for (int i = 0; i < Rules.NumberOfSimulations; i++) {
            Simulation simulation = new Simulation();

            simulation.runSimulation();

            results.add(simulation.getSimulationResult());
        }

        printResults(results);
    }

    static void printResults(ArrayList<Result> results) {
        Result finalResult = new Result();

        for (Result result : results) {
            finalResult.totalTime += result.totalTime;
            finalResult.losses += result.losses;

            for (int i = 0; i < result.timeStatistics.length; i++) {
                finalResult.timeStatistics[i] += result.timeStatistics[i];
            }
        }

        finalResult.ShowResult();
    }
}
