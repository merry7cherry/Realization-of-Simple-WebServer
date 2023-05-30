import java.io.*;

public class Request {
    private final static int BUFFER_SIZE = 1024;
    private InputStream input;
    private String uri="";

    public Request(InputStream input) {
        this.input = input;
    }

    public void parse() {
        StringBuffer request = new StringBuffer();
        int readLength;
        byte[] buffer = new byte[BUFFER_SIZE];

        try {
            readLength = input.read(buffer);//读取请求数据
        } catch (Exception e) {
            e.printStackTrace();
            readLength = -1;
        }
        for (int i = 0; i < readLength; i++) {
            request.append((char) buffer[i]);
        }
        System.out.print(request.toString());//打印请求信息
        uri = parseUri(request.toString());//返回文件名
    }

    private String parseUri(String requestString) {
        int index1, index2;
        index1 = requestString.indexOf(' ');
        if (index1 != -1) {
            index2 = requestString.indexOf(' ', index1 + 1);
            if (index2 > index1)
                return requestString.substring(index1 + 1, index2);
        }
        return null;
    }

    public String getUri() {//返回文件名
        return uri;
    }
}