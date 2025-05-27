package ru.maxx52.androidsprint

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.maxx52.androidsprint.databinding.FragmentRecipesListBinding

class RecipesListFragment : Fragment() {
    private var _binding: FragmentRecipesListBinding? = null
    private val binding
        get() = _binding ?: throw IllegalStateException("View is not initialized")

    var categoryId: Int? = null
    var categoryName: String? = null
    var categoryImageUrl: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRecipesListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args = arguments
        if (args != null) {
            categoryId = args.getInt(ARG_CATEGORY_ID, -1)
            categoryName = args.getString(ARG_CATEGORY_NAME) ?: "Без названия"
            categoryImageUrl = args.getString(ARG_CATEGORY_IMAGE_URL) ?: ""
        } else {
            categoryId = -1
            categoryName = "Без названия"
            categoryImageUrl = ""
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}