package com.example.finalprojectmovie.presentation
import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.finalprojectmovie.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import java.io.IOException
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val REQUEST_ENABLE_BT = 1
    private val REQUEST_ENABLE_BT_CONNECT = 2
    private val MY_UUID: UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB") // Example UUID
    private lateinit var bluetoothAdapter: BluetoothAdapter
    private lateinit var bluetoothSocket: BluetoothSocket

    private val requestEnableBluetoothLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            connectToPairedDevice()
        } else {
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (checkBluetoothPermission()) {
            checkBluetoothAvailability()
        }
        val navHost =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment

        val navController = navHost.navController
        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottom_navigation).apply {
                setupWithNavController(navController)
                setOnItemReselectedListener { }
            }

        val topLevelDestinations = setOf(
            R.id.homeFragment,
            R.id.myListsFragment,
            R.id.browseFragment
        )
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            bottomNavigationView.isVisible = topLevelDestinations.contains(destination.id)
        }
    }

    private fun checkBluetoothPermission(): Boolean {
        return if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.BLUETOOTH
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.BLUETOOTH),
                REQUEST_ENABLE_BT
            )
            false
        } else {
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_ENABLE_BT) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Bluetooth permission granted, check Bluetooth availability
                checkBluetoothAvailability()
            } else {
            }
        }
    }

    private fun checkBluetoothAvailability() {
        bluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (bluetoothAdapter == null) {
        } else {
            if (!bluetoothAdapter.isEnabled) {
                enableBluetooth()
            } else {
                connectToPairedDevice()
            }
        }
    }

    private fun enableBluetooth() {
        val enableBtIntent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
        requestEnableBluetoothLauncher.launch(enableBtIntent)
    }

    private fun connectToPairedDevice() {
        val pairedDevices: Set<BluetoothDevice>? = bluetoothAdapter.bondedDevices
        pairedDevices?.forEach { device ->
            if (device.name == "Device_Name") {
                if (ContextCompat.checkSelfPermission(
                        this,
                        Manifest.permission.BLUETOOTH_CONNECT
                    ) == PackageManager.PERMISSION_GRANTED
                ) {
                    try {
                        bluetoothSocket = device.createRfcommSocketToServiceRecord(MY_UUID)
                        bluetoothSocket.connect()
                        Toast.makeText(this, "Connected to ${device.name}", Toast.LENGTH_SHORT).show()
                        val outputStream = bluetoothSocket.outputStream
                        outputStream.write("Hello".toByteArray())
                    } catch (e: IOException) {
                        Toast.makeText(this, "Failed to connect to ${device.name}", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    ActivityCompat.requestPermissions(
                        this,
                        arrayOf(Manifest.permission.BLUETOOTH_CONNECT),
                        REQUEST_ENABLE_BT_CONNECT
                    )
                }
            }
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        try {
            bluetoothSocket.close()
        } catch (e: IOException) {
        }
    }
}
