package com.dash.projects.android.digiwave.views.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.dash.projects.android.digiwave.R
import com.dash.projects.android.digiwave.views.home.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycleScope.launch {
            try {
                delay(resources.getInteger(R.integer.milliSecs_3000).toLong())
            } catch (e: InterruptedException) {
                Log.d(TAG, e.printStackTrace().toString())
            } finally {
                startActivity(Intent(this@SplashScreenActivity, MainActivity::class.java))
                finish()
            }
        }
    }

    companion object {
        private const val TAG = "SplashScreenActivity"
    }
}