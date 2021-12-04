package com.example.nasa.ui.section

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.nasa.service.mars.MarsApi
import com.example.nasa.service.mars.PhotosMarsListVO
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MarsViewModel @Inject constructor(private val marsAPi: MarsApi) : ViewModel() {

    val composite = CompositeDisposable()

    val recyclerListData: MutableLiveData<PhotosMarsListVO>

    init {
        recyclerListData = MutableLiveData()
    }

    fun getRecyclerListObserver(roversName:String,
                                solNumber:String)
    : MutableLiveData<PhotosMarsListVO> {
        loadMarsPhotos(roversName,solNumber)
        return recyclerListData
    }

    fun loadMarsPhotos(roversName:String,
                       solNumber:String) {
        viewModelScope.launch {
            composite.add(
                marsAPi.getPhotosBySol(roversName, solNumber)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .distinct()
                    .subscribe({ res ->
                        recyclerListData.postValue(res)
                    }, { error ->
                        error.printStackTrace()
                    })
            )
        }
    }

    override fun onCleared() {
        composite.dispose()
        super.onCleared()
    }
}