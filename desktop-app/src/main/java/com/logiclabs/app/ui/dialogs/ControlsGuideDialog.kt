package com.logiclabs.app.ui.dialogs

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.logiclabs.core.designsystem.component.DesktopModalDialog
import com.logiclabs.core.designsystem.component.LogicIcons
import com.logiclabs.core.designsystem.component.SheetScaffold
import com.logiclabs.core.designsystem.theme.AccentCyan
import com.logiclabs.core.designsystem.theme.AmberCore
import com.logiclabs.core.designsystem.theme.ChassisDivider
import com.logiclabs.core.designsystem.theme.Dimens
import com.logiclabs.core.designsystem.theme.LogicLabsType
import com.logiclabs.core.designsystem.theme.PhosphorCore
import com.logiclabs.core.designsystem.theme.SurfaceCard
import com.logiclabs.core.designsystem.theme.SurfaceCardBorder
import com.logiclabs.core.designsystem.theme.SurfaceRaised
import com.logiclabs.core.designsystem.theme.TextPrimary
import com.logiclabs.core.designsystem.theme.TextSecondary

private data class ControlRow(val keys: String, val action: String)

private val MouseControls = listOf(
    ControlRow("Scroll Wheel", "Zoom in / out smoothly toward mouse pointer"),
    ControlRow("Middle-Drag / Space+Drag", "Pan the breadboard in any mode"),
    ControlRow("Left-Drag (Empty Board)", "Pan the breadboard in Roam / Select mode"),
    ControlRow("Left-Click / Drag (Wire)", "Click hole A then hole B, or click-drag to connect"),
    ControlRow("Right-Click", "Context menu on chip/wire/board — or cancel armed wire"),
    ControlRow("Drag IC / Wire Endpoint", "Move placed IC across columns/trenches, or re-terminate wire"),
    ControlRow("Double-Click IC", "Open live IC Pinout & Truth Table Inspector"),
    ControlRow("Hold Alt (or press L)", "Show 2.2x optical magnifying loupe at cursor")
)

private val BreadboardHotkeys = listOf(
    ControlRow("W", "Toggle Wire Draw mode"),
    ControlRow("V", "Roam / Pan & Select mode"),
    ControlRow("S", "Wire Select mode"),
    ControlRow("Esc", "Cancel armed wire, clear selection, or close dialog"),
    ControlRow("R", "Rotate selected IC 180°"),
    ControlRow("M", "Toggle Catenary / Manhattan routing on selected wire"),
    ControlRow("Arrow Keys", "Nudge selected IC (or pan board when nothing selected)"),
    ControlRow("Delete / Backspace", "Delete selected wire or IC"),
    ControlRow("Ctrl + Z / Ctrl + Y", "Multi-step Undo / Redo"),
    ControlRow("F  or  0", "Fit circuit / breadboard to screen"),
    ControlRow("+  /  -", "Zoom in / out"),
    ControlRow("L", "Toggle 2.2x optical magnifying loupe")
)

private val ConsoleHotkeys = listOf(
    ControlRow("1  –  8", "Toggle Data Switches SW0 – SW7"),
    ControlRow("Q  /  E", "Momentary Pulser A / Pulser B (hold & release)"),
    ControlRow("P", "Toggle Master +5V Power Supply"),
    ControlRow("K", "Start / Pause Stepped Clock Generator"),
    ControlRow(".", "Single-Step Clock one half-cycle")
)

private val WorkspaceHotkeys = listOf(
    ControlRow("C", "Open 74xx IC Chip Catalog (type to filter, Arrows + Enter)"),
    ControlRow("B", "Open Guided Lab Coursework Catalog (49 experiments)"),
    ControlRow("O", "Open Dual-Channel CRT Oscilloscope"),
    ControlRow("D", "Toggle Live Gate-Level Boolean Schematic"),
    ControlRow("F5", "Run Exhaustive 2^N Truth-Table Verification"),
    ControlRow("Ctrl + S", "Save Project"),
    ControlRow("Ctrl + O / Ctrl + E", "Import / Export Project (.json)"),
    ControlRow("Ctrl + 1 .. 4", "Switch Home tabs (Projects, Coursework, ICs, Settings)"),
    ControlRow("Tab / Arrows / Enter", "Navigate and select options across catalogs, menus & dialogs"),
    ControlRow("F1  or  ?", "Open this Controls & Shortcuts Guide")
)

/**
 * Dedicated Controls & Keyboard Shortcuts Reference modal, reachable via F1, ?,
 * the top toolbar '?' button, the Help menu, and Settings.
 */
@Composable
fun ControlsGuideDialog(onDismiss: () -> Unit) {
    DesktopModalDialog(
        onDismissRequest = onDismiss,
        maxWidth = 960.dp,
        maxHeight = 720.dp
    ) {
        SheetScaffold(
            title = "CONTROLS & KEYBOARD SHORTCUTS",
            subtitle = "mouse pointer, breadboard CAD hotkeys, trainer console & keyboard navigation",
            titleIcon = LogicIcons.Info,
            onDismiss = onDismiss
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(Dimens.Space2)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.Space2)
                ) {
                    ControlSectionCard(
                        title = "MOUSE POINTER",
                        accent = AccentCyan,
                        rows = MouseControls,
                        modifier = Modifier.weight(1f)
                    )
                    ControlSectionCard(
                        title = "BREADBOARD HOTKEYS",
                        accent = PhosphorCore,
                        rows = BreadboardHotkeys,
                        modifier = Modifier.weight(1f)
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(Dimens.Space2)
                ) {
                    ControlSectionCard(
                        title = "TRAINER CONSOLE KEYS",
                        accent = AmberCore,
                        rows = ConsoleHotkeys,
                        modifier = Modifier.weight(1f)
                    )
                    ControlSectionCard(
                        title = "TOOLS, FILES & KEYBOARD SELECTION",
                        accent = AccentCyan,
                        rows = WorkspaceHotkeys,
                        modifier = Modifier.weight(1f)
                    )
                }

                Text(
                    text = "Tip: Press C to open the Chip Catalog, type a part like '7408', use Up/Down arrows, and press Enter to place it immediately.",
                    style = LogicLabsType.BodySm,
                    color = TextSecondary
                )
            }
        }
    }
}

@Composable
private fun ControlSectionCard(
    title: String,
    accent: androidx.compose.ui.graphics.Color,
    rows: List<ControlRow>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(Dimens.RadiusMd))
            .background(SurfaceCard)
            .border(Dimens.Hairline, SurfaceCardBorder, RoundedCornerShape(Dimens.RadiusMd))
            .padding(horizontal = Dimens.Space3, vertical = Dimens.Space2),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            style = LogicLabsType.SwitchPlateLg,
            color = accent
        )
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(Dimens.Hairline)
                .background(ChassisDivider)
        )
        rows.forEach { row ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .clip(RoundedCornerShape(Dimens.RadiusSm))
                        .background(SurfaceRaised)
                        .border(Dimens.Hairline, accent.copy(alpha = 0.45f), RoundedCornerShape(Dimens.RadiusSm))
                        .padding(horizontal = 6.dp, vertical = 2.dp)
                ) {
                    Text(
                        text = row.keys,
                        style = LogicLabsType.TechnicalXs,
                        color = accent
                    )
                }
                Spacer(Modifier.width(Dimens.Space2))
                Text(
                    text = row.action,
                    style = LogicLabsType.BodySm,
                    color = TextPrimary,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}
