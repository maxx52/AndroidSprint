package ru.maxx52.androidsprint.ui

import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import android.widget.SeekBar
import ru.maxx52.androidsprint.databinding.FragmentRecipeBinding
import ru.maxx52.androidsprint.data.ARG_RECIPE_ID
import ru.maxx52.androidsprint.data.NON_RECIPE
import androidx.fragment.app.viewModels
import ru.maxx52.androidsprint.R
import ru.maxx52.androidsprint.ui.recipes.recipe.RecipeViewModel

class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View is not initialized")
    private val viewModel: RecipeViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { newState ->
            Log.i("!!!", "isFavorite: ${newState.isFavorite}")
        }
        val recipeId = arguments?.getInt(ARG_RECIPE_ID, -1) ?: -1
        if (recipeId == -1) {
            binding.tvRecipeTitle.text = NON_RECIPE
            return
        }
        viewModel.loadRecipe(recipeId)
        initUI()
        initRecycler()
    }

    private fun initUI() {
        viewModel.state.observe(viewLifecycleOwner) { newState ->
            val recipe = newState.recipe
            if (recipe != null) {
                binding.tvRecipeTitle.text = recipe.title
                binding.ibAddFavorites.setImageResource(if (newState.isFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty)
                binding.ibAddFavorites.setOnClickListener {
                    viewModel.onFavoritesClicked()
                }

                try {
                    val drawable = Drawable.createFromStream(requireContext()
                        .assets.open(recipe.imageUrl), null)
                    binding.ivRecipeImage.setImageDrawable(drawable)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            } else {
                binding.tvRecipeTitle.text = ""
                binding.ivRecipeImage.setImageDrawable(null)
            }
        }
    }

    private fun initRecycler() {
        binding.rvIngredients.adapter = IngredientsAdapter(emptyList())
        binding.rvMethod.adapter = MethodAdapter(emptyList())
        binding.rvIngredients.addItemDecoration(createDivider())
        binding.rvMethod.addItemDecoration(createDivider())

        viewModel.state.observe(viewLifecycleOwner) { newState ->
            val adapter = binding.rvIngredients.adapter as? IngredientsAdapter
            adapter?.updateIngredients(newState.ingredients)

            val methodAdapter = binding.rvMethod.adapter as? MethodAdapter
            methodAdapter?.updateMethods(newState.recipe?.method ?: emptyList())
        }

        binding.sbPortion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                viewModel.updatePortions(progress)
                viewModel.recalculateIngredients()
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createDivider(): MaterialDividerItemDecoration {
        return MaterialDividerItemDecoration(requireContext(),
            RecyclerView.VERTICAL).apply {
            dividerInsetStart = resources.getDimensionPixelSize(R.dimen.padding_recycler)
            dividerInsetEnd = resources.getDimensionPixelSize(R.dimen.padding_recycler)
            dividerThickness = resources.getDimensionPixelSize(R.dimen.one_pixel)
            setDividerColor(ContextCompat.getColor(requireContext(), R.color.grey_divider_color))
            isLastItemDecorated = false
        }
    }
}