package ru.maxx52.androidsprint.ui

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import ru.maxx52.androidsprint.R
import ru.maxx52.androidsprint.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private var _binding: ActivityMainBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("Binding не доступен или null")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.mainContainer, FragmentListCategories())
            }
        }

        with(binding) {
            btnCategory.setOnClickListener {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<FragmentListCategories>(R.id.mainContainer)
                }
            }
            btnFavorites.setOnClickListener {
                supportFragmentManager.commit {
                    setReorderingAllowed(true)
                    replace<FavoritesFragment>(R.id.mainContainer)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}