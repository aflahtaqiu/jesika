package id.koridor50.jesika.ui.member_list

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

class MemberListViewModel @Inject constructor(private val repository: RemoteRepository,
                                              private val context: Context): ViewModel() {

    val idLocalCommunity  = context.getPrefInt(PrefKey.LOCALCOMMUNITYIDPREFKEY)
    val idUser = context.getPrefInt(PrefKey.USERIDPREFKEY)

    var membersLiveData : MutableLiveData<LocalCommunity> = MutableLiveData()
    var userLiveData : MutableLiveData<User> = MutableLiveData()

    init {
        viewModelScope.launch {
            when(val result = repository.getLocalCommunity(idLocalCommunity).value) {
                is Result.Success<LocalCommunity> -> {
                    membersLiveData.value = result.data
                }
                is Result.Error -> {

                }
            }

            when (val result = repository.getUserDetail(idUser).value) {
                is Result.Success<User> -> {
                    userLiveData.value = result.data
                }
                is Result.Error -> {

                }
            }
        }
    }
}