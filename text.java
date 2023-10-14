package cn.swu.wzq;

import com.sun.source.tree.NewArrayTree;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class text {
    public static void main(String[] args) throws IOException {

        //建立数组存储图片地址
        String[] address = new String[5];

        //从网站一行一行读取地址
        //1.建立URL
        URL adadress = new URL("http://10.122.7.154/javaweb/data/images-url.txt");
        //2.建立连接
        URLConnection cn = adadress.openConnection();
        //3.利用bufferedReader一行一行读取文件
        BufferedReader br = new BufferedReader(new InputStreamReader(cn.getInputStream()));
        String Line;
        int count = 0;
        while((Line = br.readLine())!=null){
            address[count] = Line;
            count++;
        }

        //创建数组存储文件地址
        String[] imgnames = {"C://imgs/www/swu/edu/cn/xywh/zhanbanzongban/01.jpg",
                "C://imgs/www/swu/edu/cn/xywh/xiaoshi01/01.jpg",
                "C://imgs/www/swu/edu/cn/xywh/xiaoshi06/01.jpg",
                "C://imgs/www/swu/edu/cn/xywh/xiaoshi08/01.jpg",
                "C://imgs/www/swu/edu/cn/xywh/xiaoshi10/01.jpg"
        };

        byte[]  buffer = new byte[1024];
        int length;
        int[] filesize = new int[address.length];
        for (int i = 0; i < address.length; i++) {
            //创建存储图片的地址
            File filepath = new File(imgnames[i]);
            File parentfile =filepath.getParentFile();
            parentfile.mkdirs();
            filepath.createNewFile();

            //利用DateStaream读取图片信息，并存到指定地址
            URL url = new URL(address[i]);
            DataInputStream dataInputStream = new DataInputStream(url.openStream());
            URLConnection connection = url.openConnection();
            filesize[i] = connection.getContentLength();
            FileOutputStream img = new FileOutputStream(filepath);
            while((length = dataInputStream.read(buffer))>0){
                img.write(buffer,0,length);
            }
            dataInputStream.close();
            img.close();
        }

        int[] position = new int[filesize.length];
        for (int i = 0; i < filesize.length - 1 ; i++) {
            for(int a = 0; a < filesize.length; a++){
                if(filesize[i]>filesize[a]){
                    position[i]++;
                }
            }
        }
        //创建存储大小的txt文件
        File txtfile = new File("C://imgs/imgs-sorted.text");
        File txtparent = txtfile.getParentFile();
        txtparent.mkdirs();
        txtfile.createNewFile();
        FileWriter fw = new FileWriter("C://imgs/imgs-sorted.text");

        //输入到文件中去
        for (int i = 0; i < filesize.length; i++) {
            for(int j = 0 ;j < filesize.length; j++){
            if (position[j] == i) {
                fw.write(filesize[j] + imgnames[j]);
                fw.write("\n");
                }
            }
        }
        fw.close();
    }
}
