package com.cybershark.mediahub.ui.modules.manga.views.addmanga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.modules.manga.adapters.AddMangaAdapter
import com.cybershark.mediahub.ui.modules.manga.viewmodels.MangaViewModel

class AddMangaDialog : DialogFragment() {

    private val mangaViewModel by lazy { ViewModelProvider(this).get(MangaViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        val inflatedView = inflater.inflate(R.layout.manga_search_dialog, container,false)

        val adapter = AddMangaAdapter()

        inflatedView.findViewById<RecyclerView>(R.id.rvMangaSearch).apply {
            this.adapter = adapter
            layoutManager = LinearLayoutManager(context)
            itemAnimator = DefaultItemAnimator()
        }

        mangaViewModel.addMangaSearchList.observe(viewLifecycleOwner, Observer {
            adapter.setAdapterList(it)
        })

        inflatedView.findViewById<ImageButton>(R.id.ibSearch).setOnClickListener {
            mangaViewModel.getDummySearchList(inflatedView.findViewById<EditText>(R.id.etMangaSearch).text.toString())
        }
        return inflatedView
    }
}