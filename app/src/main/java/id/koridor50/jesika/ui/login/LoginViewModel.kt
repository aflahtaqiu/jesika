package id.koridor50.jesika.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.data.model.response.Result
import id.koridor50.jesika.data.repository.RemoteRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

class LoginViewModel @Inject constructor(private val repository: RemoteRepository) : ViewModel() {
    fun login (email: String, password: String) {
        viewModelScope.launch {
            when(val result = repository.login(email, password).value) {
                is Result.Success<User> -> {
                    Log.e("lele sukses", result.data.toString())
                }
                is Result.Error -> {
                    Log.e("lele", result.message!!)
                }
            }
        }
    }
}