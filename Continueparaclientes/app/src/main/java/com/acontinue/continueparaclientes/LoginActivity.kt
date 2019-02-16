package com.acontinue.continueparaclientes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import com.google.firebase.FirebaseApp
import kotlinx.android.synthetic.main.activity_login.*
import com.google.firebase.auth.FirebaseAuth
import androidx.appcompat.app.AppCompatDelegate
import com.acontinue.continueparaclientes.androidExtensions.hideKeyboard


class LoginActivity : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        supportActionBar?.hide()
        delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()

        if (mAuth.currentUser != null) {
            startActivity(Intent(this, DashboardActivity::class.java))
            finish()
        }

        supportActionBar?.apply {
            setHomeButtonEnabled(false)
            setDisplayHomeAsUpEnabled(false)
            setDisplayShowHomeEnabled(false)
        }

        login.setOnClickListener { doLogin() }

        noAccount.setOnClickListener { startActivity(Intent(this, NewAccount::class.java)) }
        
        password.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                doLogin()
                true
            }
            else false
        }
    }

    private fun doLogin() {
        hideKeyboard()

        val emailValue = email.text.toString()
        val passwordValue = password.text.toString()

        return when {
            emailValue.isEmpty() ->
                Toast.makeText(this, "Email é um campo obrigatório", Toast.LENGTH_SHORT).show()
            !emailValue.matches(EMAIL_REGEX.toRegex()) ->
                Toast.makeText(this, "Email inválido", Toast.LENGTH_SHORT).show()
            passwordValue.isEmpty() ->
                Toast.makeText(this, "Senha é um campo obrigatório", Toast.LENGTH_SHORT).show()
            else -> {
                linearLayout.alpha = 0.5f
                progress.visibility = View.VISIBLE

                mAuth.signInWithEmailAndPassword(
                    email.text.toString(),
                    password.text.toString()
                ).addOnCompleteListener {
                    linearLayout.alpha = 1f
                    progress.visibility = View.GONE

                    if (it.isSuccessful)
                        startActivity(Intent(this, DashboardActivity::class.java))
                    else
                        Toast.makeText(this, "Email e/ou senha incorretos", Toast.LENGTH_SHORT).show()
                }
                Unit
            }
        }
    }

    companion object {
        const val EMAIL_REGEX = "(?:[a-z0-9!#\$%&'*+/=?^_`{|}~-]+(?:\\.[a-z0-9!#\$%&'*+/=?^_`{|}~-]+)*|\"(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21\\x23-\\x5b\\x5d-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])*\")@(?:(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?|\\[(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?|[a-z0-9-]*[a-z0-9]:(?:[\\x01-\\x08\\x0b\\x0c\\x0e-\\x1f\\x21-\\x5a\\x53-\\x7f]|\\\\[\\x01-\\x09\\x0b\\x0c\\x0e-\\x7f])+)\\])"
    }
}
