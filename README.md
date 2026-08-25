# ai-dev-template

Template for development with Claude AI coding assistants.

## What this template includes

- A fully configured VS Code Dev Container.
- Claude project guidance in `CLAUDE.md`.
- Shared assistant rules in `AGENTS.md` and `.clinerules`.
- Editor defaults and recommended extensions.
- Bootstrap and environment health-check scripts.

## Quick start

1. Install Docker Desktop.
2. Open this folder in VS Code.
3. Run: `Dev Containers: Reopen in Container`.
4. Wait for post-create setup to finish.
5. Run the `Doctor: verify dev environment` task.

## Mobile Tooling In Dev Container

- Android tooling is installed in the dev container via `.devcontainer/install-android-sdk.sh`.
- Run `Doctor: verify Android tooling` to confirm SDK packages.
- iOS app builds require Xcode and `xcodebuild` on macOS.
- Run `Doctor: verify iOS build support` to see host capability status.

## Files

- `.devcontainer/devcontainer.json`: Dev Container definition.
- `.devcontainer/Dockerfile`: Container image build.
- `.devcontainer/post-create.sh`: One-time setup on container creation.
- `.vscode/settings.json`: Workspace editor defaults.
- `.vscode/extensions.json`: Recommended extensions.
- `.vscode/tasks.json`: Bootstrap and doctor tasks.
- `CLAUDE.md`: Claude coding assistant project instructions.
- `AGENTS.md`: Cross-assistant behavior and workflow rules.
- `.clinerules`: Cline-compatible project rules.
- `scripts/bootstrap.sh`: Project bootstrap script.
- `scripts/doctor.sh`: Environment validation script.

## Notes

- This template is language-agnostic and auto-detects common project manifests.
- If a `package.json`, `requirements.txt`, `pyproject.toml`, or `pre-commit-config.yaml` exists, setup scripts will install related dependencies.

## Multi-Platform Demo Product: PulseBoard

This repository now includes a single demo product implemented across four clients:

- `cpp-pulseboard`: C++ score service.
- `java-pulseboard`: Java insights service.
- `android-pulseboard`: Android frontend app.
- `ios-pulseboard`: iOS SwiftUI frontend app.
- `shared/pulseboard-data.json`: shared demo data contract for the product domain.

The mobile frontends present outputs from backend services: scoring from C++ and recommendations from Java.

## Bazel Integration

This repository includes Bazel targets that unify build orchestration across all demo projects.

The repository-level `.bazelrc` groups Bazel convenience links under `bazel/` (for example: `bazel/bin`, `bazel/out`, `bazel/testlogs`) instead of creating `bazel-bin`, `bazel-out`, and similar links at the repo root.

```bash
npx -y @bazel/bazelisk build //:all_projects
```

Per-project Bazel targets:

- `//cpp-pulseboard:pulseboard_cpp` (native `cc_binary`)
- `//java-pulseboard:pulseboard_java` (native `java_binary`)
- `//android-pulseboard:android_project_bundle` (Android project source bundle + tool availability note)
- `//ios-pulseboard:ios_project_bundle` (iOS project source bundle + host build requirement note)

The aggregate target `//:all_projects` builds all four targets above.

## CI Build Artifacts

GitHub Actions workflow `.github/workflows/build-artifacts.yml` builds `//:all_projects` and uploads artifacts on pushes to `main`, pull requests, and manual dispatch.

Published artifact bundle contents:

- `pulseboard_cpp`
- `pulseboard_java.jar`
- `android_project_bundle.txt`
- `ios_project_bundle.txt`
- `SHA256SUMS.txt`

## GitHub Releases

Workflow `.github/workflows/release-prebuilt.yml` publishes prebuilt release assets when you push a tag like `v1.0.0`.

Published release assets:

- `pulseboard-cpp-linux-x86_64`
- `pulseboard-java.jar`
- `pulseboard-android-release-unsigned.apk`
- `pulseboard-ios-simulator-app.zip`
- `SHA256SUMS.txt`

The Android release asset is unsigned with the current Gradle configuration. Converting that to a signed installable/store-distributable APK or AAB requires keystore configuration in GitHub Actions secrets.

The iOS release asset is an unsigned simulator app bundle zip built on `macos-latest`. Converting that to a signed device-installable `.ipa` requires Apple signing credentials and provisioning setup in GitHub Actions secrets.

## Recommended Workflow (Dev Container First)

1. Open this folder in VS Code.
2. Run `Dev Containers: Reopen in Container`.
3. Run task: `Doctor: verify dev environment`.
4. Run task: `Doctor: verify Android tooling`.

### C++ Demo

```bash
cd cpp-pulseboard
cmake -S . -B build
cmake --build build
./build/pulseboard
```

### Java Demo

```bash
cd java-pulseboard
bash run.sh
```

### Android App

Open `android-pulseboard` in Android Studio and run on an emulator/device.

Optional container-side checks:

```bash
bash scripts/doctor-android.sh
```

In some Linux container runtimes, `adb` may be present but not executable. If that happens, use Android Studio on the host for emulator/device deployment.

### iOS App

Open `ios-pulseboard/PulseBoard.xcodeproj` in Xcode and run on an iPhone simulator.

Note: iOS builds require Xcode on macOS and cannot run inside a Linux dev container.

