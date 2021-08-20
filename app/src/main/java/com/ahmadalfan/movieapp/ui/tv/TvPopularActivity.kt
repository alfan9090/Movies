package com.ahmadalfan.movieapp.ui.tv

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.ahmadalfan.movieapp.R
import com.ahmadalfan.movieapp.data.network.NetworkState
import com.ahmadalfan.movieapp.databinding.ActivityTvPopularBinding
import com.ahmadalfan.movieapp.utils.ProgressDialog
import kotlinx.coroutines.Job
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance

class TvPopularActivity : AppCompatActivity(), KodeinAware {
    override val kodein by kodein()
    private val factory: TvViewModelFactory by instance()
    private lateinit var viewModel: TvViewModel
    private var dialog: Dialog? = null
    private lateinit var job: Job
    private var adapter: AdapterTv? = null
    private lateinit var binding: ActivityTvPopularBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTvPopularBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        viewModel = ViewModelProvider(this, factory).get(TvViewModel::class.java)
        job = Job()
        dialog = ProgressDialog.progressDialog(this)

        adapter = AdapterTv(this)

        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.adapter = adapter

        loadData()
    }

    private fun loadData() {
        val getListItems =
            viewModel.getTvairivingTodayPageList()
        getListItems.observe(this, {
            loadNetworkState()
            adapter?.submitList(it)
        })
    }

    private fun loadNetworkState() {
        val getNetworkState = viewModel.networkStateTransaction()
        getNetworkState.observe(this, {
            binding.progressBar.visibility =
                if (it == NetworkState.LOADING) View.VISIBLE else View.GONE

            when (it) {
                NetworkState.ERROR -> {
                    binding.rlError.visibility = View.VISIBLE
                    binding.txtError.text = getString(R.string.text_error)
                    binding.recyclerView.visibility = View.GONE
                }
                NetworkState.NODATA -> {
                    binding.rlError.visibility = View.VISIBLE
                    binding.txtError.text = getString(R.string.err_empty_nodata)
                    binding.recyclerView.visibility = View.GONE
                }
                NetworkState.NOINTERNET -> {
                    binding.recyclerView.visibility = View.GONE
                    binding.rlError.visibility = View.VISIBLE
                    binding.txtError.text = getString(R.string.err_no_connection)
                }
                NetworkState.LOADED -> {
                    adapter?.setNetworkState(it)
                    binding.recyclerView.visibility = View.VISIBLE
                    binding.rlError.visibility = View.GONE
                }
            }
        })
    }

}