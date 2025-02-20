package id.koridor50.jesika.ui.voucher_list

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

class VoucherListViewModel @Inject constructor(private val repository: RemoteRepository,
                                               private val context: Context): ViewModel() {

    var voucherLiveData : MutableLiveData<List<Voucher>> = MutableLiveData()
    var userLiveData : MutableLiveData<User> = MutableLiveData()

    init {
        val idUser = context.getPrefInt(PrefKey.USERIDPREFKEY)
        viewModelScope.launch {
            when(val result = repository.getVouchers().value) {
                is Result.Success<List<Voucher>> -> {
                    voucherLiveData.value = result.data
                }
                is Result.Error -> {

                }
            }
        }

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
}