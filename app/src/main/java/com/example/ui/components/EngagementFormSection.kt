package com.example.ui.components

import android.content.Intent
import android.net.Uri
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.LocalAppThemeIsDark
import com.example.ui.theme.StudioBrandBlue
import com.example.ui.theme.StudioGreen
import com.example.viewmodel.StudioUiState
import java.net.URLEncoder
import java.nio.charset.StandardCharsets

@Composable
fun EngagementFormSection(
    uiState: StudioUiState,
    onDisciplineChange: (String) -> Unit,
    onNameChange: (String) -> Unit,
    onContactChange: (String) -> Unit,
    onMessageChange: (String) -> Unit,
    onAiEnhance: () -> Unit,
    onAcceptAiDraft: () -> Unit,
    onDismissAiDraft: () -> Unit,
    onSubmit: () -> Unit,
    onResetSubmission: () -> Unit
) {
    val context = LocalContext.current
    val colorScheme = MaterialTheme.colorScheme
    val isDark = LocalAppThemeIsDark.current
    var isDropdownExpanded by remember { mutableStateOf(false) }

    val disciplines = listOf(
        "Brand & Graphic Identity",
        "Academic Tutoring & Mentorship",
        "Writing & Poetry",
        "Practical Digital & Tech Consult",
        "General Inquiry / Bespoke Request"
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp)
            .testTag("engagement_form_section")
    ) {
        // Section Header
        Text(
            text = "ENGAGEMENT FORM",
            color = StudioBrandBlue,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.08.sp
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = "Start a Conversation",
            color = colorScheme.onSurface,
            fontSize = 26.sp,
            lineHeight = 32.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = "Zero friction, no automated funnels. Send a direct note with your objectives and receive a considered response within 24 hours.",
            color = colorScheme.secondary,
            fontSize = 13.5.sp,
            lineHeight = 20.sp,
            fontWeight = FontWeight.Normal,
            modifier = Modifier.padding(top = 8.dp, bottom = 20.dp)
        )

        // Form Container Card
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(colorScheme.surface)
                .border(
                    width = 1.dp,
                    color = if (uiState.highlightForm) StudioBrandBlue else colorScheme.outlineVariant,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 1. Discipline Selector
            Column {
                Text(
                    text = "DISCIPLINE OF INTEREST",
                    color = colorScheme.secondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.04.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorScheme.surfaceContainerLow)
                        .border(1.dp, colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                        .clickable { isDropdownExpanded = true }
                        .padding(horizontal = 14.dp, vertical = 12.dp)
                        .testTag("discipline_selector")
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = uiState.selectedDiscipline,
                            color = colorScheme.onSurface,
                            fontSize = 13.5.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        Icon(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = "Select discipline",
                            tint = StudioBrandBlue
                        )
                    }

                    DropdownMenu(
                        expanded = isDropdownExpanded,
                        onDismissRequest = { isDropdownExpanded = false },
                        modifier = Modifier
                            .background(colorScheme.surface)
                            .border(1.dp, colorScheme.outlineVariant)
                    ) {
                        disciplines.forEach { discipline ->
                            DropdownMenuItem(
                                text = {
                                    Text(
                                        text = discipline,
                                        color = if (discipline == uiState.selectedDiscipline) StudioBrandBlue else colorScheme.onSurface,
                                        fontSize = 13.sp,
                                        fontWeight = if (discipline == uiState.selectedDiscipline) FontWeight.Bold else FontWeight.Normal
                                    )
                                },
                                onClick = {
                                    onDisciplineChange(discipline)
                                    isDropdownExpanded = false
                                }
                            )
                        }
                    }
                }
            }

            // 2. Client Name Input
            Column {
                Text(
                    text = "YOUR NAME / ORGANIZATION",
                    color = colorScheme.secondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.04.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = uiState.clientName,
                    onValueChange = onNameChange,
                    placeholder = {
                        Text(
                            text = "e.g. S. Rahman",
                            color = colorScheme.secondary.copy(alpha = 0.5f),
                            fontSize = 13.5.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("client_name_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surfaceContainerLow,
                        unfocusedContainerColor = colorScheme.surfaceContainerLow,
                        focusedBorderColor = StudioBrandBlue,
                        unfocusedBorderColor = colorScheme.outlineVariant,
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            // 3. Client Contact Input
            Column {
                Text(
                    text = "EMAIL OR PHONE / WHATSAPP",
                    color = colorScheme.secondary,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.SemiBold,
                    letterSpacing = 0.04.sp
                )
                Spacer(modifier = Modifier.height(6.dp))
                OutlinedTextField(
                    value = uiState.clientContact,
                    onValueChange = onContactChange,
                    placeholder = {
                        Text(
                            text = "name@domain.com or +880...",
                            color = colorScheme.secondary.copy(alpha = 0.5f),
                            fontSize = 13.5.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("client_contact_input"),
                    singleLine = true,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surfaceContainerLow,
                        unfocusedContainerColor = colorScheme.surfaceContainerLow,
                        focusedBorderColor = StudioBrandBlue,
                        unfocusedBorderColor = colorScheme.outlineVariant,
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(8.dp)
                )
            }

            // 4. Initial Note / Context + AI Assistance
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "INITIAL NOTE / CONTEXT",
                        color = colorScheme.secondary,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        letterSpacing = 0.04.sp
                    )

                    // AI Assistance Trigger
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(StudioBrandBlue.copy(alpha = 0.12f))
                            .clickable { onAiEnhance() }
                            .padding(horizontal = 8.dp, vertical = 4.dp)
                            .testTag("ai_enhance_brief_button")
                    ) {
                        if (uiState.isAiEnhancing) {
                            CircularProgressIndicator(
                                modifier = Modifier.size(11.dp),
                                color = StudioBrandBlue,
                                strokeWidth = 1.5.dp
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Structuring...",
                                color = StudioBrandBlue,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.AutoAwesome,
                                contentDescription = "AI brief assistant",
                                tint = StudioBrandBlue,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Structure Brief",
                                color = StudioBrandBlue,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }
                Spacer(modifier = Modifier.height(6.dp))

                OutlinedTextField(
                    value = uiState.clientMessage,
                    onValueChange = onMessageChange,
                    placeholder = {
                        Text(
                            text = "Briefly outline your timeline, requirements, or learning goals...",
                            color = colorScheme.secondary.copy(alpha = 0.5f),
                            fontSize = 13.5.sp
                        )
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("client_message_input"),
                    minLines = 3,
                    maxLines = 6,
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedContainerColor = colorScheme.surfaceContainerLow,
                        unfocusedContainerColor = colorScheme.surfaceContainerLow,
                        focusedBorderColor = StudioBrandBlue,
                        unfocusedBorderColor = colorScheme.outlineVariant,
                        focusedTextColor = colorScheme.onSurface,
                        unfocusedTextColor = colorScheme.onSurface
                    ),
                    shape = RoundedCornerShape(8.dp)
                )

                // AI Suggested Brief Preview / Review Box
                AnimatedVisibility(
                    visible = uiState.aiSuggestedDraft != null,
                    enter = fadeIn(),
                    exit = fadeOut()
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 10.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(colorScheme.surfaceContainerLow)
                            .border(1.dp, StudioBrandBlue.copy(alpha = 0.4f), RoundedCornerShape(8.dp))
                            .padding(12.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "AI-STRUCTURED BRIEF SUGGESTION",
                                color = StudioBrandBlue,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Bold,
                                letterSpacing = 0.04.sp
                            )
                            Text(
                                text = "Non-binding draft",
                                color = colorScheme.secondary,
                                fontSize = 9.sp,
                                fontWeight = FontWeight.Normal
                            )
                        }
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = uiState.aiSuggestedDraft ?: "",
                            color = colorScheme.onSurface,
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            fontWeight = FontWeight.Normal
                        )
                        Spacer(modifier = Modifier.height(10.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.End,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Dismiss",
                                color = colorScheme.secondary,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.SemiBold,
                                modifier = Modifier
                                    .clickable { onDismissAiDraft() }
                                    .padding(horizontal = 8.dp, vertical = 4.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Box(
                                modifier = Modifier
                                    .clip(RoundedCornerShape(6.dp))
                                    .background(StudioBrandBlue)
                                    .clickable { onAcceptAiDraft() }
                                    .padding(horizontal = 10.dp, vertical = 5.dp)
                            ) {
                                Text(
                                    text = "Apply to Note",
                                    color = Color.White,
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            }

            // Transmit Request Button
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isDark) Color.White else Color(0xFF0F172A))
                    .clickable(enabled = !uiState.isSubmitting) { onSubmit() }
                    .padding(vertical = 14.dp)
                    .testTag("submit_inquiry_button")
            ) {
                val buttonTextColor = if (isDark) Color(0xFF0A0A0A) else Color.White

                if (uiState.isSubmitting) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(16.dp),
                            color = buttonTextColor,
                            strokeWidth = 2.dp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "TRANSMITTING...",
                            color = buttonTextColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.06.sp
                        )
                    }
                } else {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "TRANSMIT REQUEST",
                            color = buttonTextColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            letterSpacing = 0.06.sp
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                            contentDescription = null,
                            tint = buttonTextColor,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            // Feedback Message Banner
            AnimatedVisibility(
                visible = uiState.submissionFeedback != null,
                enter = fadeIn(),
                exit = fadeOut()
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(8.dp))
                        .background(colorScheme.surfaceContainerLow)
                        .border(1.dp, colorScheme.outlineVariant, RoundedCornerShape(8.dp))
                        .padding(14.dp)
                ) {
                    Row(verticalAlignment = Alignment.Top) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = StudioGreen,
                            modifier = Modifier
                                .size(18.dp)
                                .padding(top = 1.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Column {
                            Text(
                                text = uiState.submissionFeedback ?: "",
                                color = colorScheme.onSurface,
                                fontSize = 12.5.sp,
                                lineHeight = 18.sp,
                                fontWeight = FontWeight.Normal
                            )

                            // If submission was successful, offer one-tap launch to Mail or WhatsApp
                            if (uiState.submissionSuccess != null) {
                                val item = uiState.submissionSuccess
                                val encodedBody = URLEncoder.encode(
                                    "Hello Taher,\n\nI am reaching out regarding: ${item.discipline}.\n\nFrom: ${item.clientName} (${item.clientContact})\n\nContext:\n${item.initialContext}",
                                    StandardCharsets.UTF_8.toString()
                                )

                                Spacer(modifier = Modifier.height(10.dp))
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    // Open Pre-filled Email
                                    Row(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(StudioBrandBlue.copy(alpha = 0.15f))
                                            .clickable {
                                                val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                                                    data = Uri.parse("mailto:taheruddintamim20@gmail.com?subject=${URLEncoder.encode("Studio Inquiry: ${item.discipline}", "UTF-8")}&body=$encodedBody")
                                                }
                                                context.startActivity(emailIntent)
                                            }
                                            .padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Email,
                                            contentDescription = "Email",
                                            tint = StudioBrandBlue,
                                            modifier = Modifier.size(13.dp)
                                        )
                                        Spacer(modifier = Modifier.width(5.dp))
                                        Text(
                                            text = "Email Directly",
                                            color = StudioBrandBlue,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    // Open Pre-filled WhatsApp
                                    Row(
                                        modifier = Modifier
                                            .clip(RoundedCornerShape(6.dp))
                                            .background(StudioBrandBlue.copy(alpha = 0.15f))
                                            .clickable {
                                                val waIntent = Intent(Intent.ACTION_VIEW).apply {
                                                    data = Uri.parse("https://wa.me/8801955388687?text=$encodedBody")
                                                }
                                                context.startActivity(waIntent)
                                            }
                                            .padding(horizontal = 10.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(
                                            text = "WhatsApp",
                                            color = StudioBrandBlue,
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold
                                        )
                                    }

                                    // Clear / Reset
                                    Text(
                                        text = "Dismiss",
                                        color = colorScheme.secondary,
                                        fontSize = 11.sp,
                                        fontWeight = FontWeight.SemiBold,
                                        modifier = Modifier
                                            .clickable { onResetSubmission() }
                                            .padding(horizontal = 8.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
