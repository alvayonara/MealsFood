package com.alvayonara.mealsfood.dashboard

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.alvayonara.mealsfood.core.data.source.Resource
import com.alvayonara.mealsfood.core.ui.FoodAdapter
import com.alvayonara.mealsfood.core.ui.FoodAdapter.Companion.TYPE_GRID
import com.alvayonara.mealsfood.core.utils.*
import com.alvayonara.mealsfood.core.utils.Helper.setToast
import com.alvayonara.mealsfood.databinding.FragmentDashboardBinding
import org.koin.android.viewmodel.ext.android.viewModel

class DashboardFragment : Fragment(), IOnBackPressed {

    private val dashboardViewModel: DashboardViewModel by viewModel()
    private lateinit var foodAdapter: FoodAdapter

    private var _binding: FragmentDashboardBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (activity != null) {
            initView()
            initAdapter()
            initRecyclerView()
            subscribeVm()
        }
    }

    private fun initView() {
        binding.edtSearchFoodDashboard.setOnClickListener {
            val nav =
                DashboardFragmentDirections.actionNavigationDashboardToSearchFoodFragment()
            navigate(nav)
        }
    }

    private fun initAdapter() {
        foodAdapter = FoodAdapter(TYPE_GRID).apply {
            onItemClick = {
                val nav =
                    DashboardFragmentDirections.actionNavigationDashboardToDetailFoodFragment(it)
                navigate(nav)
            }
        }
    }

    private fun initRecyclerView() {
        with(binding.rvFoods) {
            layoutManager = GridLayoutManager(context, 2)
            addItemDecoration(SpacingItemDecoration(2, Helper.dpToPx(context, 16), true));
            setHasFixedSize(true)
            adapter = foodAdapter
        }
    }

    private fun subscribeVm() {
        dashboardViewModel.food.observe(viewLifecycleOwner, {
            if (it != null) {
                when (it) {
                    is Resource.Loading -> binding.progressBarDashboard.visible()
                    is Resource.Success -> {
                        binding.progressBarDashboard.gone()
                        foodAdapter.setFoods(it.data)
                    }
                    is Resource.Error -> setToast("An Error Occurred", requireActivity())
                }
            }
        })
    }

    override fun onBackPressed(): Boolean {
        return false
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.rvFoods.adapter = null
        _binding = null
    }
}