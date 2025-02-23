    package com.example.jugandoalosdados

    import android.content.DialogInterface
    import android.content.Intent
    import android.os.Bundle
    import android.view.View
    import android.widget.ArrayAdapter
    import androidx.activity.enableEdgeToEdge
    import androidx.appcompat.app.AlertDialog
    import androidx.appcompat.app.AppCompatActivity
    import androidx.core.view.ViewCompat
    import androidx.core.view.WindowInsetsCompat
    import androidx.lifecycle.lifecycleScope
    import com.bumptech.glide.Glide
    import com.example.jugandoalosdados.databinding.ActivityMainBinding
    import com.google.android.material.snackbar.Snackbar
    import kotlinx.coroutines.delay
    import kotlinx.coroutines.launch

    class MainActivity : AppCompatActivity() {

        lateinit var binding: ActivityMainBinding

        var saldo = 100
        var listParImpar = listOf("Par","Impar")
        var listMayorMenorSeven = listOf("Mayor o igual que 7","Menor que 7")
        var dado1:Int = 0
        var dado2:Int = 0
        var partidaGanada = false

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
                textSaldo.text = "$saldo"

                parImparButton.setOnClickListener{
                    botonParSeleccionado(true)
                }

                mayorMenorButton.setOnClickListener {
                    botonParSeleccionado(false)
                }

                buttonLanzarDados.setOnClickListener{
                    lifecycleScope.launch {
                        lanzarDados()
                    }
                }

            }

        }

        private fun validaSeleccionDatos():Int{
            if (binding.toggleButton.checkedButtonId == View.NO_ID){
                mensajeSnackbar("Debe elegir una opcion de juego")
                return -1
            }else if(binding.editTextNumberApuesta.text.isNullOrBlank()){
                mensajeSnackbar("Debe introducir una apuesta")
                return -1
            }else{
                return 0
            }
        }

        private fun validarSaldoApuesta(): Boolean {
            val apuesta = binding.editTextNumberApuesta.text.toString().toInt()
            if(apuesta > saldo){
                mensajeSnackbar("La apuesta es mayor al saldo disponible")
                return false
            }else{
                return true
            }
        }

        private suspend fun lanzarDados() {
            limpiarVista()

            if(validaSeleccionDatos() == 0 && validarSaldoApuesta()){
                Glide.with(this).load(R.drawable.dadtos2).into(binding.imageView)
                delay(3000)

                dado1 = (1..6).random()
                dado2 = (1..6).random()

                binding.imageView.setImageDrawable(null)

                binding.textViewApuestaUno.text = "Dado_1:  $dado1"
                binding.textViewApuestaDos.text = "Dado_2:  $dado2"

                if(binding.toggleButton.checkedButtonId == R.id.parImparButton){
                    parImpar()
                }else{
                    mayorMenor7()
                }

                delay(1000)
                seguirJugando()

            }
        }

        private fun seguirJugando() {
            mensajeAlerta()
        }

        private fun mensajeAlerta() {
            val titulo = "Jugando a los Dados"
            val mensaje = "¿Desea seguir jugando?"
            val myAlert = AlertDialog.Builder(this)

            myAlert.setTitle(titulo)
            var menAviso = "Por si no te habias dado cuenta has"
            if(partidaGanada)myAlert.setMessage("$mensaje\n\n$menAviso ganado esta ronda ;) \n ¿Subimos la apuesta?")
            else myAlert.setMessage("$mensaje\n" +
                    "\n$menAviso perdido esta ronda :(")

            myAlert.setPositiveButton("Salir del juego", DialogInterface.OnClickListener({
                    _, _->this.finish()
            }))
            myAlert.setNegativeButton("Seguir jugando", DialogInterface.OnClickListener({
                    _, _->
            }))

            myAlert.create().show()
        }


        private fun limpiarVista() {
            binding.imageViewRespuesta.visibility = View.INVISIBLE
            binding.textViewApuestaUno.text = ""
            binding.textViewApuestaDos.text = ""
        }

        private fun menor7() = (dado1 + dado2)<7
        private fun mayorIgual7() = (dado1 + dado2)>=7

        private fun mayorMenor7() {
            var gano = false
            when(binding.miSpinner.selectedItemPosition){
                0 -> gano = mayorIgual7()
                1 -> gano= menor7()
            }

            if(gano) partidaGanada() else partidaPerdida()

        }

        private fun impar() = (dado1 + dado2)%2!=0
        private fun par() = (dado1 + dado2)%2==0
        private fun parImpar() {
            var gano = false
            when(binding.miSpinner.selectedItemPosition){
                0 -> gano = par()
                1 -> gano = impar()
            }
            if(gano) partidaGanada() else partidaPerdida()
        }

        private fun botonParSeleccionado(par: Boolean) {
            val miAdapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_dropdown_item,if(par) listParImpar else listMayorMenorSeven)
            binding.miSpinner.adapter = miAdapter
        }

//        private fun mensajeSnackbar(mensaje:String){
//            Snackbar.make(binding.root, mensaje, Snackbar.LENGTH_LONG).show()
//        }
        private fun mensajeSnackbar(mensaje:String){
            Snackbar.make(binding.coordinatorLayout, mensaje, Snackbar.LENGTH_LONG).show()
        }

        private fun partidaGanada(){
            var apuesta = binding.editTextNumberApuesta.text.toString().toInt()
            saldo += apuesta
            binding.textSaldo.text= saldo.toString()
            binding.imageViewRespuesta.visibility = View.VISIBLE
            binding.imageViewRespuesta.setImageResource(R.drawable.win)
            partidaGanada = true
        }

        private fun partidaPerdida(){
            var apuesta = binding.editTextNumberApuesta.text.toString().toInt()
            saldo -= apuesta
            binding.textSaldo.text= saldo.toString()
            binding.imageViewRespuesta.visibility = View.VISIBLE
            binding.imageViewRespuesta.setImageResource(R.drawable.youlose)
            partidaGanada = false

            if (saldo==0){
                val intent = Intent(this, SegundaActivity::class.java)
                startActivity(intent)
                this.finish()
            }

        }

    }