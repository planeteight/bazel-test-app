import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

class FocusTask {
    public final String name;
    public final int minutesRequired;
    public final int impact;
    public final int deadlineHours;

    FocusTask(String name, int minutesRequired, int impact, int deadlineHours) {
        this.name = name;
        this.minutesRequired = minutesRequired;
        this.impact = impact;
        this.deadlineHours = deadlineHours;
    }

    double efficiency() {
        return (double) impact / (double) minutesRequired;
    }
}

public class Main {
    public static void main(String[] args) {
        List<FocusTask> backlog = new ArrayList<>();
        backlog.add(new FocusTask("Draft architecture memo", 40, 80, 6));
        backlog.add(new FocusTask("Fix flaky integration test", 25, 65, 4));
        backlog.add(new FocusTask("Refine Android onboarding copy", 20, 30, 24));
        backlog.add(new FocusTask("Prepare release checklist", 30, 45, 8));
        backlog.add(new FocusTask("Triage support queue", 15, 25, 3));

        backlog.sort(
                Comparator.comparingInt((FocusTask task) -> task.deadlineHours)
                        .thenComparing(Comparator.comparingDouble(FocusTask::efficiency).reversed()));

        int timeBudget = 90;
        int usedBudget = 0;
        int totalImpact = 0;
        List<FocusTask> plan = new ArrayList<>();

        for (FocusTask task : backlog) {
            if (usedBudget + task.minutesRequired > timeBudget) {
                continue;
            }
            plan.add(task);
            usedBudget += task.minutesRequired;
            totalImpact += task.impact;
        }

        System.out.println("PulseBoard Focus Planner (Java)");
        System.out.println("------------------------------");
        System.out.println("service=java-focus-planner");

        if (plan.isEmpty()) {
            System.out.println("plan.status=empty");
            System.out.println("plan.recommendation=Increase your focus budget to at least 15 minutes.");
            return;
        }

        System.out.println("plan.time_budget=" + timeBudget);
        System.out.println("plan.time_used=" + usedBudget);
        System.out.println("plan.projected_impact=" + totalImpact);

        for (int i = 0; i < plan.size(); i++) {
            FocusTask task = plan.get(i);
            System.out.println(
                    "plan.item." + (i + 1)
                            + "=" + task.name
                            + " (" + task.minutesRequired + "m, impact=" + task.impact
                            + ", due<=" + task.deadlineHours + "h)");
        }

        System.out.println("plan.recommendation=Start with item 1 and add a 5 minute review before context switching.");
    }
}
