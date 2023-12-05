package com.alqo.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alqo.entities.Post
import com.alqo.repository.PostProfileRepositoryImp
import com.alqo.repository.PostRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ListPostProfileViewModel: ViewModel() {
    private var _data: MutableLiveData<List<Post>> = MutableLiveData()
    val data: LiveData<List<Post>> = _data

    private val repository: PostRepository = PostProfileRepositoryImp()

    fun obtainData() {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val response =
                    withContext(Dispatchers.IO) {
                        repository.obtainPost()
                    }
                response.let {
                    _data.value = it
                }
            } catch (e: Exception) {
                e.message
            }
        }
    }
}