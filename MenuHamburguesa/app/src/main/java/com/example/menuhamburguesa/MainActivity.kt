package com.example.menuhamburguesa

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.menuhamburguesa.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {

        lateinit var myMaterialToolbar: MaterialToolbar
        lateinit var myNavegationView: NavigationView

            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            val view = binding.root
            enableEdgeToEdge()
            setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        myMaterialToolbar = findViewById<MaterialToolbar>(R.id.myToolbar)
        myNavegationView = findViewById<NavigationView>(R.id.myNavegationView)

        setSupportActionBar(myMaterialToolbar)

        with(binding){
            val toggle = ActionBarDrawerToggle(this@MainActivity, main, myMaterialToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
            main.addDrawerListener(toggle)
            toggle.syncState()

//            myNavegationView.setNavigationItemSelectedListener(this@MainActivity)
            myNavegationView.setNavigationItemSelectedListener {
                if(it.itemId == R.id.id_inicio){
                    val myFragmentManager : FragmentManager = supportFragmentManager
                    val myFragmentTransaction: FragmentTransaction = myFragmentManager.beginTransaction()
                    val myFragment: WebFragment = WebFragment.newInstance("https://iesclaradelrey.es/portal/index.php/es/")

                    myFragmentTransaction.replace(R.id.myLinearL, myFragment)
                        .commit()

                }
                    true
            }

            onBackPressedDispatcher.addCallback{
                if (main.isDrawerOpen(GravityCompat.START)){
                    main.closeDrawers()
                }else{
                    finish()
                }
            }
        }

    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}