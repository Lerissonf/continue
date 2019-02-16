package com.acontinue.continueparaclientes

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.acontinue.continueparaclientes.LoginActivity.Companion.CPF_REGEX
import com.acontinue.continueparaclientes.LoginActivity.Companion.EMAIL_REGEX
import com.acontinue.continueparaclientes.androidExtensions.hideKeyboard
import com.acontinue.continueparaclientes.models.Cliente
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_new_account.*

class NewAccount : AppCompatActivity() {

    private lateinit var mAuth: FirebaseAuth
    val database = FirebaseDatabase.getInstance()
    val myRef = database.getReference("users").child("cliente")

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

        cpfCreate.setOnEditorActionListener { _, actionId, _ ->
            val emailValue = cpfCreate.text.toString()
            if (!emailValue.matches(CPF_REGEX.toRegex()))
                invalidCpf.visibility = View.VISIBLE
            else
                invalidCpf.visibility = View.GONE
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
                idadeCreate.text.toString().isBlank() ->
                    Toast.makeText(this, "Idade é obrigatório", Toast.LENGTH_LONG).show()
                telefoneCreate.text.toString().isBlank() ->
                    Toast.makeText(this, "Telefone é obrigatório", Toast.LENGTH_LONG).show()
                sexoCreate.text.toString().isBlank() ->
                    Toast.makeText(this, "Sexo é obrigatório", Toast.LENGTH_LONG).show()
                name.text.toString().isBlank() ->
                    Toast.makeText(this, "Nome é obrigatório", Toast.LENGTH_LONG).show()
                emailCreate.text.toString().isBlank() ->
                    Toast.makeText(this, "Email é obrigatório", Toast.LENGTH_LONG).show()
                cpfCreate.text.toString().isBlank() ->
                    Toast.makeText(this, "Idade é obrigatório", Toast.LENGTH_LONG).show()
                passwordCreate.text.toString().isBlank() ->
                    Toast.makeText(this, "Senha é obrigatória", Toast.LENGTH_LONG).show()
                passwordConfirmation.text.toString().isBlank() ->
                    Toast.makeText(this, "Confirmação de senha é obrigatória", Toast.LENGTH_LONG).show()
                address.text.toString().isBlank() ->
                    Toast.makeText(this, "Endereço é obrigatório", Toast.LENGTH_LONG).show()
                !cpfCreate.text.toString().matches(CPF_REGEX.toRegex()) ->
                    Toast.makeText(this, "CPF inválido", Toast.LENGTH_LONG).show()
                !emailCreate.text.toString().matches(EMAIL_REGEX.toRegex()) ->
                    Toast.makeText(this, "Email inválido", Toast.LENGTH_LONG).show()
                passwordCreate.text.toString() != passwordConfirmation.text.toString() ->
                    Toast.makeText(this, "Senhas são diferentes", Toast.LENGTH_LONG).show()

                else -> {

                    progressCreate.visibility = View.VISIBLE
                    layout.alpha = 0.5f

                    mAuth.createUserWithEmailAndPassword(
                        emailCreate.text.toString(),
                        passwordCreate.text.toString()).addOnCompleteListener {

                        progressCreate.visibility = View.GONE
                        layout.alpha = 1f

                        if (it.isSuccessful && mAuth.currentUser?.uid != null) {
                            val cliente = Cliente(
                                idadeCreate.text.toString().toInt(),
                                address.text.toString(),
                                sexoCreate.text.toString(),
                                    emailCreate.text.toString(),
                                name.text.toString(),
                                cpfCreate.text.toString(),
                                    telefoneCreate.text.toString()
                                )
                            myRef.push().setValue(cliente)
                            //myRef
                            //                                    .child(mAuth.currentUser?.uid ?: throw IllegalArgumentException())
                            //                                    .setValue(cliente)
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