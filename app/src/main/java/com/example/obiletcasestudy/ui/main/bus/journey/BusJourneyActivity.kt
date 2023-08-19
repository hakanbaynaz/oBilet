package com.example.obiletcasestudy.ui.main.bus.journey

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.navigation.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.obiletcasestudy.data.remote.Resource
import com.example.obiletcasestudy.databinding.ActivityBusJourneyBinding
import com.example.obiletcasestudy.ui.base.BaseActivity
import com.example.obiletcasestudy.ui.common.AppDialog
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.Locale
import javax.inject.Inject

@AndroidEntryPoint
class BusJourneyActivity : BaseActivity<ActivityBusJourneyBinding>() {

    @Inject
    lateinit var mSimpleDateFormat: SimpleDateFormat

    override fun getBinding(): ActivityBusJourneyBinding {
        return ActivityBusJourneyBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(viewBinding.toolbar)

        val busJourneyViewModel by viewModels<BusJourneyViewModel>()
        val args by navArgs<BusJourneyActivityArgs>()

        intent.extras ?: return

        supportActionBar?.title = args.title
        supportActionBar?.subtitle = mSimpleDateFormat.format(args.departureDate)

        val requestDateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        busJourneyViewModel.busJourneysLiveData.observe(this) {
            when (it) {
                Resource.Loading -> {
                    viewBinding.progressBar.visibility = View.VISIBLE
                    viewBinding.recyclerView.visibility = View.INVISIBLE
                }

                is Resource.Success -> {
                    viewBinding.progressBar.visibility = View.GONE
                    viewBinding.recyclerView.visibility = View.VISIBLE
                    val adapter = BusJourneyAdapter(it.data.data ?: listOf())
                    viewBinding.recyclerView.layoutManager = LinearLayoutManager(this)
                    viewBinding.recyclerView.adapter = adapter
                }

                is Resource.Error -> {
                    viewBinding.progressBar.visibility = View.GONE
                    AppDialog.showErrorDialog(this, {
                        busJourneyViewModel.getJourneys(
                            args.originId,
                            args.destinationId,
                            requestDateFormat.format(args.departureDate)
                        )
                    }, {
                        finish()
                    }, it.baseResponseModel.userMessage
                    )
                }
            }
        }

        busJourneyViewModel.getJourneys(
            args.originId, args.destinationId, requestDateFormat.format(args.departureDate)
        )
    }
}