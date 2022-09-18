import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 服务器端（只负责接收消息）
 */
class ServSocket {
    // 字节数组的长度
    private static final int BYTE_LENGTH = 20;

    public static void main(String[] args) throws IOException {
        System.out.println("file.encoding:" + System.getProperty("file.encoding"));
        System.out.println("sun.jnu.encoding:" + System.getProperty("sun.jnu.encoding"));
        try (// 创建 Socket 服务器
                ServerSocket serverSocket = new ServerSocket(9999)) {
            // 获取客户端连接
            Socket clientSocket = serverSocket.accept();
            // 得到客户端发送的流对象
            try (InputStream inputStream = clientSocket.getInputStream()) {
                while (true) {
                    // 循环获取客户端发送的信息
                    byte[] bytes = new byte[BYTE_LENGTH];
                    // 读取客户端发送的信息
                    int count = inputStream.read(bytes, 0, BYTE_LENGTH);
                    if (count > 0) {
                        // 成功接收到有效消息并打印
                        System.out.println("接收到客户端的信息是:" + new String(bytes));
                    }
                    count = 0;
                }
            }
        }
    }
}