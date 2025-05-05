package com.example.proyectofinalricardomitienda

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import com.example.proyectofinalricardomitienda.databinding.ActivityPaginaPrincipalBinding
import com.example.proyectofinalricardomitienda.databinding.FragmentWebBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [WebFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class WebFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var _binding: FragmentWebBinding? = null
    private val binding get() = _binding!!

//    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
//            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentWebBinding.inflate(inflater,container,false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.myWebView.settings.javaScriptEnabled = true
        binding.myWebView.webViewClient = WebViewClient()

        try {
            binding.myWebView.loadUrl("http://10.0.2.2:8000")
        }catch (e: Exception){
            Toast.makeText(context,"Ha FALLADO",Toast.LENGTH_SHORT).show()
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WebFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(url: String) =
            WebFragment().apply {
                arguments = Bundle().apply {
                    putString(url, url)
//                    putString(ARG_PARAM2, param2)
                }
            }
    }
}