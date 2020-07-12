package id.koridor50.jesika.data.model

import com.google.gson.annotations.SerializedName

data class Voucher(
    val id:Int,
    @SerializedName("photo_url") val photoUrl: PhotoUrl,
    val name: String,
    val description: String,
    @SerializedName("cost_poin") val poin: Int,
    @SerializedName("syarat_ketentuan") val termsCondition : String
)

data class PhotoUrl(
    val url: String
)