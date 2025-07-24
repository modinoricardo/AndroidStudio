package com.example.reproductormusica

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import java.util.*
import android.content.ContentUris
import android.net.Uri
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView


class MainActivity : AppCompatActivity() {

    private var mediaPlayer: MediaPlayer? = null
    private lateinit var recyclerView: RecyclerView
    private lateinit var textCancionActual: TextView
    private lateinit var btnPausar: Button
    private var cancionEnReproduccion: String = ""
    private var enPausa = false
    //Indice de la cancion
    private var indiceCancionActual = -1
    private lateinit var canciones: List<Pair<String, Uri>>



    // 🔹 Permiso para Android 13+ (READ_MEDIA_AUDIO)
    private val requestPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            loadAudioFiles()

        } else {
            Toast.makeText(this, "Permiso denegado", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainLayout = findViewById<View>(R.id.main)
        ViewCompat.setOnApplyWindowInsetsListener(mainLayout) { view, insets ->
            val systemBarsInsets = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(
                systemBarsInsets.left,
                systemBarsInsets.top,
                systemBarsInsets.right,
                systemBarsInsets.bottom
            )
            insets
        }

        recyclerView = findViewById(R.id.recyclerCanciones)
        recyclerView.layoutManager = LinearLayoutManager(this)

        textCancionActual = findViewById(R.id.textCancionActual)
        btnPausar = findViewById(R.id.btnPausar)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        val fragmentContainer = findViewById<FrameLayout>(R.id.fragment_container)

        // Mostrar contenido principal, recargar canciones
        loadAudioFiles()
        // Ocultar contenedor de fragmentos
        fragmentContainer.visibility = View.GONE
        //Ocultar nombre cancion
        textCancionActual.visibility = View.GONE

        bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    // Mostrar contenido principal, recargar canciones
                    loadAudioFiles()
                    // Ocultar contenedor de fragmentos
                    fragmentContainer.visibility = View.GONE
                    true
                }
                R.id.nav_search -> {
                    // Mostrar fragmento búsqueda
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.fragment_container, FragmentBusqueda())
                        .commit()
                    // Mostrar contenedor de fragmentos
                    fragmentContainer.visibility = View.VISIBLE
                    true
                }
                R.id.nav_library -> {
                    Toast.makeText(this, "Biblioteca aún no implementada", Toast.LENGTH_SHORT).show()
                    // Si hay fragmento, mostrar contenedor; si no, ocultar según diseño
                    fragmentContainer.visibility = View.GONE
                    true
                }
                R.id.nav_add -> {
                    Toast.makeText(this, "Función de añadir aún no implementada", Toast.LENGTH_SHORT).show()
                    fragmentContainer.visibility = View.GONE
                    true
                }
                else -> false
            }
        }



        btnPausar.setOnClickListener {
            mediaPlayer?.let {
                if (it.isPlaying) {
                    it.pause()
                    btnPausar.text = "Reanudar"
                    enPausa = true
                } else if (enPausa) {
                    it.start()
                    btnPausar.text = "Pausar"
                    enPausa = false
                }
            }
        }

        // 🔹 Comprobar permisos
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_AUDIO)
            != PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissionLauncher.launch(Manifest.permission.READ_MEDIA_AUDIO)
        } else {
            loadAudioFiles()
        }
    }


    // 🔹 Leer canciones del almacenamiento
    private fun loadAudioFiles() {
        val canciones = mutableListOf<Pair<String, Uri>>()

        val uri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
        val cursor = contentResolver.query(
            uri,
            null,
            "${MediaStore.Audio.Media.IS_MUSIC} != 0 AND " +
                    "${MediaStore.Audio.Media.DATA} NOT LIKE '%WhatsApp%' AND " +
                    "${MediaStore.Audio.Media.DATA} NOT LIKE '%/Recordings/%'",
            null,
            "${MediaStore.Audio.Media.TITLE} ASC"
        )

        cursor?.use {
            val titleColumn = it.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val idColumn = it.getColumnIndex(MediaStore.Audio.Media._ID)

            while (it.moveToNext()) {
                val title = it.getString(titleColumn)
                val id = it.getLong(idColumn)
                val contentUri = ContentUris.withAppendedId(uri, id)
                canciones.add(Pair(title, contentUri))
            }
        }

        if (canciones.isNotEmpty()) {
            indiceCancionActual = 0 // empieza con la primera canción
            this.canciones = canciones
            val adapter = CancionAdapter(canciones) { uri, titulo ->
                Toast.makeText(this, "Reproduciendo: $titulo", Toast.LENGTH_SHORT).show()
                reproducirCancion(uri, titulo)
            }
            recyclerView.adapter = adapter
        } else {
            Toast.makeText(this, "No se encontraron canciones", Toast.LENGTH_SHORT).show()
        }
    }

    private fun reproducirCancion(uri: Uri, titulo: String) {
        indiceCancionActual = canciones.indexOfFirst { it.second == uri }
        if (indiceCancionActual == -1) indiceCancionActual = 0

        textCancionActual.visibility = View.VISIBLE
        mediaPlayer?.release()
        mediaPlayer = MediaPlayer().apply {
            setDataSource(this@MainActivity, uri)
            prepare()
            start()

            setOnCompletionListener {
                reproducirSiguiente()
            }

        }
        cancionEnReproduccion = titulo
        textCancionActual.text = "🎵 $titulo"
        btnPausar.text = "Pausar"
        enPausa = false
    }

    private fun reproducirSiguiente() {
        if (canciones.isEmpty()) return

        // Incrementa el índice para la siguiente canción
        indiceCancionActual++

        // Si se pasa del final, opcional: puedes parar o reiniciar desde la primera
        if (indiceCancionActual >= canciones.size) {
            indiceCancionActual = 0 // para loop, o haz return si quieres que pare
        }

        val (titulo, uri) = canciones[indiceCancionActual]
        reproducirCancion(uri, titulo)
    }


}
