package id.koridor50.jesika.ui.redeem_promo

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.koridor50.jesika.common.PrefKey
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.data.model.Voucher
import id.koridor50.jesika.data.model.response.Result
import id.koridor50.jesika.data.repository.RemoteRepository
import id.koridor50.jesika.utils.getPrefInt
import kotlinx.coroutines.launch
import javax.inject.Inject

class RedeemPromoViewModel @Inject constructor(private val repository: RemoteRepository,
                                               private val context: Context): ViewModel() {

    var userLiveData : MutableLiveData<User> = MutableLiveData()
    var voucherLiveData : MutableLiveData<Voucher> = MutableLiveData()

    init {

        val idUser = context.getPrefInt(PrefKey.USERIDPREFKEY)

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

    fun getVoucherDetail (idVoucher: Int) {
        viewModelScope.launch {
            when(val result = repository.getVoucherById(idVoucher).value) {
                is Result.Success<Voucher> -> {
                    voucherLiveData.value = result.data
                }
                is Result.Error -> {

                }
            }
        }
    }

    fun checkoutVoucher () {
        val idUser:Int = userLiveData.value!!.id
        val idVoucher = voucherLiveData.value!!.id


        viewModelScope.launch {
            when(val result = repository.checkoutVoucher(idVoucher, idUser).value) {
                is Result.Success<User> -> {

                }
                is Result.Error -> {
                    Log.e("lele", result.message!!)
                }
            }
        }
    }
}