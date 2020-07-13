package id.koridor50.jesika.ui.new_member

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.koridor50.jesika.common.PrefKey
import id.koridor50.jesika.data.model.LocalCommunity
import id.koridor50.jesika.data.model.LocalCommunityUser
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.data.model.response.Result
import id.koridor50.jesika.data.repository.RemoteRepository
import id.koridor50.jesika.utils.getPrefInt
import kotlinx.coroutines.launch
import javax.inject.Inject

class NewMemberViewModel @Inject constructor(private val repository: RemoteRepository,
                                             private val context: Context
): ViewModel() {
    var newMemberLiveData : MutableLiveData<User> = MutableLiveData()
    var localCommunityLiveData: MutableLiveData<LocalCommunity> = MutableLiveData()
    var errorsLiveData : MutableLiveData<String> = MutableLiveData()

    var bpjsNumber = ObservableField<String>("")

    val idLocalCommunity  = context.getPrefInt(PrefKey.LOCALCOMMUNITYIDPREFKEY)

    init {
        viewModelScope.launch {
            when(val result = repository.getLocalCommunityMembers(idLocalCommunity).value) {
                is Result.Success<LocalCommunity> -> {
                    localCommunityLiveData.value = result.data
                }

                is Result.Error -> {
                    Log.e("lele", result.message!!)
                }
            }
        }
    }

    fun storeNewMember () {

        viewModelScope.launch {
            when(val result = repository.addMemberLocalCommunity(newMemberLiveData.value!!.id,
                idLocalCommunity).value) {

                is Result.Success<LocalCommunityUser> -> {
                    Log.e("lele", "sukses ${result.data.toString()}")
                }

                is Result.Error -> {
                    Log.e("lele", result.message!!)
                }
            }
        }
    }

    fun getUserIdByBpjsNumber () {

        viewModelScope.launch {
            when(val result = repository.getUserByNoBpjs(bpjsNumber.get()!!.toInt()).value) {
                is Result.Success<User> -> {
                    newMemberLiveData.value = result.data
                }
                is Result.Error -> {
                    errorsLiveData.value = result.message
                }
            }
        }
    }
}