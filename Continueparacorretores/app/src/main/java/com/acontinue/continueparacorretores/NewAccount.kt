package com.acontinue.continueparacorretores

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.acontinue.continueparacorretores.LoginActivity.Companion.EMAIL_REGEX
import com.acontinue.continueparacorretores.androidExtensions.hideKeyboard
import com.acontinue.continueparacorretores.models.Corretor
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_account.*

class NewAccount : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("users").child("corretor")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_account)

        hideKeyboard()

        delegate.setLocalNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        FirebaseApp.initializeApp(this)
        mAuth = FirebaseAuth.getInstance()

        emailCreate.setOnEditorActionListener { _, actionId, _ ->
            val emailValue = emailCreate.text.toString()
            if (!emailValue.matches(EMAIL_REGEX.toRegex()))
                invalidEmail.visibility = View.VISIBLE
            else
                invalidEmail.visibility = View.GONE
            true

        }

        passwordConfirmation.setOnEditorActionListener { _, actionId, _ ->
            val passwordValue = passwordConfirmation.text.toString()
            if (passwordValue != passwordCreate.text.toString())
                passwordNotMatch.visibility = View.VISIBLE
            else
                passwordNotMatch.visibility = View.GONE
            true
        }

        send.setOnClickListener {
            when {
                name.text.toString().isBlank() ->
                    Toast.makeText(this, "Nome é obrigatório", Toast.LENGTH_LONG).show()
                emailCreate.text.toString().isBlank() ->
                    Toast.makeText(this, "Email é obrigatório", Toast.LENGTH_LONG).show()
                passwordCreate.text.toString().isBlank() ->
                    Toast.makeText(this, "Senha é obrigatória", Toast.LENGTH_LONG).show()
                passwordConfirmation.text.toString().isBlank() ->
                    Toast.makeText(this, "Confirmação de senha é obrigatória", Toast.LENGTH_LONG).show()
                address.text.toString().isBlank() ->
                    Toast.makeText(this, "Endereço é obrigatório", Toast.LENGTH_LONG).show()
                comission.text.toString().isBlank() ->
                    Toast.makeText(this, "Comissão é obrigatória", Toast.LENGTH_LONG).show()
                insuranceFIrms.text.toString().isBlank() ->
                    Toast.makeText(this, "\"Seguradoras\" é obrigatório", Toast.LENGTH_LONG).show()
                !emailCreate.text.toString().matches(EMAIL_REGEX.toRegex()) ->
                    Toast.makeText(this, "Email inválido", Toast.LENGTH_LONG).show()
                passwordCreate.text.toString() != passwordConfirmation.text.toString() ->
                    Toast.makeText(this, "Senhas são diferentes", Toast.LENGTH_LONG).show()
                susep.text.toString().isBlank() ->
                    Toast.makeText(this, "Inscrição susep é obrigatória", Toast.LENGTH_LONG).show()
                else -> {

                    progressCreate.visibility = View.VISIBLE
                    layout.alpha = 0.5f

                    mAuth.createUserWithEmailAndPassword(
                        emailCreate.text.toString(),
                        passwordCreate.text.toString()).addOnCompleteListener {

                        progressCreate.visibility = View.GONE
                        layout.alpha = 1f

                        if (it.isSuccessful && mAuth.currentUser?.uid != null) {
                            val corretor = Corretor(
                                comission.text.toString().toFloat(),
                                address.text.toString(),
                                susep.text.toString(),
                                mAuth.currentUser?.uid.toString(),
                                name.text.toString(),
                                insuranceFIrms.text.toString()
                                )
                            myRef.push().setValue(corretor)
                            Toast.makeText(this, "Tudo pronto", Toast.LENGTH_SHORT).show()
                            finish()
                        } else {
                            Toast.makeText(this, "Erro ao efetuar o cadastro", Toast.LENGTH_SHORT).show()
                            println(it.exception)
                        }
                    }
                }
            }
        }
    }

}