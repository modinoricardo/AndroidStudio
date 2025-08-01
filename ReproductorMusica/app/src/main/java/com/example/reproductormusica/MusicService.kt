package com.example.reproductormusica

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.os.Binder
import android.os.IBinder
import androidx.core.app.NotificationCompat

class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var currentUri: Uri? = null

    inner class MusicBinder : Binder() {
        fun getService(): MusicService = this@MusicService
    }

    private val binder = MusicBinder()

    override fun onBind(intent: Intent?): IBinder = binder

    companion object {
        const val ACTION_PLAY = "com.example.reproductormusica.PLAY"
        const val ACTION_PAUSE = "com.example.reproductormusica.PAUSE"
        const val ACTION_NEXT = "com.example.reproductormusica.NEXT"
        const val ACTION_PREVIOUS = "com.example.reproductormusica.PREVIOUS"
    }

    fun reproducir(uri: Uri) {
        if (currentUri == uri && mediaPlayer?.isPlaying == true) return

        mediaPlayer?.release()
        mediaPlayer = MediaPlayer.create(this, uri)
        mediaPlayer?.start()
        currentUri = uri

        mediaPlayer?.setOnCompletionListener {
            // Aquí puedes enviar un broadcast o manejar reproducción automática
        }
        mostrarNotificacion(uri.lastPathSegment ?: "Desconocido", true)
    }

    fun pausar() {
        mediaPlayer?.pause()
    }

    fun reanudar() {
        mediaPlayer?.start()
    }

    fun estaReproduciendo(): Boolean {
        return mediaPlayer?.isPlaying ?: false
    }

    fun posicionActual(): Int {
        return mediaPlayer?.currentPosition ?: 0
    }

    fun duracionTotal(): Int {
        return mediaPlayer?.duration ?: 1
    }

    fun buscarA(posicion: Int) {
        mediaPlayer?.seekTo(posicion)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

    private fun mostrarNotificacion(titulo: String, enReproduccion: Boolean) {
        val canalId = "musica_channel"

        // Canales solo para Android 8+
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                canalId,
                "Reproductor de música",
                NotificationManager.IMPORTANCE_LOW
            )
            getSystemService(NotificationManager::class.java).createNotificationChannel(canal)
        }

        val intentPlayPause = Intent(this, MusicNotificationReceiver::class.java).apply {
            action = if (enReproduccion) ACTION_PAUSE else ACTION_PLAY
        }
        val intentNext = Intent(this, MusicNotificationReceiver::class.java).apply {
            action = ACTION_NEXT
        }
        val intentPrev = Intent(this, MusicNotificationReceiver::class.java).apply {
            action = ACTION_PREVIOUS
        }

        val pendingPlayPause = PendingIntent.getBroadcast(this, 0, intentPlayPause, PendingIntent.FLAG_IMMUTABLE)
        val pendingNext = PendingIntent.getBroadcast(this, 1, intentNext, PendingIntent.FLAG_IMMUTABLE)
        val pendingPrev = PendingIntent.getBroadcast(this, 2, intentPrev, PendingIntent.FLAG_IMMUTABLE)

        val builder = NotificationCompat.Builder(this, canalId)
            .setContentTitle(titulo)
            .setSmallIcon(R.drawable.ic_music_note) // Asegúrate de tener un icono
            .addAction(R.drawable.ic_skip_previous, "Anterior", pendingPrev)
            .addAction(
                if (enReproduccion) R.drawable.ic_pause else R.drawable.ic_play_arrow,
                if (enReproduccion) "Pausar" else "Reproducir",
                pendingPlayPause
            )
            .addAction(R.drawable.ic_skip_next, "Siguiente", pendingNext)
            .setStyle(
                androidx.media.app.NotificationCompat.MediaStyle()
                    .setShowActionsInCompactView(0, 1, 2)
            )
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setOngoing(enReproduccion)

        startForeground(1, builder.build())
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PLAY -> {
                reanudar()
                mostrarNotificacion(currentUri?.lastPathSegment ?: "Desconocido", true)
            }
            ACTION_PAUSE -> {
                pausar()
                mostrarNotificacion(currentUri?.lastPathSegment ?: "Desconocido", false)
            }
            ACTION_NEXT -> {
                // Aquí implementa la lógica para la siguiente canción
            }
            ACTION_PREVIOUS -> {
                // Aquí implementa la lógica para la canción anterior
            }
        }
        return START_STICKY
    }


}
