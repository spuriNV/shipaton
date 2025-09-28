package com.performativemalecoach.screens

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.performativemalecoach.ui.components.CoachCard
import com.performativemalecoach.ui.components.CoachButton
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CaptionGeneratorScreen() {
    var selectedPlatform by remember { mutableStateOf("Instagram") }
    var selectedMood by remember { mutableStateOf("Thoughtful") }
    var customContext by remember { mutableStateOf("") }
    var generatedCaption by remember { mutableStateOf<String?>(null) }
    var isGenerating by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            HeaderCard()
        }

        item {
            PlatformSelectionCard(
                selectedPlatform = selectedPlatform,
                onPlatformSelected = { selectedPlatform = it }
            )
        }

        item {
            MoodSelectionCard(
                selectedMood = selectedMood,
                onMoodSelected = { selectedMood = it }
            )
        }

        item {
            ContextInputCard(
                context = customContext,
                onContextChanged = { customContext = it }
            )
        }

        item {
            GenerateButtonCard(
                isGenerating = isGenerating,
                onGenerate = {
                    isGenerating = true
                    // Simulate generation
                    coroutineScope.launch {
                        delay(2000)
                        generatedCaption = generateCaption(selectedPlatform, selectedMood, customContext)
                        isGenerating = false
                    }
                }
            )
        }

        generatedCaption?.let { caption ->
            item {
                AnimatedVisibility(
                    visible = !isGenerating,
                    enter = slideInVertically() + fadeIn()
                ) {
                    GeneratedCaptionCard(
                        caption = caption,
                        onRegenerate = {
                            generatedCaption = generateCaption(selectedPlatform, selectedMood, customContext)
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun HeaderCard() {
    CoachCard {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Icon(
                imageVector = Icons.Default.Create,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Caption Generator",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Craft the perfect performatively authentic captions",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun PlatformSelectionCard(
    selectedPlatform: String,
    onPlatformSelected: (String) -> Unit
) {
    CoachCard {
        Text(
            text = "Select Platform",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))

        LazyRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(platforms) { platform ->
                PlatformChip(
                    platform = platform,
                    isSelected = selectedPlatform == platform.name,
                    onSelected = { onPlatformSelected(platform.name) }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun PlatformChip(
    platform: Platform,
    isSelected: Boolean,
    onSelected: () -> Unit
) {
    FilterChip(
        onClick = onSelected,
        label = { Text(platform.name) },
        selected = isSelected,
        leadingIcon = {
            Icon(
                imageVector = platform.icon,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
        }
    )
}

@Composable
private fun MoodSelectionCard(
    selectedMood: String,
    onMoodSelected: (String) -> Unit
) {
    CoachCard {
        Text(
            text = "Select Mood",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Column(
            modifier = Modifier.selectableGroup()
        ) {
            moods.forEach { mood ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .selectable(
                            selected = (selectedMood == mood.name),
                            onClick = { onMoodSelected(mood.name) },
                            role = Role.RadioButton
                        )
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = (selectedMood == mood.name),
                        onClick = null
                    )
                    Column(modifier = Modifier.padding(start = 12.dp)) {
                        Text(
                            text = mood.name,
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.Medium
                        )
                        Text(
                            text = mood.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ContextInputCard(
    context: String,
    onContextChanged: (String) -> Unit
) {
    CoachCard {
        Text(
            text = "Additional Context (Optional)",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(12.dp))

        OutlinedTextField(
            value = context,
            onValueChange = onContextChanged,
            modifier = Modifier.fillMaxWidth(),
            placeholder = { Text("e.g., coffee shop, new book, weekend vibes...") },
            maxLines = 3,
            shape = RoundedCornerShape(12.dp)
        )
    }
}

@Composable
private fun GenerateButtonCard(
    isGenerating: Boolean,
    onGenerate: () -> Unit
) {
    CoachCard {
        CoachButton(
            text = if (isGenerating) "Generating..." else "Generate Caption",
            icon = if (isGenerating) null else Icons.Default.AutoFixHigh,
            onClick = onGenerate,
            enabled = !isGenerating
        )

        if (isGenerating) {
            Spacer(modifier = Modifier.height(16.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.size(24.dp),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        }
    }
}

@Composable
private fun GeneratedCaptionCard(
    caption: String,
    onRegenerate: () -> Unit
) {
    CoachCard {
        Text(
            text = "Generated Caption ✨",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = caption,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyLarge,
                lineHeight = MaterialTheme.typography.bodyLarge.lineHeight
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            OutlinedButton(
                onClick = onRegenerate,
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Regenerate")
            }

            Button(
                onClick = { /* Copy to clipboard */ },
                modifier = Modifier.weight(1f),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Share,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text("Share")
            }
        }
    }
}

data class Platform(val name: String, val icon: androidx.compose.ui.graphics.vector.ImageVector)
data class Mood(val name: String, val description: String)

private val platforms = listOf(
    Platform("Instagram", Icons.Default.PhotoCamera),
    Platform("LinkedIn", Icons.Default.Work),
    Platform("Twitter", Icons.Default.Message),
    Platform("TikTok", Icons.Default.Movie)
)

private val moods = listOf(
    Mood("Thoughtful", "Introspective and contemplative"),
    Mood("Minimalist", "Clean, simple, and intentional"),
    Mood("Entrepreneurial", "Ambitious and success-focused"),
    Mood("Aesthetic", "Visually curated and artistic"),
    Mood("Philosophical", "Deep thinking and wisdom")
)

private fun generateCaption(platform: String, mood: String, context: String): String {
    val baseCaption = when (mood) {
        "Thoughtful" -> "Sometimes the most profound insights come from the quietest moments. There's something beautiful about embracing simplicity in a complex world."

        "Minimalist" -> "Less is more. Curating intentionally. Every choice reflects values, and every value shapes the aesthetic we present to the world."

        "Entrepreneurial" -> "Building something meaningful takes patience, vision, and the courage to trust the process. Success isn't just about outcomes—it's about becoming."

        "Aesthetic" -> "Finding beauty in the details. There's art in everyday moments when you train your eye to see them. Composition matters, in photography and in life."

        "Philosophical" -> "Been thinking about authenticity lately. What does it mean to be genuine when everything is performative? Maybe the performance is part of the truth."

        else -> "Every moment is an opportunity to create something beautiful and intentional."
    }

    val platformSuffix = when (platform) {
        "Instagram" -> "\n\n#mindful #aesthetic #growth #intentionalliving"
        "LinkedIn" -> "\n\nThoughts on mindful living and intentional choices in professional and personal spaces."
        "Twitter" -> "\n\nJust thinking out loud. 🤔"
        "TikTok" -> "\n\n✨ Philosophy meets aesthetics ✨"
        else -> ""
    }

    val contextAddition = if (context.isNotBlank()) {
        "\n\nCapturing this while $context – these moments of clarity hit differently."
    } else ""

    return baseCaption + contextAddition + platformSuffix
}