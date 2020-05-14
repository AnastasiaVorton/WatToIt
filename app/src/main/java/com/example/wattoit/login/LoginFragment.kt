package com.example.wattoit.login

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.wattoit.R
import com.example.wattoit.interfaces.Communicator
import kotlinx.android.synthetic.main.activity_login.*

class LoginFragment : Fragment() {
    var communicate: Communicator? = null

    companion object {
        fun newInstance() = LoginFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.activity_login, container, false)
    }

    override fun onStart() {
        super.onStart()

        toSignInButton.setOnClickListener {
            communicate?.buttonsHandler("signIn")
        }

        toSignUpButton.setOnClickListener {
            communicate?.buttonsHandler("signUp")
        }
    }
}
