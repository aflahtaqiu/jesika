package id.koridor50.jesika.ui.create_group

import android.content.Context
import android.util.Log
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.koridor50.jesika.common.PrefKey
import id.koridor50.jesika.data.model.LocalCommunity
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.data.model.response.Result
import id.koridor50.jesika.data.repository.RemoteRepository
import id.koridor50.jesika.utils.getPrefInt
import id.koridor50.jesika.utils.savePref
import kotlinx.coroutines.launch
import javax.inject.Inject

class CreateGroupViewModel @Inject constructor(private val repository: RemoteRepository,
                                               private val context: Context) : ViewModel() {

    var name = ObservableField<String>("")
    var bpjsNumber = ObservableField<String>("")

    var localCommunityNameLiveData : MutableLiveData<String> = MutableLiveData()
    var memberLiveData : MutableLiveData<User> = MutableLiveData()
    var errorsLiveData : MutableLiveData<String> = MutableLiveData()
    var isSuccessLiveData : MutableLiveData<Boolean> = MutableLiveData()

    var selectedMembers = mutableListOf<Int>()

    val coorUserId = context.getPrefInt(PrefKey.USERIDPREFKEY)

    fun addMemberList () {
        viewModelScope.launch {
            when(val result = repository.getUserByNoBpjs(bpjsNumber.get()!!).value) {
                is Result.Success<User> -> {
                    Log.e("lele", result.data.toString())
                    selectedMembers.add(result.data.id)
                    memberLiveData.value = result.data
                }
                is Result.Error -> {
                    Log.e("lele", result.message +" dbh")
                    errorsLiveData.value = result.message
                }
            }
        }
    }

    fun createLocalCommunity() {
        val listMembers = selectedMembers.joinToString(",")

        viewModelScope.launch {
            when(val result = repository.postNewLocalCommunity(coorUserId, listMembers, name.get()!!).value) {
                is Result.Success<LocalCommunity> -> {
                    isSuccessLiveData.value = true
                    localCommunityNameLiveData.value = result.data.name
                    context.savePref(PrefKey.LOCALCOMMUNITYIDPREFKEY, result.data.id)
                }

                is Result.Error -> {
                    isSuccessLiveData.value = false
                    errorsLiveData.value = result.message
                }
            }
        }
    }
}