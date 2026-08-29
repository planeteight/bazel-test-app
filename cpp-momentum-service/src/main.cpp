#include <cmath>
#include <iomanip>
#include <iostream>
#include <string>
#include <vector>

struct DailySnapshot {
    std::string day;
    double score;
};

static double mean(const std::vector<DailySnapshot>& week) {
    if (week.empty()) {
        return 0.0;
    }

    double total = 0.0;
    for (const DailySnapshot& snapshot : week) {
        total += snapshot.score;
    }
    return total / static_cast<double>(week.size());
}

static double volatility(const std::vector<DailySnapshot>& week, double average) {
    if (week.empty()) {
        return 0.0;
    }

    double variance = 0.0;
    for (const DailySnapshot& snapshot : week) {
        double delta = snapshot.score - average;
        variance += delta * delta;
    }

    variance /= static_cast<double>(week.size());
    return std::sqrt(variance);
}

int main() {
    std::vector<DailySnapshot> week = {
        {"Mon", 62.0},
        {"Tue", 66.5},
        {"Wed", 69.0},
        {"Thu", 71.0},
        {"Fri", 74.5},
        {"Sat", 73.0},
        {"Sun", 78.0},
    };

    std::cout << "PulseBoard Momentum Service (C++)\n";
    std::cout << "--------------------------------\n";
    std::cout << "service=cpp-momentum-service\n";

    for (const DailySnapshot& snapshot : week) {
        std::cout << "trend." << snapshot.day << "="
                  << std::fixed << std::setprecision(1) << snapshot.score << "\n";
    }

    const double average = mean(week);
    const double weekStart = week.front().score;
    const double weekEnd = week.back().score;
    const double momentum = weekEnd - weekStart;
    const double weeklyVolatility = volatility(week, average);

    std::cout << "metrics.week_avg=" << std::fixed << std::setprecision(1) << average << "\n";
    std::cout << "metrics.momentum=" << std::fixed << std::setprecision(1) << momentum << "\n";
    std::cout << "metrics.volatility=" << std::fixed << std::setprecision(2) << weeklyVolatility << "\n";

    if (momentum >= 8.0 && weeklyVolatility <= 5.0) {
        std::cout << "risk.level=low\n";
        std::cout << "risk.action=Maintain your current routine and increase one stretch goal.\n";
    } else if (momentum >= 0.0) {
        std::cout << "risk.level=medium\n";
        std::cout << "risk.action=Protect consistency by pre-planning your two hardest sessions.\n";
    } else {
        std::cout << "risk.level=high\n";
        std::cout << "risk.action=Run a reset day and restart with your easiest habit chain.\n";
    }

    return 0;
}
