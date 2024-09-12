package com.sample.zap.ui.product

import ProductListViewModel
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.zap.R
import com.sample.zap.core.bases.BaseActivity
import com.sample.zap.core.models.Output
import com.sample.zap.data.util.NetworkMonitor
import com.sample.zap.data.util.NetworkUtil
import com.sample.zap.domain.model.ProductListEntity
import com.sample.zap.ui.adapter.SectionAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListActivity : BaseActivity(), NetworkMonitor.NetworkCallback {

    override fun mainLayout(): Int = R.layout.activity_product

    private val productListViewModel: ProductListViewModel by viewModel()

    private lateinit var networkMonitor: NetworkMonitor


    private lateinit var loading: ProgressBar
    private lateinit var recyclerView: RecyclerView
    private lateinit var textView: TextView


    // initialize UI
    override fun initView() {

        loading = findViewById(R.id.loading)
        recyclerView = findViewById(R.id.recyclerView)
        textView = findViewById(R.id.textViewErrorMessage)


        recyclerView.layoutManager = LinearLayoutManager(this)

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkMonitor = NetworkMonitor(this, this)

        // Check network status immediately
        if (NetworkUtil.isInternetAvailable(this)) {
            // Fetch product list from server if network is available
            productListViewModel.fetchProductList()
        } else {
            // Network is not available
            onDisconnected()
        }


        // Call the ViewModel to fetch the books list
        productListViewModel.productList.observe(this) {
            loading.visibility = View.GONE

            when (it) {
                is Output.Success -> {
                    Log.d("ProductListActivity", "Success")
                    // Handle the successful response and update RecyclerView
                    loading.visibility = View.GONE
                    val productList = it.output
                    if(productList.isEmpty()) {
                        Log.d("ProductListActivity", "Success1 ")
                        textView.visibility = View.VISIBLE
                        textView.text = "No Item Found"
                    } else {
                        updateRecyclerView(productList)
                    }

                }

                is Output.Error -> {
                    Log.d("ProductListActivity", "Error")
                    loading.visibility = View.GONE

                    // Show error message
                    Toast.makeText(
                        this,
                        """ ${it.statusCode} ${it.output.message}""",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Output.Loading -> {
                    Log.d("ProductListActivity", "Loading")
                    // Show loading spinner
                    loading.visibility = View.VISIBLE
                }

                is Output.Exception -> {
                    Log.d("ProductListActivity", "Exception")
                    loading.visibility = View.GONE
                }
            }
        }
    }


    override fun onConnected() {
        Log.d("ProductListActivity", "onConnected")
        runOnUiThread {
            textView.visibility = View.GONE
            // fetch product list from server
            productListViewModel.fetchProductList()
        }
    }

    override fun onDisconnected() {
        Log.d("ProductListActivity", "onDisconnected")
        runOnUiThread {
            textView.visibility = View.VISIBLE
            textView.text = resources.getString(R.string.unavailable_internet_connection)
        }
    }


    // Update RecyclerView adapter with the fetched productList
    private fun updateRecyclerView(booksList: List<ProductListEntity>) {
        val adapter = SectionAdapter(booksList)
        recyclerView.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        // Recheck network status when the activity comes back into view
        if (NetworkUtil.isInternetAvailable(this)) {
            productListViewModel.fetchProductList()
        } else {
            onDisconnected()
        }
    }

    override fun onPause() {
        super.onPause()
        networkMonitor.unregister()
    }

    override fun onStart() {
        Log.d("ProductListActivity", "onStart")
        super.onStart()
        networkMonitor.register()  // Start listening for network changes
    }

    override fun onStop() {
        Log.d("ProductListActivity", "onStop")
        super.onStop()
        networkMonitor.unregister()  // Stop listening to network changes
    }



}