import java.io.BufferedReader;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static void main(String[] args) {
        try{
            ServerSocket serverSocket=new ServerSocket(8888);
            System.out.println("----------------服务端执行，创建监听请求----------------");
            Socket socket=serverSocket.accept();//创建监听
            InputStream inputStream=socket.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            //获取请求内容
            String info;
            while ((info=bufferedReader.readLine())!=null) {
                System.out.println("server端---client请求为查询作者:"+info);
                String command = "cat ~/temp_file/dblp_split* | grep -o \""+ info +"\" |wc -l\n";
                String[] params = new String[] {"/bin/sh", "-c", command};
                Process process = Runtime.getRuntime().exec(params);
                process.waitFor();

                BufferedReader bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                BufferedReader bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));

                StringBuilder result = new StringBuilder();
                String line = null;
                while((line = bufrIn.readLine()) != null || (line = bufrError.readLine()) != null){
                    result.append(line).append('\n');
                }

                System.out.println(result);
            }
            //关闭资源
            socket.shutdownInput();
            bufferedReader.close();
            inputStream.close();
            socket.close();
            serverSocket.close();
        }catch(Exception exception){

        }

    }

}
