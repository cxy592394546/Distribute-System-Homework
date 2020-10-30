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
            String line1 = null;
            String line0 = null;
            while ((info=bufferedReader.readLine())!=null) {
                System.out.println("Server端1---查询文件:"+"dblp_split_1&6.xml");
                System.out.println("Server端1---client请求为查询作者:"+info);
                String file_path1 = "~/temp_file/dblp_split_1.xml";
                String command1 = "cat " + file_path1 + " | grep -o \""+ info +"\" |wc -l\n";
                String file_path0 = "~/temp_file/dblp_split_6.xml";
                String command0 = "cat " + file_path0 + " | grep -o \""+ info +"\" |wc -l\n";
                String[] params1 = new String[] {"/bin/sh", "-c", command1};
                Process process1 = Runtime.getRuntime().exec(params1);
                process1.waitFor();

                BufferedReader bufrIn1 = new BufferedReader(new InputStreamReader(process1.getInputStream(), "UTF-8"));
                BufferedReader bufrError1 = new BufferedReader(new InputStreamReader(process1.getErrorStream(), "UTF-8"));

                while((line1 = bufrIn1.readLine()) != null || (line1 = bufrError1.readLine()) != null) {
                    result.append(line1 + " ");
                }
                String[] params0 = new String[] {"/bin/sh", "-c", command0};
                Process process0 = Runtime.getRuntime().exec(params0);
                process0.waitFor();

                BufferedReader bufrIn0 = new BufferedReader(new InputStreamReader(process0.getInputStream(), "UTF-8"));
                BufferedReader bufrError0 = new BufferedReader(new InputStreamReader(process0.getErrorStream(), "UTF-8"));

                while((line0 = bufrIn0.readLine()) != null || (line0 = bufrError0.readLine()) != null) {
                    result.append(line0);
                }
            }
            System.out.println("Server端1-dblp_split_1&6:" + result + "次");
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
