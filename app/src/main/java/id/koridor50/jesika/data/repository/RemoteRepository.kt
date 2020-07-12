package id.koridor50.jesika.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.koridor50.jesika.data.IApiEndpoint
import id.koridor50.jesika.data.model.LocalCommunity
import id.koridor50.jesika.data.model.User
import id.koridor50.jesika.data.model.Voucher
import id.koridor50.jesika.data.model.response.Result
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.lang.Exception
import javax.inject.Inject

class RemoteRepository @Inject constructor(
    private val apiEndpoint: IApiEndpoint,
    private val ioDispatcher: CoroutineDispatcher
) {

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

    suspend fun getUserDetail (idUser: Int) : LiveData<Result<User>> {
        val liveData = MutableLiveData<Result<User>>()
        withContext(ioDispatcher) {
            try {
                val successResponse = apiEndpoint.getUserDetail(idUser)
                liveData.postValue(Result.Success(successResponse))
            } catch (e:Exception) {
                liveData.postValue(Result.Error(e.localizedMessage))
            }
        }
        return liveData
    }

    suspend fun getUserByNoBpjs (bpjsNumber: Int) : LiveData<Result<User>> {
        val liveData = MutableLiveData<Result<User>>()
        withContext(ioDispatcher) {
            try {
                val successResponse = apiEndpoint.getUserByNoBpjs(bpjsNumber).items.first()
                liveData.postValue(Result.Success(successResponse))
            } catch (e:Exception) {
                liveData.postValue(Result.Error(e.localizedMessage))
            }
        }
        return liveData
    }

    suspend fun postNewLocalCommunity (coorUserId: Int, listIdMember: String, name: String) :
            LiveData<Result<LocalCommunity>> {
        val liveData = MutableLiveData<Result<LocalCommunity>>()
        withContext(ioDispatcher) {
            try {
                val successResponse = apiEndpoint.postLocalCommunity(coorUserId, listIdMember, name)
                liveData.postValue(Result.Success(successResponse))
            } catch (e:Exception) {
                liveData.postValue(Result.Error(e.localizedMessage))
            }
        }
        return liveData
    }

    suspend fun getVouchers () :
            LiveData<Result<List<Voucher>>> {
        val liveData = MutableLiveData<Result<List<Voucher>>>()
        withContext(ioDispatcher) {
            try {
                val successResponse = apiEndpoint.getVouchers()
                liveData.postValue(Result.Success(successResponse))
            } catch (e:Exception) {
                liveData.postValue(Result.Error(e.localizedMessage))
            }
        }
        return liveData
    }

    suspend fun getVoucherById (idVoucher: Int) :
            LiveData<Result<Voucher>> {
        val liveData = MutableLiveData<Result<Voucher>>()
        withContext(ioDispatcher) {
            try {
                val successResponse = apiEndpoint.getVoucherById(idVoucher)
                liveData.postValue(Result.Success(successResponse))
            } catch (e:Exception) {
                liveData.postValue(Result.Error(e.localizedMessage))
            }
        }
        return liveData
    }

    suspend fun checkoutVoucher (idVoucher: Int, idUser: Int) : LiveData<Result<User>> {
        val liveData = MutableLiveData<Result<User>>()
        withContext(ioDispatcher) {
            try {
                val successResponse = apiEndpoint.checkoutVoucher(idVoucher, idUser, idUser)
                liveData.postValue(Result.Success(successResponse))
            } catch (e:Exception) {
                liveData.postValue(Result.Error(e.localizedMessage))
            }
        }
        return liveData
    }
}

