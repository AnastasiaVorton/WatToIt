package com.example.wattoit

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.wattoit.data.DietBody
import com.example.wattoit.data.RegistrationResponse
import com.example.wattoit.data.RestClient
import kotlinx.android.synthetic.main.fragment_preferences.*
import retrofit2.Call
import retrofit2.Callback


/**
 * A simple [Fragment] subclass.
 * Use the [PreferencesFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class PreferencesFragment : Fragment() {
    private val restClient = RestClient()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_preferences, container, false)
    }

    override fun onStart() {
        super.onStart()

        savePreferencesButton.setOnClickListener {
            val selectedDietId: Int = radioGroup.checkedRadioButtonId
            val dietRadio: RadioButton = activity!!.findViewById(selectedDietId)
            val dietText = dietRadio.text.toString().toLowerCase()

            restClient.getApiService(activity!!.applicationContext).setDiet(DietBody(dietText)).enqueue(
                object: Callback<RegistrationResponse> {
                    override fun onFailure(call: Call<RegistrationResponse>, t: Throwable) {
                        Toast.makeText(activity,
                            "Error", Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(
                        call: Call<RegistrationResponse>,
                        response: retrofit2.Response<RegistrationResponse>
                    ) {
                        if (response.code() == 200) {
                            val intent = Intent(activity, SearchActivity::class.java).apply {}
                            activity?.startActivity(intent)

                        } else if (response.code() == 400) {
                            Toast.makeText(activity,
                                response.message(), Toast.LENGTH_LONG).show()
                        }
                    }
                }
            )
        }

    }
}
