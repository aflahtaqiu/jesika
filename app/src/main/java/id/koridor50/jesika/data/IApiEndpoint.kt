package id.koridor50.jesika.data

import id.koridor50.jesika.data.model.LocalCommunity
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.data.model.Voucher
import id.koridor50.jesika.data.model.response.ResponseGetuserByBpjs
import retrofit2.http.*

interface IApiEndpoint {

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ) : User

    @GET("users/{id}")
    suspend fun getUserDetail (
        @Path("id") idUser: Int
    ) : User

    @GET("users")
    suspend fun getUserByNoBpjs(
        @Query("bpjs_number") bpjsNumber: Int
    ) : ResponseGetuserByBpjs

    @FormUrlEncoded
    @POST("local_communities")
    suspend fun postLocalCommunity (
        @Field("user_id") coorUserId: Int,
        @Field("list_members") listIdMember: String,
        @Field("name") name: String
    ) : LocalCommunity

    @GET("vouchers")
    suspend fun getVouchers() : List<Voucher>

    @GET("vouchers/{id})")
    suspend fun getVoucherById (
        @Path("id") id: Int
    ) : Voucher

    @FormUrlEncoded
    @POST("vouchers/{idVoucher}/checkout")
    suspend fun checkoutVoucher (
        @Path("idVoucher") idVoucher : Int,
        @Query("user_id") idUser: Int,
        @Field("user_id") idUserField: Int
    ) : User
}