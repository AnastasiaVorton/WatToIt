package com.example.wattoit.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.wattoit.R
import com.example.wattoit.data.RegistrationResponse
import com.example.wattoit.data.RestClient
import com.example.wattoit.main.ui.search.SearchFragment
import com.example.wattoit.utils.isOkResponseCode
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.sign_up_fragment.*
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class SignUpFragment : Fragment() {
    var communicate: LoginCommunacator? = null
    lateinit var restClient: RestClient

    companion object {
        fun newInstance() = LoginActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        restClient = RestClient()

        return inflater.inflate(R.layout.sign_up_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()

        signUpButton.setOnClickListener {
            if (password.text.toString() != passwordRepeat.text.toString()) {
                Toast.makeText(
                    activity,
                    "Passwords don't match", Toast.LENGTH_LONG
                ).show()
                return@setOnClickListener
            }

            val credentials =
                jsonLogin(login.text.toString(), email.text.toString(), password.text.toString())

            restClient.getApiService(requireActivity().applicationContext).register(credentials)
                .enqueue(
                    object : Callback<RegistrationResponse> {
                        override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                            Timber.d{"Registration failed"}
                            Toast.makeText(
                                activity,
                                "Error", Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<RegistrationResponse>,
                            response: retrofit2.Response<RegistrationResponse>
                        ) {
                            if (isOkResponseCode(response.code())) {
                                requireActivity().supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragmentContainer, PreferencesFragment())
                                    .addToBackStack(null)
                                    .commit()
                            } else {
                                Toast.makeText(
                                    activity,
                                    response.message(), Toast.LENGTH_LONG
                                ).show()
                            }
                        }
                    }
                )
        }
    }

    private fun createJsonRequestBody(vararg params: Pair<String, String>) =
        RequestBody.create(
            okhttp3.MediaType.parse("application/json; charset=utf-8"),
            JSONObject(mapOf(*params)).toString()
        )


    private fun jsonLogin(username: String, email: String, password: String) =
        createJsonRequestBody("username" to username, "email" to email, "password" to password)
}
