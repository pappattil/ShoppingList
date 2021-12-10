package com.example.shoppinglist

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import kotlinx.android.synthetic.main.activity_splash.*

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val startmain= Intent(this, ScrollingActivity::class.java)
        val anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim)


        imageView2.startAnimation(anim)
        textView.startAnimation(anim)

        anim.setAnimationListener(object: Animation.AnimationListener{
            override fun onAnimationStart(animation: Animation?) { }
            override fun onAnimationEnd(animation: Animation?) {

                startActivity(startmain)
                finish()
            }
            override fun onAnimationRepeat(animation: Animation?) { }
        })
    }
}