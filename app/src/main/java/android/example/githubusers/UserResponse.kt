package android.example.githubusers

data class UserResponse (
        val totalCount: Int,
        val inCompleteResults: Boolean,
        val items: List<User>
        )