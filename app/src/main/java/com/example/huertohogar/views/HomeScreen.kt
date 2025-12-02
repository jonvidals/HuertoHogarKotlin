package com.example.huertohogar.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import com.example.huertohogar.navigation.Screen
import com.example.huertohogar.viewmodels.MainViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreenWithDrawer(
    viewModel: MainViewModel,
    onNavigateToCatalog: () -> Unit
) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val openDrawer: () -> Unit = {
        scope.launch { drawerState.open() }
    }

    val closeDrawer: () -> Unit = {
        scope.launch { drawerState.close() }
    }

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet {
                DrawerContent(
                    onCatalogClick = {
                        closeDrawer()
                        onNavigateToCatalog()
                    },
                    onProfileClick = {
                        closeDrawer()
                        viewModel.navigateTo(Screen.Profile)
                    },
                    onCloseDrawer = closeDrawer
                )
            }
        },
        content = {
            Scaffold(
                topBar = {
                    HuertoHogarTopBar(
                        onMenuClick = openDrawer,
                        onProfileClick = { viewModel.navigateTo(Screen.Profile) }
                    )
                },
                content = { paddingValues ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddingValues),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            
                        androidx.compose.foundation.Image(
                            painter = androidx.compose.ui.res.painterResource(com.example.huertohogar.R.drawable.logo_sample),
                            contentDescription = "Logo",
                            modifier = androidx.compose.ui.Modifier.size(120.dp)
                        )
                        androidx.compose.foundation.layout.Spacer(modifier = androidx.compose.ui.Modifier.height(12.dp))
Text(
                                "Bienvenido a Huerto Hogar",
                                style = MaterialTheme.typography.headlineMedium
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Button(onClick = onNavigateToCatalog) {
                                Text("Ver Catálogo")
                            }
                        }
                    }
                }
            )
        }
    )
}

@Composable
fun DrawerContent(
    onCatalogClick: () -> Unit,
    onProfileClick: () -> Unit,
    onCloseDrawer: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(vertical = 16.dp)
    ) {
        Text(
            text = "Huerto Hogar",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = MaterialTheme.colorScheme.primary
        )

        HorizontalDivider(modifier = Modifier.padding(vertical = 8.dp))

        DrawerMenuItem(
            icon = Icons.Filled.Home,
            label = "Inicio",
            onClick = onCloseDrawer
        )

        DrawerMenuItem(
            icon = Icons.Filled.ShoppingCart,
            label = "Catálogo de Productos",
            onClick = onCatalogClick
        )

        DrawerMenuItem(
            icon = Icons.Filled.Person,
            label = "Perfil",
            onClick = onProfileClick
        )
    }
}

@Composable
fun DrawerMenuItem(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = label,
            tint = MaterialTheme.colorScheme.primary
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = label,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HuertoHogarTopBar(onMenuClick: () -> Unit, onProfileClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Huerto Hogar",
                color = Color.White
            )
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(Icons.Filled.Menu, contentDescription = "Menú", tint = Color.White)
            }
        },
        actions = {
            IconButton(onClick = onProfileClick) {
                Icon(Icons.Filled.AccountCircle, contentDescription = "Perfil", tint = Color.White)
            }
        },
        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary
        )
    )
}