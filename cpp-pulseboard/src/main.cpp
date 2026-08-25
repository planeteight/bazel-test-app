#include <iomanip>
#include <iostream>
#include <string>
#include <vector>

struct Habit {
    std::string name;
    int completed;
    int target;
};

static std::string progressBar(int completed, int target, int width = 20) {
    if (target <= 0) {
        return "[invalid target]";
    }

    int filled = (completed * width) / target;
    if (filled < 0) {
        filled = 0;
    }
    if (filled > width) {
        filled = width;
    }

    return "[" + std::string(filled, '#') + std::string(width - filled, '-') + "]";
}

int main() {
    std::vector<Habit> habits = {
        {"Hydration", 6, 8},
        {"Focus Blocks", 3, 4},
        {"Walk", 1, 1},
        {"Reading", 20, 30},
    };

    std::cout << "PulseBoard Score Service (C++)\n";
    std::cout << "------------------------------\n";
    std::cout << "service=cpp-pulseboard\n";

    int completedSum = 0;
    int targetSum = 0;

    for (const Habit& habit : habits) {
        completedSum += habit.completed;
        targetSum += habit.target;
        double pct = (habit.target == 0) ? 0.0 : (100.0 * habit.completed / habit.target);

        std::cout << std::left << std::setw(14) << habit.name
                  << " " << progressBar(habit.completed, habit.target)
                  << " " << std::fixed << std::setprecision(1) << pct << "%"
                  << " (" << habit.completed << "/" << habit.target << ")\n";
    }

    double daily = (targetSum == 0) ? 0.0 : (100.0 * completedSum / targetSum);
    std::cout << "\nscore.daily=" << std::fixed << std::setprecision(1) << daily << "\n";

    if (daily >= 80.0) {
        std::cout << "score.status=Great momentum today.\n";
    } else if (daily >= 60.0) {
        std::cout << "score.status=Good start, keep the streak alive.\n";
    } else {
        std::cout << "score.status=Early day. Focus on one easy win.\n";
    }

    return 0;
}
