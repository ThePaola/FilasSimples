import java.text.DecimalFormat;
import java.util.ArrayList;

public class Simulation {
    private final RNG rng;
    private final Result result;
    private final DecimalFormat df;
    private final ArrayList<Event> events = new ArrayList<>();
    private int currentQueueSize;

    Simulation() {
        rng = new RNG();
        result = new Result();
        df = new DecimalFormat("#.####");

        events.add(new Event(EventType.ARRIVAL, Rules.firstArrival));

        currentQueueSize = 0;
    }

    public void runSimulation() {
        for(int i = 0; i < Rules.SimulationSteps-1; i++) {
            Event event = events.remove(0);

            updateTimeAndStatistics(event);

            if (event.type == EventType.ARRIVAL) {
                arrival(event.time);
            }

            if (event.type == EventType.DEPARTURE) {
                departure(event.time);
            }

            if (event.type == EventType.ROUTING) {
                System.out.println("ROUTING!");
            }
        }
    }

    private void updateTimeAndStatistics(Event event) {
        double delta = event.time - result.totalTime;

        result.timeStatistics[currentQueueSize] += Double.parseDouble(df.format(delta));
        result.totalTime = event.time;
    }

    private void insertEventByLowestTime(Event event) {
        int index = 0;
        for (Event e : events) {
            if (event.time < e.time) {
                break;
            }
            index++;
        }
        events.add(index, event);
    }

    private double generateArrivalTime(double lastEventTime) {
        return Double.parseDouble(df.format(lastEventTime + (Rules.maxArrival - Rules.minArrival) * rng.Generate() + Rules.minArrival));
    }

    private double generateDepartureTime(double lastEventTime) {
        return Double.parseDouble(df.format(lastEventTime + (Rules.maxService - Rules.minService) * rng.Generate() + Rules.minService));
    }

    private void arrival(double lastEventTime) {
        if(currentQueueSize < Rules.capacity) {
            currentQueueSize++;
            if(currentQueueSize <= Rules.servers) {
                insertEventByLowestTime(new Event(EventType.DEPARTURE, generateDepartureTime(lastEventTime)));
            }
        } else {
            result.losses++;
        }

        insertEventByLowestTime(new Event(EventType.ARRIVAL, generateArrivalTime(lastEventTime)));
    }

    private void departure(double lastEventTime) {
        currentQueueSize--;
        if(currentQueueSize >= Rules.servers) {
            insertEventByLowestTime(new Event(EventType.DEPARTURE, generateDepartureTime(lastEventTime)));
        }
    }

    public Result getSimulationResult() {
        return result;
    }
}
