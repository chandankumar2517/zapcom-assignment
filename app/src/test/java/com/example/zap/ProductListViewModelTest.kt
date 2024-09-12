package com.example.zap

import ProductListViewModel
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.sample.zap.core.models.Output
import com.sample.zap.domain.model.Item
import com.sample.zap.domain.model.ProductListEntity
import com.sample.zap.domain.usecase.ProductUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.*
import org.junit.*
import org.junit.rules.TestRule
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class ProductListViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule() // To execute LiveData synchronously

    @Mock
    private lateinit var productUseCase: ProductUseCase

    private lateinit var productListViewModel: ProductListViewModel

    // For controlling coroutines execution in tests
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher) // Set the test dispatcher as the main dispatcher

        productListViewModel = ProductListViewModel(productUseCase)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // Reset the main dispatcher
    }

    @Test
    fun `fetchProductList should update LiveData with loading and success states`() = runTest {
        // Arrange
        val mockItemList1 = listOf( Item(title = "Item1", image = "http://example.com/image1.jpg"),
            Item(title = "Item2", image = "http://example.com/image2.jpg"))

        val mockItemList2 = listOf(
            Item(title = "Item3", image = "http://example.com/image3.jpg"),
            Item(title = "Item4", image = "http://example.com/image4.jpg")
        )

        val mockProductList = listOf(
            ProductListEntity("Banner", mockItemList1),
            ProductListEntity("horizontalFreeScroll", mockItemList2)
        )

        `when`(productUseCase.run()).thenReturn(Output.Success(mockProductList))

        // Act
        productListViewModel.fetchProductList()

        // Simulate the passage of time for coroutines
        advanceUntilIdle()

        // Assert
        Assert.assertTrue(productListViewModel.productList.value is Output.Loading)
        Assert.assertTrue(productListViewModel.productList.value is Output.Success)
        Assert.assertEquals(mockProductList, (productListViewModel.productList.value as Output.Success).output)
    }

    @Test
    fun `fetchProductList should update LiveData with error state on failure`() = runTest {
        // Arrange
        val exception = Exception("Network error")
        `when`(productUseCase.run()).thenThrow(exception)

        // Act
        productListViewModel.fetchProductList()

        // Simulate the passage of time for coroutines
        advanceUntilIdle()

        // Assert
        Assert.assertTrue(productListViewModel.productList.value is Output.Loading)
        Assert.assertTrue(productListViewModel.productList.value is Output.Error)
        Assert.assertEquals(
            "Network error",
            (productListViewModel.productList.value as Output.Error).output.message
        )
    }
}