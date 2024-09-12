import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.sample.zap.core.bases.BaseViewModel
import com.sample.zap.core.models.Output
import com.sample.zap.domain.model.ProductListEntity
import com.sample.zap.domain.usecase.ProductUseCase

class ProductListViewModel(
    private val productUseCase: ProductUseCase
) : BaseViewModel(productUseCase) {

    private val _productList = MutableLiveData<Output<List<ProductListEntity>>>()
    val productList: LiveData<Output<List<ProductListEntity>>> = _productList



    // Fetch the list of product from the API
    fun fetchProductList() {

        // Update LiveData to indicate loading state
        _productList.value = Output.Loading()

        // Invoke the use case and pass a lambda to handle the result
        productUseCase {
            // Update LiveData with the result (success or error)
            _productList.value = it
        }
    }


    override fun onCleared() {
        super.onCleared()
        productUseCase.onCleared()
    }
}
