import java.io.Closeable;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class Network {
    public static void main(String[] args) {
        try {
            for (Enumeration<NetworkInterface> nics = NetworkInterface
                    .getNetworkInterfaces(); nics.hasMoreElements();) {
                NetworkInterface ifc = nics.nextElement();

                System.out.println("==============" + ifc.getDisplayName() + "==============");
                System.out.println("isLoopback:" + ifc.isLoopback());
                System.out.println("isVirtual:" + ifc.isVirtual());
                System.out.println("isUp:" + ifc.isUp());
                System.out.println("index:" + ifc.getIndex());
                for (Enumeration<InetAddress> addrs = ifc
                        .getInetAddresses(); addrs.hasMoreElements();) {
                    InetAddress address = addrs.nextElement();
                    System.out.println("getHostAddress:" + address.getHostAddress());
                    System.out.println("address:" + address);
                    System.out.println("isSiteLocalAddress:" + address.isSiteLocalAddress());
                }
            }
            System.out.println(InetAddress.getLocalHost());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
