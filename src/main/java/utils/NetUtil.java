package utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public class NetUtil {

	/**
	 * 执行CMD命令,并返回String字符串
	 */
	public static String executeCmd(String strCmd) throws Exception {
		Process p = Runtime.getRuntime().exec("cmd /c " + strCmd);
		StringBuilder sbCmd = new StringBuilder();
		BufferedReader br = new BufferedReader(new InputStreamReader(p.getInputStream(),"GB2312"));
		String line;
		while ((line = br.readLine()) != null) {
			sbCmd.append(line + "\n");
		}
		return sbCmd.toString();
	}

	/**
	 * 连接ADSL
	 */
	public static boolean connAdsl(String adslTitle, String adslName, String adslPass) throws Exception {
		System.out.println("正在建立连接.");
		String adslCmd = "rasdial " + adslTitle + " " + adslName + " " + adslPass;
		String tempCmd = executeCmd(adslCmd);
		// 判断是否连接成功
		if (tempCmd.indexOf("已连接") > 0) {
			System.out.println("已成功建立连接.");
			return true;
		} else {
			System.err.println(tempCmd);
			System.err.println("建立连接失败");
			return false;
		}
	}

	/**
	 * 断开ADSL
	 */
	public static void cutAdsl(String adslTitle) throws Exception {
		try {
			//通过process方式发起windows命令行命令执行
			String cmd = "ipconfig /release";
			//String cmd = "ncpa.cpl";
			Process p = Runtime.getRuntime().exec(cmd);
			p.waitFor();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws Exception {
		//connAdsl("宽带", "", "");
		//Thread.sleep(1000);
		cutAdsl("宽带");
		//Thread.sleep(10000);
		// 再连，分配一个新的IP
		//connAdsl("宽带", "", "");
	}

}