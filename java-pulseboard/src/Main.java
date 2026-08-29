import java.util.ArrayList;
import java.util.List;

class Habit {
    public final String name;
    public final int completed;
    public final int target;

    Habit(String name, int completed, int target) {
        this.name = name;
        this.completed = completed;
        this.target = target;
    }
}

public class Main {
    public static void main(String[] args) {
        List<Habit> habits = new ArrayList<>();
        habits.add(new Habit("Hydration", 6, 8));
        habits.add(new Habit("Focus Blocks", 3, 4));
        habits.add(new Habit("Walk", 1, 1));
        habits.add(new Habit("Reading", 20, 30));

        Habit weakest = null;
        int worstGap = Integer.MIN_VALUE;
        int totalCompleted = 0;
        int totalTarget = 0;

        for (Habit habit : habits) {
            int gap = habit.target - habit.completed;
            if (gap > worstGap) {
                worstGap = gap;
                weakest = habit;
            }
            totalCompleted += habit.completed;
            totalTarget += habit.target;
        }

        System.out.println("PulseBoard Insights Service (Java)");
        System.out.println("---------------------------------");
        System.out.println("service=java-pulseboard");
        System.out.println("insight.progress=" + totalCompleted + "/" + totalTarget);

        if (weakest == null || worstGap <= 0) {
            System.out.println("insight.focus=none");
            System.out.println("insight.recommendation=All habits are on track. Keep your streak alive.");
        } else {
            String unit = weakest.name.equals("Reading") ? "minutes" : "sessions";
            System.out.println("insight.focus=" + weakest.name);
            System.out.println("insight.remaining=" + worstGap + " " + unit);
            System.out.println("insight.recommendation=Add one focused block for " + weakest.name + " to close the largest gap.");
        }
    }
}
