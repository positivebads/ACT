package com.automatedcontacttracing.act

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.automatedcontacttracing.act.base.presentation.BaseActivity
import kotlinx.coroutines.delay

class SplashActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        lifecycleScope.launchWhenStarted {
            delay(3000)
            startActivity(Intent(this@SplashActivity, MainActivity::class.java))
            this@SplashActivity.finish()
        }
    }
}