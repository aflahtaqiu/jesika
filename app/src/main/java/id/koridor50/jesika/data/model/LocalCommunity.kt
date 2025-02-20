package id.koridor50.jesika.data.model

import com.google.gson.annotations.SerializedName

data class LocalCommunity(
    val id: Int,
    @SerializedName("user_id") val coorUserId: Int,
    val count: Int,
    val name: String,
    val anggota: List<LocalCommunityUser>
)