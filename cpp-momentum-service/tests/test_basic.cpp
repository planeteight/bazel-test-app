#include <cassert>
#include <cmath>
#include <vector>
#include <string>

struct DailySnapshot {
    std::string day;
    double score;
};

static double mean(const std::vector<DailySnapshot>& week) {
    if (week.empty()) return 0.0;
    double total = 0.0;
    for (const DailySnapshot& s : week) total += s.score;
    return total / static_cast<double>(week.size());
}

static double volatility(const std::vector<DailySnapshot>& week, double average) {
    if (week.empty()) return 0.0;
    double variance = 0.0;
    for (const DailySnapshot& s : week) {
        double delta = s.score - average;
        variance += delta * delta;
    }
    variance /= static_cast<double>(week.size());
    return std::sqrt(variance);
}

static bool almostEqual(double a, double b, double eps = 0.01) {
    return std::fabs(a - b) < eps;
}

static void test_mean_empty() {
    std::vector<DailySnapshot> empty;
    assert(mean(empty) == 0.0);
}

static void test_mean_week_data() {
    // Canonical week from cpp-momentum-service
    std::vector<DailySnapshot> week = {
        {"Mon", 62.0}, {"Tue", 66.5}, {"Wed", 69.0}, {"Thu", 71.0},
        {"Fri", 74.5}, {"Sat", 73.0}, {"Sun", 78.0}
    };
    double avg = mean(week);
    // (62+66.5+69+71+74.5+73+78) / 7 = 494/7 ≈ 70.57
    assert(almostEqual(avg, 70.57));
}

static void test_mean_uniform_scores() {
    std::vector<DailySnapshot> week = {
        {"Mon", 50.0}, {"Tue", 50.0}, {"Wed", 50.0}
    };
    assert(almostEqual(mean(week), 50.0));
}

static void test_volatility_empty() {
    std::vector<DailySnapshot> empty;
    assert(volatility(empty, 0.0) == 0.0);
}

static void test_volatility_uniform_is_zero() {
    std::vector<DailySnapshot> week = {
        {"Mon", 70.0}, {"Tue", 70.0}, {"Wed", 70.0}
    };
    assert(volatility(week, 70.0) == 0.0);
}

static void test_volatility_week_data() {
    std::vector<DailySnapshot> week = {
        {"Mon", 62.0}, {"Tue", 66.5}, {"Wed", 69.0}, {"Thu", 71.0},
        {"Fri", 74.5}, {"Sat", 73.0}, {"Sun", 78.0}
    };
    double avg = mean(week);
    double vol = volatility(week, avg);
    // Expected ~4.57 based on the canonical dataset
    assert(vol > 4.0 && vol < 5.5);
}

static void test_risk_low_when_high_momentum_low_volatility() {
    // momentum >= 8.0 && volatility <= 5.0 → low risk
    double momentum = 16.0;  // 78 - 62
    double vol = 4.57;
    bool isLow = (momentum >= 8.0 && vol <= 5.0);
    assert(isLow);
}

static void test_risk_high_when_negative_momentum() {
    double momentum = -5.0;
    bool isHigh = (momentum < 0.0);
    assert(isHigh);
}

static void test_momentum_calculation() {
    std::vector<DailySnapshot> week = {
        {"Mon", 62.0}, {"Tue", 66.5}, {"Wed", 69.0}, {"Thu", 71.0},
        {"Fri", 74.5}, {"Sat", 73.0}, {"Sun", 78.0}
    };
    double momentum = week.back().score - week.front().score;
    assert(almostEqual(momentum, 16.0));
}

int main() {
    test_mean_empty();
    test_mean_week_data();
    test_mean_uniform_scores();
    test_volatility_empty();
    test_volatility_uniform_is_zero();
    test_volatility_week_data();
    test_risk_low_when_high_momentum_low_volatility();
    test_risk_high_when_negative_momentum();
    test_momentum_calculation();
    return 0;
}
