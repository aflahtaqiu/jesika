package id.koridor50.jesika.ui.redeem_promo

import android.content.Context
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

    var isSuccessCheckoutLiveData : MutableLiveData<Boolean> = MutableLiveData()
    var messageLiveData : MutableLiveData<String> = MutableLiveData()

    init {
        val idUser = context.getPrefInt(PrefKey.USERIDPREFKEY)
        isSuccessCheckoutLiveData.value = false

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
                    messageLiveData.value = "Anda yakin ingin menggunakan voucher ini? Poin anda akan berkurang sebesar ${result.data.poin}"
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
                    isSuccessCheckoutLiveData.value = true
                }
                is Result.Error -> {
                    isSuccessCheckoutLiveData.value = false
                }
            }
        }
    }
}