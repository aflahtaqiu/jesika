package id.koridor50.jesika.ui.voucher_list

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.data.model.Voucher
import id.koridor50.jesika.data.model.response.Result
import id.koridor50.jesika.data.repository.RemoteRepository
import id.koridor50.jesika.utils.getPrefInt
import kotlinx.coroutines.launch
import javax.inject.Inject

class VoucherListViewModel @Inject constructor(private val repository: RemoteRepository): ViewModel() {

    var voucherLiveData : MutableLiveData<List<Voucher>> = MutableLiveData()

    init {
        viewModelScope.launch {
            when(val result = repository.getVouchers().value) {
                is Result.Success<List<Voucher>> -> {
                    voucherLiveData.value = result.data
                }
                is Result.Error -> {

                }
            }
        }
    }
}