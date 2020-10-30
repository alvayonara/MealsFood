package com.alvayonara.mealsfood.favorite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.alvayonara.mealsfood.R
import com.alvayonara.mealsfood.core.data.source.Resource
import com.alvayonara.mealsfood.core.ui.FoodAdapter
import com.alvayonara.mealsfood.core.ui.ViewModelFactory
import com.alvayonara.mealsfood.core.utils.gone
import com.alvayonara.mealsfood.core.utils.visible
import com.alvayonara.mealsfood.dashboard.DashboardViewModel
import com.alvayonara.mealsfood.detail.DetailFoodActivity
import kotlinx.android.synthetic.main.fragment_dashboard.*
import kotlinx.android.synthetic.main.fragment_favorite.*

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? = inflater.inflate(R.layout.fragment_favorite, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (activity != null) {
            val factory = ViewModelFactory.getInstance(requireActivity())
            favoriteViewModel = ViewModelProvider(this, factory)[FavoriteViewModel::class.java]

            val foodAdapter = FoodAdapter(FoodAdapter.TYPE_FAVORITE).apply {
                onItemClick = {
                    val intent = Intent(requireActivity(), DetailFoodActivity::class.java).putExtra(
                        DetailFoodActivity.EXTRA_FOOD_DATA, it
                    )
                    startActivity(intent)
                }
            }

            progress_bar_favorite.visible()

            favoriteViewModel.favoriteFood.observe(viewLifecycleOwner, {
                progress_bar_favorite.gone()

                foodAdapter.setFoods(it)
            })

            with(rv_favorite_foods) {
                layoutManager = LinearLayoutManager(context)
                setHasFixedSize(true)
                adapter = foodAdapter
            }
        }
    }
}