package com.cybershark.mediahub.ui.manga

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.cybershark.mediahub.R

class MangaFragment : Fragment() {

    private lateinit var mangaViewModel: MangaViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        mangaViewModel =  ViewModelProviders.of(this).get(MangaViewModel::class.java)
        val rootView = inflater.inflate(R.layout.fragment_manga, container, false)
        val textView: TextView = rootView.findViewById(R.id.text_manga)
        mangaViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return rootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
