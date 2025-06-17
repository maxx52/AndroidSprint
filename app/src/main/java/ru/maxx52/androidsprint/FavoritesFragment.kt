package ru.maxx52.androidsprint

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.maxx52.androidsprint.databinding.FragmentFavoritesBinding
import ru.maxx52.androidsprint.entities.FAVORITES_KEY
import ru.maxx52.androidsprint.entities.PREFS_NAME
import ru.maxx52.androidsprint.entities.Recipe
import ru.maxx52.androidsprint.entities.STUB

class FavoritesFragment : Fragment() {

    private var _binding: FragmentFavoritesBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentFavoritesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initRecycler()
    }

    private fun initRecycler() {
        val adapter = RecipesListAdapter(getFavoriteRecipes())
        binding.rvFavorites.adapter = adapter
    }

    private fun getFavoriteRecipes(): List<Recipe> {
        val favoritesSet = getFavorites()
        val favoritesIds = favoritesSet.mapNotNull { it.toIntOrNull() }.toSet()
        return STUB.getRecipesByIds(favoritesIds)
    }

    private fun getFavorites(): Set<String> {
        val sharedPrefs = requireActivity()
            .getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        return sharedPrefs.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}