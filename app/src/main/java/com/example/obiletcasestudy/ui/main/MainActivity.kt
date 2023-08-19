package com.example.obiletcasestudy.ui.main

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.fragment.NavHostFragment
import com.example.obiletcasestudy.R
import com.example.obiletcasestudy.data.remote.Resource
import com.example.obiletcasestudy.databinding.ActivityMainBinding
import com.example.obiletcasestudy.ui.base.BaseActivity
import com.example.obiletcasestudy.ui.common.AppDialog
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : BaseActivity<ActivityMainBinding>() {

    override fun getBinding(): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val mainViewModel by viewModels<MainViewModel>()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController

        viewBinding.appToggleButton.setOnButtonCheckedListener {
            if (it == R.id.buttonBus) {
                navController.navigate(R.id.action_global_busFragment)
            } else {
                navController.navigate(R.id.action_global_flightFragment)
            }
        }

        //Initialize location values for the first time.
        mainViewModel.busLocationsLiveData.observe(this) {
            when (it) {
                is Resource.Loading -> {
                    viewBinding.loaderView.visibility = View.VISIBLE
                }

                is Resource.Success -> {
                    viewBinding.loaderView.visibility = View.GONE
                }

                is Resource.Error -> {
                    AppDialog.showErrorDialog(
                        this,
                        { mainViewModel.init() },
                        { finish() },
                        it.baseResponseModel.userMessage
                    )
                }
            }
        }
    }

}