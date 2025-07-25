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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_player)

        seekBar = findViewById(R.id.seekBar)
        textTitulo = findViewById(R.id.textTitulo)
        btnPlayPause = findViewById(R.id.btnPlayPause)
        textTiempoActual = findViewById(R.id.textTiempoActual)
        textDuracionTotal = findViewById(R.id.textDuracionTotal)


        val uriString = intent.getStringExtra("songUri")
        val titulo = intent.getStringExtra("songName")

        if (uriString != null) {
            val uri = Uri.parse(uriString)
            textTitulo.text = titulo

            mediaPlayer = MediaPlayer().apply {
                setDataSource(this@PlayerActivity, uri)
                prepare()
                start()
                this@PlayerActivity.isPlaying = true

                setOnCompletionListener {
                    btnPlayPause.text = "Reproducir"
                    this@PlayerActivity.isPlaying = false
                }
            }


            // Inicializar SeekBar
            seekBar.max = mediaPlayer!!.duration

            if (mediaPlayer != null) {
                val duracion = mediaPlayer!!.duration
                textDuracionTotal.text = formatoTiempo(duracion)
                seekBar.max = duracion

                handler.post(object : Runnable {
                    override fun run() {
                        mediaPlayer?.let {
                            val posicion = it.currentPosition
                            val duracion = it.duration
                            val tiempoRestante = duracion - posicion

                            seekBar.progress = posicion
                            textTiempoActual.text = formatoTiempo(posicion)
                            textDuracionTotal.text = "${formatoTiempo(tiempoRestante)}" // Guion delante para indicar que es tiempo que queda

                            handler.postDelayed(this, 500)
                        }
                    }
                })

            }

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


}
