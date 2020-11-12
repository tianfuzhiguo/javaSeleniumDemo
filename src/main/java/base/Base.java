package base;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public abstract class Base {
	public static WebDriver driver;
	/**
	 * 获取WebElement对象
	 * @param webElement   页面元素
	 */
	public WebElement getWebElement(By webElement){
		return driver.findElement(webElement);
	}

	/**
	 * 操作按钮
	 * @param webElement   页面元素
	 */
	public void click(By webElement) {
		waitForLoad(webElement);
		getWebElement(webElement).click();
	}

	/**
	 * 输入
	 * @param webElement    页面元素
	 * @param value         输入值
	 */
	public void inputValue(By webElement, String value) {
		waitForLoad(webElement);
		JavascriptExecutor driver_js = (JavascriptExecutor) driver;
		driver_js.executeScript("arguments[0].removeAttribute(\"readonly\") ", getWebElement(webElement));
		getWebElement(webElement).clear();
		getWebElement(webElement).sendKeys(value);
	}

	/**
	 * 获取下拉列表选中值
	 * @param webElement    页面元素
	 */
	public String getSeclected(By webElement) {
		waitForLoad(webElement);
		Select sel = new Select(getWebElement(webElement));
		return sel.getFirstSelectedOption().getText().trim();
	}
	/**
	 * 获取值页面元素值
	 * @param webElement    页面元素
	 */
	public String getValue(By webElement) {
		waitForLoad(webElement);
		return getWebElement( webElement).getText().trim();
	}

	/**
	 * 获取日历时间
	 * @param webElement    页面元素
	 * @return YYYY-MM-DD
	 */
	public String getCalendarValue(By webElement, By year,By month,By day) {
		waitForLoad(webElement);
		click(webElement);
		String y = getWebElement(year).getText().trim();
		String m = getWebElement(month).getText().trim();
		String d = getWebElement(day).getText().trim();
		if(m.length()==1){
			m = "0"+m;
		}
		if(d.length()==1){
			d = "0"+d;
		}
		return y+"-"+m+"-"+d;
	}
	/**
	 * 获取值页面元素属性值
	 * @param webElement    页面元素
	 * @param att           元素属性
	 */
	public String getAttValue(By webElement,String att) {
		waitForLoad(webElement);
		return getWebElement(webElement).getAttribute(att).toString().trim().replace(" ", "");
	}
	/**
	 * 选择下拉列表
	 * @param webElement    页面元素
	 * @param value         下拉列表value值
	 */
	public void selValue(By webElement, String value) {
		waitForLoad(webElement);
		Select sel = new Select(getWebElement(webElement));
		sel.selectByValue(value);
	}

	/**
	 * 选择frame
	 * @param webElement    页面元素
	 */
	public void selFrame(By webElement) {
		driver.switchTo().defaultContent();
		waitForLoad(webElement);
		driver.switchTo().frame(getWebElement(webElement));
	}

	/**
	 * 清空数据
	 * @param webElement    页面元素
	 */
	public void clear(By webElement) {
		waitForLoad(webElement);
		getWebElement(webElement).clear();
	}

	/**
	 * 选中弹出框
	 */
	public Alert selAlert(){
		return driver.switchTo().alert();
	}
	/**
	 * 获取下拉列表数组
	 * @param webElement    页面元素
	 * @return
	 */
	public String[] getArraly(By webElement) {
		waitForLoad(webElement);
		int num;
		Select sel = new Select(getWebElement(webElement));
		num = sel.getOptions().size();
		List<WebElement> list = sel.getOptions();
		String[] array = new String[num];
		for (int i = 0; i < num; i++) {
			array[i] = list.get(i).getText().toString();
		}
		return array;
	}

	/**
	 * 设置元素等待时间
	 * @param webElement   页面元素
	 */
	public void waitForLoad(final By webElement) {
		WebDriverWait wait = new WebDriverWait(driver, 10); // timeOut为等待时间，单位秒
		wait.until(new ExpectedCondition<Boolean>() {
			public Boolean apply(WebDriver d) {
				boolean loadcomplete = getWebElement(webElement).isDisplayed();
				return loadcomplete;
			}
		});
		//此系统中常会有看不见的浮层挡住页面元素，保险起见，查找到元素后再休眠300毫秒
		try {Thread.sleep(300);} catch (InterruptedException e) {}
	}

	/**
	 * 等待某元素消失
	 * @param locator   定位器
	 * @param timeOut   超时时间
	 */
	public void waitForDisapp(By locator, int timeOut) {
		WebDriverWait wait = new WebDriverWait(driver, timeOut);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
	}

	/**
	 * 定位到新打开的窗口
	 */
	public void selNewWindow() {
		String current_handles = driver.getWindowHandle();
		// 打开新窗口后获取所有页面的句柄
		Set<String> all_handles = driver.getWindowHandles();
		// 通过循环，将当前的窗口句柄去除
		Iterator<String> it = all_handles.iterator();
		while (it.hasNext()) {
			if (it.next() == current_handles) {
				continue;
			}
			driver.switchTo().window(it.next());
		}
	}
	/**
	 * 获取元素坐标
	 * @param webElement   页面元素
	 * @return             坐标
	 */
	public String getLocation(By webElement){
		waitForLoad(webElement);
		return String.valueOf(getWebElement(webElement).getLocation()).trim().replace(" ", "");
	}
	/**
	 * x坐标
	 */
	public int getX(By webElement){
		waitForLoad(webElement);
		String location;
		location = String.valueOf(getWebElement(webElement).getLocation()).trim().replace(" ", "");
		return Integer.parseInt(location.substring(1, location.indexOf(",")));
	}
	/**
	 * Y坐标
	 */
	public int getY(By webElement){
		waitForLoad(webElement);
		String location;
		location = String.valueOf(getWebElement(webElement).getLocation()).trim().replace(" ", "");
		return Integer.parseInt(location.substring(location.indexOf(",")+1, location.length()-1));
	}
	/**
	 * 页面滚动
	 * @param offset  0     是顶部
	 *                10000 是底部
	 */
	public void scroll(int offset){
		JavascriptExecutor driver_js= (JavascriptExecutor) driver;
		driver_js.executeScript("window.scrollTo(0,"+offset+")");
	}
	/**
	 * 校验数字精度
	 * @param webElement   页面元素
	 * @return   小数点后的位数
	 */
	public int getAccuracy(By webElement){
		waitForLoad(webElement);
		String str =getValue(webElement);
		return str.substring(str.indexOf(".")+1,str.length()).length();
	}
	/**
	 * 校验数字精度
	 * @param value  需要校验的字符串
	 * @return   小数点后的位数
	 */
	public int getStrAccuracy(String value){
		return value.substring(value.indexOf(".")+1,value.length()).length();
	}

	/**
	 * 关闭浏览器
	 */
	public void close(){
		driver.quit();
	}
}