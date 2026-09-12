package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.InquiryEntity
import com.example.data.StudioDatabase
import com.example.data.StudioRepository
import com.example.model.StudioData
import com.example.model.StudioService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

data class StudioUiState(
    val selectedDiscipline: String = "Brand & Graphic Identity",
    val clientName: String = "",
    val clientContact: String = "",
    val clientMessage: String = "",
    val isSubmitting: Boolean = false,
    val submissionSuccess: InquiryEntity? = null,
    val submissionFeedback: String? = null,
    val activeServiceDetail: StudioService? = null,
    val isHistoryDialogOpen: Boolean = false,
    val isAiEnhancing: Boolean = false,
    val aiSuggestedDraft: String? = null,
    val highlightForm: Boolean = false
)

class StudioViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: StudioRepository

    private val _uiState = MutableStateFlow(StudioUiState())
    val uiState: StateFlow<StudioUiState> = _uiState.asStateFlow()

    val inquiries: StateFlow<List<InquiryEntity>>

    init {
        val db = StudioDatabase.getDatabase(application)
        repository = StudioRepository(db.inquiryDao())
        inquiries = repository.inquiries.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )
    }

    fun selectDiscipline(serviceName: String) {
        _uiState.value = _uiState.value.copy(
            selectedDiscipline = serviceName,
            highlightForm = true
        )
        viewModelScope.launch {
            delay(1200)
            _uiState.value = _uiState.value.copy(highlightForm = false)
        }
    }

    fun openServiceDetail(service: StudioService) {
        _uiState.value = _uiState.value.copy(activeServiceDetail = service)
    }

    fun closeServiceDetail() {
        _uiState.value = _uiState.value.copy(activeServiceDetail = null)
    }

    fun updateClientName(name: String) {
        _uiState.value = _uiState.value.copy(clientName = name)
    }

    fun updateClientContact(contact: String) {
        _uiState.value = _uiState.value.copy(clientContact = contact)
    }

    fun updateClientMessage(message: String) {
        _uiState.value = _uiState.value.copy(clientMessage = message)
    }

    fun toggleHistoryDialog(open: Boolean) {
        _uiState.value = _uiState.value.copy(isHistoryDialogOpen = open)
    }

    fun deleteInquiry(id: Long) {
        viewModelScope.launch {
            repository.removeInquiry(id)
        }
    }

    fun enhanceBriefWithAi() {
        val currentMessage = _uiState.value.clientMessage.trim()
        val discipline = _uiState.value.selectedDiscipline

        _uiState.value = _uiState.value.copy(isAiEnhancing = true)

        viewModelScope.launch {
            delay(800) // Realistic thoughtful processing
            val structuredDraft = if (currentMessage.isNotEmpty()) {
                buildString {
                    appendLine("Objective:")
                    appendLine("• $currentMessage")
                    appendLine()
                    appendLine("Discipline Focus:")
                    appendLine("• $discipline")
                    appendLine()
                    appendLine("Desired Outcome:")
                    when {
                        discipline.contains("Brand") -> {
                            appendLine("• High-precision vector logo mark, typographic pairing, and production asset kit.")
                        }
                        discipline.contains("Tutoring") -> {
                            appendLine("• Focused 1-on-1 conceptual breakdown with structured exercise sets.")
                        }
                        discipline.contains("Writing") -> {
                            appendLine("• Evocative bespoke draft with refined cadence and thematic clarity.")
                        }
                        discipline.contains("Tech") -> {
                            appendLine("• Optimized AI workflow pipeline and reduced operational friction.")
                        }
                        else -> {
                            appendLine("• Tailored bespoke milestone review with direct personal communication.")
                        }
                    }
                    appendLine()
                    append("Timeline / Budget Notes: Standard direct consultation cycle.")
                }
            } else {
                buildString {
                    appendLine("Objective:")
                    appendLine("• Requesting consultation for $discipline.")
                    appendLine()
                    appendLine("Desired Deliverables:")
                    when {
                        discipline.contains("Brand") -> appendLine("• Vector identity, typography guidelines, and scalable marks.")
                        discipline.contains("Tutoring") -> appendLine("• Analytical curriculum walkthrough and exam strategy.")
                        discipline.contains("Writing") -> appendLine("• Original creative prose/verse commission.")
                        else -> appendLine("• Software optimization and workflow architecture guidance.")
                    }
                    append("Availability: Ready for direct review within 24h.")
                }
            }

            _uiState.value = _uiState.value.copy(
                isAiEnhancing = false,
                aiSuggestedDraft = structuredDraft
            )
        }
    }

    fun acceptAiDraft() {
        val draft = _uiState.value.aiSuggestedDraft ?: return
        _uiState.value = _uiState.value.copy(
            clientMessage = draft,
            aiSuggestedDraft = null
        )
    }

    fun dismissAiDraft() {
        _uiState.value = _uiState.value.copy(aiSuggestedDraft = null)
    }

    fun submitInquiry() {
        val state = _uiState.value
        val name = state.clientName.trim().ifEmpty { "Client" }
        val contact = state.clientContact.trim()
        val message = state.clientMessage.trim().ifEmpty { "General project engagement inquiry." }
        val discipline = state.selectedDiscipline

        if (contact.isEmpty()) {
            _uiState.value = _uiState.value.copy(
                submissionFeedback = "Please provide an email or phone/WhatsApp for direct reply."
            )
            return
        }

        _uiState.value = _uiState.value.copy(
            isSubmitting = true,
            submissionFeedback = "Transmitting inquiry directly to Taher Uddin Tamim..."
        )

        viewModelScope.launch {
            val id = repository.submitInquiry(
                discipline = discipline,
                clientName = name,
                clientContact = contact,
                initialContext = message
            )

            val created = InquiryEntity(
                id = id,
                discipline = discipline,
                clientName = name,
                clientContact = contact,
                initialContext = message
            )

            delay(600)

            _uiState.value = _uiState.value.copy(
                isSubmitting = false,
                submissionSuccess = created,
                submissionFeedback = "Inquiry logged for $name regarding \"$discipline\". Direct response via email or WhatsApp within 24 hours.",
                clientMessage = "",
                aiSuggestedDraft = null
            )
        }
    }

    fun resetSubmissionStatus() {
        _uiState.value = _uiState.value.copy(
            submissionSuccess = null,
            submissionFeedback = null
        )
    }
}
