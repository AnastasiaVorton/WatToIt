package com.example.wattoit.main.ui.home_second

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.example.wattoit.R

class HomeSecondFragment : Fragment() {
    companion object {
        fun newInstance() =
            HomeSecondFragment()
    }

    private lateinit var viewModel: HomeSecondViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home_second, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeSecondViewModel::class.java)
    }
}
