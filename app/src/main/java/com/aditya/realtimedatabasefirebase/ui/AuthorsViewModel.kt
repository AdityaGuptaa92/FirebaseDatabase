package com.aditya.realtimedatabasefirebase.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.aditya.realtimedatabasefirebase.data.Author
import com.aditya.realtimedatabasefirebase.data.NODE_AUTHORS
import com.google.firebase.database.*

class AuthorsViewModel : ViewModel() {

    private val dbAuthors = FirebaseDatabase.getInstance().getReference(NODE_AUTHORS)

    private val _authors = MutableLiveData<List<Author>>()
    val authors: LiveData<List<Author>>
        get() = _authors

    private val _author = MutableLiveData<Author>()
    val author: LiveData<Author>
        get() = _author


    private val _result = MutableLiveData<Exception?>()
    val result: LiveData<Exception?>
        get() = _result

    fun addAuthor(author: Author) {
        author.id = dbAuthors.push().key
        dbAuthors.child(author.id!!).setValue(author)
            .addOnCompleteListener {
                if (it.isSuccessful) {
                    _result.value = null
                } else {
                    _result.value = it.exception
                }
            }
    }

    private val childEventListener = object : ChildEventListener {
        override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {

            val author = snapshot.getValue(Author::class.java)
            author?.id = snapshot.key
            _author.value = author

        }

        override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onChildRemoved(snapshot: DataSnapshot) {

        }

        override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

        }

        override fun onCancelled(error: DatabaseError) {

        }

    }

    fun getRealTimeUpdates() {
        dbAuthors.addChildEventListener(childEventListener)
    }

    fun fetchAuthors() {
        dbAuthors.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {

                    val authors = mutableListOf<Author>()
                    for (authorSnapshot in snapshot.children) {
                        val author = authorSnapshot.getValue(Author::class.java)
                        author?.id = authorSnapshot.key
                        author?.let {
                            authors.add(it)
                        }
                        _authors.value = authors
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }


    override fun onCleared() {
        super.onCleared()
        dbAuthors.removeEventListener(childEventListener)
    }

}