package com.example.wattoit.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.wattoit.R

class MainActivity : AppCompatActivity(), LoginCommunacator {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val loginFragment = LoginActivity()
        loginFragment.communicate = this

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, loginFragment)
            .addToBackStack(null)
            .commit()
    }


    override fun loginButtonsHandler(buttonType: String) {
        val nextFragment: Fragment?

        if (buttonType == "signIn") {
            nextFragment = SignInFragment()
            nextFragment.communicate = this
        } else {
            nextFragment = SignUpFragment()
            nextFragment.communicate = this
        }

        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, nextFragment)
            .addToBackStack(null)
            .commit()
    }
}
