package com.sample.zap.ui.product

import ProductListViewModel
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sample.zap.R
import com.sample.zap.core.bases.BaseActivity
import com.sample.zap.core.models.Output
import com.sample.zap.domain.model.ProductListEntity
import com.sample.zap.ui.adapter.SectionAdapter
import org.koin.androidx.viewmodel.ext.android.viewModel

class ProductListActivity : BaseActivity() {

    override fun mainLayout(): Int = R.layout.activity_product

    private val productListViewModel: ProductListViewModel by viewModel()

    private lateinit var loading: ProgressBar
    private lateinit var recyclerView: RecyclerView


    // initialize UI
    override fun initView() {

        loading = findViewById(R.id.loading)
        recyclerView = findViewById(R.id.recyclerView)


        recyclerView.layoutManager = LinearLayoutManager(this)

        val dividerDrawable = ContextCompat.getDrawable(this, R.drawable.divider)
        val dividerItemDecoration = DividerItemDecoration(
            recyclerView.context,
            LinearLayoutManager.VERTICAL
        )
        dividerDrawable?.let { drawable ->
            dividerItemDecoration.setDrawable(drawable)
        }
        recyclerView.addItemDecoration(dividerItemDecoration)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // fetch product list from server
        productListViewModel.fetchProductList()


        // Call the ViewModel to fetch the books list
        productListViewModel.productList.observe(this) {
            loading.visibility = View.GONE

            when (it) {
                is Output.Success -> {
                    // Handle the successful response and update RecyclerView
                    loading.visibility = View.GONE
                    val productList = it.output
                    updateRecyclerView(productList)
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
            }
        }
    }


    // Update RecyclerView adapter with the fetched booksList
    private fun updateRecyclerView(booksList: List<ProductListEntity>) {
        val adapter = SectionAdapter(booksList)
        recyclerView.adapter = adapter
    }

}