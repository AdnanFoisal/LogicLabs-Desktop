# Contributing to Logic Labs Desktop

Thank you for your interest in improving **Logic Labs Desktop**! Whether you want to add a new TTL 74xx IC, author a guided digital logic experiment, fix a UI/rendering bug, or optimize performance for older classroom PCs, contributions are warmly welcomed.

---

## Development Setup

### Prerequisites
- **JDK 21** (Eclipse Temurin 21 recommended; Gradle targets JVM 17 bytecode)
- **Git**
- **Linux, Windows, or macOS** (no Android SDK required)

### Clone & Run Locally

```bash
git clone https://github.com/AdnanFoisal/LogicLabs-Desktop.git
cd LogicLabs-Desktop
./gradlew :desktop-app:run
```

On Windows PowerShell/CMD, use `.\gradlew.bat :desktop-app:run`.

---

## Architecture & Module Boundaries

Logic Labs Desktop keeps simulation logic strictly separated from Compose UI so tests run fast and headlessly:

- **`:core-digital`** — Pure JVM discrete-event logic simulator (GPL-3.0, derived from `hneemann/Digital`). Keep free of UI dependencies.
- **`:core-bridge`** — Breadboard tie-point topology (DSU netlist), 14 TTL 74xx IC specs (`TTLChipCatalog`), and `IDEAL` vs `PRACTICAL` simulation rules.
- **`:core-data`** — Offline JSON persistence, autosave drafts, `LOGIC-...` share bytecode codec, settings, and badge tracking.
- **`:core-designsystem`** — 9 hardware color palettes (`HardwareTheme`), fonts, icons, `DesktopScrollbars`, and modal scaffolds.
- **`:feature-breadboard`** — 3-layer `BreadboardCanvas`, offscreen rasterized `BoardCache`, painters (`DipPainter`, `WirePainter`, `LoupeRenderer`), and mouse/keyboard CAD gestures.
- **`:feature-instruments`** — K&H IDL-800A `TrainerConsoleDock`, `ClockDial`, switches, pulsers, LEDs, and 7-segment displays.
- **`:feature-tools`** — 49 coursework labs, Dual-Trace CRT Oscilloscope, Live Gate-Level Schematic, $2^N$ Truth-Table Verifier, A4 PDF Certificates, and async `ConsoleSfx`.
- **`:desktop-app`** — Desktop window entry point (`Main.kt`), Home Hub, Workbench composition, menu bar, global hotkeys, and visual/performance test suites.
- **`:core-testing`** — Multi-module simulation & experiment verification tests.

---

## Verification Checklist Before Opening a PR

1. **Run the full 9-module test suite (301+ tests):**
   ```bash
   ./gradlew test
   ```
2. **If you modified UI screens, dialogs, scrollbars, or themes**, run the headless visual screenshot audit and inspect the rendered PNGs:
   ```bash
   ./gradlew :desktop-app:test \
     --tests "com.logiclabs.app.DesktopVisualAuditTest" \
     -Dlogiclabs.captureScreenshots=build/screenshots
   ```
3. **Keep 4 GB RAM desktops fast:**
   - Avoid adding continuous `rememberInfiniteTransition` loops when the bench is idle.
   - Avoid blocking the Swing/Compose UI thread with disk I/O or audio calls.
   - Ensure any new scrollable view uses the themed scrollbars from `:core-designsystem` (`DesktopScrollbars.kt`).
4. **Never commit build artifacts, personal paths, or tokens** (`dist/`, `dist-cache/`, `build/` are gitignored).

---

## Pull Request Guidelines

- Use clear, descriptive commit messages (e.g., `feat(bridge): add 74138 3-to-8 decoder IC`, `fix(breadboard): prevent wire drag offset at 2x zoom`).
- Include before/after screenshots in your PR description for UI changes.
- By contributing, you agree that your contributions will be licensed under **GPL-3.0-only** (matching [`LICENSE`](LICENSE)).
