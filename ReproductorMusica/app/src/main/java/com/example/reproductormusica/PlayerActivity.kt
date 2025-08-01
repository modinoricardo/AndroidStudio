package com.example.reproductormusica

import android.content.*
import android.net.Uri
import android.os.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class PlayerActivity : AppCompatActivity() {

    private lateinit var seekBar: SeekBar
    private lateinit var textTitulo: TextView
    private lateinit var btnPlayPause: Button
    private lateinit var textTiempoActual: TextView
    private lateinit var textDuracionTotal: TextView

    private lateinit var listaUris: ArrayList<String>
    private var indiceActual = 0
    private var isPlaying = false

    private var musicService: MusicService? = null
    private var serviceBound = false

    private val handler = Handler(Looper.getMainLooper())

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, binder: IBinder?) {
            val musicBinder = binder as MusicService.MusicBinder
            musicService = musicBinder.getService()
            serviceBound = true

            val uri = Uri.parse(listaUris[indiceActual])
            musicService?.reproducir(uri)
            actualizarSeekBar()
            btnPlayPause.text = "Pausar"
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            serviceBound = false
        }
    }

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

        val titulo = intent.getStringExtra("songName") ?: "Sin título"
        textTitulo.text = titulo

        val serviceIntent = Intent(this, MusicService::class.java)
        startService(serviceIntent)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)


        btnPlayPause.setOnClickListener {
            if (!serviceBound) return@setOnClickListener
            isPlaying = !isPlaying
            if (isPlaying) {
                musicService?.reanudar()
                btnPlayPause.text = "Pausar"
            } else {
                musicService?.pausar()
                btnPlayPause.text = "Reproducir"
            }
        }

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sb: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser && serviceBound) {
                    musicService?.buscarA(progress)
                }
            }

            override fun onStartTrackingTouch(sb: SeekBar?) {}
            override fun onStopTrackingTouch(sb: SeekBar?) {}
        })
    }

    private fun actualizarSeekBar() {
        handler.post(object : Runnable {
            override fun run() {
                if (serviceBound) {
                    val pos = musicService?.posicionActual() ?: 0
                    val dur = musicService?.duracionTotal() ?: 1
                    val restante = dur - pos

                    seekBar.max = dur
                    seekBar.progress = pos

                    textTiempoActual.text = formatoTiempo(pos)
                    textDuracionTotal.text = "-${formatoTiempo(restante)}"

                    handler.postDelayed(this, 500)
                }
            }
        })
    }

    private fun formatoTiempo(ms: Int): String {
        val minutos = (ms / 1000) / 60
        val segundos = (ms / 1000) % 60
        return String.format("%02d:%02d", minutos, segundos)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (serviceBound) {
            unbindService(connection)
            serviceBound = false
        }
        handler.removeCallbacksAndMessages(null)
    }
}
