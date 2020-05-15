package com.example.wattoit.login

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.wattoit.R
import com.example.wattoit.data.SessionManager
import com.example.wattoit.login.data.LoginResponse
import com.example.wattoit.login.data.RestClient
import com.example.wattoit.main.FrontActivity
import com.example.wattoit.utils.isOkResponseCode
import com.github.ajalt.timberkt.Timber
import kotlinx.android.synthetic.main.sign_in_fragment.*
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback

class SignInFragment : Fragment() {
    var communicate: LoginCommunacator? = null
    lateinit var restClient: RestClient
    lateinit var sessionManager: SessionManager

    companion object {
        fun newInstance() = LoginActivity()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        restClient = RestClient()
        sessionManager = SessionManager(requireActivity().applicationContext)

        return inflater.inflate(R.layout.sign_in_fragment, container, false)
    }

    override fun onStart() {
        super.onStart()

        signInButton.setOnClickListener {
            val credentials = jsonLogin(login.text.toString(), password.text.toString())

            restClient.getApiService(requireActivity().applicationContext).login(credentials)
                .enqueue(
                    object : Callback<LoginResponse> {
                        override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                            Timber.d{"Error while login"}
                            Toast.makeText(
                                activity,
                                "Error", Toast.LENGTH_LONG
                            ).show()
                        }

                        override fun onResponse(
                            call: Call<LoginResponse>,
                            response: retrofit2.Response<LoginResponse>
                        ) {
                            sessionManager.saveAuthToken(response.body()!!.token)

                            if (isOkResponseCode(response.code())) {
                                val intent = Intent(activity, FrontActivity::class.java).apply {}
                                activity?.startActivity(intent)
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


    private fun jsonLogin(username: String, password: String) =
        createJsonRequestBody("username" to username, "password" to password)
}
