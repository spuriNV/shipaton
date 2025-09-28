package com.performativemalecoach.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Photo
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.EmojiEvents
import androidx.compose.ui.graphics.vector.ImageVector

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem(
        name = "Home",
        route = Screen.Home.route,
        icon = Icons.Default.Home
    ),
    BottomNavItem(
        name = "Chat",
        route = Screen.Chat.route,
        icon = Icons.Default.Chat
    ),
    BottomNavItem(
        name = "Outfit",
        route = Screen.OutfitAnalysis.route,
        icon = Icons.Default.Photo
    ),
    BottomNavItem(
        name = "Captions",
        route = Screen.CaptionGenerator.route,
        icon = Icons.Default.Create
    ),
    BottomNavItem(
        name = "Challenges",
        route = Screen.DailyChallenges.route,
        icon = Icons.Default.EmojiEvents
    ),
    BottomNavItem(
        name = "Profile",
        route = Screen.Profile.route,
        icon = Icons.Default.Person
    )
)