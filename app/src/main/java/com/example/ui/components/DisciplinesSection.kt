package com.example.ui.components

import android.content.Intent
import android.net.Uri
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.OpenInNew
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.StudioData
import com.example.model.StudioService
import com.example.ui.theme.StudioBrandBlue

@Composable
fun DisciplinesSection(
    onSelectServiceForInquiry: (String) -> Unit,
    onOpenServiceDetail: (StudioService) -> Unit
) {
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .testTag("disciplines_section")
    ) {
        // Section Divider and Title
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 24.dp)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(colorScheme.outlineVariant)
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "SELECTED DISCIPLINES",
                    color = StudioBrandBlue,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 0.08.sp
                )
                Text(
                    text = "[04 PRACTICES]",
                    color = colorScheme.secondary,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.04.sp
                )
            }
        }

        // The 4 linear typographic cards
        StudioData.services.forEachIndexed { index, service ->
            ServiceLinearCard(
                service = service,
                onInquire = { onSelectServiceForInquiry(service.title) },
                onDetail = { onOpenServiceDetail(service) },
                onOpenUrl = { url ->
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                    context.startActivity(intent)
                }
            )

            if (index < StudioData.services.size - 1) {
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}

@Composable
fun ServiceLinearCard(
    service: StudioService,
    onInquire: () -> Unit,
    onDetail: () -> Unit,
    onOpenUrl: (String) -> Unit
) {
    val colorScheme = MaterialTheme.colorScheme

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(colorScheme.surface)
            .border(1.dp, colorScheme.outlineVariant, RoundedCornerShape(12.dp))
            .padding(18.dp)
            .testTag("service_card_${service.id}")
    ) {
        // Top index bar
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = service.numberTag,
                color = StudioBrandBlue,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.06.sp
            )
            Text(
                text = service.toolsTag,
                color = colorScheme.secondary,
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                letterSpacing = 0.04.sp
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Title
        Text(
            text = service.title,
            color = colorScheme.onSurface,
            fontSize = 20.sp,
            lineHeight = 26.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = (-0.01).sp
        )

        // Description
        Text(
            text = service.shortDescription,
            color = colorScheme.secondary,
            fontSize = 13.5.sp,
            lineHeight = 21.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 8.dp, bottom = 14.dp)
        )

        // Links and Actions
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // External Proof link if present (e.g. Behance)
            if (service.folioUrl != null && service.folioLabel != null) {
                Row(
                    modifier = Modifier
                        .clickable { onOpenUrl(service.folioUrl) }
                        .padding(vertical = 2.dp)
                        .testTag("folio_link_${service.id}"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = service.folioLabel,
                        color = StudioBrandBlue,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.04.sp
                    )
                    Spacer(modifier = Modifier.width(4.dp))
                    Icon(
                        imageVector = Icons.Default.OpenInNew,
                        contentDescription = "External folio",
                        tint = StudioBrandBlue,
                        modifier = Modifier.size(13.dp)
                    )
                }
            }

            // Two Action Buttons: Detail Dossier & Inquire
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Inquire CTA
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(StudioBrandBlue.copy(alpha = 0.14f))
                        .border(1.dp, StudioBrandBlue.copy(alpha = 0.35f), RoundedCornerShape(8.dp))
                        .clickable { onInquire() }
                        .padding(horizontal = 14.dp, vertical = 9.dp)
                        .testTag("inquire_btn_${service.id}"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = service.inquiryActionLabel,
                        color = StudioBrandBlue,
                        fontSize = 11.5.sp,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 0.04.sp
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                        contentDescription = "Inquire",
                        tint = StudioBrandBlue,
                        modifier = Modifier.size(13.dp)
                    )
                }

                // Scope Breakdown Detail Trigger
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorScheme.surfaceContainerLow)
                        .border(1.dp, colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                        .clickable { onDetail() }
                        .padding(horizontal = 12.dp, vertical = 9.dp)
                        .testTag("detail_btn_${service.id}"),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Info,
                        contentDescription = "Scope details",
                        tint = colorScheme.secondary,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = "Scope Dossier",
                        color = colorScheme.onSurface,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}
