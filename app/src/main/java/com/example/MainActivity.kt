package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.CubicBezierEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.ui.components.ColophonFooter
import com.example.ui.components.DirectChannelsSection
import com.example.ui.components.DisciplinesSection
import com.example.ui.components.DockSegment
import com.example.ui.components.EngagementFormSection
import com.example.ui.components.FaqSection
import com.example.ui.components.HeaderBar
import com.example.ui.components.HeroSection
import com.example.ui.components.InquiryHistoryDialog
import com.example.ui.components.IosGlassCommandDeck
import com.example.ui.components.ProtocolSection
import com.example.ui.components.ServiceDetailSheet
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.StudioBrandBlue
import com.example.viewmodel.StudioViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

class MainActivity : ComponentActivity() {
    private val viewModel: StudioViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            var isDarkTheme by rememberSaveable { mutableStateOf(true) }

            MyApplicationTheme(darkTheme = isDarkTheme) {
                StudioAppScreen(
                    viewModel = viewModel,
                    isDarkTheme = isDarkTheme,
                    onToggleTheme = { isDarkTheme = !isDarkTheme }
                )
            }
        }
    }
}

@Composable
fun StudioAppScreen(
    viewModel: StudioViewModel,
    isDarkTheme: Boolean,
    onToggleTheme: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val inquiries by viewModel.inquiries.collectAsStateWithLifecycle()
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val colorScheme = MaterialTheme.colorScheme

    // Floating Dock Navigation State
    var selectedSegment by remember { mutableStateOf(DockSegment.STUDIO) }
    var isElsewhereOpen by remember { mutableStateOf(false) }

    // Sync dock selection with scroll position
    val calculatedSegment by remember {
        derivedStateOf {
            if (scrollState.value > 950) {
                DockSegment.DIRECT_REACH
            } else {
                DockSegment.STUDIO
            }
        }
    }

    // Keep selected segment updated unless elsewhere is active
    LaunchedEffect(calculatedSegment) {
        if (!isElsewhereOpen && selectedSegment != DockSegment.ELSEWHERE) {
            selectedSegment = calculatedSegment
        }
    }

    // Logo Entrance / Launch Animation (Runs strictly once on first app mount)
    var splashFinished by rememberSaveable { mutableStateOf(false) }
    val splashProgress = remember { Animatable(if (splashFinished) 1f else 0f) }

    LaunchedEffect(Unit) {
        if (!splashFinished) {
            // Display centered for 600ms with ambient glow pulse
            delay(600)
            // Interpolate into permanent anchor in top-left header with cubic-bezier(0.16, 1, 0.3, 1)
            splashProgress.animateTo(
                targetValue = 1f,
                animationSpec = tween(
                    durationMillis = 850,
                    easing = CubicBezierEasing(0.16f, 1f, 0.3f, 1f)
                )
            )
            splashFinished = true
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(colorScheme.background)
            .statusBarsPadding()
            .navigationBarsPadding(),
        containerColor = colorScheme.background
    ) { innerPadding ->
        BoxWithConstraints(
            modifier = Modifier
                .fillMaxSize()
                .background(colorScheme.background),
            contentAlignment = Alignment.TopCenter
        ) {
            val totalWidthPx = constraints.maxWidth.toFloat()
            val totalHeightPx = constraints.maxHeight.toFloat()

            // Target header logo position (approx 16.dp horizontal, 12.dp vertical)
            val headerAnchorXPx = 34f
            val headerAnchorYPx = 22f

            val currentLogoX = (totalWidthPx / 2f - 40f) * (1f - splashProgress.value) + headerAnchorXPx * splashProgress.value
            val currentLogoY = (totalHeightPx / 2f - 40f) * (1f - splashProgress.value) + headerAnchorYPx * splashProgress.value
            val currentLogoScale = 1.3f * (1f - splashProgress.value) + 1.0f * splashProgress.value

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = null
                    ) {
                        if (isElsewhereOpen) isElsewhereOpen = false
                    },
                contentAlignment = Alignment.TopCenter
            ) {
                // Editorial BroadSheet Frame (max 460dp width)
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .widthIn(max = 460.dp)
                        .background(colorScheme.background)
                        .border(
                            width = 1.dp,
                            color = colorScheme.outlineVariant.copy(alpha = 0.3f)
                        )
                ) {
                    // Sticky Header with Monogram & Symmetrical Controls
                    HeaderBar(
                        inquiryCount = inquiries.size,
                        onOpenInquiries = { viewModel.toggleHistoryDialog(true) },
                        onToggleTheme = onToggleTheme,
                        showMonogramInHeader = splashProgress.value >= 0.99f
                    )

                    // Scrollable Monograph Flow
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .verticalScroll(scrollState)
                            .testTag("main_scrollable_container")
                    ) {
                        // 1. Hero / Core Statement
                        HeroSection(
                            onExploreDisciplines = {
                                coroutineScope.launch {
                                    scrollState.animateScrollTo(290)
                                }
                            },
                            onStartProject = {
                                coroutineScope.launch {
                                    scrollState.animateScrollTo(1150)
                                }
                            }
                        )

                        Spacer(modifier = Modifier.height(16.dp))

                        // 2. The Four Core Disciplines
                        DisciplinesSection(
                            onSelectServiceForInquiry = { serviceName ->
                                viewModel.selectDiscipline(serviceName)
                                coroutineScope.launch {
                                    scrollState.animateScrollTo(1150)
                                }
                            },
                            onOpenServiceDetail = { service ->
                                viewModel.openServiceDetail(service)
                            }
                        )

                        Spacer(modifier = Modifier.height(36.dp))

                        // 3. Direct Engagement Form
                        EngagementFormSection(
                            uiState = uiState,
                            onDisciplineChange = { viewModel.selectDiscipline(it) },
                            onNameChange = { viewModel.updateClientName(it) },
                            onContactChange = { viewModel.updateClientContact(it) },
                            onMessageChange = { viewModel.updateClientMessage(it) },
                            onAiEnhance = { viewModel.enhanceBriefWithAi() },
                            onAcceptAiDraft = { viewModel.acceptAiDraft() },
                            onDismissAiDraft = { viewModel.dismissAiDraft() },
                            onSubmit = { viewModel.submitInquiry() },
                            onResetSubmission = { viewModel.resetSubmissionStatus() }
                        )

                        Spacer(modifier = Modifier.height(32.dp))

                        // 4. Direct Quick Channels (Email & WhatsApp)
                        DirectChannelsSection()

                        Spacer(modifier = Modifier.height(32.dp))

                        // 5. Practice Protocol
                        ProtocolSection()

                        Spacer(modifier = Modifier.height(32.dp))

                        // 6. Frequently Clarified (FAQ)
                        FaqSection()

                        Spacer(modifier = Modifier.height(16.dp))

                        // 7. Colophon / Footer
                        ColophonFooter()

                        // Generous bottom spacer so floating dock never obscures footer or content
                        Spacer(modifier = Modifier.height(96.dp))
                    }
                }

                // 8. Fixed Bottom "iOS 27" Segmented Glass Command Deck
                Box(
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 12.dp)
                ) {
                    IosGlassCommandDeck(
                        selectedSegment = if (isElsewhereOpen) DockSegment.ELSEWHERE else selectedSegment,
                        onSegmentSelected = { segment ->
                            selectedSegment = segment
                            isElsewhereOpen = false
                            coroutineScope.launch {
                                when (segment) {
                                    DockSegment.STUDIO -> scrollState.animateScrollTo(0)
                                    DockSegment.DIRECT_REACH -> scrollState.animateScrollTo(1150)
                                    DockSegment.ELSEWHERE -> {}
                                }
                            }
                        },
                        isElsewhereOpen = isElsewhereOpen,
                        onToggleElsewhere = {
                            isElsewhereOpen = !isElsewhereOpen
                        },
                        onDismissElsewhere = {
                            isElsewhereOpen = false
                        }
                    )
                }

                // 9. Floating Splash Logo Animation Layer (Active only on first mount)
                if (splashProgress.value < 0.99f) {
                    // Soft electric-blue ambient glow pulse
                    Box(
                        modifier = Modifier
                            .offset { IntOffset(currentLogoX.roundToInt(), currentLogoY.roundToInt()) }
                            .size(34.dp)
                            .graphicsLayer {
                                scaleX = currentLogoScale
                                scaleY = currentLogoScale
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        // Ambient glow ring
                        Box(
                            modifier = Modifier
                                .size(48.dp)
                                .shadow(
                                    elevation = 20.dp,
                                    shape = CircleShape,
                                    spotColor = StudioBrandBlue.copy(alpha = 0.5f)
                                )
                                .background(StudioBrandBlue.copy(alpha = 0.12f * (1f - splashProgress.value)), CircleShape)
                        )

                        Image(
                            painter = painterResource(id = R.drawable.logo),
                            contentDescription = "Taher Studio Monogram Initial Mount",
                            modifier = Modifier.size(34.dp)
                        )
                    }
                }
            }
        }
    }

    // Service Detail Modal Sheet
    uiState.activeServiceDetail?.let { activeService ->
        ServiceDetailSheet(
            service = activeService,
            onDismiss = { viewModel.closeServiceDetail() },
            onSelectAndInquire = { serviceName ->
                viewModel.selectDiscipline(serviceName)
                coroutineScope.launch {
                    scrollState.animateScrollTo(1150)
                }
            }
        )
    }

    // Inquiry History Modal Dialog
    if (uiState.isHistoryDialogOpen) {
        InquiryHistoryDialog(
            inquiries = inquiries,
            onDismiss = { viewModel.toggleHistoryDialog(false) },
            onDeleteInquiry = { id -> viewModel.deleteInquiry(id) }
        )
    }
}
