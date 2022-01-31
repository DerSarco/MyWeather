package com.globant.carlosmunoz.myweather.ui.fragments.settings

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.globant.carlosmunoz.myweather.R
import com.globant.carlosmunoz.myweather.databinding.FragmentSettingsBinding
import com.globant.carlosmunoz.myweather.userPrefs

class SettingsFragment : Fragment() {

    private lateinit var mBinding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        mBinding = FragmentSettingsBinding.inflate(inflater, container, false)
        checkCurrentPreference()
        setupRadioButtons()
        return mBinding.root
    }

    private fun checkCurrentPreference() {
        when (userPrefs.unitPref) {
            "standard" -> mBinding.rbStandard.isChecked = true
            "metric" -> mBinding.rbMetric.isChecked = true
            "imperial" -> mBinding.rbImperial.isChecked = true
        }
    }

    private fun setupRadioButtons() {
        mBinding.radioGroup.setOnCheckedChangeListener { radioGroup, _ ->
            val selectedOption = radioGroup.checkedRadioButtonId
            saveUserPref(selectedOption)
        }
    }

    private fun saveUserPref(selectedOption: Int) {
        when (selectedOption) {
            R.id.rb_standard -> userPrefs.unitPref = "standard"
            R.id.rb_metric -> userPrefs.unitPref = "metric"
            R.id.rb_imperial -> userPrefs.unitPref = "imperial"
        }
        Toast.makeText(
            requireContext(),
            "Metric saved!, Current: ${userPrefs.unitPref}",
            Toast.LENGTH_SHORT
        ).show()
    }

}