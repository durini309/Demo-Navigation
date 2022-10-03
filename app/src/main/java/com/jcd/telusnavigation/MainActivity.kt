package com.jcd.telusnavigation

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.jcd.telusnavigation.first_flow.FirstFlowActivity

class MainActivity : AppCompatActivity() {

    private lateinit var buttonSendNotificationImplicit: Button
    private lateinit var buttonOpenFirstFlow: Button
    private lateinit var buttonSendNotificationExplicit: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        buttonSendNotificationImplicit = findViewById(R.id.button_notificationFirstFlowExplicit)
        buttonSendNotificationExplicit = findViewById(R.id.button_notificationFirstFlowImplicit)
        buttonOpenFirstFlow = findViewById(R.id.button_openFirstFlow)
        setListeners()
    }

    private fun setListeners() {
        buttonSendNotificationImplicit.setOnClickListener {
            showFirstFlowNotificationImplicit()
        }

        buttonSendNotificationExplicit.setOnClickListener {
            showFirstFlowNotificationExplicit()
        }

        buttonOpenFirstFlow.setOnClickListener {
            startActivity(Intent(this, FirstFlowActivity::class.java))
        }
    }



}