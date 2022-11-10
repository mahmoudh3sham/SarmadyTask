package com.sarmady.task.ui.home

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.sarmady.task.data.DataRepository

class HomeViewModel @ViewModelInject constructor(repository: DataRepository): ViewModel()  {

    val results = repository.getPhotosResults().cachedIn(viewModelScope)

}