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
import id.koridor50.jesika.ui.profile.ProfileViewModel
import id.koridor50.jesika.ui.redeem_promo.RedeemPromoViewModel
import id.koridor50.jesika.ui.tambah_anggota.TambahAnggotaViewModel
import id.koridor50.jesika.ui.voucher_list.VoucherListViewModel
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class AppModule (private val app: Application) {

    @Provides
    @Singleton
    fun provideContext(): Context = app

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://jesika-api.herokuapp.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    @Provides
    @Singleton
    fun provideApiServices(retrofit: Retrofit) : IApiEndpoint = retrofit.create(IApiEndpoint::class.java)

    @Provides
    @Singleton
    fun provideRemoteRepository (apiEndpoint: IApiEndpoint) =
        RemoteRepository(apiEndpoint)


    @Provides
    @Singleton
    fun provideChatRoomViewModel (repository: RemoteRepository) = ChatRoomViewModel(repository)

    @Provides
    @Singleton
    fun provideCreateGroupRoomViewModel (repository: RemoteRepository) = CreateGroupViewModel(repository)

    @Provides
    @Singleton
    fun provideHomeViewModel (repository: RemoteRepository) = HomeViewModel(repository)

    @Provides
    @Singleton
    fun provideLoginViewModel (repository: RemoteRepository) = LoginViewModel(repository)

    @Provides
    @Singleton
    fun provideMemberListViewModel (repository: RemoteRepository) = MemberListViewModel(repository)

    @Provides
    @Singleton
    fun provideProfileViewModel (repository: RemoteRepository) = ProfileViewModel(repository)

    @Provides
    @Singleton
    fun provideRedeemPromoViewModel (repository: RemoteRepository) = RedeemPromoViewModel(repository)

    @Provides
    @Singleton
    fun provideTambahAnggotaViewModel (repository: RemoteRepository) = TambahAnggotaViewModel(repository)

    @Provides
    @Singleton
    fun provideVoucherListViewModel (repository: RemoteRepository) = VoucherListViewModel(repository)

}