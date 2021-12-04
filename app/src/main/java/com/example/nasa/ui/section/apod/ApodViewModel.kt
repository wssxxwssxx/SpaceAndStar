package com.example.nasa.ui.section.apod

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasa.service.apod.ApodApi
import com.example.nasa.service.apod.ApodVO
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.BackpressureStrategy
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class ApodViewModel @Inject constructor(private val apodApi: ApodApi): ViewModel() {

    val apodListLiveData: MutableLiveData<Response<List<ApodVO>>>

    val composite = CompositeDisposable()

    init {
        apodListLiveData = MutableLiveData()
    }

    fun getApodListData()
    : MutableLiveData<Response<List<ApodVO>>>{
        loadData()
        return apodListLiveData
    }

    fun loadData(){
        viewModelScope.launch {
            composite.add(
                apodApi.getApod("5")
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .distinct()
                    .subscribe ({ res ->
                        apodListLiveData.postValue(res)
                    }, {error ->
                        error.printStackTrace()
                    },{BackpressureStrategy.BUFFER})
            )
        }
    }

    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }

}