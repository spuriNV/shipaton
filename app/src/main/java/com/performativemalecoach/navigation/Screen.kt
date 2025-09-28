package com.performativemalecoach.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Chat : Screen("chat")
    object OutfitAnalysis : Screen("outfit")
    object CaptionGenerator : Screen("captions")
    object DailyChallenges : Screen("challenges")
    object Profile : Screen("profile")
}