package com.sample.zap.ui.product

import ProductListViewModel
import android.os.Bundle
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

       /* val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            LinearLayoutManager.VERTICAL
        )
        dividerDrawable?.let { drawable ->
            dividerItemDecoration.setDrawable(drawable)
        }
        recyclerView.addItemDecoration(dividerItemDecoration)*/
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        networkMonitor = NetworkMonitor(this, this)


        // Call the ViewModel to fetch the books list
        productListViewModel.productList.observe(this) {
            loading.visibility = View.GONE

            when (it) {
                is Output.Success -> {
                    // Handle the successful response and update RecyclerView
                    loading.visibility = View.GONE
                    val productList = it.output
                    if(productList.isEmpty()) {
                        textView.text = "No Item"
                    } else {
                        updateRecyclerView(productList)
                    }

                }

                is Output.Error -> {
                    loading.visibility = View.GONE

                    // Show error message
                    Toast.makeText(
                        this,
                        """ ${it.statusCode} ${it.output.message}""",
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is Output.Loading -> {
                    // Show loading spinner
                    loading.visibility = View.VISIBLE
                }

                is Output.Exception -> loading.visibility = View.GONE
            }
        }
    }


    override fun onConnected() {
        // fetch product list from server
        productListViewModel.fetchProductList()
    }

    override fun onDisconnected() {
        Toast.makeText(this, R.string.unavailable_internet_connection, Toast.LENGTH_SHORT).show()
    }


    // Update RecyclerView adapter with the fetched productList
    private fun updateRecyclerView(booksList: List<ProductListEntity>) {
        val adapter = SectionAdapter(booksList)
        recyclerView.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        networkMonitor.register()  // Start listening for network changes
    }

    override fun onStop() {
        super.onStop()
        networkMonitor.unregister()  // Stop listening to network changes
    }
}