package com.example.wattoit.main.ui.log_out

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wattoit.R
import com.example.wattoit.data.SessionManager
import com.example.wattoit.login.MainActivity
import kotlinx.android.synthetic.main.fragment_log_out.*

class LogOutFragment : Fragment() {
    lateinit var sessionManager: SessionManager
    companion object {
        fun newInstance() = LogOutFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_log_out, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()

        sessionManager = SessionManager(requireActivity().applicationContext)
        logOutButton.setOnClickListener {
            sessionManager.deleteAuthToken()

            val intent = Intent(activity, MainActivity::class.java).apply {}
            activity?.startActivity(intent)
        }
    }
}
