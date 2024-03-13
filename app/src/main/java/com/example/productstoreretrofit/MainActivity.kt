package com.example.productstoreretrofit

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.productstoreretrofit.data.ProductRepositoryImplementation
import com.example.productstoreretrofit.data.model.ProductX
import com.example.productstoreretrofit.presntation.ProductViewModel
import com.example.productstoreretrofit.ui.theme.ProductStoreRetrofitTheme
import kotlinx.coroutines.flow.collectLatest

class MainActivity : ComponentActivity() {
    private val vm by viewModels<ProductViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return ProductViewModel(ProductRepositoryImplementation(api = RetrofitInstance.api))
                            as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ProductStoreRetrofitTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val productList = vm.product.collectAsState().value
                    val context = LocalContext.current
                    LaunchedEffect(key1 = vm.showErrorToastChannel) {
                        vm.showErrorToastChannel.collectLatest {
                            if (it) {
                                Toast.makeText(
                                    context, "ERROR", Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                    if (productList.isEmpty()) {
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.fillMaxSize(),
                        ) {
                            CircularProgressIndicator()
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            contentPadding = PaddingValues(20.dp)
                        ) {
                            items(productList.size) { index ->
                                ProductCart(product = productList[index])
                                Spacer(modifier = Modifier.padding(20.dp))
                            }

                        }
                    }

                }
            }
        }
    }
}

@Composable
fun ProductCart(product: ProductX) {
    val imgState = rememberAsyncImagePainter(
        model = ImageRequest
            .Builder(LocalContext.current)
            .data(product.thumbnail)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .height(300.dp)
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
    ) {
        val modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
        if (imgState is AsyncImagePainter.State.Success) {
            Image(painter = imgState.painter, contentDescription = null,modifier=modifier, contentScale = ContentScale.Crop)
        } else {
            Image(painter = painterResource(id = R.drawable.ic_launcher_foreground), contentDescription = null,modifier=modifier)
        }
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = product.title+" -- Price: ${product.price}$",
            modifier = Modifier.padding(horizontal = 16.dp),
            fontSize = 16.sp,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(5.dp))
        Text(
            text = product.description,
            modifier = Modifier.padding(horizontal = 5.dp),
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold
        )


    }

}











