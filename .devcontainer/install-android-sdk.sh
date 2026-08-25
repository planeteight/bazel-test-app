#!/usr/bin/env bash
set -euo pipefail

ANDROID_SDK_ROOT="${ANDROID_SDK_ROOT:-/opt/android-sdk}"
CMDLINE_TOOLS_ZIP="commandlinetools-linux-11076708_latest.zip"
CMDLINE_TOOLS_URL="https://dl.google.com/android/repository/${CMDLINE_TOOLS_ZIP}"
SDKMANAGER="${ANDROID_SDK_ROOT}/cmdline-tools/latest/bin/sdkmanager"

if [[ -x "${SDKMANAGER}" ]]; then
  echo "[android-sdk] already installed"
  exit 0
fi

echo "[android-sdk] installing into ${ANDROID_SDK_ROOT}"
sudo mkdir -p "${ANDROID_SDK_ROOT}/cmdline-tools"
sudo chown -R "$(id -u):$(id -g)" "${ANDROID_SDK_ROOT}"

workdir="$(mktemp -d)"
trap 'rm -rf "${workdir}"' EXIT

wget -q "${CMDLINE_TOOLS_URL}" -O "${workdir}/${CMDLINE_TOOLS_ZIP}"
unzip -q "${workdir}/${CMDLINE_TOOLS_ZIP}" -d "${workdir}"
mkdir -p "${ANDROID_SDK_ROOT}/cmdline-tools/latest"
cp -R "${workdir}/cmdline-tools/"* "${ANDROID_SDK_ROOT}/cmdline-tools/latest/"

yes | "${SDKMANAGER}" --licenses >/dev/null || true
"${SDKMANAGER}" \
  "platform-tools" \
  "platforms;android-34" \
  "build-tools;34.0.0"

# Emulator packages are useful for CI and local testing when host/container support exists.
"${SDKMANAGER}" "emulator" || true

echo "[android-sdk] installed"
