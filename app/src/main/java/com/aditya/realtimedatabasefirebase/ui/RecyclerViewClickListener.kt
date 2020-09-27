package com.aditya.realtimedatabasefirebase.ui

import android.view.View
import com.aditya.realtimedatabasefirebase.data.Author

interface RecyclerViewClickListener {

    fun onRecyclerViewItemClicked(view: View, author: Author)
}