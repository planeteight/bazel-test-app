# PulseBoard iOS App

This app is the PulseBoard frontend for mobile. It presents score and insight outputs from backend services:

- `cpp-pulseboard` for scoring.
- `java-pulseboard` for insights and recommendations.

## Run in Xcode

1. Open `ios-pulseboard/PulseBoard.xcodeproj` in Xcode.
2. Pick an iPhone simulator.
3. Press Run.

## Notes

- iOS builds require Xcode on macOS and are not supported inside Linux dev containers.
- The target uses SwiftUI with a single screen for stable demo behavior.
