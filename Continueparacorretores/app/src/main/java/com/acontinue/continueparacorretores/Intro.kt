package com.acontinue.continueparacorretores

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.github.paolorotolo.appintro.AppIntro
import com.github.paolorotolo.appintro.AppIntroFragment
import com.github.paolorotolo.appintro.model.SliderPage

class Intro : AppIntro() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()

        getSharedPreferences(resources.getString(R.string.app_name), Context.MODE_PRIVATE).also {
            if (!it.getBoolean("firstStart", true)) openLogin()
            else it.edit().putBoolean("firstStart", false).apply()
        }

        val sliderPage1 = SliderPage().apply {
            title = "Oi"
            description = "Estamos preparando tudo para você"
            bgColor = ContextCompat.getColor(this@Intro, R.color.black)
        }

        val sliderPage2 = SliderPage().apply {
            title = "Mais um instante"
            description = "Fazendo mais coisas"
            bgColor = ContextCompat.getColor(this@Intro, R.color.yellow)
            titleColor = ContextCompat.getColor(this@Intro, R.color.black)
            descColor = ContextCompat.getColor(this@Intro, R.color.black)
        }
        addSlide(AppIntroFragment.newInstance(sliderPage1))
        addSlide(AppIntroFragment.newInstance(sliderPage2))
    }

    override fun onDonePressed(currentFragment: Fragment?) {
        super.onDonePressed(currentFragment)
        openLogin()
    }

    private fun openLogin() {
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }
}
