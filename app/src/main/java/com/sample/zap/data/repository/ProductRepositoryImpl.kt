package com.sample.zap.data.repository

import com.sample.zap.core.bases.BaseRepository
import com.sample.zap.core.models.Output
import com.sample.zap.data.remote.ProductResponses
import com.sample.zap.data.remote.RemoteDataSource
import com.sample.zap.data.wrapper.toProductEntity
import com.sample.zap.domain.model.ProductListEntity
import com.sample.zap.domain.repository.ProductRepository

class ProductRepositoryImpl : BaseRepository(), ProductRepository {

    override fun product(): Output<List<ProductListEntity>> {
        // Create the Request to get list of product

        return safeApiCall(
            // Call the API to get the product responses
             remoteDataSource.makeRemoteCall(RemoteDataSource::class.java).productslist(),
            // Transform the API response to a list of ProductListEntity
            transform = { response: List<ProductResponses> ->
                response.toProductEntity()
            },
            // Provide a default response if necessary
            default = emptyList(), // or provide a default empty list if needed
            // Error message if the call fails
            error = "Failed to fetch product list"
        )
    }
}
