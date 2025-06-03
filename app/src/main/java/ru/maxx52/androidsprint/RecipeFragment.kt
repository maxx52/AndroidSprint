package ru.maxx52.androidsprint

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.maxx52.androidsprint.databinding.FragmentRecipeBinding
import ru.maxx52.androidsprint.entities.Recipe

class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View is not initialized")
    private var recipe: Recipe? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    @Suppress("INFERRED_TYPE_VARIABLE_INTO_POSSIBLE_EMPTY_INTERSECTION")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recipe = if (Build.VERSION.SDK_INT >= 33) {
            arguments?.getParcelable(ARG_RECIPE_ID, Recipe::class.java)
        } else {
            arguments?.getParcelable(ARG_RECIPE_ID)
        }

        recipe?.let {
            binding.tvRecipeTitle.text = it.title
        } ?: run {
            binding.tvRecipeTitle.text = "Рецепта нет"
        }
    }
}