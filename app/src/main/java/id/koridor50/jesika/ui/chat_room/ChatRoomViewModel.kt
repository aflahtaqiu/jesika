package id.koridor50.jesika.ui.chat_room

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import id.koridor50.jesika.common.PrefKey
import id.koridor50.jesika.data.model.LocalCommunity
import id.koridor50.jesika.data.model.response.Result
import id.koridor50.jesika.data.repository.RemoteRepository
import id.koridor50.jesika.utils.getPrefInt
import kotlinx.coroutines.launch
import javax.inject.Inject

class ChatRoomViewModel @Inject constructor(private val repository: RemoteRepository,
                                            private val context: Context) : ViewModel() {

    val localCommunityId: Int by lazy {
        context.getPrefInt(PrefKey.LOCALCOMMUNITYIDPREFKEY)
    }

    var localCommunityNameLiveData: MutableLiveData<String> = MutableLiveData()

    init {
        viewModelScope.launch {
            when(val result = repository.getLocalCommunity(localCommunityId).value) {
                is Result.Success<LocalCommunity> -> {
                    localCommunityNameLiveData.value = result.data.name
                }
            }
        }
    }
}