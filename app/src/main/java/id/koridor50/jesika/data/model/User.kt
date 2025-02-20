package id.koridor50.jesika.data.model

import com.google.gson.annotations.SerializedName
import id.koridor50.jesika.utils.toSimpleString
import java.util.*

data class User (
    val id: Int,
    val name: String,
    @SerializedName("bpjs_number") val bpjsNumber: Int,
    val poins: Double,
    @SerializedName("phone_number") val phoneNumber: String,
    val password: String,
    val email: String,
    @SerializedName("tagihan_bayar") val isPaid: Boolean,
    @SerializedName("local_community") val localCommunity: LocalCommunity?,
    @SerializedName("tanggal_lahir") val date: Date
) {
    val birthDate
        get() = date.toSimpleString()

    val aliasName
        get() = name.substring(0,2).capitalize()

    val roundPoin
        get() = String.format("%.0f", poins)
}