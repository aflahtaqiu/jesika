package id.koridor50.jesika.ui.login

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.koridor50.jesika.common.PrefKey
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.data.model.response.Result
import id.koridor50.jesika.data.repository.RemoteRepository
import id.koridor50.jesika.utils.savePref
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: RemoteRepository, private val context: Context) : ViewModel() {

    var usersLiveData : MutableLiveData<User> = MutableLiveData()
    var errorsLiveData : MutableLiveData<String> = MutableLiveData()

    var email = ObservableField<String>("")
    var password = ObservableField<String>("")

    fun login () {
        viewModelScope.launch {
            when(val result = repository.login(email.get()!!, password.get()!!).value) {
                is Result.Success<User> -> {
                    context.savePref(PrefKey.USERIDPREFKEY, result.data.id)

                    result.data.localCommunity?.let {
                        context.savePref(PrefKey.LOCALCOMMUNITYIDPREFKEY, result.data.localCommunity.id)
                    }

                    usersLiveData.value = result.data
                }
                is Result.Error -> {
                    errorsLiveData.value = result.message
                }
            }
        }
    }
}