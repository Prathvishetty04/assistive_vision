
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import java.io.OutputStream
import java.util.UUID

class BluetoothHelper {

    private var socket: BluetoothSocket? = null
    private var outputStream: OutputStream? = null

    fun connect(deviceName: String): Boolean {
        val adapter = BluetoothAdapter.getDefaultAdapter()
        val device: BluetoothDevice? =
            adapter.bondedDevices.firstOrNull { it.name == deviceName }

        return try {
            val uuid: UUID = device!!.uuids[0].uuid
            socket = device.createRfcommSocketToServiceRecord(uuid)
            socket!!.connect()
            outputStream = socket!!.outputStream
            true
        } catch (e: Exception) {
            false
        }
    }

    fun send(command: String) {
        outputStream?.write(command.toByteArray())
    }
}