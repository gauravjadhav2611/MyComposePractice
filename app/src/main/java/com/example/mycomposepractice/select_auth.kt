import android.Manifest
import android.R
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Build
import androidx.compose.material.icons.outlined.Call
import androidx.compose.material.icons.outlined.DateRange
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat
import com.google.android.gms.location.LocationServices


import android.content.pm.PackageManager
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CardColors
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.ui.graphics.RectangleShape
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectAuthScreen(navController: NavHostController){
    var latitude by remember {
        mutableDoubleStateOf(0.0)
    }
    var longitude by remember {
        mutableDoubleStateOf(0.0)
    }
    var showPermissionDialog by remember { mutableStateOf(false) }
    var showGpsDialog by remember { mutableStateOf(false) }
    val context = LocalContext.current

    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    fun getCurrentLocation() {
        Log.d("Inside_getCurrentLocation","${ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED}")
        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location ->
                    Log.d("Location", "Location object = $location")
                    if (location != null) {
                        latitude = location.latitude
                        longitude = location.longitude

                        Log.d("Location", "Latitude: $latitude")
                        Log.d("Location", "Longitude: $longitude")
                    } else {
                        Log.d("Location", "Location is NULL")
                        showGpsDialog = true
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("Location", "Failed: ${e.message}")
                }
        }
    }

    val permissionLauncher =
        rememberLauncherForActivityResult(
            contract = ActivityResultContracts.RequestPermission()
        ) { granted ->
            if (granted) {
                getCurrentLocation()
            } else {
                Log.d("Location Permission Denied","")
                showPermissionDialog = true
            }
        }

    LaunchedEffect(Unit) {
        Log.d("Inside_LaunchedEffect","1")
        if (
            ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            Log.d("Inside_LaunchedEffect","inside IF getCurrentLocation")
            getCurrentLocation()
        } else {
            Log.d("Inside_LaunchedEffect","inside ELSE permissionLauncher.launch")
            permissionLauncher.launch(
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    getCurrentLocation()
                }
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }

    val itemsList = listOf(
        MenuItem("Games", Icons.Outlined.Email, Color(0xFFEAC75F)),
        MenuItem("Pre-Auth", Icons.Outlined.Build, Color(0xFF67AB6A)),
        MenuItem("Auth Complete", Icons.Outlined.AccountCircle, Color(0xFFEC4D80)),
        MenuItem("Increase Auth", Icons.Outlined.Call, Color(0xFFD0891C)),
        MenuItem("Auth Cancel", Icons.Outlined.DateRange, Color(0xFFCA42E1))
    )

    val authList = listOf(
        "Sale",
        "Pre-Auth",
        "Auth Complete",
        "Increase Auth",
        "Auth Cancel",
    )

    var selectedIndex by remember {
        mutableStateOf<Int?>(null)
    }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("Location Permission Required") },
            text = { Text("This app needs location access to continue. Please enable it in the app settings.") },
            confirmButton = {
                Button(onClick = {
                    showPermissionDialog = false
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Open Settings")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    if (showGpsDialog) {
        AlertDialog(
            onDismissRequest = { showGpsDialog = false },
            title = { Text("Location Services Disabled") },
            text = { Text("GPS / Location is turned off on your device. Please enable it in device settings.") },
            confirmButton = {
                Button(onClick = {
                    showGpsDialog = false
                    context.startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                }) {
                    Text("Enable Location")
                }
            },
            dismissButton = {
                TextButton(onClick = { showGpsDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }

    Scaffold (
        topBar = {
            TopAppBar(
                title = {
                    Text("Jagat Traders Pvt. Ltd")
                },
                actions = {
                    IconButton(
                        onClick = {
                            navController.navigate("settings")
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Outlined.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ){
            padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(1.dp)
            ) {
                itemsIndexed(itemsList) { index, item ->
                    AuthElement(
                        item = item,
                        selected = selectedIndex == index,
                        onClick = {
                            selectedIndex = index
                            if(index == 1){
                                navController.navigate("preAuth")
                            }
                            if(index == 0){
                                navController.navigate("gamescreen")
                            }
                        }
                    )
                }
            }

            Card(
                modifier = Modifier.padding(20.dp),
                border = BorderStroke(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.error
                ),
                shape = RoundedCornerShape( 10.dp),
                colors = CardDefaults.cardColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                    contentColor = MaterialTheme.colorScheme.onErrorContainer,
                )
            ){
                Column (
                    modifier = Modifier.padding(10.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ){
                    if(latitude != 0.0)
                        Text(
                            "Latitude: $latitude"
                        )
                    if(longitude != 0.0)
                        Text(
                            "Longitude: $longitude"
                        )
                }
            }
        }
    }
}

@Composable
fun AuthElement(
    item: MenuItem,
    selected: Boolean,
    onClick: () -> Unit,
){
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .fillMaxWidth()
            .height(80.dp)
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(10.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        ),
        border = BorderStroke(
            width = if(selected) 3.dp else 1.dp,
            color = if(selected) Color(0xFFF1C651) else MaterialTheme.colorScheme.outlineVariant
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {

        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Icon(
                imageVector = item.icon,
                contentDescription = null,
                tint = item.iconColor,
                modifier = Modifier.size(34.dp)
            )

            Spacer(modifier = Modifier.width(24.dp))

            Text(
                text = item.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium,
                color = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

data class MenuItem(
    val title : String,
    val icon : ImageVector,
    val iconColor : Color
)