package com.cybershark.mediahub.ui.modules.manga.views.addmanga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cybershark.mediahub.R
import com.cybershark.mediahub.databinding.MangaSearchDialogBinding
import com.cybershark.mediahub.ui.modules.manga.adapters.AddMangaAdapter
import com.cybershark.mediahub.ui.modules.manga.viewmodels.MangaViewModel

class AddMangaDialog : DialogFragment() {

    private lateinit var binding : MangaSearchDialogBinding
    private val mangaViewModel by lazy { ViewModelProvider(this).get(MangaViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = MangaSearchDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchListeners()
    }

    private fun setupSearchListeners() {
        binding.ibSearch.setOnClickListener {
            mangaViewModel.getDummySearchList(binding.etMangaSearch.text.toString())
        }
        binding.etMangaSearch.doOnTextChanged { text, start, before, count ->
            mangaViewModel.getDummySearchList(text?.toString() ?: "")
        }
    }

    private fun setupRecyclerView() {
        val adapter = AddMangaAdapter()
        binding.rvMangaSearch.apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }
        mangaViewModel.addMangaSearchList.observe(viewLifecycleOwner, Observer {
            adapter.setAdapterList(it)
        })
    }
}