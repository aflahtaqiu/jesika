package id.koridor50.jesika.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.TextUtils
import android.view.animation.AnimationUtils
import id.koridor50.jesika.R
import android.content.Intent
import android.util.Log

import id.koridor50.jesika.common.PrefKey
import id.koridor50.jesika.utils.getPrefInt
import kotlinx.android.synthetic.main.activity_splash_screen.*

class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val animFadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in)

        splashScreenLayout.startAnimation(animFadeIn)
    }

    override fun onStart() {
        super.onStart()

        Handler().postDelayed(Runnable {
            val cls = when(!(getPrefInt(PrefKey.USERIDPREFKEY) == 0 || getPrefInt(PrefKey.USERIDPREFKEY) == -1)) {
                true -> MainActivity::class.java
                false -> AuthActivity::class.java
            }

            val intent = Intent(this, cls)
            startActivity(intent)
            finish()

        }, 3000)
    }
}