package com.aditya.realtimedatabasefirebase.ui

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aditya.realtimedatabasefirebase.R
import com.aditya.realtimedatabasefirebase.data.Author
import kotlinx.android.synthetic.main.fragment_authors.*


class AuthorsFragment : Fragment(), RecyclerViewClickListener {

    private lateinit var viewModel: AuthorsViewModel
    private val adapter = AuthorsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel = ViewModelProvider(this).get(AuthorsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_authors, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        adapter.listener = this
        recycler_view_authors.adapter = adapter

        viewModel.fetchAuthors()
        viewModel.getRealTimeUpdates()

        viewModel.authors.observe(viewLifecycleOwner, {
            adapter.setAuthors(it)
        })

        viewModel.author.observe(viewLifecycleOwner, {
            adapter.addAuthor(it)
        })

        button_add.setOnClickListener {
            AddAuthorDialogFragment()
                .show(childFragmentManager, "")
        }
    }

    override fun onRecyclerViewItemClicked(view: View, author: Author) {
        when (view.id) {
            R.id.button_edit -> {
                EditAuthorDialogFragment(author).show(childFragmentManager, "")

            }

            R.id.button_delete -> {
                AlertDialog.Builder(requireContext()).also {
                    it.setTitle("Are you sure you want to delete?")
                    it.setPositiveButton("yes") { _, _ ->
                        viewModel.deleteAuthor(author)
                    }
                }.create().show()
            }
        }

    }
}