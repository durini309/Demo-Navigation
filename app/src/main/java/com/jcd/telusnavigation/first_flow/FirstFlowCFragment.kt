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

private const val LOGGER_TAG = "FirstFlow-FragmentC"

class FirstFlowCFragment : Fragment(R.layout.fragment_first_flow_c) {

    private val args: FirstFlowCFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(LOGGER_TAG, "onViewCreated")
        requireView().findViewById<Button>(R.id.button_firstFlowC_next).setOnClickListener {
            // Redirects user to next fragment, fragment D
            requireView().findNavController().navigate(
                FirstFlowCFragmentDirections.actionFirstFlowCFragmentToFirstFlowDFragment()
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // If argument is true, we override the "back" action and we navigate to fragment B
            // giving the impression to the user that B was on back stack
            if (args.fromNotification) {
                requireView().findNavController().navigate(
                    FirstFlowCFragmentDirections.actionFirstFlowCFragmentBackToFirstFlowBFragment(
                    fromNotification = true
                    )
                )
                isEnabled = true
            } else {
                // Follows the default behavior. This means the user reached this fragment
                // using the normal flow "A -> B -> C". This will pop "B" from backstack
                isEnabled = false
                requireActivity().onBackPressed()
            }
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