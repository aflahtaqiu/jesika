package id.koridor50.jesika.ui.profile

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.koridor50.jesika.common.PrefKey
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.data.model.UsersVouchers
import id.koridor50.jesika.data.model.Voucher
import id.koridor50.jesika.data.model.response.ResponseVouchersUsed
import id.koridor50.jesika.data.model.response.Result
import id.koridor50.jesika.data.repository.RemoteRepository
import id.koridor50.jesika.utils.clearPref
import id.koridor50.jesika.utils.getPrefInt
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel @Inject constructor(private val repository: RemoteRepository,
                                           private val context: Context): ViewModel() {

    var userLiveData : MutableLiveData<User> = MutableLiveData()
    var voucherLiveData : MutableLiveData<Voucher> = MutableLiveData()

    var usedVoucherLiveData : MutableLiveData<MutableList<Voucher>> = MutableLiveData()

    val idUser = context.getPrefInt(PrefKey.USERIDPREFKEY)

    var list = mutableListOf<Voucher>()

    init {

        getUserDetail()
        getVouchers()

        getUsedVouchers()
    }

    fun getUserDetail () {
        viewModelScope.launch {
            when(val result = repository.getUserDetail(idUser).value) {
                is Result.Success<User> -> {
                    userLiveData.value = result.data
                }
                is Result.Error -> {

                }
            }
        }
    }

    fun getVouchers () {
        viewModelScope.launch {
            when(val result = repository.getVouchers().value) {
                is Result.Success<List<Voucher>> -> {
                    voucherLiveData.value = result.data.first()
                }
                is Result.Error -> {

                }
            }
        }
    }

    fun getUsedVouchers () {
        viewModelScope.launch {

            var vouchers = mutableListOf<Voucher>()

            when(val result = repository.getUsedVouchers(idUser).value) {
                is Result.Success<List<UsersVouchers>> -> {

                    result.data.forEach {

                        when(val result = repository.getVoucherById(it.idVoucher).value) {
                            is Result.Success<Voucher> -> {
                                vouchers.add(result.data)
                            }
                        }
                    }
                }
                is Result.Error -> {
                }
            }
            usedVoucherLiveData.value = vouchers
        }
    }

    fun clearPreference() = context.clearPref()
}