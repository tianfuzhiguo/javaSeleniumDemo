package utils;

import base.Base;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import java.net.URL;

public class GridUtil extends Base {
    public static void getNode(String url, String browser) {
        DesiredCapabilities desiredCapabilities;
        //判断要打开的浏览器
        if (browser.equals("chrome")) {
            desiredCapabilities = DesiredCapabilities.chrome();
            //取消 chrome正受到自动测试软件的控制的信息栏
            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("excludeSwitches", new String[]{
                    "enable-automation"
            });
        } else if (browser.equals( "ie")) {
            desiredCapabilities = DesiredCapabilities.internetExplorer();
        } else {
            desiredCapabilities = DesiredCapabilities.firefox();
        }

        //拼接要执行脚本的node 节点地址
        url = url + "/wd/hub";
        try {
            driver = new RemoteWebDriver(new URL(url), desiredCapabilities);
        }catch (Exception e){
            e.printStackTrace();
        }
   }
}
