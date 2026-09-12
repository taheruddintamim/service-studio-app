package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.StudioBrandBlue

@Composable
fun HeroSection(
    onExploreDisciplines: () -> Unit = {},
    onStartProject: () -> Unit = {}
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 24.dp)
            .testTag("hero_section")
    ) {
        // Tagline badge
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 12.dp)
        ) {
            Text(
                text = "SERVICE STUDIO",
                color = StudioBrandBlue,
                fontSize = 11.sp,
                letterSpacing = 0.08.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(modifier = Modifier.width(8.dp))
            Box(
                modifier = Modifier
                    .width(20.dp)
                    .height(1.dp)
                    .background(colorScheme.outlineVariant)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "V.04",
                color = colorScheme.secondary,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.04.sp
            )
        }

        // Main Primary Title
        Text(
            text = "An analytical mind paired with graphic design discipline. Offering focused creative and educational services.",
            color = colorScheme.onSurface,
            fontSize = 24.sp,
            lineHeight = 32.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.01).sp,
            modifier = Modifier.padding(vertical = 4.dp)
        )

        // Subtitle Narrative
        Text(
            text = "A single, quiet studio practice driven by structural precision, intentional restraint, and personal accountability across every single engagement.",
            color = colorScheme.secondary,
            fontSize = 14.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 10.dp, bottom = 22.dp)
        )

        // Quick Direct Actions
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // Start Conversation (Primary)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(StudioBrandBlue)
                    .clickable { onStartProject() }
                    .padding(vertical = 13.dp)
                    .testTag("hero_start_project_button")
            ) {
                Text(
                    text = "START A PROJECT →",
                    color = Color.White,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.04.sp
                )
            }

            // Discover Services (Secondary outline)
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .weight(1f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(colorScheme.surfaceContainerLow)
                    .border(width = 1.dp, color = colorScheme.outlineVariant, shape = RoundedCornerShape(8.dp))
                    .clickable { onExploreDisciplines() }
                    .padding(vertical = 13.dp)
                    .testTag("hero_explore_button")
            ) {
                Text(
                    text = "DISCOVER SERVICES",
                    color = colorScheme.onSurface,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.04.sp
                )
            }
        }
    }
}
