#!/usr/bin/env bash
set -euo pipefail

SCRIPT_DIR="$(cd "$(dirname "${BASH_SOURCE[0]}")" && pwd)"
REPO_ROOT="$(cd "${SCRIPT_DIR}/.." && pwd)"

echo "[android-doctor] checking Android toolchain"

required_tools=(sdkmanager java javac)
missing=0

for tool in "${required_tools[@]}"; do
  if ! command -v "${tool}" >/dev/null 2>&1; then
    echo "[android-doctor] missing: ${tool}"
    missing=1
  fi
done

if [[ ${missing} -eq 1 ]]; then
  echo "[android-doctor] FAIL: install missing tools"
  exit 1
fi

echo "[android-doctor] checking SDK packages"
installed_packages="$(sdkmanager --list_installed)"
required_packages=(
  "platform-tools"
  "platforms;android-34"
  "build-tools;34.0.0"
)

for pkg in "${required_packages[@]}"; do
  if ! grep -Fq "${pkg}" <<<"${installed_packages}"; then
    echo "[android-doctor] missing SDK package: ${pkg}"
    missing=1
  fi
done

if [[ ${missing} -eq 1 ]]; then
  echo "[android-doctor] FAIL: install missing Android SDK packages"
  exit 1
fi

echo "[android-doctor] checking project files"
for file in "${REPO_ROOT}/android-pulseboard/build.gradle" "${REPO_ROOT}/android-pulseboard/settings.gradle" "${REPO_ROOT}/android-pulseboard/app/build.gradle"; do
  if [[ ! -f "${file}" ]]; then
    echo "[android-doctor] missing file: ${file}"
    exit 1
  fi
done

adb_bin="$(command -v adb || true)"
host_arch="$(uname -m)"

if [[ "${host_arch}" == "aarch64" ]]; then
  echo "[android-doctor] WARN: skipping direct adb execution on ${host_arch} container runtime"
  echo "[android-doctor]       Use Android Studio on host for device/emulator deployment if needed"
elif adb version >/dev/null 2>&1; then
  echo "[android-doctor] adb executable works"
else
  echo "[android-doctor] WARN: adb exists but is not runnable in this container runtime"
  echo "[android-doctor]       Use Android Studio on host for device/emulator deployment if needed"
fi

if [[ -f "${REPO_ROOT}/android-pulseboard/gradlew" ]]; then
  echo "[android-doctor] Gradle wrapper found"
else
  echo "[android-doctor] WARN: no Gradle wrapper in android-pulseboard"
  echo "[android-doctor]       Gradle sync/build should be run from Android Studio"
fi

echo "[android-doctor] PASS (with warnings if noted above)"
