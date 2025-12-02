package com.example.huertohogar.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.huertohogar.model.Producto
import com.example.huertohogar.viewmodels.CartViewModel
import com.example.huertohogar.viewmodels.ProductListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListScreen(
    listViewModel: ProductListViewModel,
    cartViewModel: CartViewModel,
    onProductClick: (Int) -> Unit,
    onBackClick: () -> Unit,
    onCartClick: () -> Unit
) {
    val productos by listViewModel.products.collectAsState()
    val error by listViewModel.error.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    var addedProductName by remember { mutableStateOf<String?>(null) }

    LaunchedEffect(Unit) {
        listViewModel.loadFromApi()
    }

    LaunchedEffect(addedProductName) {
        addedProductName?.let { name ->
            snackbarHostState.showSnackbar(
                message = "Se agregó $name al carrito",
                duration = SnackbarDuration.Short
            )
            addedProductName = null
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver"
                        )
                    }
                },
                title = { Text("Listado de productos") },
                actions = {
                    TextButton(onClick = onCartClick) {
                        Text("Carrito", color = MaterialTheme.colorScheme.primary)
                    }
                }
            )
        }
    ) { padding ->

        if (error != null) {
            Text(
                text = error ?: "",
                modifier = Modifier.padding(20.dp),
                color = MaterialTheme.colorScheme.error
            )
        }

        LazyColumn(
            modifier = Modifier
                .padding(padding)
                .fillMaxSize()
        ) {
            items(productos) { producto ->
                ProductListItem(
                    producto = producto,
                    onClick = { onProductClick(producto.id) },

                    onAddToCart = {
                        cartViewModel.add(producto)
                        addedProductName = producto.nombre
                    }
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductListItem(
    producto: Producto,
    onClick: () -> Unit,
    onAddToCart: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        onClick = onClick
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = producto.imagen,
                contentDescription = producto.nombre,
                modifier = Modifier
                    .size(90.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.width(16.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = producto.nombre,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = producto.categoria_nombre,
                    style = MaterialTheme.typography.bodyMedium
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$${producto.precio}",
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Black
                )
            }

            Button(
                onClick = onAddToCart,
                shape = RoundedCornerShape(8.dp)
            ) {
                Text("Agregar")
            }
        }
    }
}
