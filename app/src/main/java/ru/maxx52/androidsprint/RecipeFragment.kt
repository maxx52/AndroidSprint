package ru.maxx52.androidsprint

import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.maxx52.androidsprint.databinding.FragmentRecipeBinding
import ru.maxx52.androidsprint.entities.Recipe
import ru.maxx52.androidsprint.entities.ARG_RECIPE_ID

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
        initRecycler()
        initUI()
    }

    fun initRecycler() {
        val ingredientsAdapter = IngredientsAdapter(recipe?.ingredients ?: emptyList())
        binding.rvIngredients.adapter = ingredientsAdapter

        val methodAdapter = MethodAdapter(recipe?.method ?: emptyList())
        binding.rvMethod.adapter = methodAdapter
    }

    fun initUI() {
        binding.tvRecipeTitle.text = recipe?.title
        try {
            val drawable = Drawable.createFromStream(requireContext().assets.open(recipe?.imageUrl ?: ""), null)
            binding.ivRecipeImage.setImageDrawable(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}