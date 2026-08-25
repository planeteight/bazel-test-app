#!/usr/bin/env bash
set -euo pipefail

echo "[post-create] starting setup"

if command -v git >/dev/null 2>&1; then
  git config --global --add safe.directory "${PWD}" || true
fi

if [[ -f package.json ]]; then
  echo "[post-create] package.json detected"
  if [[ -f package-lock.json ]]; then
    npm ci
  else
    npm install
  fi
fi

if [[ -f requirements.txt ]]; then
  echo "[post-create] requirements.txt detected"
  python -m pip install --upgrade pip
  python -m pip install -r requirements.txt
fi

if [[ -f pyproject.toml ]]; then
  echo "[post-create] pyproject.toml detected"
  python -m pip install --upgrade pip
fi

if [[ -f .pre-commit-config.yaml ]]; then
  echo "[post-create] pre-commit config detected"
  python -m pip install pre-commit
  pre-commit install
fi

if [[ -f .devcontainer/install-android-sdk.sh ]]; then
  echo "[post-create] installing Android SDK command line tools"
  bash .devcontainer/install-android-sdk.sh
fi

if [[ -f scripts/doctor-ios.sh ]]; then
  echo "[post-create] checking iOS build support"
  bash scripts/doctor-ios.sh
fi

echo "[post-create] setup complete"
