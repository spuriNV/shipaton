package com.performativemalecoach.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.performativemalecoach.ui.components.CoachCard
import com.performativemalecoach.ui.components.CoachButton
import com.performativemalecoach.ui.components.ScoreCircle
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun OutfitAnalysisScreen() {
    var hasPhoto by remember { mutableStateOf(false) }
    var isAnalyzing by remember { mutableStateOf(false) }
    var analysisComplete by remember { mutableStateOf(false) }
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
            if (!hasPhoto) {
                PhotoUploadCard { photoType ->
                    hasPhoto = true
                    isAnalyzing = true
                    // Simulate analysis
                    coroutineScope.launch {
                        delay(3000)
                        isAnalyzing = false
                        analysisComplete = true
                    }
                }
            } else if (isAnalyzing) {
                AnalyzingCard()
            } else if (analysisComplete) {
                AnalysisResultCard()
            }
        }

        if (analysisComplete) {
            item {
                DetailedFeedbackCard()
            }
            item {
                RecommendationsCard()
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
                imageVector = Icons.Default.PhotoCamera,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(48.dp)
            )
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = "Outfit Analysis",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            Text(
                text = "Get expert feedback on your aesthetic choices",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                textAlign = TextAlign.Center
            )
        }
    }
}

@Composable
private fun PhotoUploadCard(onPhotoSelected: (String) -> Unit) {
    CoachCard {
        Text(
            text = "Upload Your Look",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            CoachButton(
                text = "Take Photo",
                icon = Icons.Default.PhotoCamera,
                onClick = { onPhotoSelected("camera") },
                modifier = Modifier.weight(1f)
            )
            CoachButton(
                text = "From Gallery",
                icon = Icons.Default.Photo,
                onClick = { onPhotoSelected("gallery") },
                modifier = Modifier.weight(1f)
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier.fillMaxWidth(),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f)
            ),
            shape = RoundedCornerShape(12.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    imageVector = Icons.Default.Lightbulb,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Pro Tip",
                    style = MaterialTheme.typography.labelLarge,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = "For best results, take a full-body photo in good lighting",
                    style = MaterialTheme.typography.bodySmall,
                    textAlign = TextAlign.Center,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun AnalyzingCard() {
    CoachCard {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(
                modifier = Modifier.size(64.dp),
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Analyzing Your Look...",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text = "Chad is evaluating your aesthetic choices",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
            )
        }
    }
}

@Composable
private fun AnalysisResultCard() {
    CoachCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Analysis Complete! 🎯",
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Your outfit scored impressively well",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                )
            }
            ScoreCircle(
                score = 89,
                size = 100
            )
        }
    }
}

@Composable
private fun DetailedFeedbackCard() {
    CoachCard {
        Text(
            text = "Detailed Feedback",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))

        val feedbackItems = listOf(
            FeedbackItem("Color Coordination", 95, "Excellent earth tone palette"),
            FeedbackItem("Fit & Silhouette", 87, "Well-tailored with good proportions"),
            FeedbackItem("Accessories", 82, "Subtle and tasteful choices"),
            FeedbackItem("Overall Aesthetic", 91, "Strong cohesive visual identity")
        )

        feedbackItems.forEach { item ->
            FeedbackRow(item)
            if (item != feedbackItems.last()) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun FeedbackRow(item: FeedbackItem) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.category,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "${item.score}%",
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )
        }
        Spacer(modifier = Modifier.height(4.dp))
        LinearProgressIndicator(
            progress = item.score / 100f,
            modifier = Modifier.fillMaxWidth(),
            color = MaterialTheme.colorScheme.primary,
            trackColor = MaterialTheme.colorScheme.surfaceVariant,
        )
        Spacer(modifier = Modifier.height(4.dp))
        Text(
            text = item.feedback,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
        )
    }
}

@Composable
private fun RecommendationsCard() {
    CoachCard {
        Text(
            text = "Optimization Suggestions",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))

        val recommendations = listOf(
            "Consider a vintage leather watch to complete the aesthetic",
            "The fit could be elevated with slight tapering at the ankles",
            "A minimal canvas tote bag would add thoughtful functionality"
        )

        recommendations.forEachIndexed { index, recommendation ->
            Row(
                verticalAlignment = Alignment.Top
            ) {
                Text(
                    text = "${index + 1}.",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = recommendation,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.weight(1f)
                )
            }
            if (index != recommendations.lastIndex) {
                Spacer(modifier = Modifier.height(12.dp))
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        CoachButton(
            text = "Analyze Another Look",
            icon = Icons.Default.Refresh,
            onClick = { /* Reset analysis */ }
        )
    }
}

data class FeedbackItem(
    val category: String,
    val score: Int,
    val feedback: String
)