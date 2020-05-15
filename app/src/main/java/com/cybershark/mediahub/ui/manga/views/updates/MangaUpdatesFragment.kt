package com.cybershark.mediahub.ui.manga.views.updates

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.manga.adapters.MangaUpdatesAdapter
import com.cybershark.mediahub.ui.manga.viewmodels.MangaViewModel
import kotlinx.android.synthetic.main.fragment_manga_updates.*

class MangaUpdatesFragment : Fragment() {

    private lateinit var mangaViewModel: MangaViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        mangaViewModel=ViewModelProvider(this).get(MangaViewModel::class.java)
        return inflater.inflate(R.layout.fragment_manga_updates, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvMangaUpdates.layoutManager=LinearLayoutManager(context)
        rvMangaUpdates.addItemDecoration(DividerItemDecoration(context,LinearLayoutManager.VERTICAL))
        val adapter=MangaUpdatesAdapter()
        mangaViewModel.dummyData.observe(viewLifecycleOwner, Observer {
            adapter.setAdapterList(it)
        })
        rvMangaUpdates.adapter=adapter
    }
}
