package com.performativemalecoach.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.performativemalecoach.ui.components.CoachCard

@Composable
fun DailyChallengesScreen() {
    var challenges by remember { mutableStateOf(getDailyChallenges()) }

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
            ProgressOverviewCard(challenges)
        }

        items(challenges) { challenge ->
            ChallengeCard(
                challenge = challenge,
                onToggleComplete = { challengeId ->
                    challenges = challenges.map {
                        if (it.id == challengeId) it.copy(isCompleted = !it.isCompleted)
                        else it
                    }
                }
            )
        }

        item {
            Spacer(modifier = Modifier.height(80.dp))
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
                imageVector = Icons.Default.EmojiEvents,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Daily Challenges",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Optimize your performance one task at a time",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun ProgressOverviewCard(challenges: List<Challenge>) {
    val completedCount = challenges.count { it.isCompleted }
    val totalCount = challenges.size
    val totalPoints = challenges.sumOf { if (it.isCompleted) it.points else 0 }

    CoachCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    text = "Today's Progress",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "$completedCount of $totalCount completed",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.primary,
                    fontWeight = FontWeight.Medium
                )
            }

            Column(horizontalAlignment = Alignment.End) {
                Text(
                    text = "$totalPoints",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "points earned",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LinearProgressIndicator(
            progress = completedCount.toFloat() / totalCount,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
    }
}

@Composable
private fun ChallengeCard(
    challenge: Challenge,
    onToggleComplete: (String) -> Unit
) {
    CoachCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.Top
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
                    .background(
                        if (challenge.isCompleted)
                            MaterialTheme.colorScheme.primary
                        else
                            MaterialTheme.colorScheme.surfaceVariant
                    ),
                contentAlignment = Alignment.Center
            ) {
                if (challenge.isCompleted) {
                    Icon(
                        imageVector = Icons.Default.Check,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.size(16.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.width(16.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = challenge.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold,
                        textDecoration = if (challenge.isCompleted) TextDecoration.LineThrough else null,
                        color = if (challenge.isCompleted)
                            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                        else
                            MaterialTheme.colorScheme.onSurface
                    )

                    DifficultyBadge(challenge.difficulty)
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = challenge.description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                    textDecoration = if (challenge.isCompleted) TextDecoration.LineThrough else null
                )

                Spacer(modifier = Modifier.height(12.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.Star,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "${challenge.points} points",
                            style = MaterialTheme.typography.bodySmall,
                            fontWeight = FontWeight.Medium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }

                    TextButton(
                        onClick = { onToggleComplete(challenge.id) }
                    ) {
                        Text(
                            text = if (challenge.isCompleted) "Mark Incomplete" else "Mark Complete",
                            style = MaterialTheme.typography.labelMedium
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun DifficultyBadge(difficulty: String) {
    val (backgroundColor, textColor) = when (difficulty) {
        "Easy" -> MaterialTheme.colorScheme.primaryContainer to MaterialTheme.colorScheme.onPrimaryContainer
        "Medium" -> MaterialTheme.colorScheme.secondaryContainer to MaterialTheme.colorScheme.onSecondaryContainer
        "Hard" -> MaterialTheme.colorScheme.tertiaryContainer to MaterialTheme.colorScheme.onTertiaryContainer
        else -> MaterialTheme.colorScheme.surfaceVariant to MaterialTheme.colorScheme.onSurfaceVariant
    }

    AssistChip(
        onClick = { },
        label = {
            Text(
                text = difficulty,
                style = MaterialTheme.typography.labelSmall
            )
        },
        colors = AssistChipDefaults.assistChipColors(
            containerColor = backgroundColor,
            labelColor = textColor
        ),
        border = null,
        modifier = Modifier.height(24.dp)
    )
}

data class Challenge(
    val id: String,
    val title: String,
    val description: String,
    val points: Int,
    val difficulty: String,
    val isCompleted: Boolean = false
)

private fun getDailyChallenges(): List<Challenge> {
    return listOf(
        Challenge(
            id = "1",
            title = "Morning Aesthetic Setup",
            description = "Arrange your coffee and reading materials in a visually pleasing way before your first meeting",
            points = 15,
            difficulty = "Easy"
        ),
        Challenge(
            id = "2",
            title = "Mindful Social Media",
            description = "Post something authentic yet curated that demonstrates your thoughtful lifestyle",
            points = 25,
            difficulty = "Medium"
        ),
        Challenge(
            id = "3",
            title = "Intellectual Reference Drop",
            description = "Naturally mention a book, podcast, or film that showcases your refined taste in conversation",
            points = 20,
            difficulty = "Medium"
        ),
        Challenge(
            id = "4",
            title = "Sustainable Choice Visible",
            description = "Make an environmentally conscious choice and ensure it's subtly noticed (tote bag, metal straw, etc.)",
            points = 30,
            difficulty = "Easy"
        ),
        Challenge(
            id = "5",
            title = "Deep Conversation Starter",
            description = "Initiate a meaningful conversation about purpose, creativity, or personal growth",
            points = 40,
            difficulty = "Hard"
        ),
        Challenge(
            id = "6",
            title = "Artisanal Preference",
            description = "Choose something handcrafted, local, or small-batch over a mainstream alternative",
            points = 20,
            difficulty = "Easy"
        )
    )
}