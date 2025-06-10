package ru.maxx52.androidsprint

import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import ru.maxx52.androidsprint.databinding.FragmentRecipeBinding
import ru.maxx52.androidsprint.entities.Recipe
import ru.maxx52.androidsprint.entities.ARG_RECIPE_ID
import ru.maxx52.androidsprint.entities.STUB.getRecipeById

class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View is not initialized")
    private var recipe: Recipe? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recipeId = arguments?.getInt(ARG_RECIPE_ID, -1) ?: -1
        if (recipeId == -1) {
            binding.tvRecipeTitle.text = "Рецепт не найден"
            return
        }
        recipe = getRecipeById(recipeId)

        if (recipe == null) {
            binding.tvRecipeTitle.text = "Рецепт не найден"
            return
        }
        initUI()
        initRecycler()
    }

    private fun initUI() {
        binding.tvRecipeTitle.text = recipe!!.title
        try {
            val drawable = Drawable.createFromStream(requireContext().assets
                .open(recipe!!.imageUrl), null)
            binding.ivRecipeImage.setImageDrawable(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun initRecycler() {
        binding.rvIngredients.adapter = IngredientsAdapter(recipe!!.ingredients)
        binding.rvMethod.adapter = MethodAdapter(recipe!!.method)
        binding.sbPortion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvPortionDescription.text = progress.toString()
        }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // TODO not implemented
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // TODO("Not yet implemented")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}