import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.sample.zap.core.bases.BaseViewModel
import com.sample.zap.core.models.Output
import com.sample.zap.data.remote.ErrorResponse
import com.sample.zap.domain.model.ProductListEntity
import com.sample.zap.domain.usecase.ProductUseCase
import kotlinx.coroutines.launch

class ProductListViewModel(
    private val productUseCase: ProductUseCase
) : BaseViewModel(productUseCase) {

    private val _productList = MutableLiveData<Output<List<ProductListEntity>>>()
    val productList: LiveData<Output<List<ProductListEntity>>> = _productList


    // Fetch the list of product from the API
    fun fetchProductList() {

        // Update LiveData to indicate loading state
        _productList.postValue(Output.Loading())

        // Launch coroutine to fetch product list
        viewModelScope.launch {
            try {
                val result = productUseCase.run()  // Run the use case to fetch data
               // println("ProductListViewModel ==>  $result")
                _productList.postValue(result)  // Post result to LiveData
            } catch (e: Exception) {
                _productList.postValue(
                    Output.Error(
                        statusCode = -1,
                        output = ErrorResponse(message = e.message ?: "Unknown error")
                    )
                )
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        productUseCase.onCleared()
    }
}
