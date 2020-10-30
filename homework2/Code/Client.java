import java.io.*;
import java.time.Duration;
import java.time.Instant;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client {
    public static void main(String[] args) {
        //查询到的总次数；
        int times = 0;
        //异常标记(默认为0)
        int flag = 0;
        String server6_tolerance = "";
        //查询对象
        String author;
        //记录时间（开始）
        Instant inst1 = Instant.now();
        System.out.println("Inst1 : " + inst1);
        for(int i = 0; i < x; i++) {
            int server_num = i + 1;
            String[] host = new String[]{};
            try {
                //发送到xxxx端口
                Socket socket = new Socket(host[i], xxxx);

                //向服务端发起查询
                OutputStream outputStream = socket.getOutputStream();
                PrintWriter printWriter = new PrintWriter(outputStream);
                printWriter.write(author);
                printWriter.flush();
                socket.shutdownOutput();

                //从客户端接收查询结果
                InputStream inputStream = socket.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String info;

                //将每个服务端查询到的次数输出到屏幕
                while ((info = bufferedReader.readLine()) != null) {
                    String[] arr = info.split("\\s+");

                    if (i == 0) {
                        server6_tolerance = arr[1];
                    }

                    if (flag == 1) {
                        flag = 0;
                        System.out.println("Server" + i + ":" + arr[1] + "次");
                        times = times + Integer.parseInt(arr[1]);
                        System.out.println("Server" + server_num + ":" + arr[0] + "次");
                        times = times + Integer.parseInt(arr[0]);
                    } else if (flag == 0) {
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
                if (i == 6){
                    flag = 0;
                    System.out.println("Server6:" + server6_tolerance + "次");
                    times = times + Integer.parseInt(server6_tolerance);
                }
            }catch (IOException e) {
                System.out.println("server" + server_num + " error:" + e);
                flag = 1;
                if (i == 6){
                    flag = 0;
                    System.out.println("Server6:" + server6_tolerance + "次");
                    times = times + Integer.parseInt(server6_tolerance);
                }
            }
        }
        String str = "查询到的总次数:" + times;
        System.out.println(str);

        //记录时间（结束）
        Instant inst2 = Instant.now();
        System.out.println("Inst2 : " + inst2);
        System.out.println("Difference in milliseconds : " + Duration.between(inst1, inst2).toMillis());

        //将查询到的结果输出
        byte[] sourceByte = str.getBytes();
        if(null != sourceByte){
            try {
                //文件路径（路径+文件名）
                File file = new File("xxxx");
                //文件不存在则创建文件，先创建目录
                if (!file.exists()) {
                    File dir = new File(file.getParent());
                    dir.mkdirs();
                    file.createNewFile();
                }
                //文件输出流用于将数据写入文件
                FileOutputStream outStream = new FileOutputStream(file);
                PrintStream printStreams = new PrintStream(new FileOutputStream(file));
                // 在已有的基础上添加字符串
                printStreams.append("查询对象为:" + author + "\n" + str +
                        "\n" + "Difference in milliseconds : " + Duration.between(inst1, inst2).toMillis());
                printStreams.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }
}

// Author: Cui Xinyu
// ID: 1853444
// Date: 10/30 2020