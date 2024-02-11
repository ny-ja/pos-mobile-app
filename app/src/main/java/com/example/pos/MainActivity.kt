package com.example.pos

import android.content.res.Configuration
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MonetizationOn
import androidx.compose.material.icons.filled.Payment
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ShoppingBag
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.SwapHoriz
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Badge
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.DialogProperties
import coil.compose.rememberImagePainter
import com.example.pos.ui.theme.POSTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            POSTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }
}

@Composable
fun App() {
    val isLoggedIn = remember { mutableStateOf(false) }

    if (isLoggedIn.value) {
        HomeDashboard()
    } else {
        LoginPage(onLoginSuccess = { isLoggedIn.value = true })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginPage(onLoginSuccess: () -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }
    var rememberMe by remember { mutableStateOf(false) }
    val context = LocalContext.current

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            Spacer(modifier = Modifier.height(50.dp))
            Image(
                painter = painterResource(id = R.drawable.logo),
                contentDescription = "App Logo",
                modifier = Modifier.size(120.dp)
            )
            Spacer(modifier = Modifier.height(30.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                shape = RoundedCornerShape(8.dp),
                elevation = CardDefaults.elevatedCardElevation(8.dp)
            ) {
                Column(
                    modifier = Modifier
                        .padding(16.dp)
                        .fillMaxWidth()
                ) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { username = it },
                        label = { Text("Username") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    OutlinedTextField(
                        value = password,
                        onValueChange = { password = it },
                        label = { Text("Password") },
                        singleLine = true,
                        visualTransformation = if (passwordVisible) VisualTransformation.None else PasswordVisualTransformation(),
                        trailingIcon = {
                            val image = if (passwordVisible)
                                Icons.Filled.Visibility
                            else
                                Icons.Filled.VisibilityOff

                            IconButton(onClick = { passwordVisible = !passwordVisible }) {
                                Icon(image, "Toggle password visibility")
                            }
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Checkbox(
                            checked = rememberMe,
                            onCheckedChange = { rememberMe = it },
                            colors = CheckboxDefaults.colors(
                                checkedColor = MaterialTheme.colorScheme.primary
                            )
                        )
                        Text(text = "Remember Me", fontSize = 16.sp)
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = {
                            if (username == "" && password == "") {
                                onLoginSuccess()
                            } else {
                                Toast.makeText(context, "Invalid credentials", Toast.LENGTH_SHORT).show()
                            }
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(8.dp)
                    ) {
                        Text("Login", fontSize = 18.sp)
                    }
                }
            }
        }
    }
}

data class BottomNavItem(
    val name: String,
    val route: String,
    val icon: ImageVector
)

val bottomNavItems = listOf(
    BottomNavItem("Home", "home", Icons.Filled.Home),
    BottomNavItem("Products", "products", Icons.Filled.ShoppingBag),
    BottomNavItem("Settings", "settings", Icons.Filled.Settings),
)

data class Product(
    val id: Int,
    var productName: String,
    var price: Double,
    var stock: MutableState<Int>,
    val image: Int,
    var quantity: MutableState<Int>,
    val description: String
)


@Composable
fun HomeScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            "Dashboard",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Divider()

        // First row of cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HomeCard("Total Sales", "₱1234", MaterialTheme.colorScheme.primaryContainer, Icons.Default.MonetizationOn)
            HomeCard("Average Sale", "₱56.78", MaterialTheme.colorScheme.secondaryContainer,
                Icons.AutoMirrored.Filled.TrendingUp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Second row of cards
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HomeCard("Transactions", "89", MaterialTheme.colorScheme.tertiaryContainer, Icons.Default.SwapHoriz)
            HomeCard("Products Sold", "321", MaterialTheme.colorScheme.errorContainer, Icons.Default.ShoppingCart)
        }
    }
}

