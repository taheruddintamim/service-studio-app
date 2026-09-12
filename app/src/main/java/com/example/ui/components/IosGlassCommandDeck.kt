package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.NorthEast
import androidx.compose.material.icons.filled.Public
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LocalAppThemeIsDark
import com.example.ui.theme.StudioBrandBlue

enum class DockSegment(val index: Int, val label: String) {
    STUDIO(0, "01 STUDIO"),
    DIRECT_REACH(1, "02 REACH"),
    ELSEWHERE(2, "03 ELSEWHERE")
}

data class ExternalChannel(
    val title: String,
    val description: String,
    val url: String,
    val badge: String
)

val studioExternalChannels = listOf(
    ExternalChannel(
        title = "Behance",
        description = "Case Studies & Vector Folio",
        url = "https://www.behance.net/tahertamim",
        badge = "FOLIO"
    ),
    ExternalChannel(
        title = "LinkedIn",
        description = "Professional Dossier",
        url = "https://www.linkedin.com/in/taher-tamim/",
        badge = "CAREER"
    ),
    ExternalChannel(
        title = "Facebook",
        description = "Public Network",
        url = "https://www.facebook.com/TaherUddinTamim",
        badge = "COMMUNITY"
    ),
    ExternalChannel(
        title = "Instagram",
        description = "Visual Experiments",
        url = "https://www.instagram.com/ami.e.taher/",
        badge = "CREATIVE"
    )
)

