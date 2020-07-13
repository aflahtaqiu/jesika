package id.koridor50.jesika.ui.home

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.koridor50.jesika.common.PrefKey
import id.koridor50.jesika.data.model.LocalCommunity
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.data.model.response.Result
import id.koridor50.jesika.data.repository.RemoteRepository
import id.koridor50.jesika.utils.getPrefInt
import kotlinx.coroutines.launch
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: RemoteRepository, private val context: Context): ViewModel() {

    var userLiveData : MutableLiveData<User> = MutableLiveData()
    var localCommunityLiveData : MutableLiveData<LocalCommunity> = MutableLiveData()

    val idLocalCommunity  = context.getPrefInt(PrefKey.LOCALCOMMUNITYIDPREFKEY)
    val idLoggedInUser = context.getPrefInt(PrefKey.USERIDPREFKEY)

    init {
        getUserData()
        getLocalCommunityData()
    }

    fun getUserData () {
        viewModelScope.launch {
            when(val result = repository.getUserDetail(idLoggedInUser).value) {
                is Result.Success<User> -> {
                    userLiveData.value = result.data
                }
                is Result.Error -> {

                }
            }


        }
    }

    fun getLocalCommunityData () {
        viewModelScope.launch {
            when(val result = repository.getLocalCommunity(idLocalCommunity).value) {
                is Result.Success<LocalCommunity> -> {
                    localCommunityLiveData.value = result.data
                }
                is Result.Error -> {

                }
            }
        }
    }
}