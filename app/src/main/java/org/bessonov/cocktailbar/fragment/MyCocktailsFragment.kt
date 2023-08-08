package org.bessonov.cocktailbar.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.bessonov.cocktailbar.R
import org.bessonov.cocktailbar.adapter.CocktailListAdapter
import org.bessonov.cocktailbar.databinding.FragmentMyCocktailsBinding
import org.bessonov.cocktailbar.fragment.AddCocktailFragment.Companion.SAVE
import org.bessonov.cocktailbar.state.MyCocktailsState
import org.bessonov.cocktailbar.viewmodel.MyCocktailsViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MyCocktailsFragment : Fragment() {

    private var _binding: FragmentMyCocktailsBinding? = null
    private val binding: FragmentMyCocktailsBinding
        get() = _binding ?: throw RuntimeException("FragmentMyCocktailsBinding == null")

    private val viewModel by lazy {
        ViewModelProvider(this)[MyCocktailsViewModel::class.java]
    }

    @Inject
    lateinit var cocktailListAdapter: CocktailListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMyCocktailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModelState()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeViewModelState() {
        viewModel.uiState
            .flowWithLifecycle(
                lifecycle = viewLifecycleOwner.lifecycle,
                minActiveState = Lifecycle.State.CREATED
            )
            .onEach { state -> handleState(state = state) }
            .launchIn(viewLifecycleOwner.lifecycleScope)
    }

    private fun handleState(state: MyCocktailsState) {
        when (state) {
            is MyCocktailsState.Loading -> reduce(state = state)
            is MyCocktailsState.Empty -> reduce(state = state)
            is MyCocktailsState.Content -> reduce(state = state)
        }
    }

    private fun reduce(state: MyCocktailsState.Loading) {
        showLoadingProgress()
    }

    private fun showLoadingProgress() {
        binding.loadingProgress.visibility = View.VISIBLE
    }

    private fun reduce(state: MyCocktailsState.Empty) {
        hideLoadingProgress()
        hideCocktailList()
        showGreetingContent()
        setAddClickListener()
    }

    private fun hideLoadingProgress() {
        binding.loadingProgress.visibility = View.GONE
    }

    private fun hideCocktailList() {
        binding.cocktailListRv.visibility = View.GONE
    }

    private fun showGreetingContent() {
        binding.greetingImageIv.visibility = View.VISIBLE
        binding.titleTv.visibility = View.VISIBLE
        binding.titleDescriptionTv.visibility = View.VISIBLE
        binding.arrowIconIv.visibility = View.VISIBLE
    }

    private fun reduce(state: MyCocktailsState.Content) {
        hideLoadingProgress()
        showContent()
        setCocktailList(state = state)
        setCocktailListItemClickListener()
        setAddClickListener()
        setupAddCocktailFragmentListener(state = state)
    }

    private fun showContent() {
        binding.titleTv.visibility = View.VISIBLE
        binding.cocktailListRv.visibility = View.VISIBLE
    }

    private fun setCocktailList(state: MyCocktailsState.Content) {
        binding.cocktailListRv.adapter = cocktailListAdapter
        cocktailListAdapter.submitList(state.cocktailList)
    }

    private fun setCocktailListItemClickListener() {
        cocktailListAdapter.onItemClick = {}
    }

    private fun setAddClickListener() {
        binding.addCocktailFab.setOnClickListener {
            findNavController()
                .navigate(R.id.action_navigation_my_cocktails_to_navigation_add_cocktail)
        }
    }

    private fun setupAddCocktailFragmentListener(state: MyCocktailsState.Content) {
        AddCocktailFragment.setupListener(parentFragmentManager, this) { result ->
            if (result == SAVE) {
                binding.cocktailListRv.scrollToPosition(state.cocktailList.size - 1)
            }
        }
    }
}