@Composable
fun HomeCard(title: String, value: String, backgroundColor: Color, icon: ImageVector) {
    Card(
        modifier = Modifier
            .padding(8.dp)
            .width(180.dp)
            .height(120.dp),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(containerColor = backgroundColor)
    ) {
        Column(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Icon(
                icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimaryContainer
            )
            Text(text = title, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimaryContainer)
            Text(text = value, style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.onPrimaryContainer)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeDashboard() {
    val products = remember {
        mutableStateOf(
            listOf(
                Product(id = 1, productName = "Smartphone", price = 299.99, stock = mutableStateOf(10), R.drawable.smartphone, quantity = mutableStateOf(0), " A portable device that combines mobile communication and computing functions."),
                Product(id = 2, productName = "Laptop", price = 800.00, stock = mutableStateOf(5), R.drawable.laptop, quantity = mutableStateOf(0), "A compact, portable personal computer with a clam-shell form factor."),
                Product(id = 3, productName = "Smartwatch", price = 199.99, stock = mutableStateOf(8), R.drawable.smartwatch, quantity = mutableStateOf(0), "A wearable device that offers smartphone-like functionalities on your wrist."),
                Product(id = 4, productName = "Wireless Earbuds", price = 59.99, stock = mutableStateOf(15), R.drawable.earbud, quantity = mutableStateOf(0), "A small, wireless audio device that fits inside the ear canal."),
                Product(id = 5, productName = "E-Reader", price = 129.99, stock = mutableStateOf(7), R.drawable.ereader, quantity = mutableStateOf(0), "A portable electronic device designed primarily for reading digital e-books and periodicals."),
                Product(id = 6, productName = "Portable Speaker", price = 70.00, stock = mutableStateOf(12), R.drawable.speaker, quantity = mutableStateOf(0), "An audio output device that converts electrical signals into sound waves."),
                Product(id = 7, productName = "Tablet", price = 250.00, stock = mutableStateOf(9), R.drawable.tablet, quantity = mutableStateOf(0), "A portable touchscreen computer in a slate form factor."),
                Product(id = 8, productName = "Gaming Console", price = 499.99, stock = mutableStateOf(4), R.drawable.gaming_console, quantity = mutableStateOf(0), " A dedicated electronic device designed to play video games on a display."),
                Product(id = 9, productName = "VR Headset", price = 350.00, stock = mutableStateOf(6), R.drawable.vr_headset, quantity = mutableStateOf(0), "A head-mounted device that provides virtual reality experiences."),
                Product(id = 10, productName = "Fitness Tracker", price = 99.99, stock = mutableStateOf(10), R.drawable.fitness_tracker, quantity = mutableStateOf(0), "A wearable device that monitors and records physical activity and health-related data.")
            )
        )
    }
    var selectedProduct by remember { mutableStateOf<Product?>(null) }
    val cart = remember { mutableStateListOf<Product>() }
    val selectedRoute = remember { mutableStateOf("home") }
    var showCheckoutDialog by remember { mutableStateOf(false) }
    var cashGiven by remember { mutableStateOf("") }
    var showCashPaymentDialog by remember { mutableStateOf(false) }
    var showReceiptDialog by remember { mutableStateOf(false) }
    var changeGiven by remember { mutableStateOf(0.0) }
    var totalForCheckout by remember { mutableStateOf(0.0) }

    fun onCheckout() {
        if (cart.isNotEmpty()) {
            val subtotal = cart.sumOf { it.price * it.quantity.value }
            val taxRate = 0.12  // 12% tax rate
            val taxAmount = subtotal * taxRate
            totalForCheckout = subtotal + taxAmount
            showCheckoutDialog = true
        }
    }

    fun removeFromCart(product: Product) {
        cart.remove(product)
        products.value.find { it.id == product.id }?.let {
            it.stock.value += product.quantity.value
        }
    }

    fun handleAddToCart(product: Product) {
        if (product.stock.value > 0) {
            val existingProduct = cart.firstOrNull { it.id == product.id }
            if (existingProduct != null) {
                existingProduct.quantity.value++
                product.stock.value--
            } else {
                val newProduct = product.copy(quantity = mutableStateOf(1))
                cart.add(newProduct)
                product.stock.value--
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("POS") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle navigation icon click */ }) {
                        Icon(Icons.Filled.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    val cartItemCount = cart.sumOf { it.quantity.value }
                    Box(contentAlignment = Alignment.TopEnd) {
                        IconButton(onClick = { selectedRoute.value = "cart" }) {
                            Icon(Icons.Filled.ShoppingCart, contentDescription = "Cart")
                        }
                        if (cartItemCount > 0) {
                            Badge(
                                containerColor = MaterialTheme.colorScheme.error,
                                modifier = Modifier.align(Alignment.TopEnd)
                            ) {
                                Text(text = cartItemCount.toString())
                            }
                        }
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                bottomNavItems.forEach { item ->
                    NavigationBarItem(
                        icon = { Icon(item.icon, contentDescription = item.name) },
                        label = { Text(item.name) },
                        selected = item.route == selectedRoute.value,
                        onClick = { selectedRoute.value = item.route }
                    )
                }
            }
        },
        content = { innerPadding ->
            Box(modifier = Modifier.padding(innerPadding)) {
                when (selectedRoute.value) {
                    "home" -> HomeScreen()
                    "products" -> Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text(
                            "Products",
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.primary
                        )

                        Divider()

                        LazyColumn(
                            contentPadding = PaddingValues(2.dp),
                            verticalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            items(products.value) { product ->
                                ProductCard(
                                    product = product,
                                    onClick = { handleAddToCart(product) },
                                    onProductClick = { selectedProduct = it }
                                )
                            }
                        }
                    }
                    "settings" -> SettingScreen()
                    "cart" -> CartScreen(cart, onRemoveFromCart = { product -> removeFromCart(product) }, onCheckout = { onCheckout() })
                }
            }
        }
    )

    selectedProduct?.let { product ->
        ProductDetailsDialog(product = product) {
            selectedProduct = null // Reset the selected product on dialog dismiss
        }
    }

    if (showCheckoutDialog) {
        AlertDialog(
            onDismissRequest = { showCheckoutDialog = false },
            title = { Text("Confirm Checkout") },
            text = { Text("Are you sure you want to complete your purchase?") },
            confirmButton = {
                Button(
                    onClick = {
                        showCheckoutDialog = false
                        showCashPaymentDialog = true // Open cash payment dialog
                        cashGiven = ""
                    }
                ) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(onClick = { showCheckoutDialog = false }) {
                    Text("No")
                }
            }
        )
    }

    if (showCashPaymentDialog) {
        AlertDialog(
            onDismissRequest = { showCashPaymentDialog = false },
            title = {
                Text(
                    "Cash Payment",
                    style = MaterialTheme.typography.titleLarge,
                    color = MaterialTheme.colorScheme.onPrimaryContainer
                )
            },
            text = {
                Column {
                    Text(
                        "Total: ₱${String.format("%.2f", totalForCheckout)}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    TextField(
                        value = cashGiven,
                        onValueChange = { cashGiven = it },
                        label = { Text("Cash", style = MaterialTheme.typography.bodyMedium) },
                        keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Number),
                        colors = TextFieldDefaults.textFieldColors(
                            textColor = MaterialTheme.colorScheme.onSurface,
                            containerColor = MaterialTheme.colorScheme.surface,
                            focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val cash = cashGiven.toDoubleOrNull() ?: 0.0
                        if (cash >= totalForCheckout) {
                            changeGiven = cash - totalForCheckout
                            cart.clear()
                            showCashPaymentDialog = false
                            showReceiptDialog = true
                        } else {
                            // Handle insufficient cash
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text("Confirm Payment", style = MaterialTheme.typography.titleMedium)
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.medium
        )
    }

    if (showReceiptDialog) {
        AlertDialog(
            onDismissRequest = { showReceiptDialog = false },
            title = {
                Text(
                    "Receipt",
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
            },
            text = {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(all = 16.dp),
                    horizontalAlignment = Alignment.Start
                ) {
                    Text(
                        "Total: ₱${String.format("%.2f", totalForCheckout)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Cash: ₱$cashGiven",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        "Change: ₱${String.format("%.2f", changeGiven)}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = { showReceiptDialog = false },
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                ) {
                    Text(
                        "OK",
                        style = MaterialTheme.typography.bodyLarge
                    )
                }
            },
            containerColor = MaterialTheme.colorScheme.surface,
            shape = MaterialTheme.shapes.medium
        )
    }

}

@Composable
fun ProductCard(
    product: Product,
    onClick: () -> Unit,
    onProductClick: (Product) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable { onProductClick(product) },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            val painter = rememberImagePainter(data = product.image,
                builder = {
                    crossfade(true)
                })
            Image(
                painter = painter,
                contentDescription = "${product.productName} image",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(RoundedCornerShape(8.dp)),
                contentScale = ContentScale.Crop
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = product.productName,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.padding(vertical = 2.dp)
            )
            Text(
                text = "₱${product.price}",
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(bottom = 4.dp)
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                FilledTonalButton(
                    onClick = { onClick() },
                    modifier = Modifier.fillMaxWidth(0.5f),
                    shape = RoundedCornerShape(50)
                ) {
                    Icon(
                        Icons.Filled.ShoppingCart,
                        contentDescription = "Add to Cart",
                        modifier = Modifier.size(24.dp)
                    )
                    Text(
                        "Add to Cart",
                        modifier = Modifier.padding(start = 8.dp),
                        color = MaterialTheme.colorScheme.onPrimaryContainer
                    )
                }
            }
        }
    }
}


@Composable
fun ProductDetailsDialog(product: Product, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                text = product.productName,
                style = MaterialTheme.typography.headlineSmall,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(id = product.image),
                    contentDescription = "Product image",
                    modifier = Modifier
                        .size(150.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .border(2.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(12.dp)),
                    contentScale = ContentScale.Crop
                )
                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    "Price: ₱${product.price}",
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    "Stock: ${product.stock.value}",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    product.description,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        },
        confirmButton = {
            Button(
                onClick = onDismiss,
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Text(
                    "Close",
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        },
        containerColor = MaterialTheme.colorScheme.surface,
        shape = RoundedCornerShape(16.dp),
        properties = DialogProperties(usePlatformDefaultWidth = false)
    )
}


@Composable
fun CartScreen(cart: List<Product>, onRemoveFromCart: (Product) -> Unit, onCheckout: () -> Unit) {
    val subtotal = cart.sumOf { it.price * it.quantity.value }
    val taxRate = 0.12  // 12% tax rate
    val taxAmount = subtotal * taxRate
    val total = subtotal + taxAmount

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(2.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (cart.isEmpty()) {
            item {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier.fillMaxSize()
                ) {
                    Text("Cart is empty.", style = MaterialTheme.typography.titleMedium)
                }
            }
        } else {
            items(cart) { product ->
                CartItemCard(
                    product = product,
                    onRemove = { onRemoveFromCart(product) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(16.dp))
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp, vertical = 8.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        "Subtotal: ₱${String.format("%.2f", subtotal)}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "Tax (12%): ₱${String.format("%.2f", taxAmount)}",
                        style = MaterialTheme.typography.bodyLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Text(
                        "Total: ₱${String.format("%.2f", total)}",
                        style = MaterialTheme.typography.titleMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = onCheckout,
                        modifier = Modifier.fillMaxWidth(),
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.secondary)
                    ) {
                        Text("Check Out and Pay", style = MaterialTheme.typography.titleMedium)
                    }
                }
            }
        }
    }
}

@Composable
fun CartItemCard(product: Product, onRemove: () -> Unit) {
    val totalPrice = product.price * product.quantity.value

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val painter = painterResource(id = product.image)
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
                contentScale = ContentScale.Crop
            )

            Column {
                Text(product.productName, style = MaterialTheme.typography.titleMedium)
                Text("Unit Price: ₱${product.price}", style = MaterialTheme.typography.titleSmall)
                Text("Quantity: ${product.quantity.value}", style = MaterialTheme.typography.titleSmall)
                Text("Total Price: ₱$totalPrice", style = MaterialTheme.typography.titleSmall)
            }
            IconButton(onClick = onRemove) {
                Icon(Icons.Filled.Close, contentDescription = "Remove")
            }
        }
    }
}

@Composable
fun SettingScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text(
            "Settings",
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary
        )

        Divider()

        SettingItem(title = "Manage Products", icon = Icons.AutoMirrored.Filled.List)
        SettingItem(title = "Payment Methods", icon = Icons.Default.Payment)
        SettingItem(title = "Configure Taxes", icon = Icons.Default.Calculate)
        SettingItem(title = "User Preferences", icon = Icons.Default.Person)
    }
}

@Composable
fun SettingItem(title: String, icon: ImageVector) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(16.dp))

        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AppPreviewDark() {
    POSTheme(darkTheme = true) {
        App()
    }
}
