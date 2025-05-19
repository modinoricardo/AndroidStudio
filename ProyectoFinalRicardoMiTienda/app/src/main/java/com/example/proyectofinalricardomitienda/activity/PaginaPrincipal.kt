package com.example.proyectofinalricardomitienda.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.addCallback
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.proyectofinalricardomitienda.R
import com.example.proyectofinalricardomitienda.databinding.ActivityPaginaPrincipalBinding
import com.example.proyectofinalricardomitienda.fragment.ProductFragment
import com.example.proyectofinalricardomitienda.fragment.ShoppingCartFragment
import com.example.proyectofinalricardomitienda.fragment.WebFragment
import com.example.proyectofinalricardomitienda.util.Util
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView

class PaginaPrincipal : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private lateinit var binding: ActivityPaginaPrincipalBinding

    lateinit var myMaterialToolbar: MaterialToolbar

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_logout -> {
                showLogoutConfirmationDialog()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLogoutConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle("Cerrar sesión")
            .setMessage("¿Estás seguro de que quieres cerrar sesión y salir de la aplicación?")
            .setPositiveButton("Sí") { dialog, _ ->
                Util.accessToken = ""
                Util.refrehToken = ""
                finishAffinity()
            }
            .setNegativeButton("No") { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPaginaPrincipalBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Ajuste de inset/padding…
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val sys = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(sys.left, sys.top, sys.right, sys.bottom)
            insets
        }

        // Toolbar
        myMaterialToolbar = findViewById(R.id.myToolbar)
        setSupportActionBar(myMaterialToolbar)

        // Drawer toggle
        val toggle = ActionBarDrawerToggle(
            this, binding.main, myMaterialToolbar,
            R.string.navigation_drawer_open,
            R.string.navigation_drawer_close
        )
        binding.main.addDrawerListener(toggle)
        toggle.syncState()

        // Listener de navegación
        binding.myNavegationView.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.id_inicio -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.myLinearL, WebFragment.newInstance(Util.URL))
                        .commit()
                }
                R.id.id_productos -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.myLinearL, ProductFragment.newInstance())
                        .commit()
                }
                R.id.id_carrito -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.myLinearL, ShoppingCartFragment.newInstance())
                        .commit()
                }
            }
            binding.main.closeDrawer(GravityCompat.START)
            true
        }

        // *** Aquí cargamos la pestaña Home por defecto ***
        if (savedInstanceState == null) {
            // 1) Marcamos el item Home en el menú
            binding.myNavegationView.setCheckedItem(R.id.id_inicio)
            // 2) Mostramos el WebFragment
            supportFragmentManager.beginTransaction()
                .replace(R.id.myLinearL, WebFragment.newInstance(Util.URL))
                .commit()
        }

        // Manejar “back” en el drawer
        onBackPressedDispatcher.addCallback(this) {
            if (binding.main.isDrawerOpen(GravityCompat.START)) {
                binding.main.closeDrawer(GravityCompat.START)
            } else {
                finish()
            }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        TODO("Not yet implemented")
    }
}