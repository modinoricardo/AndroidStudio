package com.example.proyectofinalricardomitienda

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.proyectofinalricardomitienda.databinding.ActivityPaginaPrincipalBinding
import com.example.proyectofinalricardomitienda.util.Util
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class PaginaPrincipal : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener  {
    private lateinit var binding: ActivityPaginaPrincipalBinding

    lateinit var myMaterialToolbar: MaterialToolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaginaPrincipalBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        myMaterialToolbar = findViewById<MaterialToolbar>(R.id.myToolbar)
        setSupportActionBar(myMaterialToolbar)

        with(binding){
            val toggle = ActionBarDrawerToggle(this@PaginaPrincipal, main, myMaterialToolbar,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close)
            main.addDrawerListener(toggle)
            toggle.syncState()

//            myNavegationView.setNavigationItemSelectedListener(this@MainActivity)
            myNavegationView.setNavigationItemSelectedListener {
                if(it.itemId == R.id.id_inicio){
                    val myFragmentManager : FragmentManager = supportFragmentManager
                    val myFragmentTransaction: FragmentTransaction = myFragmentManager.beginTransaction()
                    val myFragment: WebFragment = WebFragment.newInstance(Util.URL)

                    myFragmentTransaction.replace(R.id.myLinearL, myFragment)
                        .commit()

                    main.closeDrawer(GravityCompat.START)
                }else if(it.itemId == R.id.id_productos){
                    val myFragmentManager : FragmentManager = supportFragmentManager
                    val myFragmentTransaction: FragmentTransaction = myFragmentManager.beginTransaction()
                    val myFragment: ProductFragment = ProductFragment.newInstance()

                    myFragmentTransaction
                        .replace(R.id.myLinearL, myFragment)
                        .commit()
                    main.closeDrawer(GravityCompat.START)
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