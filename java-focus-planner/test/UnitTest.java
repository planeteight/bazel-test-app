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

public class UnitTest {

    static void assertEqual(double expected, double actual, double eps) {
        if (Math.abs(expected - actual) > eps) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    static void assertEqual(Object expected, Object actual) {
        if (!expected.equals(actual)) {
            throw new AssertionError("Expected " + expected + " but got " + actual);
        }
    }

    static void assertTrue(boolean condition, String message) {
        if (!condition) throw new AssertionError(message);
    }

    static void testEfficiencyCalculation() {
        FocusTask task = new FocusTask("Draft architecture memo", 40, 80, 6);
        assertEqual(2.0, task.efficiency(), 0.001);
    }

    static void testEfficiencyLowImpactTask() {
        FocusTask task = new FocusTask("Triage support queue", 15, 25, 3);
        assertEqual(25.0 / 15.0, task.efficiency(), 0.001);
    }

    static void testSortingPrioritizesEarliestDeadlineFirst() {
        List<FocusTask> tasks = new ArrayList<>();
        tasks.add(new FocusTask("Draft architecture memo", 40, 80, 6));
        tasks.add(new FocusTask("Triage support queue", 15, 25, 3));
        tasks.add(new FocusTask("Prepare release checklist", 30, 45, 8));

        tasks.sort(
            Comparator.comparingInt((FocusTask t) -> t.deadlineHours)
                .thenComparing(Comparator.comparingDouble(FocusTask::efficiency).reversed()));

        assertEqual("Triage support queue", tasks.get(0).name);
        assertEqual("Draft architecture memo", tasks.get(1).name);
        assertEqual("Prepare release checklist", tasks.get(2).name);
    }

    static void testSortingBreaksTiesByEfficiency() {
        // Both due in 4 hours; higher efficiency should come first
        FocusTask highEfficiency = new FocusTask("High Eff", 10, 90, 4);
        FocusTask lowEfficiency  = new FocusTask("Low Eff",  10, 20, 4);
        List<FocusTask> tasks = new ArrayList<>();
        tasks.add(lowEfficiency);
        tasks.add(highEfficiency);

        tasks.sort(
            Comparator.comparingInt((FocusTask t) -> t.deadlineHours)
                .thenComparing(Comparator.comparingDouble(FocusTask::efficiency).reversed()));

        assertEqual("High Eff", tasks.get(0).name);
    }

    static void testBudgetFittingSelectsCorrectTasks() {
        List<FocusTask> backlog = new ArrayList<>();
        backlog.add(new FocusTask("Draft architecture memo", 40, 80, 6));
        backlog.add(new FocusTask("Fix flaky integration test", 25, 65, 4));
        backlog.add(new FocusTask("Refine Android onboarding copy", 20, 30, 24));
        backlog.add(new FocusTask("Prepare release checklist", 30, 45, 8));
        backlog.add(new FocusTask("Triage support queue", 15, 25, 3));

        backlog.sort(
            Comparator.comparingInt((FocusTask t) -> t.deadlineHours)
                .thenComparing(Comparator.comparingDouble(FocusTask::efficiency).reversed()));

        int timeBudget = 90;
        int usedBudget = 0;
        int totalImpact = 0;
        List<FocusTask> plan = new ArrayList<>();

        for (FocusTask task : backlog) {
            if (usedBudget + task.minutesRequired > timeBudget) continue;
            plan.add(task);
            usedBudget += task.minutesRequired;
            totalImpact += task.impact;
        }

        assertTrue(!plan.isEmpty(), "Plan should not be empty for a 90-minute budget");
        assertTrue(usedBudget <= timeBudget, "Used budget must not exceed time budget");
        assertTrue(totalImpact > 0, "Total impact should be positive");
    }

    static void testBudgetTooSmallProducesEmptyPlan() {
        List<FocusTask> backlog = new ArrayList<>();
        backlog.add(new FocusTask("Triage support queue", 15, 25, 3));

        int timeBudget = 10; // smaller than the smallest task
        List<FocusTask> plan = new ArrayList<>();
        for (FocusTask task : backlog) {
            if (timeBudget >= task.minutesRequired) plan.add(task);
        }

        assertTrue(plan.isEmpty(), "Plan should be empty when budget is too small");
    }

    public static void main(String[] args) {
        testEfficiencyCalculation();
        testEfficiencyLowImpactTask();
        testSortingPrioritizesEarliestDeadlineFirst();
        testSortingBreaksTiesByEfficiency();
        testBudgetFittingSelectsCorrectTasks();
        testBudgetTooSmallProducesEmptyPlan();
        System.out.println("All FocusPlanner tests passed.");
    }
}
