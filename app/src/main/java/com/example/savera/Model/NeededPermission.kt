package com.example.savera.Model

enum class NeededPermission(
    val permission: String,
    val title: String,
    val description: String,
    val permanentlyDeniedDescription: String,
) {
    POST_NOTIFICATION(
        permission = android.Manifest.permission.POST_NOTIFICATIONS,
        title = "Post Notification permission",
        description = "This permission is needed to post notifications. Please grant the permission.",
        permanentlyDeniedDescription = "This permission is needed to post notifications. Please grant the permission in app settings.",
    );

    fun permissionTextProvider(isPermanentDenied: Boolean): String {
        return if (isPermanentDenied) this.permanentlyDeniedDescription else this.description
    }
}

fun getNeededPermission(permission: String): NeededPermission {
    return NeededPermission.values().find { it.permission == permission } ?: throw IllegalArgumentException("Permission $permission is not supported")
}