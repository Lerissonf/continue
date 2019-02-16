package com.acontinue.continueparacorretores

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.acontinue.continueparacorretores.androidExtensions.drawableFromUrl
import com.acontinue.continueparacorretores.models.Corretor
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import kotlinx.android.synthetic.main.nav_header_dashboard.*
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext


class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, CoroutineScope {
    private val job = Job()
    override val coroutineContext: CoroutineContext get() = Dispatchers.Main + job

    private var user:FirebaseUser? = null
    private var corretor:Corretor? = null

    private val database = FirebaseDatabase.getInstance()
    var myRef = database
        .getReference("users")
        .child("corretor")

    private val mListener = FirebaseAuth.AuthStateListener {
        if (it.currentUser == null) {
            Toast.makeText(this@DashboardActivity, "Não logado", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this@DashboardActivity, LoginActivity::class.java))
            finish()
        }
    }

    private val userListener = object: ValueEventListener {
        override fun onDataChange(p0: DataSnapshot) {
            user?.uid?.let {
                corretor = p0.child(it).getValue(Corretor::class.java)
                launch (Dispatchers.Default) {
                    while(name_user == null) delay(100)
                    withContext(Dispatchers.Main) {
                        name_user.text = corretor?.nome ?: ""
                    }
                }
            }
        }

        override fun onCancelled(p0: DatabaseError) {
            Toast
                .makeText(this@DashboardActivity, "Erro ao ler do banco de dados", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private lateinit var mAuth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            mAuth.signOut()
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_clients -> {
                // Handle the camera action
            }
            R.id.nav_new_clients -> {

            }
            R.id.nav_logout -> mAuth.signOut()
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun onStart() {
        super.onStart()
        launch (Dispatchers.Default) {
            while(FirebaseAuth.getInstance() == null) delay(100)
            mAuth = FirebaseAuth.getInstance()
            user = mAuth.currentUser

            while(name_email == null) delay(100)
            withContext(Dispatchers.Main) {
                name_email.text = user?.email ?: ""
            }

            withContext(Dispatchers.Main) {
                mAuth.addAuthStateListener(mListener)
            }
        }

        myRef.addValueEventListener(userListener)
    }

    override fun onStop() {
        super.onStop()
        mAuth.removeAuthStateListener(mListener)
        myRef.removeEventListener(userListener)
    }
}
