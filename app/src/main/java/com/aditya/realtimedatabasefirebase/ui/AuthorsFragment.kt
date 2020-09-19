package com.aditya.realtimedatabasefirebase.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.aditya.realtimedatabasefirebase.R
import kotlinx.android.synthetic.main.fragment_authors.*



class AuthorsFragment : Fragment() {

    private lateinit var viewModel: AuthorsViewModel
    private val adapter = AuthorsAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        viewModel = ViewModelProvider(this).get(AuthorsViewModel::class.java)
        return inflater.inflate(R.layout.fragment_authors,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        recycler_view_authors.adapter = adapter

        viewModel.fetchAuthors()
        viewModel.authors.observe(viewLifecycleOwner, {
            adapter.setAuthors(it)
        })



        button_add.setOnClickListener {
            AddAuthorDialogFragment()
                .show(childFragmentManager,"")
        }
    }

}