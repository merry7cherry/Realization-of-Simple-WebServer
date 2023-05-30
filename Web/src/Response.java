import java.io.*;

public class Response {
    private static final int BUFFER_SIZE = 1024;
    Request request;
    OutputStream output;

    public Response(OutputStream output) {
        this.output = output;
    }

    public void setRequest(Request request) {
        this.request = request;
    }

    public void sendStaticResource() throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        FileInputStream fis = null;
        try {
            File file = new File(
                    HttpServer.WEB_ROOT, request.getUri());//创建文件对象
            if (file.exists()) {
                output.write("HTTP/1.1 200 OK\n".getBytes());
                output.write("Content-Type: text/html; charset=UTF-8\n\n".getBytes());
                fis = new FileInputStream(file);
                int readLength;
                while ((readLength = fis.read(buffer, 0, BUFFER_SIZE)) > 0) {
                    output.write(buffer, 0, readLength);
                }
            }//若没找到文件，则返回界面404 file not found
            else {
                String errMsg = "HTTP/1.1 404 File Not Found\r\n" + "Content-Type: text/html\r\n"
                        + "Content-Length: 23\r\n" + "\r\n" + "<h1> File Not Found</h1>";
                output.write(errMsg.getBytes());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (fis != null) {
                fis.close();
            }
        }
    }
}