package com.example.huertohogar.views

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.huertohogar.viewmodels.ProductDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetailScreen(
    productId: Int,
    onNavigateUp: () -> Unit,
    viewModel: ProductDetailViewModel = viewModel()
) {
    val producto by viewModel.product.collectAsState()

    LaunchedEffect(productId) {
        viewModel.loadProduct(productId)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalle del producto") },
                navigationIcon = {
                    IconButton(onClick = onNavigateUp) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                }
            )
        }
    ) { padding ->

        producto?.let { p ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(16.dp)
                    .fillMaxSize()
            ) {
                AsyncImage(
                    model = p.imagen,
                    contentDescription = p.nombre,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )

                Spacer(Modifier.height(16.dp))

                Text(text = p.nombre, style = MaterialTheme.typography.headlineMedium)
                Spacer(Modifier.height(8.dp))

                Text(text = "Categoría: ${p.categoria_id}")
                Spacer(Modifier.height(8.dp))

                Text(text = "Precio: $${p.precio}", style = MaterialTheme.typography.bodyLarge)
                Spacer(Modifier.height(16.dp))

                Text(text = p.descripcion)
            }
        } ?: Text(
            text = "Cargando...",
            modifier = Modifier
                .padding(padding)
                .padding(24.dp)
        )
    }
}
