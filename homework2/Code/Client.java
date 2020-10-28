import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        try {
            int i = 1;

            //发送到8888端口
            Socket socket = new Socket("100.68.186.80", 8888);

            //向服务端发起查询
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write("Paul Kocher");
            printWriter.flush();
            socket.shutdownOutput();

            //从客户端接收查询结果
            InputStream inputStream = socket.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String info;

            while ((info=bufferedReader.readLine())!=null) {
                System.out.println("Server端"+ i + ":" + info + "次");
            }


            //关闭资源
            inputStream.close();
            bufferedReader.close();
            printWriter.close();
            outputStream.close();
            socket.close();
        }
        catch (UnknownHostException e) {
            e.printStackTrace();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}