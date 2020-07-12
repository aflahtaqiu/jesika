package id.koridor50.jesika.di

import dagger.Component
import id.koridor50.jesika.JesikaApp
import id.koridor50.jesika.data.repository.RemoteRepository
import id.koridor50.jesika.ui.chat_room.ChatRoomFragment
import id.koridor50.jesika.ui.chat_room.ChatRoomViewModel
import id.koridor50.jesika.ui.create_group.CreateGroupFragment
import id.koridor50.jesika.ui.create_group.CreateGroupViewModel
import id.koridor50.jesika.ui.home.HomeFragment
import id.koridor50.jesika.ui.home.HomeViewModel
import id.koridor50.jesika.ui.login.LoginFragment
import id.koridor50.jesika.ui.login.LoginViewModel
import id.koridor50.jesika.ui.member_list.MemberListFragment
import id.koridor50.jesika.ui.member_list.MemberListViewModel
import id.koridor50.jesika.ui.new_member.NewMemberFragment
import id.koridor50.jesika.ui.new_member.NewMemberViewModel
import id.koridor50.jesika.ui.profile.ProfileFragment
import id.koridor50.jesika.ui.profile.ProfileViewModel
import id.koridor50.jesika.ui.redeem_promo.RedeemPromoFragment
import id.koridor50.jesika.ui.redeem_promo.RedeemPromoViewModel
import id.koridor50.jesika.ui.voucher_list.VoucherListFragment
import id.koridor50.jesika.ui.voucher_list.VoucherListViewModel
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface AppComponent {

    fun inject(target: ChatRoomFragment)
    fun inject(target: ChatRoomViewModel)

    fun inject(target: CreateGroupFragment)
    fun inject(target: CreateGroupViewModel)

    fun inject(target: HomeFragment)
    fun inject(target: HomeViewModel)

    fun inject(target: LoginFragment)
    fun inject(target: LoginViewModel)

    fun inject(target: MemberListFragment)
    fun inject(target: MemberListViewModel)

    fun inject(target: ProfileFragment)
    fun inject(target: ProfileViewModel)

    fun inject(target: RedeemPromoFragment)
    fun inject(target: RedeemPromoViewModel)

    fun inject(target: NewMemberFragment)
    fun inject(target: NewMemberViewModel)

    fun inject(target: VoucherListFragment)
    fun inject(target: VoucherListViewModel)

    fun inject(target: RemoteRepository)
}