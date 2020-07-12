package id.koridor50.jesika.di

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import id.koridor50.jesika.data.IApiEndpoint
import id.koridor50.jesika.data.repository.RemoteRepository
import id.koridor50.jesika.ui.chat_room.ChatRoomViewModel
import id.koridor50.jesika.ui.create_group.CreateGroupViewModel
import id.koridor50.jesika.ui.home.HomeViewModel
import id.koridor50.jesika.ui.login.LoginViewModel
import id.koridor50.jesika.ui.member_list.MemberListViewModel
import id.koridor50.jesika.ui.new_member.NewMemberViewModel
import id.koridor50.jesika.ui.profile.ProfileViewModel
import id.koridor50.jesika.ui.redeem_promo.RedeemPromoViewModel
import id.koridor50.jesika.ui.voucher_list.VoucherListViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
class AppModule (private val app: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        return loggingInterceptor
    }

    @Provides
    @Singleton
    fun provideOkhttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .connectTimeout(45L, TimeUnit.SECONDS)
            .writeTimeout(45L, TimeUnit.SECONDS)
            .readTimeout(45L, TimeUnit.SECONDS)
            .addInterceptor(loggingInterceptor)
            .build()
    }

    @Provides
    @Singleton
    fun provideRetrofit(client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jesika-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

    @Provides
    @Singleton
    fun provideApiServices(retrofit: Retrofit) : IApiEndpoint = retrofit.create(IApiEndpoint::class.java)

    @Provides
    @Singleton
    fun getIoDispatcher() = Dispatchers.IO

    @Provides
    @Singleton
    fun provideRemoteRepository (apiEndpoint: IApiEndpoint, ioDispatcher: CoroutineDispatcher) =
        RemoteRepository(apiEndpoint, ioDispatcher)

    @Provides
    @Singleton
    fun provideChatRoomViewModel (repository: RemoteRepository) = ChatRoomViewModel(repository)

    @Provides
    @Singleton
    fun provideCreateGroupRoomViewModel (repository: RemoteRepository, context: Context) =
        CreateGroupViewModel(repository, context)

    @Provides
    @Singleton
    fun provideHomeViewModel (repository: RemoteRepository) = HomeViewModel(repository)

    @Provides
    @Singleton
    fun provideLoginViewModel (repository: RemoteRepository) = LoginViewModel(repository)

    @Provides
    @Singleton
    fun provideMemberListViewModel (repository: RemoteRepository, context: Context) =
        MemberListViewModel(repository, context)

    @Provides
    @Singleton
    fun provideProfileViewModel (repository: RemoteRepository, context: Context) =
        ProfileViewModel(repository, context)

    @Provides
    @Singleton
    fun provideRedeemPromoViewModel (repository: RemoteRepository,  context: Context) =
        RedeemPromoViewModel(repository, context)


    @Provides
    @Singleton
    fun provideNewMemberViewModel (repository: RemoteRepository, context: Context) =
        NewMemberViewModel(repository, context)

    @Provides
    @Singleton
    fun provideVoucherListViewModel (repository: RemoteRepository) = VoucherListViewModel(repository)

}