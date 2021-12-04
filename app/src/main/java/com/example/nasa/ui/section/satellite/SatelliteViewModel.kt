package com.example.nasa.ui.section.satellite

import android.annotation.SuppressLint
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasa.service.satellite.SatelliteApi
import com.example.nasa.service.satellite.SattelliteVO
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import retrofit2.Response
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class SatelliteViewModel @Inject constructor(private val satelliteApi: SatelliteApi): ViewModel() {

    val satelliteViewModel: MutableLiveData<SattelliteVO>
    val compositeDisposable = CompositeDisposable()

    init {
        satelliteViewModel = MutableLiveData()
    }

    fun getSatelliteViewModel(idSatellite: String):
            MutableLiveData<SattelliteVO>{

        loadDataUpdate(idSatellite)
        return satelliteViewModel

    }

    fun loadDataUpdate(idSatellite: String){
        loadData(idSatellite)
        viewModelScope.launch{
            compositeDisposable.add(
                Observable.interval(10000,TimeUnit.MILLISECONDS)
                    .subscribe {
                     loadData(idSatellite)
                    }
            )
        }
    }

    @SuppressLint("CheckResult")
    fun loadData(idSatellite: String){
                satelliteApi.getSattellitePosition(idSatellite)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                            result ->
                        satelliteViewModel.postValue(result)
                    },{
                            error -> error.printStackTrace()
                    })
    }

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}