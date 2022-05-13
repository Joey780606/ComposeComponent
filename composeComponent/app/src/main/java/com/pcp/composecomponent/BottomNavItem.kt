package com.pcp.composecomponent

import androidx.compose.ui.graphics.vector.ImageVector

// Badge Step 02: Create Item data class
data class BottomNavItem (
    val name: String,
    val route: String,
    val icon: ImageVector,
    val badgeCount: Int = 0
)
