#!/usr/bin/env bash
set -euo pipefail

echo "[ios-doctor] checking iOS build prerequisites"

if command -v xcodebuild >/dev/null 2>&1; then
  echo "[ios-doctor] xcodebuild available"
  xcodebuild -version | head -n 2
  echo "[ios-doctor] PASS"
  exit 0
fi

echo "[ios-doctor] xcodebuild not found"
echo "[ios-doctor] iOS app builds require Xcode on macOS and are not supported in this Linux dev container"
echo "[ios-doctor] Use GitHub Actions macOS runners or a macOS host to build ios-pulseboard"

exit 0
