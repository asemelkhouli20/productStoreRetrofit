package com.example.productstoreretrofit.data

import com.example.productstoreretrofit.data.model.ProductX
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import java.lang.Exception

class ProductRepositoryImplementation(
    private val api: API
) : ProductRepository {
    override suspend fun getProductList(): Flow<Result<List<ProductX>>> {
        return flow {
            val productFromAPI = try {
                api.getProduct()

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Result.Error(message = e.message))
                return@flow
            } catch (e: HttpException) {
                emit(Result.Error(message = e.message))
                return@flow
            } catch (e: Exception) {
                emit(Result.Error(message = e.message))
                return@flow
            }
            emit(Result.Success(productFromAPI.products))
        }


    }
}