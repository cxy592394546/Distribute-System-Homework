import java.io.*;
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
            StringBuilder result = new StringBuilder();
            String line = null;
            while ((info=bufferedReader.readLine())!=null) {
                System.out.println("Server端1---查询文件:"+"dblp_split_1.xml");
		System.out.println("Server端1---client请求为查询作者:"+info);
                String file_path = "~/temp_file/dblp_split_1.xml";
                String command = "cat " + file_path + " | grep -o \""+ info +"\" |wc -l\n";
                String[] params = new String[] {"/bin/sh", "-c", command};
                Process process = Runtime.getRuntime().exec(params);
                process.waitFor();

                BufferedReader bufrIn = new BufferedReader(new InputStreamReader(process.getInputStream(), "UTF-8"));
                BufferedReader bufrError = new BufferedReader(new InputStreamReader(process.getErrorStream(), "UTF-8"));

                while((line = bufrIn.readLine()) != null || (line = bufrError.readLine()) != null) {
                    result.append(line);
                }
            }
            System.out.println("Server端1:" + result + "次");
            socket.shutdownInput();

            //向客户端发送数据
            OutputStream outputStream = socket.getOutputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);
            printWriter.write(String.valueOf(result));
            printWriter.flush();

            //关闭资源
            printWriter.close();
            outputStream.close();
            bufferedReader.close();
            inputStream.close();
            socket.close();
            serverSocket.close();
        }catch(Exception exception){

        }

    }

}
