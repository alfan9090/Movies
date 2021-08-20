package com.ahmadalfan.movieapp.ui.home

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.ahmadalfan.movieapp.data.model.ResultMovie
import com.ahmadalfan.movieapp.data.model.TvResult
import com.ahmadalfan.movieapp.databinding.ActivityHomeBinding
import com.ahmadalfan.movieapp.ui.detail.DetailActivity
import com.ahmadalfan.movieapp.ui.movie.PopularMovieActivity
import com.ahmadalfan.movieapp.ui.tv.*
import com.ahmadalfan.movieapp.ui.movie.UpcomingActivity
import com.ahmadalfan.movieapp.utils.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.kodein.di.KodeinAware
import org.kodein.di.android.kodein
import org.kodein.di.generic.instance
import java.net.SocketTimeoutException

class HomeActivity : AppCompatActivity(), KodeinAware, TvAdapter.HomeAdapterListener,
    MovieAdapter.PopularAdapterListener {
    override val kodein by kodein()
    private var dialog: Dialog? = null
    private lateinit var job: Job
    private lateinit var viewModel: HomeViewModel
    private val factory: HomeViewModelFatory by instance()

    private var tvTodayArrayList: ArrayList<TvResult>? = ArrayList()
    private var tvPopularArrayList: ArrayList<TvResult>? = ArrayList()
    private var popularMovieArrayList: ArrayList<ResultMovie>? = ArrayList()
    private var upcomingMovieArrayList: ArrayList<ResultMovie>? = ArrayList()
    private var adapterTv: TvAdapter? = null
    private var adapterMovie: MovieAdapter? = null


    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        viewModel = ViewModelProvider(this, factory).get(HomeViewModel::class.java)
        job = Job()
        dialog = ProgressDialog.progressDialog(this)


        binding.tvListUpcoming.setOnClickListener {
            val intent = Intent(this, UpcomingActivity::class.java)
            startActivity(intent)
        }

        binding.tvListMoviePopular.setOnClickListener {
            val intent = Intent(this, PopularMovieActivity::class.java)
            startActivity(intent)
        }
        binding.tvListTvToday.setOnClickListener {
            val intent = Intent(this, TvAiringTodayActivity::class.java)
            startActivity(intent)
        }

        binding.tvListTvPopular.setOnClickListener {
            val intent = Intent(this, TvPopularActivity::class.java)
            startActivity(intent)
        }

        getListTvToday()
        getListPopularMovie()
        getListMovieUpcaming()
        getListTvPopular()

    }

    private fun getListTvToday() {
        job = lifecycleScope.launch {
            try {
                dialog?.show()
                val resp = viewModel.getTvToday(1)
                when (resp.code()) {
                    200 -> {
                        for (i in resp.body()!!.results!!.indices) {
                            val temp = resp.body()?.results!![i]
                            val articles = TvResult(
                                temp.backdrop_path,
                                temp.first_air_date,
                                temp.id,
                                temp.name,
                                temp.original_language,
                                temp.original_name,
                                temp.overview,
                                temp.popularity,
                                temp.poster_path,
                                temp.vote_average,
                                temp.vote_count,
                            )
                            tvTodayArrayList?.add(articles)
                            adapterTv =
                                TvAdapter(
                                    applicationContext,
                                    tvTodayArrayList!!,
                                    this@HomeActivity, ""
                                )
                            binding.rvTvRoday.also {
                                val iLayout = GridLayoutManager(applicationContext, 5)
                                it.layoutManager = iLayout
                                it.setHasFixedSize(true)
                            }
                            binding.rvTvRoday.adapter = adapterTv
                        }

                    }
                    400 -> {
                        toast("Terjadi kesalahan, silahkan coba lagi")
                    }

                }
                dialog?.dismiss()
            } catch (e: ApiException) {
                dialog?.dismiss()
                toast("Terjadi kesalahan, silahkan coba lagi")
            } catch (e: NoInternetException) {
                dialog?.dismiss()
                toast("No connection")
            } catch (e: SocketTimeoutException) {
                dialog?.dismiss()
                toast("Terjadi kesalahan, silahkan coba lagi")
            } catch (e: SslExpired) {
                dialog?.dismiss()
                toast("Terjadi kesalahan, silahkan coba lagi")
            } catch (e: TokenExpired) {
                dialog?.dismiss()
                toast("Terjadi kesalahan, silahkan coba lagi")
            }
        }

    }

    private fun getListPopularMovie() {
        job = lifecycleScope.launch {
            try {
                val resp = viewModel.getMoviePopularAtHome(1)
                when (resp.code()) {
                    200 -> {
                        for (i in resp.body()!!.results!!.indices) {
                            val temp = resp.body()?.results!![i]
                            val articles = ResultMovie(
                                temp.backdrop_path,
                                temp.id,
                                temp.original_language,
                                temp.original_title,
                                temp.overview,
                                temp.popularity,
                                temp.poster_path,
                                temp.release_date,
                                temp.title,
                                temp.vote_average,
                                temp.vote_count,
                            )
                            popularMovieArrayList?.add(articles)
                            adapterMovie =
                                MovieAdapter(
                                    applicationContext,
                                    popularMovieArrayList!!,
                                    this@HomeActivity, ""
                                )
                            binding.rvMoviePopular.also {
                                val iLayout = GridLayoutManager(applicationContext, 5)
                                it.layoutManager = iLayout
                                it.setHasFixedSize(true)
                            }
                            binding.rvMoviePopular.adapter = adapterMovie
                        }

                    }
                    400 -> {
                        toast("Terjadi kesalahan, silahkan coba lagi")
                    }
                }
            } catch (e: ApiException) {
                toast("Terjadi kesalahan, silahkan coba lagi")
            } catch (e: NoInternetException) {
                toast("No connection")
            } catch (e: SocketTimeoutException) {
                toast("Terjadi kesalahan, silahkan coba lagi")
            } catch (e: SslExpired) {
                toast("Terjadi kesalahan, silahkan coba lagi")
            } catch (e: TokenExpired) {
                toast("Terjadi kesalahan, silahkan coba lagi")
            }
        }

    }

    private fun getListMovieUpcaming() {
        job = lifecycleScope.launch {
            try {
                val resp = viewModel.getMovieUpcamingAtHome(1)
                when (resp.code()) {
                    200 -> {
                        for (i in resp.body()!!.results!!.indices) {
                            val temp = resp.body()?.results!![i]
                            val result = ResultMovie(
                                temp.backdrop_path,
                                temp.id,
                                temp.original_language,
                                temp.original_title,
                                temp.overview,
                                temp.popularity,
                                temp.poster_path,
                                temp.release_date,
                                temp.title,
                                temp.vote_average,
                                temp.vote_count,
                            )
                            upcomingMovieArrayList?.add(result)
                            adapterMovie =
                                MovieAdapter(
                                    applicationContext,
                                    upcomingMovieArrayList!!,
                                    this@HomeActivity, ""
                                )
                            binding.rvUpcoming.also {
                                val iLayout = GridLayoutManager(applicationContext, 5)
                                it.layoutManager = iLayout
                                it.setHasFixedSize(true)
                            }
                            binding.rvUpcoming.adapter = adapterMovie
                        }

                    }
                    400 -> {
                        toast("Terjadi kesalahan, silahkan coba lagi")
                    }
                }
            } catch (e: ApiException) {
                toast("Terjadi kesalahan, silahkan coba lagi")
            } catch (e: NoInternetException) {
                toast("No connection")
            } catch (e: SocketTimeoutException) {
                toast("Terjadi kesalahan, silahkan coba lagi")
            } catch (e: SslExpired) {
                toast("Terjadi kesalahan, silahkan coba lagi")
            } catch (e: TokenExpired) {
                toast("Terjadi kesalahan, silahkan coba lagi")
            }
        }

    }

    private fun getListTvPopular() {
        job = lifecycleScope.launch {
            try {
                val resp = viewModel.getTvPopulaAtHome(1)
                when (resp.code()) {
                    200 -> {
                        for (i in resp.body()!!.results!!.indices) {
                            val temp = resp.body()?.results!![i]
                            val articles = TvResult(
                                temp.backdrop_path,
                                temp.first_air_date,
                                temp.id,
                                temp.name,
                                temp.original_language,
                                temp.original_name,
                                temp.overview,
                                temp.popularity,
                                temp.poster_path,
                                temp.vote_average,
                                temp.vote_count,
                            )
                            tvPopularArrayList?.add(articles)
                            adapterTv =
                                TvAdapter(
                                    applicationContext,
                                    tvPopularArrayList!!,
                                    this@HomeActivity, ""
                                )
                            binding.rvTvPopular.also {
                                val iLayout = GridLayoutManager(applicationContext, 5)
                                it.layoutManager = iLayout
                                it.setHasFixedSize(true)
                            }
                            binding.rvTvPopular.adapter = adapterTv
                        }

                    }
                    400 -> {
                        toast("Terjadi kesalahan, silahkan coba lagi")
                    }
                }
            } catch (e: ApiException) {
                toast("Terjadi kesalahan, silahkan coba lagi")
            } catch (e: NoInternetException) {
                toast("No connection")
            } catch (e: SocketTimeoutException) {
                toast("Terjadi kesalahan, silahkan coba lagi")
            } catch (e: SslExpired) {
                toast("Terjadi kesalahan, silahkan coba lagi")
            } catch (e: TokenExpired) {
                toast("Terjadi kesalahan, silahkan coba lagi")
            }
        }

    }


    override fun onAdapteHomerSelect(tvResult: TvResult?) {
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
    }

    override fun onAdaptePopularSelect(resultMovie: ResultMovie?) {
        val intent = Intent(this, DetailActivity::class.java)
        startActivity(intent)
     }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
        dialog?.dismiss()
    }
}