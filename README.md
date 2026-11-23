# Logic Labs — Windows & Linux Mint Desktop Edition

A native **Windows 10/11** and **Linux Mint (Ubuntu/Debian x64)** desktop port of [Logic Labs](https://github.com/AdnanFoisal/LogicLabs) (v1.1.2) — a full offline digital twin of the **K&H IDL-800A Digital Logic Trainer** and **AD-200 Breadboard**, rebuilt with Kotlin + Compose Multiplatform Desktop (Skiko/JVM) for mouse pointer, keyboard shortcuts, and laptop/PC screens.

## Features (100% Parity + Desktop Enhancements)

- **Authentic AD-200 Breadboard & 14 TTL 74xx ICs**: `7400`, `7402`, `7404`, `7408`, `7410`, `7411`, `7420`, `7432`, `7448`, `7474`, `7476`, `7483`, `7486`, `74266` with DIP pinout diagrams, internal gate mappings, and double-click IC Pinout Inspector.
- **49 Guided Experiments**: 12 classic curriculum labs + 37 extended bench builds, live truth tables, ANSI gate schematics, and exhaustive $2^N$ Truth-Table Verification with HMAC-SHA256 seal and A4 PDF Lab Certificate export (Apache PDFBox).
- **K&H IDL-800A Trainer Console Rack**: Resizable/collapsible right-hand workstation dock with Master `+5V` Power, Data Switches `SW0–SW7`, Momentary Pulsers `A/B`, Logic LEDs `L0–L7` with POV afterglow, Dual 7-Segment Hex displays, Stepped Clock (`1 Hz – 100 kHz` + Single-Step), Diagnostics, and `IDEAL` vs `PRACTICAL` IC burnout/explosion physics.
- **Desktop Instrumentation**: Dual-Channel CRT Oscilloscope, Live Gate-Level Boolean Schematic (`D`), Synthesized Console Audio (`javax.sound.sampled`), 9 Hardware Themes (`OBSIDIAN`, `AMBER_CRT`, `HP_SLATE`, `CLEANROOM`, `CYBERPUNK_NEON`, `TOKYO_NIGHT`, `SOLARIZED_DARK`, `VINTAGE_BRITISH_LAB`, `TITANIUM_FROST`).
- **Desktop Mouse & Keyboard Ergonomics**:
  - Scroll-wheel zoom toward cursor, middle-drag / `Space`+drag pan, hover socket voltage & IC pin readout, live armed-wire cursor preview, right-click context menus, toggleable `2.2x` optical loupe (`L`/`Alt`), double-click IC pinout inspector.
  - Full CAD & Console hotkeys (`1–8` for `SW0–SW7`, `Q`/`E` Pulsers, `P` Power, `K`/`.` Clock, `W`/`V`/`S`/`R`/`M`/`Delete`/`F`/`+`/`-`/`C`/`B`/`O`/`D`/`F5`/`Esc`), keyboard-navigable catalogs (`↑`/`↓`/`Enter`), and **Multi-step Undo / Redo (`Ctrl+Z` / `Ctrl+Y`)**.
  - Built-in **Controls & Keyboard Shortcuts Guide** reachable via `F1`, `?`, top menu **Help**, Home sidebar, and Settings.
- **Cross-OS Storage & Sharing**: Persists to `%APPDATA%\LogicLabs` on Windows and `~/.local/share/LogicLabs` on Linux by default, or `<appDir>/data` automatically when an empty `portable.txt` sits beside the executable. Native JSON import/export and `LOGIC-...` bytecode clipboard sharing.

## Ready-to-Run Distributions

### Windows 10 / 11 x64
Located in `dist/LogicLabs-Windows-Portable/` and `dist/LogicLabs-Windows-Portable.zip`:
- Extract the ZIP and double-click `LogicLabs.exe` (bundles Eclipse Temurin 21 Windows x64 JRE — no Java install required).
- Includes Per-Monitor V2 DPI manifest for crisp display on laptops (`1366×768` at 100%–150% scaling) and PC monitors, plus automatic GPU-to-software-render fallback.

### Linux Mint / Ubuntu / Debian x64
Located in `/home/antigravity/LogicLabs-Linux/`:
- **`logic-labs_1.1.2_amd64.deb`**: Double-click in Linux Mint (GDebi) or run `sudo apt install ./logic-labs_1.1.2_amd64.deb` (bundles Eclipse Temurin 21 Linux x64 JRE, GLIBC 2.17+ compatible for Mint 19–22 & LMDE, Application Menu shortcut + hicolor icons).
- **`LogicLabs-Linux-Mint-Portable.tar.gz`**: Extract anywhere and run `./LogicLabs` (zero `sudo` required; includes `./install-desktop-shortcut.sh` and automatic GPU-to-software-render fallback).

## Building From Source

### Run locally (Linux / Windows / macOS with JDK 21)
```bash
./gradlew :desktop-app:run
```

### Run full 9-module test suite
```bash
./gradlew test
```

### Rebuild Linux Mint `.deb` & portable bundle
```bash
./packaging/build-linux-mint.sh
```

### Build official Windows `.exe` / `.msi` installer (on Windows 10/11 with JDK 21 + WiX 3.x)
```bat
build-windows.bat
```
Or push to GitHub to run `.github/workflows/build-windows.yml` on `windows-latest`.

## License & Attribution

- Licensed under **GPL-3.0-only** — see [`LICENSE`](LICENSE) and [`THIRD_PARTY_NOTICES.md`](THIRD_PARTY_NOTICES.md).
- Original Logic Labs Android source code by **Adnan Foisal (CUET CSE)**.
- `:core-digital` simulation engine derived from **hneemann/Digital** (GPL-3.0).
