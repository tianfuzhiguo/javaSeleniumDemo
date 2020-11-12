package utils;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import base.Base;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.net.URL;

/**
 * 工具类
 * @author dujianxiao
 *
 */
public class Util extends Base{
	private String baseUrl = FileUtil.getValue("global.properties", "frontUrl");
	private String username = FileUtil.getValue("global.properties", "frontUsername");
	private String password = FileUtil.getValue("global.properties", "frontPassword");

	/**
	 * 初始化浏览器
	 */
	public void openBrower(String broswer){
		if(broswer.equals("chrome")) {
			//方法一
			System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
			//取消 chrome正受到自动测试软件的控制的信息栏
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("excludeSwitches", new String[]{"enable-automation"});
			driver = new ChromeDriver(options);
		}else if(broswer.equals("ie")){

		}else {
			System.setProperty("webdriver.gecko.driver", "driver/geckodriver.exe");
			driver = new FirefoxDriver();
		}

	    //方法二
        /*
		ChromeOptions options = new ChromeOptions();
		//去掉浏览器错误提示：chrome正正受到自动测试软件的控制和--ignore-certificate-error。
		options.addArguments("--test-type", "--ignore-certificate-error。");
		options.addArguments("disable-infobars");
		System.setProperty("webdriver.chrome.driver", "driver/chromedriver.exe");
		driver = new ChromeDriver(options);
        */
	}

	/**
	 * 登录方法
	 */
	public void login(String broswer){
		openBrower(broswer);
		driver.get(baseUrl);
		driver.manage().window().maximize();
		driver.findElement(By.id("loginbtn")).click();
		waitForLoad(By.id("project"));
	}

	public void gridLogin(String url, String browser) {
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
			driver.get(baseUrl);
			driver.manage().window().maximize();
			driver.findElement(By.id("loginbtn")).click();
			waitForLoad(By.id("project"));
		}catch (Exception e){
			e.printStackTrace();
		}
	}

	/**
	 * 选择二级菜单
	 * @param first   一级菜单
	 * @param second  二级菜单
	 */
	public void chooseMenu(String first,String second){
		driver.findElement(By.linkText(first)).click();
		driver.findElement(By.cssSelector("b.navbar_hide")).click();
		waitForLoad(By.linkText(second));
		driver.findElement(By.linkText(second)).click();
	}

	/**
	 * 错误截屏
	 */
	public static void screen(String imageName){
		try{
			// getScreenshotAs()对当前窗口进行截图
			File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			// 需要指定图片的保存路径及文件名
			FileUtils.copyFile(srcFile, new File("screen/" + imageName + ".png"));
		}catch(Exception e){

		}
	}
}
