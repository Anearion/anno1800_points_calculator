package com.anesoft.anno1800influencecalculator.base

import androidx.lifecycle.ViewModel

abstract class BaseViewModel :  ViewModel() {

    open fun onCreate() {}

    open fun onResume(){}

}