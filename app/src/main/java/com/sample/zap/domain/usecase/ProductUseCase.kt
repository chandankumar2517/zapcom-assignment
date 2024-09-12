package com.sample.zap.domain.usecase

import com.sample.zap.core.bases.BaseUseCase
import com.sample.zap.core.models.Output
import com.sample.zap.domain.model.ProductListEntity
import com.sample.zap.domain.repository.ProductRepository

class ProductUseCase(private var productRepository: ProductRepository) : BaseUseCase<List<ProductListEntity>>() {

    override suspend fun run(): Output<List<ProductListEntity>> {
        return productRepository.product()
    }
}