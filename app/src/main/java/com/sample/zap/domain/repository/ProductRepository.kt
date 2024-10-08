package com.sample.zap.domain.repository

import com.sample.zap.core.models.Output
import com.sample.zap.domain.model.ProductListEntity

interface ProductRepository {
     suspend fun product() : Output<List<ProductListEntity>>
}