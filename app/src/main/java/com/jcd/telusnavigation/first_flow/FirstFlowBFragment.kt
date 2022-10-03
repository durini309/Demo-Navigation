package com.jcd.telusnavigation.first_flow

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.jcd.telusnavigation.R

private const val LOGGER_TAG = "FirstFlow-FragmentB"

class FirstFlowBFragment : Fragment(R.layout.fragment_first_flow_b) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(LOGGER_TAG, "onViewCreated")
        view.findViewById<Button>(R.id.button_firstFlowB_navigate).setOnClickListener {
            // Redirects user to next fragment, fragment C
            requireView().findNavController().navigate(
                FirstFlowBFragmentDirections.actionFirstFlowBFragmentToFirstFlowCFragment()
            )
        }

        /**
         * It's not required to override the "Back" behaviour even though the user could have
         * reached fragment B by an action from C. This is because, every time a Fragment
         * from a navigation graph (that is not graph's home) is opened via an implicit or
         * explicit intent, the home fragment from that nav graph will be opened. So, basically
         * if the user reaches fragment D by a deeplink, the backstack will include
         * fragment "A".
         */
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