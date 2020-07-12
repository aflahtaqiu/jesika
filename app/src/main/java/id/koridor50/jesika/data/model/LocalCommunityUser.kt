package id.koridor50.jesika.data.model

import com.google.gson.annotations.SerializedName

data class LocalCommunityUser(
    val id: Int,
    @SerializedName("user_id") val coorUserId: Int,
    @SerializedName("local_community_id") val localCommunityid: Int,
    @SerializedName("tagihan_bayar") val isPaid: Boolean,
    val user: User
)