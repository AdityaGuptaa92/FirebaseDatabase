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

    var listener: RecyclerViewClickListener? = null

    class AuthorsViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AuthorsViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.recycler_view_author, parent, false)
        return AuthorsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: AuthorsViewHolder, position: Int) {
        holder.view.text_view_name.text = authors[position].name
        holder.view.button_edit.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, authors[position])
        }
        holder.view.button_delete.setOnClickListener {
            listener?.onRecyclerViewItemClicked(it, authors[position])
        }
    }

    override fun getItemCount(): Int = authors.size

    fun setAuthors(authors: List<Author>) {
        this.authors = authors as MutableList<Author>
        notifyDataSetChanged()
    }

    fun addAuthor(author: Author) {
        if (!authors.contains(author)) {
            authors.add(author)

        } else {
            val index = authors.indexOf(author)
            if (author.isDeleted) {
                authors.removeAt(index)
            } else {
                authors[index] = author
            }
        }
        notifyDataSetChanged()
    }
}