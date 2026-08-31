import XCTest
@testable import PulseBoard

final class PulseBoardTests: XCTestCase {

    // MARK: - Habit.percentage

    func testHydration_percentage() {
        // 6 of 8 glasses → 75 %
        let habit = Habit(name: "Hydration", completed: 6, target: 8)
        XCTAssertEqual(habit.percentage, 75)
    }

    func testFocusBlocks_percentage() {
        // 3 of 4 blocks → 75 %
        let habit = Habit(name: "Focus Blocks", completed: 3, target: 4)
        XCTAssertEqual(habit.percentage, 75)
    }

    func testWalk_completedMeansOneHundredPercent() {
        // 1 of 1 walk → 100 %
        let habit = Habit(name: "Walk", completed: 1, target: 1)
        XCTAssertEqual(habit.percentage, 100)
    }

    func testReading_partialProgress() {
        // 20 of 30 minutes → 66 % (truncated integer)
        let habit = Habit(name: "Reading", completed: 20, target: 30)
        XCTAssertEqual(habit.percentage, 66)
    }

    func testHabit_zeroTargetReturnsZeroPercent() {
        // Guard: target = 0 should not divide by zero
        let habit = Habit(name: "Undefined", completed: 5, target: 0)
        XCTAssertEqual(habit.percentage, 0)
    }

    func testHabit_zeroCompletedIsZeroPercent() {
        let habit = Habit(name: "Hydration", completed: 0, target: 8)
        XCTAssertEqual(habit.percentage, 0)
    }

    func testHabit_exceedingTargetCapsAboveHundred() {
        // No artificial cap in the model; percentage can exceed 100 when over-target
        let habit = Habit(name: "Walk", completed: 3, target: 1)
        XCTAssertGreaterThan(habit.percentage, 100)
    }

    // MARK: - Default habits list completeness

    func testDefaultHabitsContainFourItems() {
        // ContentView defines exactly four tracked habits
        let expectedNames = ["Hydration", "Focus Blocks", "Walk", "Reading"]
        let habits = [
            Habit(name: "Hydration",     completed: 6,  target: 8),
            Habit(name: "Focus Blocks",  completed: 3,  target: 4),
            Habit(name: "Walk",          completed: 1,  target: 1),
            Habit(name: "Reading",       completed: 20, target: 30)
        ]
        XCTAssertEqual(habits.count, 4)
        for (index, habit) in habits.enumerated() {
            XCTAssertEqual(habit.name, expectedNames[index])
        }
    }

    func testHabitIds_areUnique() {
        let habits = [
            Habit(name: "Hydration",    completed: 6,  target: 8),
            Habit(name: "Focus Blocks", completed: 3,  target: 4),
            Habit(name: "Walk",         completed: 1,  target: 1),
            Habit(name: "Reading",      completed: 20, target: 30)
        ]
        let ids = Set(habits.map { $0.id })
        XCTAssertEqual(ids.count, habits.count, "Each Habit should have a unique UUID")
    }
}

