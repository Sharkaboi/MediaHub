package com.cybershark.mediahub.ui.modules.stats.views

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.cybershark.mediahub.R
import com.cybershark.mediahub.ui.modules.stats.viewmodels.StatsViewModel

class StatsFragment : Fragment() {

    private val statsViewModel by lazy { ViewModelProvider(this).get(StatsViewModel::class.java) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_stats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }
}
