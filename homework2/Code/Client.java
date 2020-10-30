import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        //查询到的总次数；
        int times = 0;

        //异常标记(默认为0)
        int flag = 0;
        for(int i = 0; i < 3; i++) {
            int server_num = i + 1;
            String[] host = new String[]{"100.68.186.80", "192.168.91.129", "192.168.91.130",
                    "192.168.91.131", "192.168.91.132", "192.168.91.133"};
            try {
                //发送到8888端口
                Socket socket = new Socket(host[i], 8888);

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

                //将每个服务端查询到的次数输出到屏幕
                while ((info = bufferedReader.readLine()) != null) {
                    String [] arr = info.split("\\s+");

                    if(flag == 1){
                        flag = 0;
                        System.out.println("Server" + i + ":" + arr[1] + "次");
                        times = times + Integer.parseInt(arr[1]);
                        System.out.println("Server" + server_num + ":" + arr[0] + "次");
                        times = times + Integer.parseInt(arr[0]);
                    }
                    else if(flag == 0){
                        System.out.println("Server" + server_num + ":" + arr[0] + "次");
                        times = times + Integer.parseInt(arr[0]);
                    }
                }

                //关闭资源
                inputStream.close();
                bufferedReader.close();
                printWriter.close();
                outputStream.close();
                socket.close();

            }
            catch (UnknownHostException e) {
                System.out.println("server" + server_num + " error:" + e);
                flag = 1;
            }catch (IOException e) {
                System.out.println("server" + server_num + " error:" + e);
                flag = 1;
            }
        }
        String str = "查询到的总次数:" + times;
        System.out.println(str);

        //将查询到的结果输出
        byte[] sourceByte = str.getBytes();
        if(null != sourceByte){
            try {
                File file = new File("./1853444-hw2-q1.log");		//文件路径（路径+文件名）
                if (!file.exists()) {	//文件不存在则创建文件，先创建目录
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();
                }
                FileOutputStream outStream = new FileOutputStream(file);	//文件输出流用于将数据写入文件
                outStream.write(sourceByte);
                outStream.close();	//关闭文件输出流
            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }
}