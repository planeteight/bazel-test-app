#!/usr/bin/env bash
set -euo pipefail

echo "[demo] running C++ PulseBoard"
pushd cpp-momentum-service >/dev/null
cmake -S . -B build
cmake --build build
./build/pulseboard
popd >/dev/null

echo
echo "[demo] running Java PulseBoard"
pushd java-focus-planner >/dev/null
bash run.sh
popd >/dev/null

echo
echo "[demo] done"
