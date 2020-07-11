package id.koridor50.jesika.data

import id.koridor50.jesika.data.model.User
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface IApiEndpoint {

    @FormUrlEncoded
    @POST("users/login")
    suspend fun login(
        @Field("email") email:String,
        @Field("password") password: String
    ) : User
}