import SwiftUI

struct Habit: Identifiable {
    let id = UUID()
    let name: String
    let completed: Int
    let target: Int

    var percentage: Int {
        guard target > 0 else { return 0 }
        return Int((Double(completed) / Double(target)) * 100)
    }
}

struct ContentView: View {
    private let habits: [Habit] = [
        Habit(name: "Hydration", completed: 6, target: 8),
        Habit(name: "Focus Blocks", completed: 3, target: 4),
        Habit(name: "Walk", completed: 1, target: 1),
        Habit(name: "Reading", completed: 20, target: 30)
    ]

    private let score: Int = 69
    private let status: String = "Good start, keep the streak alive."
    private let recommendation: String = "Add one focused block for Reading to close the largest gap."

    var body: some View {
        ZStack {
            LinearGradient(
                colors: [Color(red: 0.95, green: 0.97, blue: 1.0), Color.white],
                startPoint: .topLeading,
                endPoint: .bottomTrailing
            )
            .ignoresSafeArea()

            VStack(alignment: .leading, spacing: 16) {
                Text("PulseBoard")
                    .font(.system(size: 36, weight: .bold, design: .rounded))
                    .foregroundStyle(Color(red: 0.06, green: 0.11, blue: 0.30))

                Text("Daily routine tracker")
                    .foregroundStyle(Color(red: 0.25, green: 0.31, blue: 0.48))

                VStack(alignment: .leading, spacing: 4) {
                    Text("Connected Services")
                        .font(.system(size: 16, weight: .bold, design: .rounded))
                        .foregroundStyle(Color(red: 0.06, green: 0.11, blue: 0.30))
                    Text("Score: cpp-momentum-service")
                        .font(.system(size: 14, weight: .regular, design: .rounded))
                        .foregroundStyle(Color(red: 0.13, green: 0.20, blue: 0.37))
                    Text("Insights: java-focus-planner")
                        .font(.system(size: 14, weight: .regular, design: .rounded))
                        .foregroundStyle(Color(red: 0.13, green: 0.20, blue: 0.37))
                }
                .padding(.top, 2)

                Text("\\(score)%")
                    .font(.system(size: 42, weight: .heavy, design: .rounded))
                    .foregroundStyle(Color(red: 0.06, green: 0.42, blue: 1.0))

                ProgressView(value: Double(score), total: 100)
                    .tint(Color(red: 0.06, green: 0.42, blue: 1.0))

                VStack(alignment: .leading, spacing: 8) {
                    ForEach(habits) { habit in
                        Text("\\(habit.name): \\(habit.completed)/\\(habit.target) (\\(habit.percentage)%)")
                            .font(.system(size: 17, weight: .medium, design: .rounded))
                            .foregroundStyle(Color(red: 0.13, green: 0.20, blue: 0.37))
                    }
                }
                .padding(.top, 8)

                Text(status)
                    .font(.system(size: 18, weight: .semibold, design: .rounded))
                    .foregroundStyle(Color(red: 0.10, green: 0.49, blue: 0.22))
                    .padding(.top, 4)

                Text("Next Best Action")
                    .font(.system(size: 16, weight: .bold, design: .rounded))
                    .foregroundStyle(Color(red: 0.06, green: 0.11, blue: 0.30))

                Text(recommendation)
                    .font(.system(size: 17, weight: .regular, design: .rounded))
                    .foregroundStyle(Color(red: 0.13, green: 0.20, blue: 0.37))

                Spacer()
            }
            .padding(24)
        }
    }
}

#Preview {
    ContentView()
}
