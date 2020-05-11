package com.example.wattoit.login

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.example.wattoit.R

class MainActivity : AppCompatActivity(),
    LoginCommunacator {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        setSupportActionBar(toolbar)
//
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }

        val loginFragment = LoginActivity()
        loginFragment.communicate = this

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainer, loginFragment)
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
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
