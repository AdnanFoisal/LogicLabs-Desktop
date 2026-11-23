package com.logiclabs.app.ui.hud

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import com.logiclabs.app.ui.state.BoardMode
import com.logiclabs.core.designsystem.component.LogicIcons
import com.logiclabs.core.designsystem.theme.AccentCyan
import com.logiclabs.core.designsystem.theme.AmberCore
import com.logiclabs.core.designsystem.theme.ChassisBase
import com.logiclabs.core.designsystem.theme.Dimens
import com.logiclabs.core.designsystem.theme.LogicLabsType
import com.logiclabs.core.designsystem.theme.SurfaceCard
import com.logiclabs.core.designsystem.theme.SurfaceCardBorder
import com.logiclabs.core.designsystem.theme.TextPrimary
import com.logiclabs.core.designsystem.theme.TextSecondary
import com.logiclabs.core.designsystem.theme.TextTertiary

/** The actions the rail exposes. */
enum class ToolAction {
    CHIPS,
    SCOPE,
    LABS,
    ROAM,
    FIT_VIEW,
    ROTATE,
    CLEAR,
    DIAGRAM
}

/**
 * Horizontally swipable tool ribbon inspired by professional EDA toolbars.
 *
 * Replaces the rigid 6-segment fixed bar with a side-scrollable ribbon of rounded
 * action pills (`Scroll`, `Wire`, `Select`, `ICs`, `Scope`, `Logic`, `Labs`, `Fit View`,
 * `Rotate`, `Clear`), plus a slim telemetry status line beneath it (`LATENCY`, `MODE`,
 * `REALTIME`).
 */
@Composable
fun ToolRail(
    boardMode: BoardMode,
    onAction: (ToolAction) -> Unit,
    onModeChange: (BoardMode) -> Unit,
    deleteLabel: String,
    simulationModeLabel: String = "PRACTICAL",
    chipCount: Int = 0,
    wireCount: Int = 0,
    diagramActive: Boolean = false,
    modifier: Modifier = Modifier
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier.fillMaxWidth()
    ) {
        // 1. Horizontal Scrollable Tool Ribbon
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState)
                .padding(horizontal = Dimens.CommandBarInset, vertical = 4.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Roam / Pan tool
            ToolRibbonPill(
                icon = LogicIcons.Roam,
                label = "ROAM",
                shortcut = "V",
                isActive = boardMode == BoardMode.ROAM,
                onClick = { onModeChange(BoardMode.ROAM) }
            )

            // Wire Draw tool
            ToolRibbonPill(
                icon = LogicIcons.WireDraw,
                label = "WIRE",
                shortcut = "W",
                isActive = boardMode == BoardMode.WIRE_DRAW,
                onClick = {
                    onModeChange(
                        if (boardMode == BoardMode.WIRE_DRAW) BoardMode.ROAM else BoardMode.WIRE_DRAW
                    )
                }
            )

            // Wire / IC Select tool
            ToolRibbonPill(
                icon = LogicIcons.WireSelect,
                label = "SELECT",
                shortcut = "S",
                isActive = boardMode == BoardMode.WIRE_SELECT,
                onClick = {
                    onModeChange(
                        if (boardMode == BoardMode.WIRE_SELECT) BoardMode.ROAM else BoardMode.WIRE_SELECT
                    )
                }
            )

            // IC Chip catalog
            ToolRibbonPill(
                icon = LogicIcons.Chip,
                label = "ICs",
                shortcut = "C",
                isActive = false,
                onClick = { onAction(ToolAction.CHIPS) }
            )

            // Oscilloscope & Logic Analyzer
            ToolRibbonPill(
                icon = LogicIcons.Scope,
                label = "SCOPE",
                shortcut = "O",
                isActive = false,
                onClick = { onAction(ToolAction.SCOPE) }
            )

            // Boolean / gate-level diagram view of the built circuit
            ToolRibbonPill(
                icon = LogicIcons.Schematic,
                label = "LOGIC",
                shortcut = "D",
                isActive = diagramActive,
                onClick = { onAction(ToolAction.DIAGRAM) }
            )

            // Coursework Lab catalog
            ToolRibbonPill(
                icon = LogicIcons.Labs,
                label = "LABS",
                shortcut = "B",
                isActive = false,
                onClick = { onAction(ToolAction.LABS) }
            )

            // Fit to Viewport / Frame board
            ToolRibbonPill(
                icon = LogicIcons.FitBoard,
                label = "FIT",
                shortcut = "F",
                isActive = false,
                onClick = { onAction(ToolAction.FIT_VIEW) }
            )

            // Rotate selected IC
            ToolRibbonPill(
                icon = LogicIcons.Rotate,
                label = "ROTATE",
                shortcut = "R",
                isActive = false,
                onClick = { onAction(ToolAction.ROTATE) }
            )

            // Delete selected / Clear
            ToolRibbonPill(
                icon = LogicIcons.Trash,
                label = deleteLabel,
                shortcut = "Del",
                isActive = false,
                onClick = { onAction(ToolAction.CLEAR) }
            )
        }

        // 2. Slim Telemetry Strip
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(ChassisBase)
                .horizontalScroll(rememberScrollState())
                .padding(horizontal = Dimens.CommandBarInset + 2.dp, vertical = 3.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "ELEMENTS: $chipCount ICs, $wireCount WIRES",
                style = LogicLabsType.SwitchPlate,
                color = AccentCyan,
                softWrap = false
            )
            Text(
                text = "MODE: $simulationModeLabel",
                style = LogicLabsType.SwitchPlate,
                color = AmberCore,
                softWrap = false
            )
            Text(
                text = "1 : 1 REALTIME",
                style = LogicLabsType.SwitchPlate,
                color = TextTertiary,
                softWrap = false
            )
            Text(
                text = "RIGHT-CLICK FOR MENU · SCROLL ZOOM · F1 CONTROLS",
                style = LogicLabsType.SwitchPlate,
                color = TextTertiary,
                softWrap = false
            )
        }
    }
}

@Composable
private fun ToolRibbonPill(
    icon: ImageVector,
    label: String,
    shortcut: String? = null,
    isActive: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val shape = RoundedCornerShape(8.dp)
    val bgColor = if (isActive) AmberCore else SurfaceCard
    val contentColor = if (isActive) ChassisBase else TextSecondary
    val borderColor = if (isActive) AmberCore else SurfaceCardBorder

    Row(
        modifier = modifier
            .clip(shape)
            .background(bgColor)
            .border(1.dp, borderColor, shape)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                role = Role.Button,
                onClick = onClick
            )
            .padding(horizontal = 9.dp, vertical = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            modifier = Modifier.size(15.dp),
            tint = contentColor
        )
        Text(
            text = label,
            style = LogicLabsType.SwitchPlate,
            color = contentColor
        )
        if (shortcut != null) {
            Text(
                text = "[$shortcut]",
                style = LogicLabsType.TechnicalXs,
                color = if (isActive) ChassisBase.copy(alpha = 0.8f) else TextTertiary
            )
        }
    }
}
