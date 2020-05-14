package com.example.wattoit.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wattoit.R

class AllergyFragment : Fragment() {
    companion object {
        fun newInstance() = AllergyFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.allergy_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()
    }
}
