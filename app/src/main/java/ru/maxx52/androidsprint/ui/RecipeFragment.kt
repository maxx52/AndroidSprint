package ru.maxx52.androidsprint.ui

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
import ru.maxx52.androidsprint.data.NON_RECIPE
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import ru.maxx52.androidsprint.R
import ru.maxx52.androidsprint.data.BASE_URL
import ru.maxx52.androidsprint.ui.recipes.recipe.RecipeViewModel

class RecipeFragment : Fragment() {
    private var _binding: FragmentRecipeBinding? = null
    private val binding get() = _binding ?: throw IllegalStateException("View is not initialized")
    private val viewModel: RecipeViewModel by viewModels()
    private val args: RecipeFragmentArgs by navArgs()
    private lateinit var ingredientsAdapter: IngredientsAdapter

    inner class PortionSeekBarListener : SeekBar.OnSeekBarChangeListener {
        override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
            ingredientsAdapter.triggerChangeIngredients(progress)
            viewModel.updatePortions(progress)
            viewModel.recalculateIngredients()
        }

        override fun onStartTrackingTouch(seekBar: SeekBar?) {}
        override fun onStopTrackingTouch(seekBar: SeekBar?) {}
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentRecipeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.state.observe(viewLifecycleOwner) { newState ->
            Log.i("!!!", "isFavorite: ${newState.isFavorite}")
        }
        val recipeId = args.recipeId
        if (recipeId == -1) {
            binding.tvRecipeTitle.text = NON_RECIPE
            return
        }
        viewModel.loadRecipe(recipeId)
        binding.sbPortion.setOnSeekBarChangeListener(PortionSeekBarListener())
        initUI()
    }

    private fun initUI() {
        ingredientsAdapter = IngredientsAdapter(::onChangeIngredients)
        val methodAdapter = MethodAdapter()
        binding.rvIngredients.adapter = ingredientsAdapter
        binding.rvMethod.adapter = methodAdapter
        binding.rvIngredients.addItemDecoration(createDivider())
        binding.rvMethod.addItemDecoration(createDivider())

        viewModel.state.observe(viewLifecycleOwner) { newState ->
            val recipe = newState.recipe
            if (recipe != null) {
                binding.tvRecipeTitle.text = recipe.title
                binding.ibAddFavorites.setImageResource(if (newState.isFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty)
                binding.ibAddFavorites.setOnClickListener {
                    viewModel.onFavoritesClicked()
                }

                val drawable = newState.recipeImageUrl
                val completeImageUrl = "$BASE_URL$drawable"
                if (drawable != null) {
                    Glide.with(this)
                        .load(completeImageUrl)
                        .placeholder(R.drawable.img_placeholder)
                        .into(binding.ivRecipeImage)
                }
            } else {
                binding.tvRecipeTitle.text = ""
                binding.ivRecipeImage.setImageDrawable(null)
            }

            ingredientsAdapter.dataSet = newState.ingredients.toMutableList()
            methodAdapter.submitList(newState.recipe?.method ?: emptyList())
        }
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

    private fun onChangeIngredients(newPortions: Int) {
        Log.d("RecipeFragment", "Количество порций изменено на $newPortions")
    }
}