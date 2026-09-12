package com.example.ui.theme

import androidx.compose.ui.graphics.Color

// ==========================================
// 1. Core Brand & Monogram Electric Accents
// ==========================================
val StudioBrandBlue = Color(0xFF0084FF)
val StudioBrandBlueLight = Color(0xFF00A3FF)
val StudioBrandBlueDark = Color(0xFF0066FF)
val StudioWarmAmber = Color(0xFFE58543)
val StudioGreen = Color(0xFF34D399)

// ==========================================
// 2. Dark Theme Tokens (Obsidian Monograph)
// ==========================================
val DarkCanvas = Color(0xFF0A0A0A)
val DarkSurface = Color(0xFF111111)
val DarkSurfaceHigh = Color(0xFF161616)
val DarkSurfaceHighest = Color(0xFF222225)
val DarkSurfaceContainerLow = Color(0xFF141416)
val DarkSurfaceContainerLowest = Color(0xFF0A0A0A)
val DarkTypographyPrimary = Color(0xFFFFFFFF)
val DarkTypographySecondary = Color(0xFF8E8E93)
val DarkTypographyMuted = Color(0xFF636366)
val DarkBorder = Color(0x12FFFFFF) // 0.07 opacity
val DarkBorderHighlight = Color(0x24FFFFFF)
val DarkPrimary = Color(0xFF0084FF)
val DarkPrimaryContainer = Color(0xFF0066FF)

// ==========================================
// 3. Light Theme Tokens (High-Contrast Tech Editorial)
// ==========================================
val LightCanvas = Color(0xFFF8FAFC)
val LightSurface = Color(0xFFFFFFFF)
val LightSurfaceHigh = Color(0xFFF1F5F9)
val LightSurfaceHighest = Color(0xFFE2E8F0)
val LightSurfaceContainerLow = Color(0xFFF8FAFC)
val LightSurfaceContainerLowest = Color(0xFFF1F5F9)
val LightTypographyPrimary = Color(0xFF0F172A)
val LightTypographySecondary = Color(0xFF475569)
val LightTypographyMuted = Color(0xFF94A3B8)
val LightBorder = Color(0x12000000) // 0.07 opacity
val LightBorderHighlight = Color(0x22000000)
val LightPrimary = Color(0xFF0066FF)
val LightPrimaryContainer = Color(0xFF0084FF)

// Legacy alias compatibility for existing components
val StudioSurface = DarkSurface
val StudioSurfaceContainerLowest = DarkCanvas
val StudioSurfaceContainerLow = DarkSurfaceContainerLow
val StudioSurfaceContainerHigh = DarkSurfaceHigh
val StudioSurfaceContainerHighest = DarkSurfaceHighest
val StudioOnSurface = DarkTypographyPrimary
val StudioOnSurfaceVariant = DarkTypographySecondary
val StudioSecondary = DarkTypographySecondary
val StudioOutline = DarkBorderHighlight
val StudioOutlineVariant = DarkBorder
val StudioPrimary = StudioBrandBlueLight
val StudioPrimaryContainer = StudioBrandBlue
