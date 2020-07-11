package id.koridor50.jesika.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.koridor50.jesika.data.IApiEndpoint
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.data.model.response.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val apiEndpoint: IApiEndpoint
) {

    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO

    suspend fun login (email: String, password: String) : LiveData<Result<User>> {
        val liveData = MutableLiveData<Result<User>>()
        withContext(ioDispatcher) {
            try {
                val successResponse = apiEndpoint.login(email, password)
                liveData.postValue(Result.Success(successResponse))
            } catch (e:Exception) {
                liveData.postValue(Result.Error(e.localizedMessage))
            }
        }
        return liveData
    }
}