    package com.example.jugandoalosdados

    import android.os.Bundle
    import android.util.Log
    import android.view.View
    import android.widget.AdapterView
    import android.widget.ArrayAdapter
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import androidx.core.view.get
    import com.example.jugandoalosdados.databinding.ActivityMainBinding

    class MainActivity : AppCompatActivity() {

        lateinit var binding: ActivityMainBinding

        var saldo = 100
        var listParImpar = listOf("Par","Impar")
        var listMayorMenorSeven = listOf("Mayor o igual que 7","Menor que 7")

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            binding = ActivityMainBinding.inflate(layoutInflater)
            val view = binding.root
            setContentView(view)
            enableEdgeToEdge()
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
                val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
                insets
            }

            with(binding){
                textSaldo.text = saldo.toString()

                parImparButton.setOnClickListener{
                    botonParSeleccionado(true);
                }

                mayorMenorButton.setOnClickListener {
                    botonParSeleccionado(false);
                }

                buttonLanzarDados.setOnClickListener{
                    lanzarDados();
                }

            }

        }

        private fun validaLanzarDados(): Boolean {
            return binding.parImparButton.isActivated && binding.miSpinner.isActivated
        }

        private fun lanzarDados() {
            if(!validaLanzarDados()){

            }else{

            }

        }

        private fun botonParSeleccionado(par: Boolean) {
            var miAdapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,if(par) listParImpar else listMayorMenorSeven)
            binding.miSpinner.adapter = miAdapter
        }

    }