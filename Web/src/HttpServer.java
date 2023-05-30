import java.io.*;
import java.net.*;

public class HttpServer {
    private static final int SERVER_PORT = 81;//端口号
    public static final String WEB_ROOT = System.getProperty("user.dir")//获取文件路径
            + File.separator + "webroot";
    private static final String SHUTDOWN_COMMAND = "/QUIT";

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        System.out.println(WEB_ROOT);
        server.await();
    }

    public void await() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(SERVER_PORT);//创建服务器
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }

        while (true) {//等待连接
            Socket socket = null;
            InputStream input = null;
            OutputStream output = null;
            try {
                socket = serverSocket.accept();
                input = socket.getInputStream();
                output = socket.getOutputStream();

                // 创建Request对象并解析
                Request request = new Request(input);
                request.parse();
                // 检查是否是关闭服务命令
                if (request.getUri().equals(SHUTDOWN_COMMAND)) {
                    break;
                }

                // 创建 Response 对象
                Response response = new Response(output);
                response.setRequest(request);
                response.sendStaticResource();

                // 关闭 socket 对象
                socket.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}




