/**
 * 
 */
package bot;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.events.EventFiringWebDriver;

import DB.BD;
import music.MusicPlayer;

/**
 * @author ruben
 *
 */
public class Bot {
	private WebDriver driver;
	private String username;
	private String pass;
	private String profurl;
	private MusicPlayer mp;
	private boolean music;

	public Set<String> getStoryViewers(int num){
		Set<String> viewers = new HashSet<String>();
		try {
			driver.navigate().to(profurl);
			driver.findElement(By.xpath("/html/body/div[1]/section/main/div/header/div/div/span/img")).click();
			for (int i = 1; i < num; i++) {
				driver.findElement(By.className("ow3u_")).click();
			}
			driver.findElement(By.xpath("//*[@id=\"react-root\"]/section/div/div/section/div[2]/div[3]/div[2]/button")).click();
			List<WebElement> us = driver.findElements(By.xpath("/html/body/div[4]/div/div/div/div[2]/div/div/div"));
			int k = 0;
			JavascriptExecutor je = (JavascriptExecutor) driver;
			je.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("/html/body/div[4]/div/div/div/div[2]/div/div/div[5]")));
			je.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("/html/body/div[4]/div/div/div/div[2]/div/div/div[1]")));
			while(viewers.size()!=us.size()) {
				k++;
				try {
					//   .//
					String name ="@" + driver.findElement(By.xpath("/html/body/div[4]/div/div/div/div[2]/div/div/div["+k+"]/div[2]/div/div/a")).getAttribute("title");
					if(!viewers.contains(name)) {
						System.out.println(name);
						viewers.add(name);
					}
					if(k==13) {
						je.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("/html/body/div[4]/div/div/div/div[2]/div/div/div["+(k-1)+"]")));
						k = 0;
					}

				}catch(Exception e) {
					
				}
			}
		}catch(Exception e) {
		}
		return viewers;
	}
		
	
	public int countHistories() {
		int num = 0;
		try {
			driver.navigate().to(profurl);
			
			driver.findElement(By.xpath("/html/body/div[1]/section/main/div/header/div/div/span/img")).click();
			
			Thread.sleep(1000);
			
			
            num = driver.findElements(By.className("_7zQEa")).size();	
			
			
		}catch(Exception e) {
			return -1;
		}
		return num;
	}
	
	public Bot(boolean hide, boolean music) throws InterruptedException {
		this.music = music;
		if(hide) {
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--headless");
			options.addArguments("--mute-audio");
			driver = new ChromeDriver(options);
		}else {
			driver = new ChromeDriver();
		}		
		System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}
	
	public void disconnect() {
		driver.navigate().to(profurl);
		
		driver.findElement(By.xpath("/html/body/div[1]/section/main/div/header/section/div[1]/div/button")).click();;
		driver.findElement(By.xpath("/html/body/div[4]/div/div/div/div/button[9]")).click();;

		driver.close();
		
		if(mp!=null) mp.stop();
	}
	
	public int login(String us, String pas) throws InterruptedException {
		username = us;
		pass = pas;
		driver.get("https://www.instagram.com/");
		
		String loginurl = driver.getCurrentUrl();

		WebElement usfield = driver.findElement(By.name("username"));
		usfield.sendKeys(username);
		WebElement passfield = driver.findElement(By.name("password"));
		passfield.sendKeys(pass);
		passfield.submit();
		
		Thread.sleep(4000);
		
		if(driver.getCurrentUrl().equals(loginurl)) {
			return -1;
		}
		
		if(music) {
			mp = new MusicPlayer();
			mp.start();
		}
		
		if(username.charAt(0)=='@') {
			profurl = "https://www.instagram.com/"+username.substring(1, username.length())+"/"; 
		}else {
			profurl = "https://www.instagram.com/"+username+"/"; 
		}
		
		return 1;
	}
	
	public Set<String> getFollowing(){
		Set<String> following = new HashSet<String>();

		JavascriptExecutor je = (JavascriptExecutor) driver;

		driver.navigate().to(profurl);

		driver.findElement(By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[3]/a")).click();

		try {    
			WebElement scroll = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]"));
			long lastht = 0, ht = 1;
			while(lastht != ht) {
				lastht = ht;
				Thread.sleep(500);
				ht = (long) je.executeScript("arguments[0].scrollTo(0, arguments[0].scrollHeight);"
						+ "return arguments[0].scrollHeight;", scroll);
			}
		    
			List<WebElement> names = scroll.findElements(By.tagName("a"));
			for (WebElement we : names) {
				String name = "@"+we.getAttribute("title");
				if(!name.contentEquals("@")) following.add(name);
			}
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		
		return following;
	}
	
	public Set<String> getFollowers(){
		Set<String> followers = new HashSet<String>();
		JavascriptExecutor je = (JavascriptExecutor) driver;
		
		driver.navigate().to(profurl);
				
		driver.findElement(By.xpath("/html/body/div[1]/section/main/div/header/section/ul/li[2]/a")).click();
		
		try {    
			WebElement scroll = driver.findElement(By.xpath("/html/body/div[4]/div/div/div[2]"));
			long lastht = 0, ht = 1;
			while(lastht != ht) {
				lastht = ht;
				Thread.sleep(500);
				ht = (long) je.executeScript("arguments[0].scrollTo(0, arguments[0].scrollHeight);"
						+ "return arguments[0].scrollHeight;", scroll);
			}
		    
			List<WebElement> names = scroll.findElements(By.tagName("a"));
			for (WebElement we : names) {
				String name = "@"+we.getAttribute("title");
				if(!name.contentEquals("@")) followers.add(name);
			}
		} catch (Exception e) {
		    e.printStackTrace();
		}
		
		return followers;
	}
	
	public void unfollow(String user){
		driver.navigate().to("https://www.instagram.com/"+user.substring(1, user.length())+"/");
				
		driver.findElement(By.className("nZSzR")).findElements(By.tagName("button")).get(1).click();;

		driver.findElement(By.className("mt3GC")).findElements(By.tagName("button")).get(0).click();;
	}
	
	
	/**
	 * @param args
	 */


}
