package com.jcd.telusnavigation.first_flow

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.NavDeepLinkBuilder
import androidx.navigation.findNavController
import com.jcd.telusnavigation.R

private const val LOGGER_TAG = "FirstFlow-FragmentA"

class FirstFlowAFragment : Fragment(R.layout.fragment_first_flow_a) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(LOGGER_TAG, "onViewCreated")
        view.findViewById<Button>(R.id.button_firstFlowA_navigate).setOnClickListener {
            // Redirects user to next fragment, fragment B
            requireView().findNavController().navigate(
                FirstFlowAFragmentDirections.actionFirstFlowAFragmentToFirstFlowBFragment()
            )
        }
    }

    override fun onAttach(context: Context) {
        Log.d(LOGGER_TAG, "onAttach")
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(LOGGER_TAG, "onCreate")
        super.onCreate(savedInstanceState)
    }

    override fun onStart() {
        Log.d(LOGGER_TAG, "onStart")
        super.onStart()
    }

    override fun onResume() {
        Log.d(LOGGER_TAG, "onResume")
        super.onResume()
    }

    override fun onPause() {
        Log.d(LOGGER_TAG, "onPause")
        super.onPause()
    }

    override fun onStop() {
        Log.d(LOGGER_TAG, "onStop")
        super.onStop()
    }

    override fun onDestroyView() {
        Log.d(LOGGER_TAG, "onDestroyView")
        super.onDestroyView()
    }

    override fun onDetach() {
        Log.d(LOGGER_TAG, "onDetach")
        super.onDetach()
    }
}