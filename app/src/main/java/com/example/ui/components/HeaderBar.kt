package com.example.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DarkMode
import androidx.compose.material.icons.filled.LightMode
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.ui.theme.LocalAppThemeIsDark
import com.example.ui.theme.StudioBrandBlue

@Composable
fun HeaderBar(
    inquiryCount: Int = 0,
    onOpenInquiries: () -> Unit = {},
    onToggleTheme: () -> Unit = {},
    showMonogramInHeader: Boolean = true
) {
    val isDark = LocalAppThemeIsDark.current
    val colorScheme = MaterialTheme.colorScheme

    val rotationAngle by animateFloatAsState(
        targetValue = if (isDark) 0f else 180f,
        animationSpec = tween(durationMillis = 350),
        label = "theme_toggle_rotation"
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(colorScheme.surface.copy(alpha = 0.96f))
            .border(width = 1.dp, color = colorScheme.outlineVariant)
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .testTag("header_bar")
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            // 1. Top-Left Corner: Brand Monogram & Identity
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                if (showMonogramInHeader) {
                    Box(
                        modifier = Modifier
                            .size(34.dp)
                            .testTag("brand_monogram"),
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Taher Studio Monogram",
                            modifier = Modifier.size(34.dp)
                        )
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                }

                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "TAHER UDDIN TAMIM",
                            color = colorScheme.onSurface,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.08.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "SERVICE STUDIO",
                        color = colorScheme.secondary,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.06.sp
                    )
                }
            }

            // 2. Top-Right Corner: Symmetrical Controls (Inquiries badge & Theme Switcher)
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Direct Engagement or Inquiry Log Pill
                if (inquiryCount > 0) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(StudioBrandBlue.copy(alpha = 0.15f))
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null
                            ) { onOpenInquiries() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .testTag("inquiry_history_button")
                    ) {
                        Text(
                            text = "LOG ($inquiryCount)",
                            color = StudioBrandBlue,
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.04.sp
                        )
                    }
                } else {
                    Text(
                        text = "DIRECT ENGAGEMENT",
                        color = StudioBrandBlue,
                        fontSize = 9.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.04.sp
                    )
                }

                // Symmetrical Theme Switcher (Sun / Moon)
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(colorScheme.surfaceVariant)
                        .border(1.dp, colorScheme.outlineVariant, CircleShape)
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) { onToggleTheme() }
                        .testTag("theme_toggle_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = if (isDark) Icons.Default.LightMode else Icons.Default.DarkMode,
                        contentDescription = if (isDark) "Switch to Light Mode" else "Switch to Dark Mode",
                        tint = if (isDark) StudioBrandBlue else colorScheme.onSurface,
                        modifier = Modifier
                            .size(17.dp)
                            .graphicsLayer {
                                rotationZ = rotationAngle
                            }
                    )
                }
            }
        }
    }
}
