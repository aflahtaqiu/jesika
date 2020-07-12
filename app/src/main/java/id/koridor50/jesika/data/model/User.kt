package id.koridor50.jesika.data.model

import com.google.gson.annotations.SerializedName

data class User (
    val id: Int,
    val name: String,
    @SerializedName("bpjs_number") val bpjsNumber: Int,
    val poins: Double,
    @SerializedName("phone_number") val phoneNumber: String,
    val password: String,
    val email: String,
    @SerializedName("tagihan_bayar") val isLunas: Boolean,
    @SerializedName("local_community") val localCommunity: LocalCommunity
)