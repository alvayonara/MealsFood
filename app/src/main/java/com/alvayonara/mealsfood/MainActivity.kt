package com.alvayonara.mealsfood

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.transition.Slide
import androidx.transition.TransitionManager
import com.alvayonara.mealsfood.area.AreaFoodFragment
import com.alvayonara.mealsfood.category.CategoryFoodFragment
import com.alvayonara.mealsfood.core.base.BaseActivity
import com.alvayonara.mealsfood.core.utils.IOnBackPressed
import com.alvayonara.mealsfood.core.utils.gone
import com.alvayonara.mealsfood.core.utils.visible
import com.alvayonara.mealsfood.databinding.ActivityMainBinding
import com.alvayonara.mealsfood.detail.DetailFoodFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : BaseActivity<ActivityMainBinding>(),
    NavController.OnDestinationChangedListener,
    BottomNavigationView.OnNavigationItemSelectedListener {

    private var navController: NavController? = null

    override val bindingInflater: (LayoutInflater) -> ActivityMainBinding
        get() = ActivityMainBinding::inflate

    override fun setup() {
        setupView()
    }

    override fun setupView() {
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        navController?.addOnDestinationChangedListener(this)
        binding.navView.setOnNavigationItemSelectedListener(this)
        binding.navView.selectedItemId = R.id.action_dashboard
        supportFragmentManager.registerFragmentLifecycleCallbacks(object :
            FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentViewCreated(
                fm: FragmentManager,
                f: Fragment,
                v: View,
                savedInstanceState: Bundle?
            ) {
                TransitionManager.beginDelayedTransition(
                    binding.root,
                    Slide(Gravity.BOTTOM).excludeTarget(R.id.nav_host_fragment, true)
                )
                when (f) {
                    is DetailFoodFragment -> binding.navView.gone()
                    is CategoryFoodFragment -> binding.navView.gone()
                    is AreaFoodFragment -> binding.navView.gone()
                    else -> binding.navView.visible()
                }
            }
        }, true)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_dashboard -> navController?.navigate(R.id.navigation_dashboard)
            R.id.action_search -> navController?.navigate(R.id.navigation_search_food)
            R.id.action_favorite -> navController?.navigate(R.id.navigation_favorite)
            R.id.action_settings -> navController?.navigate(R.id.navigation_settings)
        }
        return true
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        if (destination.id == R.id.navigation_search_food) binding.navView.menu.getItem(1).isChecked =
            true
    }

    override fun onBackPressed() {
        val fragment =
            this.supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as? NavHostFragment
        val currentFragment = fragment?.childFragmentManager?.fragments?.get(0) as? IOnBackPressed
        currentFragment?.onBackPressed()?.takeIf { !it }?.let {
            super.onBackPressed()
        }
    }
}