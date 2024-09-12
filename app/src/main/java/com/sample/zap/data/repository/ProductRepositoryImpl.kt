package com.sample.zap.data.repository

import com.sample.zap.core.bases.BaseRepository
import com.sample.zap.core.models.Output
import com.sample.zap.data.remote.ProductResponses
import com.sample.zap.data.wrapper.toProductEntity
import com.sample.zap.domain.model.ProductListEntity
import com.sample.zap.domain.repository.ProductRepository

class ProductRepositoryImpl : BaseRepository(), ProductRepository {

    override suspend fun product(): Output<List<ProductListEntity>> {
        return safeApiCall(
            call = { remoteDataSource.productslist() },
            transform = { response: List<ProductResponses> -> response.toProductEntity() },
            default = emptyList(),
            error = "Failed to fetch product list"
        )
    }
}