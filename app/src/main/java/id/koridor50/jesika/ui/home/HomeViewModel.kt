package id.koridor50.jesika.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import id.koridor50.jesika.data.repository.RemoteRepository
import javax.inject.Inject

class HomeViewModel @Inject constructor(private val repository: RemoteRepository): ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val text: LiveData<String> = _text
}