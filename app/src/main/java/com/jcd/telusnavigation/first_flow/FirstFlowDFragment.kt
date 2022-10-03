package com.jcd.telusnavigation.first_flow

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.jcd.telusnavigation.R
import com.jcd.telusnavigation.second_flow.SecondFlowActivity

private const val LOGGER_TAG = "FirstFlow-FragmentD"

class FirstFlowDFragment : Fragment(R.layout.fragment_first_flow_d) {

    private val args: FirstFlowDFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d(LOGGER_TAG, "onViewCreated")
        requireView().findViewById<Button>(R.id.button_firstFlowD_secondFlow).setOnClickListener {
            // Goes to second flow
            startActivity(Intent(requireContext(), SecondFlowActivity::class.java))
        }
        requireView().findViewById<Button>(R.id.button_firstFlowD_backToA).setOnClickListener {
            // Gets back to Fragment A, removing all the backstack entries between A and D
            requireView().findNavController().navigate(
                FirstFlowDFragmentDirections.actionFirstFlowDFragmentToFirstFlowAFragment()
            )
        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            // If argument is true, we override the "back" action and we navigate to fragment C
            // giving the impression to the user that C was on back stack
            if (args.fromNotification) {
                requireView().findNavController().navigate(
                    FirstFlowDFragmentDirections.actionFirstFlowDFragmentBackToFirstFlowCFragment(
                        fromNotification = true
                    )
                )
                isEnabled = true
            } else {
                // Follows the default behavior. This means the user reached this fragment
                // using the normal flow "A -> B -> C -> D". This will pop "C" from backstack
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