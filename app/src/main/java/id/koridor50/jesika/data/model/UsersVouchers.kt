package id.koridor50.jesika.data.model

import com.google.gson.annotations.SerializedName

data class UsersVouchers (
    val id:Int,
    @SerializedName("user_id") val idUser:Int,
    @SerializedName("voucher_id") val idVoucher: Int
)