package com.aditya.realtimedatabasefirebase.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.realtimedatabasefirebase.data.Author
import com.aditya.realtimedatabasefirebase.data.NODE_AUTHORS
import com.google.firebase.FirebaseApp
import com.google.firebase.database.FirebaseDatabase

class AuthorsViewModel : ViewModel() {

    private val _result = MutableLiveData<Exception?>()
    val result:LiveData<Exception?>
    get() = _result

    fun addAuthor(author: Author) {
        val dbAuthors = FirebaseDatabase.getInstance().getReference(NODE_AUTHORS)
        author.id = dbAuthors.push().key
        dbAuthors.child(author.id!!).setValue(author)
            .addOnCompleteListener {
                if(it.isSuccessful){
                    _result.value = null
                }else{
                    _result.value = it.exception
                }
            }

    }
}