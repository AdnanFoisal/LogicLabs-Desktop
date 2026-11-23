#!/usr/bin/env bash
set -euo pipefail

# Resolve real script path even when invoked via /usr/bin/logic-labs symlink
SELF_PATH="$(readlink -f "$0")"
SELF_DIR="$(dirname "$SELF_PATH")"

if [[ -f "$SELF_DIR/app/LogicLabs-linux-all.jar" ]]; then
    APP_ROOT="$SELF_DIR"
elif [[ -f "$SELF_DIR/../app/LogicLabs-linux-all.jar" ]]; then
    APP_ROOT="$(cd "$SELF_DIR/.." && pwd)"
else
    MSG="Logic Labs application bundle (app/LogicLabs-linux-all.jar) was not found near $SELF_DIR."
    echo "ERROR: $MSG" >&2
    command -v zenity >/dev/null 2>&1 && zenity --error --title="Logic Labs" --text="$MSG" 2>/dev/null || true
    exit 2
fi

JAVA_BIN="$APP_ROOT/runtime/bin/java"
JAR_PATH="$APP_ROOT/app/LogicLabs-linux-all.jar"

if [[ ! -x "$JAVA_BIN" ]]; then
    MSG="Bundled Java runtime was not found at: $JAVA_BIN"
    echo "ERROR: $MSG" >&2
    command -v zenity >/dev/null 2>&1 && zenity --error --title="Logic Labs" --text="$MSG" 2>/dev/null || true
    exit 3
fi

FORCE_SOFTWARE=0
if [[ "${LOGICLABS_SOFTWARE_RENDER:-0}" == "1" ]]; then
    FORCE_SOFTWARE=1
fi
for arg in "$@"; do
    if [[ "$arg" == "--software" ]]; then
        FORCE_SOFTWARE=1
    fi
done

run_jvm() {
    local sw="$1"
    if [[ "$sw" == "1" ]]; then
        "$JAVA_BIN" \
            -Xms128m \
            -Xmx768m \
            -Dfile.encoding=UTF-8 \
            -Dsun.java2d.dpiaware=true \
            -Dawt.useSystemAAFontSettings=on \
            -Dswing.aatext=true \
            -Dskiko.renderApi=SOFTWARE \
            "-Dlogiclabs.app.dir=$APP_ROOT" \
            -jar "$JAR_PATH"
    else
        "$JAVA_BIN" \
            -Xms128m \
            -Xmx768m \
            -Dfile.encoding=UTF-8 \
            -Dsun.java2d.dpiaware=true \
            -Dawt.useSystemAAFontSettings=on \
            -Dswing.aatext=true \
            "-Dlogiclabs.app.dir=$APP_ROOT" \
            -jar "$JAR_PATH"
    fi
}

if [[ "$FORCE_SOFTWARE" == "1" ]]; then
    exec "$JAVA_BIN" \
        -Xms128m \
        -Xmx768m \
        -Dfile.encoding=UTF-8 \
        -Dsun.java2d.dpiaware=true \
        -Dawt.useSystemAAFontSettings=on \
        -Dswing.aatext=true \
        -Dskiko.renderApi=SOFTWARE \
        "-Dlogiclabs.app.dir=$APP_ROOT" \
        -jar "$JAR_PATH"
fi

START_TS="$(date +%s)"
set +e
run_jvm 0
EXIT_CODE=$?
set -e
END_TS="$(date +%s)"
ELAPSED=$((END_TS - START_TS))

# Automatic fallback to Skia software renderer if GPU initialization failed quickly on an older Linux Mint laptop
if [[ "$EXIT_CODE" -ne 0 && "$ELAPSED" -le 4 ]]; then
    echo "[Logic Labs] Hardware renderer exited rapidly (code $EXIT_CODE); retrying with Skia SOFTWARE renderer..." >&2
    exec "$JAVA_BIN" \
        -Xms128m \
        -Xmx768m \
        -Dfile.encoding=UTF-8 \
        -Dsun.java2d.dpiaware=true \
        -Dawt.useSystemAAFontSettings=on \
        -Dswing.aatext=true \
        -Dskiko.renderApi=SOFTWARE \
        "-Dlogiclabs.app.dir=$APP_ROOT" \
        -jar "$JAR_PATH"
fi

exit "$EXIT_CODE"
