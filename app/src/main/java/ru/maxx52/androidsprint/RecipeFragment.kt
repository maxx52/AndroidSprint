package ru.maxx52.androidsprint

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.divider.MaterialDividerItemDecoration
import android.widget.SeekBar
import ru.maxx52.androidsprint.databinding.FragmentRecipeBinding
import ru.maxx52.androidsprint.entities.Recipe
import ru.maxx52.androidsprint.entities.ARG_RECIPE_ID
import ru.maxx52.androidsprint.entities.NON_RECIPE
import ru.maxx52.androidsprint.entities.PREFS_NAME
import ru.maxx52.androidsprint.entities.FAVORITES_KEY
import ru.maxx52.androidsprint.entities.STUB.getRecipeById
import androidx.core.content.edit

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
            binding.tvRecipeTitle.text = NON_RECIPE
            return
        }
        recipe = getRecipeById(recipeId)

        if (recipe == null) {
            binding.tvRecipeTitle.text = NON_RECIPE
            return
        }
        initUI()
        initRecycler()
    }

    private fun initUI() {
        binding.tvRecipeTitle.text = recipe?.title ?: ""
        try {
            val drawable = Drawable.createFromStream(requireContext().assets
                .open(recipe?.imageUrl ?: ""), null)
            binding.ivRecipeImage.setImageDrawable(drawable)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        val currentRecipeId: String = recipe?.id.toString()
        val favorites = getFavorites()
        val isFavorite = favorites.contains(currentRecipeId)
        binding.ibAddFavorites.setImageResource(if (isFavorite) R.drawable.ic_heart else R.drawable.ic_heart_empty)
        binding.ibAddFavorites.setOnClickListener {
            if (isFavorite) {
                favorites.remove(currentRecipeId)
                binding.ibAddFavorites.setImageResource(R.drawable.ic_heart_empty)
            } else {
                favorites.add(currentRecipeId)
                binding.ibAddFavorites.setImageResource(R.drawable.ic_heart)
            }
            saveFavorites(favorites)
        }
    }

    private fun getFavorites(): MutableSet<String> {
        val sharedPrefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val favoritesSet = sharedPrefs.getStringSet(FAVORITES_KEY, emptySet()) ?: emptySet()
        return HashSet(favoritesSet)
    }

    private fun saveFavorites(favorites: MutableSet<String>) {
        val sharedPrefs = requireActivity().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        sharedPrefs.edit { putStringSet(FAVORITES_KEY, favorites) }
    }

    private fun initRecycler() {
        binding.rvIngredients.adapter = IngredientsAdapter(recipe?.ingredients ?: emptyList())
        binding.rvMethod.adapter = MethodAdapter(recipe?.method ?: emptyList())
        binding.rvIngredients.addItemDecoration(createDivider())
        binding.rvMethod.addItemDecoration(createDivider())

        binding.sbPortion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                binding.tvPortionDescription.text = progress.toString()
                (binding.rvIngredients.adapter as? IngredientsAdapter)?.updateIngredients(progress.toBigDecimal())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                // todo
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // todo
            }
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