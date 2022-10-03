package com.jcd.telusnavigation.second_flow

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.jcd.telusnavigation.R

class SecondFlowAFragment: Fragment(R.layout.fragment_second_flow_a) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireView().findViewById<Button>(R.id.button_secondFlowA_navigate).setOnClickListener {
            requireView().findNavController().navigate(
                SecondFlowAFragmentDirections.actionSecondFlowAFragmentToSecondFlowBFragment()
            )
        }
    }

}