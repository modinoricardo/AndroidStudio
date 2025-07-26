package com.example.reproductormusica

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class PlayerActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var seekBar: SeekBar
    private lateinit var textTitulo: TextView
    private lateinit var btnPlayPause: Button
    private var isPlaying = false
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var textTiempoActual: TextView
    private lateinit var textDuracionTotal: TextView
    private lateinit var listaUris: ArrayList<String>
    private var indiceActual = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        seekBar = findViewById(R.id.seekBar)
        textTitulo = findViewById(R.id.textTitulo)
        btnPlayPause = findViewById(R.id.btnPlayPause)
        textTiempoActual = findViewById(R.id.textTiempoActual)
        textDuracionTotal = findViewById(R.id.textDuracionTotal)

        listaUris = intent.getStringArrayListExtra("listaUris") ?: arrayListOf()
        indiceActual = intent.getIntExtra("indiceActual", 0)

        val uriString = intent.getStringExtra("songUri")
        val titulo = intent.getStringExtra("songName")

        if (uriString != null) {
            reproducirCancion(Uri.parse(uriString), titulo ?: "Desconocido")
        }

        btnPlayPause.setOnClickListener {
            mediaPlayer?.let {
                if (isPlaying) {
                    it.pause()
                    btnPlayPause.text = "Reproducir"
                } else {
                    it.start()
                    btnPlayPause.text = "Pausar"
                }
                isPlaying = !isPlaying
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
        handler.removeCallbacksAndMessages(null)
    }

    private fun formatoTiempo(millis: Int): String {
        val minutos = (millis / 1000) / 60
        val segundos = (millis / 1000) % 60
        return String.format("%02d:%02d", minutos, segundos)
    }

    private fun reproducirCancion(uri: Uri, titulo: String) {
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(this@PlayerActivity, uri)
            prepare()
            start()
            this@PlayerActivity.isPlaying = true  // Aquí referenciamos la variable de la clase

            setOnCompletionListener {
                this@PlayerActivity.isPlaying = false
                btnPlayPause.text = "Reproducir"
                reproducirSiguiente()
            }
        }


        textTitulo.text = titulo
        seekBar.max = mediaPlayer!!.duration
        btnPlayPause.text = "Pausar"

        actualizarSeekBar()
    }


    private fun reproducirSiguiente() {
        if (listaUris.isEmpty()) return

        indiceActual++
        if (indiceActual >= listaUris.size) {
            indiceActual = 0 // Opcional: reiniciar lista o parar reproducción
        }

        val siguienteUri = Uri.parse(listaUris[indiceActual])
        val siguienteTitulo = "Canción ${indiceActual + 1}" // O pasa el título también si quieres

        reproducirCancion(siguienteUri, siguienteTitulo)
    }

    private fun actualizarSeekBar() {
        handler.post(object : Runnable {
            override fun run() {
                mediaPlayer?.let {
                    val posicion = it.currentPosition
                    val duracion = it.duration
                    val tiempoRestante = duracion - posicion

                    seekBar.progress = posicion
                    textTiempoActual.text = formatoTiempo(posicion)
                    textDuracionTotal.text = "-${formatoTiempo(tiempoRestante)}"

                    handler.postDelayed(this, 500)
                }
            }
        })
    }


}
