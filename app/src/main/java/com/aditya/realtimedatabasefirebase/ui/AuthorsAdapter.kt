package com.aditya.realtimedatabasefirebase.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.aditya.realtimedatabasefirebase.R
import com.aditya.realtimedatabasefirebase.data.Author
import kotlinx.android.synthetic.main.recycler_view_author.view.*


class AuthorsAdapter : RecyclerView.Adapter<AuthorsAdapter.AuthorsViewHolder>() {

    private var authors = mutableListOf<Author>()

    class AuthorsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorsViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_author, parent, false)
        return AuthorsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AuthorsViewHolder, position: Int) {
        holder.view.text_view_name.text = authors[position].name
    }

    override fun getItemCount(): Int = authors.size

    fun setAuthors(authors: List<Author>) {
        this.authors = authors as MutableList<Author>
        notifyDataSetChanged()
    }
}