@Composable
fun IosGlassCommandDeck(
    selectedSegment: DockSegment,
    onSegmentSelected: (DockSegment) -> Unit,
    isElsewhereOpen: Boolean,
    onToggleElsewhere: () -> Unit,
    onDismissElsewhere: () -> Unit,
    modifier: Modifier = Modifier
) {
    val isDark = LocalAppThemeIsDark.current
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme

    // Glass Styling Tokens
    val dockBgColor = if (isDark) {
        Color(18, 18, 20, 180) // rgba(18, 18, 20, 0.70)
    } else {
        Color(255, 255, 255, 185) // rgba(255, 255, 255, 0.72)
    }

    val dockBorderColor = if (isDark) {
        Color(255, 255, 255, 26) // rgba(255, 255, 255, 0.10)
    } else {
        Color(255, 255, 255, 153) // rgba(255, 255, 255, 0.60)
    }

    val shadowColor = if (isDark) {
        Color(0, 0, 0, 130)
    } else {
        Color(15, 23, 42, 20)
    }

    val activePillBg = if (isDark) {
        Color(255, 255, 255, 31) // rgba(255, 255, 255, 0.12)
    } else {
        Color(0x0F, 0x17, 0x2A) // Solid #0F172A
    }

    val activePillText = Color.White
    val inactiveText = if (isDark) Color(0xFF8E8E93) else Color(0xFF475569)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 1. Sleek Popover Capsule / Micro-Sheet (when 03 ELSEWHERE is tapped)
        AnimatedVisibility(
            visible = isElsewhereOpen,
            enter = slideInVertically(
                initialOffsetY = { it / 2 },
                animationSpec = spring(dampingRatio = Spring.DampingRatioLowBouncy, stiffness = Spring.StiffnessMedium)
            ) + fadeIn(),
            exit = slideOutVertically(targetOffsetY = { it / 2 }) + fadeOut()
        ) {
            Card(
                modifier = Modifier
                    .widthIn(max = 380.dp)
                    .fillMaxWidth()
                    .padding(bottom = 10.dp)
                    .shadow(16.dp, RoundedCornerShape(20.dp), spotColor = shadowColor)
                    .border(
                        1.dp,
                        if (isDark) Color(255, 255, 255, 35) else Color(255, 255, 255, 200),
                        RoundedCornerShape(20.dp)
                    )
                    .testTag("elsewhere_popover_sheet"),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(
                    containerColor = if (isDark) Color(0xFF141416).copy(alpha = 0.96f) else Color.White.copy(alpha = 0.96f)
                )
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "ELSEWHERE // CHANNELS",
                            color = StudioBrandBlue,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.05.sp
                        )
                        Box(
                            modifier = Modifier
                                .clip(CircleShape)
                                .background(colorScheme.surfaceVariant)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) { onDismissElsewhere() }
                                .padding(horizontal = 8.dp, vertical = 2.dp)
                        ) {
                            Text(
                                text = "CLOSE ✕",
                                color = colorScheme.secondary,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    studioExternalChannels.forEach { channel ->
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 4.dp)
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isDark) Color(0xFF1A1A1E) else Color(0xFFF1F5F9))
                                .border(
                                    1.dp,
                                    if (isDark) Color(255, 255, 255, 15) else Color(0, 0, 0, 15),
                                    RoundedCornerShape(10.dp)
                                )
                                .clickable {
                                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(channel.url))
                                    context.startActivity(intent)
                                }
                                .padding(horizontal = 14.dp, vertical = 10.dp),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Column {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Text(
                                        text = channel.title,
                                        color = colorScheme.onSurface,
                                        fontSize = 13.sp,
                                        fontWeight = FontWeight.Bold
                                    )
                                    Spacer(modifier = Modifier.width(6.dp))
                                    Box(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(3.dp))
                                            .background(StudioBrandBlue.copy(alpha = 0.15f))
                                            .padding(horizontal = 5.dp, vertical = 1.dp)
                                    ) {
                                        Text(
                                            text = channel.badge,
                                            color = StudioBrandBlue,
                                            fontSize = 8.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }
                                }
                                Spacer(modifier = Modifier.height(2.dp))
                                Text(
                                    text = channel.description,
                                    color = colorScheme.secondary,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Normal
                                )
                            }
                            Icon(
                                imageVector = Icons.Default.NorthEast,
                                contentDescription = "Open link",
                                tint = StudioBrandBlue,
                                modifier = Modifier.size(15.dp)
                            )
                        }
                    }
                }
            }
        }

        // 2. Fixed Floating Segmented Command Bar (Glass Dock)
        Box(
            modifier = Modifier
                .widthIn(max = 380.dp)
                .fillMaxWidth(0.94f)
                .height(52.dp)
                .shadow(
                    elevation = if (isDark) 16.dp else 12.dp,
                    shape = CircleShape,
                    spotColor = shadowColor,
                    ambientColor = shadowColor
                )
                .clip(CircleShape)
                .background(dockBgColor)
                .border(1.dp, dockBorderColor, CircleShape)
                .padding(4.dp)
                .testTag("ios_glass_command_deck"),
            contentAlignment = Alignment.CenterStart
        ) {
            BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
                val totalWidth = maxWidth
                val segmentWidth = totalWidth / 3

                val animatedOffsetX by animateDpAsState(
                    targetValue = segmentWidth * selectedSegment.index,
                    animationSpec = spring(
                        dampingRatio = Spring.DampingRatioLowBouncy,
                        stiffness = Spring.StiffnessMedium
                    ),
                    label = "dock_segment_indicator"
                )

                // Animated Sliding Highlight Pill
                Box(
                    modifier = Modifier
                        .offset(x = animatedOffsetX)
                        .width(segmentWidth)
                        .height(44.dp)
                        .clip(CircleShape)
                        .background(activePillBg)
                        .border(
                            1.dp,
                            if (isDark) Color(255, 255, 255, 38) else Color(0, 0, 0, 10),
                            CircleShape
                        )
                )

                // Three Segment Buttons
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DockSegment.entries.forEach { segment ->
                        val isSelected = selectedSegment == segment

                        Box(
                            modifier = Modifier
                                .width(segmentWidth)
                                .height(44.dp)
                                .clip(CircleShape)
                                .clickable(
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = null
                                ) {
                                    if (segment == DockSegment.ELSEWHERE) {
                                        onToggleElsewhere()
                                    } else {
                                        onDismissElsewhere()
                                        onSegmentSelected(segment)
                                    }
                                }
                                .testTag("dock_segment_${segment.name.lowercase()}"),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = segment.label,
                                color = if (isSelected) activePillText else inactiveText,
                                fontSize = 10.5.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.SemiBold,
                                letterSpacing = 0.04.sp
                            )
                        }
                    }
                }
            }
        }
    }
}
