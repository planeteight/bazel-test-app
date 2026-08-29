# PulseBoard iOS App

This app is the PulseBoard frontend for mobile. It presents analytics and planning outputs from backend services:

- `cpp-momentum-service` for weekly momentum and risk analytics.
- `java-focus-planner` for focus-session planning recommendations.

## Run in Xcode

1. Open `ios-pulseboard/PulseBoard.xcodeproj` in Xcode.
2. Pick an iPhone simulator.
3. Press Run.

## Notes

- iOS builds require Xcode on macOS and are not supported inside Linux dev containers.
- The target uses SwiftUI with a single screen for stable demo behavior.
