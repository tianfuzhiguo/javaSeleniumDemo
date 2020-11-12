package utils;

/**
 * 修改服务器时间
 * @author dujianxiao
 */
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.commons.net.telnet.TelnetClient;
import utils.FileUtil;

public class TelnetUtil {
    private static TelnetClient telnetClinet = null;
    private static InputStream is = null;
    private static OutputStream os = null;
    private static String ip = FileUtil.getValue("global.properties", "server");
    private static String  username = FileUtil.getValue("global.properties", "serverusername");
    private static String password = FileUtil.getValue("global.properties", "serverpassword");

    /**
     * 设置服务器日期
     * @param date   YYYY-MM-DD
     */
    public static void setDate(String date){
        TelnetUtil test = new TelnetUtil();
        test.connection(ip, 23);//登录本机,23端口
        if(test.findStr("login"))//如果远程计算机返回login字符
            test.sendTelnetMsg(username+"\n");//传输用户名,改写你的用户名,该用户名属于TelnetClients组
        if(test.findStr("password"))//如果远程计算机返回password
            test.sendTelnetMsg(password+"\n");//传输用户密码,改写你的密码
        if(test.findStr(">"))//如果远程计算机返回>字符
            test.sendTelnetMsg("date\n");
        test.sendTelnetMsg(date+"\n");
        try {
            telnetClinet.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 设置服务器日期
     * @param date   HH:MM:SS
     */
    public static void setTime(String time){
        TelnetUtil test = new TelnetUtil();
        test.connection(ip, 23);//登录本机,23端口
        if(test.findStr("login"))//如果远程计算机返回login字符
            test.sendTelnetMsg(username+"\n");//传输用户名,改写你的用户名,该用户名属于TelnetClients组
        if(test.findStr("password"))//如果远程计算机返回password
            test.sendTelnetMsg(password+"\n");//传输用户密码,改写你的密码
        if(test.findStr(">"))//如果远程计算机返回>字符
            test.sendTelnetMsg("time\n");
        test.sendTelnetMsg(time+".00"+"\n");
        try {
            telnetClinet.disconnect();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接远程计算机,连接完成后，获取读取流与发送流
     * @param ip  远程计算机IP地址
     * @param port 远程计算机端口
     * */
    public void connection(String ip,int port){
        try{
            telnetClinet = new TelnetClient();
            telnetClinet.connect(ip, port);
            is = telnetClinet.getInputStream();
            os = telnetClinet.getOutputStream();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 获取远程计算机返回的信息
     * */
    public String readTelnetMsg() {
        try{
            int len = 0;
            byte [] b = new byte[1024];
            do{
                len = is.read(b);
                if(len>=0)
                    return new String(b,0,len);
            }while(len>=0);
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 向远端计算机发送指令消息
     * @param msg 需要传送的指令
     * **/
    public void sendTelnetMsg(String msg){
        byte [] b = msg.getBytes();
        try{
            os.write(b, 0, b.length);
            os.flush();
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    /**
     * 查找远端计算机返回的指令中是否包含想要指令
     * 一直查找，直到包含，返回true
     * */
    public boolean findStr(String str){
        for(;;){
            String msg = readTelnetMsg();
            if(msg.indexOf(str)!=-1)
                return true;
        }
    }
}

