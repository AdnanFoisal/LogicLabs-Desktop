# Changelog

All notable changes to **Logic Labs Desktop** are documented in this file.

The format follows [Keep a Changelog](https://keepachangelog.com/en/1.1.0/), and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.1.3] — 2026-11-23

### Performance (4 GB RAM Optimization)
- **11.2× Faster Rasterized Breadboard (`BoardCache`):** Upgraded the breadboard static layer from a replayed Skia `Picture` (39.60 ms/frame) to a RAM-capped offscreen Skia `Image` (`3.52 ms/frame`, `8.70 MB` footprint), delivering silky panning and zooming even under software rendering.
- **Zero-Stall Async Console Audio (`ConsoleSfx`):** Moved synthesized switch/relay/chime `javax.sound.sampled` audio playback onto a dedicated non-blocking daemon queue (`0.0117 ms/click`), eliminating UI thread freezes when clicking switches or routing wires.
- **3-Layer `BreadboardCanvas` Separation:** Split static board raster, dynamic nets/ICs/wires, and interactive cursor/loupe overlays so pointer hovers never invalidate the full board.
- **Removed Heavy Idle GPU/CPU Loops:** Replaced 16 px Gaussian `RenderEffect` blur in `GlassSurface` with crisp translucent surfaces and eliminated continuous 60 FPS idle `rememberInfiniteTransition` redraws.
- **4 GB RAM JVM & Launcher Tuning:** Updated Linux (`packaging/linux/logic-labs.sh`) and Windows (`packaging/windows/launcher.c`) launchers to `-Xms48m -Xmx320m -XX:+UseSerialGC` plus automatic Mesa `llvmpipe` detection on Linux Mint/Ubuntu.

### UI & Desktop Ergonomics Polish
- **Hardware-Themed Scrollbars Everywhere (`DesktopScrollbars.kt`):** Added visible, theme-matched vertical and horizontal scrollbars across all 18 scrollable views (Home, Coursework, 74xx IC Reference, Settings, Trainer Console, Oscilloscope probe bars, Verifier table, Chip/Lab catalogs, and Pinout Inspector).
- **Horizontal Mouse-Wheel & Overflow Arrows:** Enabled standard scroll-wheel horizontal scrolling plus clickable `‹` / `›` navigation buttons on horizontal chips, IC tabs, and Oscilloscope probe pickers.
- **Keyboard Paging & Clock Dial Ergonomics:** Added `PageUp` / `PageDown` scrolling across dialogs/pages, `Shift + Scroll Wheel` + clickable `−` / `+` frequency step buttons on `ClockDial`, and `[8]..[1]` keycap hints directly beneath Data Switches `SW7..SW0`.
- **Home Hub & Coursework Overhaul:** Added a **Quick-Start Classic Labs** shelf on Home, curriculum unit filter chips in Coursework, and fixed ANSI gate schematics so symbols sit crisply beside their truth tables without stretching.
- **Desktop Multi-Column Layouts:** Upgraded 74xx IC Reference to a 2-column datasheet (`DIP package + TTL specs` & 2-column internal gate grid) and Settings to a spacious 2-column desktop rack.
- **Workbench & Theme Refinements:** Balanced `CommandBar` with hover tooltips, crisp `−` / `+` zoom buttons, sharper Cleanroom daylight splitter contrast, responsive `DesktopModalDialog` sizing, and fixed `7-SEG BCD / HEX A / HEX B` breadboard faceplate silkscreen alignment.

### Verification & Distribution
- Expanded automated test suite to **301 passing tests** across all 9 Gradle modules, including `PerformanceArchitectureTest` and headless `DesktopVisualAuditTest`.
- Published self-contained binaries for Linux Mint/Ubuntu/Debian (`.deb` & portable `.tar.gz`) and Windows 10/11 (portable `.zip`).

---

## [1.1.2] — 2026-11-23

### Added
- Initial native **Windows 10/11** and **Linux Mint (Ubuntu/Debian x64)** desktop port of Logic Labs built with Kotlin 2.0 + Compose Multiplatform Desktop.
- Full parity with Logic Labs Android: AD-200 Breadboard, 14 TTL 74xx ICs, K&H IDL-800A Trainer Console, 49 Guided Experiments, Dual-Channel CRT Oscilloscope, Live Gate-Level Boolean Schematic, Exhaustive $2^N$ Truth-Table Verifier with A4 PDF Lab Certificate export, and 9 Hardware Themes.
- Desktop CAD mouse interactions (cursor-centered wheel zoom, middle/Space drag pan, right-click context menus, double-click IC Pinout Inspector, `2.2×` optical loupe) and full keyboard shortcuts (`F1` Controls Guide, multi-step `Ctrl+Z` / `Ctrl+Y` Undo/Redo).
- Cross-OS offline persistence (`%APPDATA%\LogicLabs`, `~/.local/share/LogicLabs`) plus `portable.txt` USB mode, `.logiclabs.json` import/export, and `LOGIC-...` share codes.
- Self-contained Linux `.deb`, Linux Portable `.tar.gz`, and Windows Portable `.zip` distributions bundling Eclipse Temurin 21 JRE.
