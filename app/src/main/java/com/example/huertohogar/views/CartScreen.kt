package com.example.huertohogar.views

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.huertohogar.viewmodels.CartViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CartScreen(
    onBackClick: () -> Unit,
    cartViewModel: CartViewModel = viewModel()
) {
    val items by cartViewModel.items.collectAsState(initial = emptyList())
    val total by remember(items) { mutableStateOf(cartViewModel.total()) }
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Carrito") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Atrás")
                    }
                }
            )
        },
        bottomBar = {
            Surface(tonalElevation = 2.dp) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        "Total: $${cartViewModel.total()}",
                        fontWeight = FontWeight.Bold
                    )
                    Button(onClick = { }) {
                        Text("Comprar")
                    }
                }
            }
        }
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            items(items) { item ->
                ListItem(
                    headlineContent = { Text(item.product.nombre) },
                    supportingContent = {
                        Text("Cantidad: ${item.quantity}")
                    },
                    trailingContent = {
                        Row {
                            TextButton(
                                onClick = { cartViewModel.removeOne(item.product.id) }
                            ) {
                                Text("-")
                            }
                            Spacer(Modifier.width(8.dp))
                            TextButton(
                                onClick = { cartViewModel.add(item.product) }
                            ) {
                                Text("+")
                            }
                        }
                    }
                )
                Divider()
            }
        }
    }
}
