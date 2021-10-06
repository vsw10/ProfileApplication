package com.alive.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.rxjava3.annotations.NonNull
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.PublishSubject
import java.util.*

class FirstFragmentViewModel : ViewModel() {

    private val storeValuesSubject = PublishSubject.create<Triple<String,String,String>>()
    val storeValuesObservable: @NonNull Observable<Triple<String, String, String>>? get() = storeValuesSubject.share()

    var data = MutableLiveData<Triple<String,String,String>>()

    fun saveEnteredValues(firstName:String,
                          lastName:String,
                            dob :String) {
        data.value = Triple(firstName,
            lastName,
            dob)
    //    storeValuesSubject.onNext(Triple(firstName,lastName,dob))

    }



}