package com.example.fakestore.core.presentation.base

import androidx.lifecycle.ViewModel
import javax.inject.Inject

open class BaseViewModel @Inject constructor() : ViewModel() {


//    fun changeLocal(lng: LNG) {
//        repository.changeLocal(lng)
//    }
//
//    fun getLocal(): LNG = repository.getLocal()
//
//    fun getLocalizationByKey(keys: List<String>, lng: LNG): LiveData<Map<String, String>> =
//        liveData(Dispatchers.IO + errorHandle()) {
//            val localizationResponse = repository.getScreenLocalization(keys)
//            when (lng) {
//                LNG.AR -> {
//                    emit(localizationResponse.mapValues {
//                        it.value.ABBR_AR
//                    })
//                }
//                LNG.EN -> {
//                    emit(localizationResponse.mapValues {
//                        it.value.ABBR
//                    })
//                }
//
//            }
//
//
//        }

    override fun onCleared() {
        super.onCleared()

    }
}