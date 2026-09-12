package com.example.model

data class StudioService(
    val id: String,
    val numberTag: String,
    val toolsTag: String,
    val title: String,
    val shortDescription: String,
    val folioLabel: String? = null,
    val folioUrl: String? = null,
    val inquiryActionLabel: String,
    val detailedOverview: String,
    val deliverables: List<String>,
    val methodology: String,
    val turnaround: String
)

data class FaqItem(
    val question: String,
    val answer: String
)

data class ExternalLinkItem(
    val name: String,
    val url: String,
    val tag: String
)

object StudioData {
    val services = listOf(
        StudioService(
            id = "brand_identity",
            numberTag = "01 // IDENTITY",
            toolsTag = "ADOBE AI / PS",
            title = "Brand & Graphic Identity",
            shortDescription = "Clear breakdown of bespoke logo marks, rigorous visual systems, and high-precision vector artwork built with Adobe Illustrator & Photoshop. Grounded in mathematical balance and lasting typographic hierarchy.",
            folioLabel = "See Selected Folio on Behance",
            folioUrl = "https://www.behance.net/tahertamim",
            inquiryActionLabel = "Inquire about Brand Design",
            detailedOverview = "A focused visual identity practice turning core company ethos into bespoke visual marks, geometric emblems, and consistent vector design systems. Every project is crafted strictly in vector format for infinite scalability across digital and physical substrates.",
            deliverables = listOf(
                "Primary & Secondary Vector Logo Marks",
                "Scalable Monograms & Favicons",
                "Color System & Accessibility Hierarchy",
                "Typographic Pairing & Hierarchy Sheet",
                "Master Production Assets (.AI, .SVG, .PNG, .PDF)"
            ),
            methodology = "Initial concept interrogation → mathematical vector grid mapping → contrast & reduction testing → final master package delivery.",
            turnaround = "5–10 business days per milestone"
        ),
        StudioService(
            id = "academic_tutoring",
            numberTag = "02 // ACADEMICS",
            toolsTag = "SCIENCE • MATH • EN",
            title = "Academic Tutoring & Mentorship",
            shortDescription = "Focused science, mathematics, and English tutoring drawn from real academic excellence (GPA 5.00 science background). Personalized analytical frameworks designed to demystify complex syllabi and instill lasting confidence.",
            inquiryActionLabel = "Inquire about Mentoring",
            detailedOverview = "Individualized mentorship grounded in rigorous scientific discipline. Drawing directly from real top-tier academic performance (GPA 5.00), sessions systematically break down difficult concepts into logical first-principles models.",
            deliverables = listOf(
                "Curriculum Breakdown & Concept Deconstruction",
                "Targeted Problem-Solving Worksheets",
                "Exam Strategy & Time Allocation Frameworks",
                "1-on-1 Interactive Review Sessions"
            ),
            methodology = "Diagnostic assessment → conceptual first-principles breakdown → guided practice → independent verification.",
            turnaround = "Scheduled weekly or intensive modular blocks"
        ),
        StudioService(
            id = "writing_poetry",
            numberTag = "03 // LITERATURE",
            toolsTag = "PROSE • VERSE",
            title = "Writing & Poetry",
            shortDescription = "Bespoke writing, poetic verse, and creative expression. Sculpting evocative prose and thoughtful cadences for publications, dedicated personal commissions, and contemplative art projects.",
            inquiryActionLabel = "Inquire about Writing",
            detailedOverview = "Articulate, evocative creative writing and verse commissions. Balancing poetic nuance with rhythmic clarity for literary journals, special dedications, bespoke book chapters, or visual art collaborations.",
            deliverables = listOf(
                "Original Poetic Verse (Stanzas / Free Verse / Rhymed)",
                "Evocative Short Prose & Reflective Essays",
                "Artisan Editorial Inscriptions & Statements",
                "Text Typography & Layout Typesetting Guidance"
            ),
            methodology = "Thematic dialogue → draft composition & rhythmic polishing → collaborative reading review → final formatted transcript.",
            turnaround = "3–7 business days"
        ),
        StudioService(
            id = "tech_consult",
            numberTag = "04 // SYSTEMS",
            toolsTag = "AI • WORKFLOW • TOOLS",
            title = "Practical Digital & Tech Consult",
            shortDescription = "Productivity optimization, AI workflow guidance, and computer software assistance. Hands-on configuration of modern toolchains to eliminate operational friction and elevate individual creative leverage.",
            inquiryActionLabel = "Inquire about Tech Support",
            detailedOverview = "Practical technological guidance tailored for creators, students, and independent operators. Focused on implementing effective AI workflows, software setup, hardware optimization, and distraction-free digital productivity environments.",
            deliverables = listOf(
                "AI Prompt Engineering & Pipeline Guidance",
                "Productivity & Workspace Architecture Audit",
                "Creative Software Toolchain Setup (Adobe, OS utilities)",
                "Direct Troubleshooting & Optimization Notes"
            ),
            methodology = "Workflow friction audit → step-by-step toolchain configuration → testing → actionable takeaway protocol.",
            turnaround = "1–3 business days per advisory block"
        )
    )

    val faqs = listOf(
        FaqItem(
            question = "What services do you offer?",
            answer = "Taher offers four focused disciplines: Brand & Graphic Identity (logos, typography, vectors), Academic Tutoring & Mentorship (science, math, English), Writing & Poetry (bespoke prose and verse), and Practical Digital & Tech Advisory (AI toolchains, productivity, workflow)."
        ),
        FaqItem(
            question = "How do I request a service?",
            answer = "Select the discipline of interest in the Engagement Form below, describe your goals or context, and send your request. Taher reviews every submission personally and responds within 24 hours via email or WhatsApp."
        ),
        FaqItem(
            question = "Can I request a custom or blended service?",
            answer = "Yes. Select 'General Inquiry / Bespoke Request' in the form and detail your project context. Taher will clarify if it aligns with his studio capabilities and propose a tailored engagement scope."
        ),
        FaqItem(
            question = "How does pricing and quotation work?",
            answer = "Every project operates with direct, transparent terms. After understanding your requirements, a clear written milestone and compensation quote is provided prior to any work taking place."
        ),
        FaqItem(
            question = "Can we work together remotely?",
            answer = "Yes. The studio operates from Bangladesh and collaborates with clients worldwide across asynchronous channels, shared vector repositories, and direct messaging."
        )
    )

    val externalLinks = listOf(
        ExternalLinkItem("Behance", "https://www.behance.net/tahertamim", "FOLIO"),
        ExternalLinkItem("LinkedIn", "https://www.linkedin.com/in/taher-tamim/", "NETWORK"),
        ExternalLinkItem("WhatsApp", "https://wa.me/8801955388687", "DIRECT CHAT"),
        ExternalLinkItem("Email", "mailto:taheruddintamim20@gmail.com", "DIRECT MAIL"),
        ExternalLinkItem("Facebook", "https://www.facebook.com/TaherUddinTamim", "COMMUNITY"),
        ExternalLinkItem("Instagram", "https://www.instagram.com/ami.e.taher/", "SOCIAL")
    )
}
