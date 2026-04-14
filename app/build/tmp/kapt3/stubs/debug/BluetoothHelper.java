
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\t\u001a\u00020\nJ\u000e\u0010\u000b\u001a\u00020\f2\u0006\u0010\r\u001a\u00020\nR\u0010\u0010\u0003\u001a\u0004\u0018\u00010\u0004X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u0010\u0010\u0005\u001a\u0004\u0018\u00010\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000e"}, d2 = {"LBluetoothHelper;", "", "()V", "outputStream", "Ljava/io/OutputStream;", "socket", "Landroid/bluetooth/BluetoothSocket;", "connect", "", "deviceName", "", "send", "", "command", "app_debug"})
public final class BluetoothHelper {
    @org.jetbrains.annotations.Nullable()
    private android.bluetooth.BluetoothSocket socket;
    @org.jetbrains.annotations.Nullable()
    private java.io.OutputStream outputStream;
    
    public BluetoothHelper() {
        super();
    }
    
    public final boolean connect(@org.jetbrains.annotations.NotNull()
    java.lang.String deviceName) {
        return false;
    }
    
    public final void send(@org.jetbrains.annotations.NotNull()
    java.lang.String command) {
    }
}