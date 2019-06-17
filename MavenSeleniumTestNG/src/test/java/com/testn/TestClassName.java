package com.testn;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class TestClassName {

	static String username1 = "";
	static String password1 = "";
	
	@Test
	public static void Main() {
		// TODO Auto-generated method stub
		System.setProperty("webdriver.chrome.driver",
				"C:\\Data\\Tools\\Selenium\\chromedriver_win32\\chromedriver.exe");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().maximize();
		String baseUrl = "https://the-internet.herokuapp.com/";
		String expectedTitle = "The Internet";
		String actualTitle = "";
		driver.get(baseUrl);
		actualTitle = driver.getTitle();
		if (actualTitle.contentEquals(expectedTitle)) {
			System.out.println("Test Passed!");
		} else {
				System.out.println("Test Failed");
		}
		driver.findElement(By.xpath("(//div[@id=\"content\"]//a)[21]")).click();

		try {
			FileInputStream file = new FileInputStream(new File("C:\\Data\\Login.xlsx"));

			// Create Workbook instance holding reference to .xlsx file
			XSSFWorkbook workbook = new XSSFWorkbook(file);

			// Get first/desired sheet from the workbook
			XSSFSheet sheet = workbook.getSheetAt(0);

			// Iterate through each rows one by one
			Iterator<Row> rowIterator = sheet.iterator();
			rowIterator.next();
			while (rowIterator.hasNext()) {
				Row row = rowIterator.next();
				// For each row, iterate through all the columns
				Iterator<Cell> cellIterator = row.cellIterator();

				Cell username = cellIterator.next();
				username1 = username.getStringCellValue();
				System.out.println(username1);
				Cell password = cellIterator.next();
				password1 = password.getStringCellValue();
				System.out.println(password1);
				System.out.println("");
				
				driver.findElement(By.xpath("//div[@class=\"example\"]//input[@name=\"username\"]")).sendKeys(username1);
				driver.findElement(By.xpath("//div[@class=\"example\"]//input[@name=\"password\"]")).sendKeys(password1);
				driver.findElement(By.xpath("//div[@id=\"content\"]//button[@class=\"radius\"]")).click();
				actualTitle = driver.findElement(By.xpath("//div[@class=\"row\"]//div[@class=\"flash success\"]")).getText();
				expectedTitle = "You logged into a secure area!";
				String[] parts = actualTitle.split("\n");
				System.out.println(parts[0]);
				if (parts[0].contentEquals(expectedTitle)) {
					System.out.println("Login Passed");
				} else {
					System.out.println("Login Failed");
				}
				driver.findElement(By.xpath("//div[@class=\"row\"]//a[@class=\"button secondary radius\"]")).click();
				actualTitle = driver.findElement(By.xpath("//div[@class=\"row\"]//div[@class=\"flash success\"]")).getText();
				expectedTitle = "You logged out of the secure area!";
				parts = actualTitle.split("\n");
				System.out.println(parts[0]);
				if (parts[0].contentEquals(expectedTitle)) {
					System.out.println("Logout Passed!");
				} else {
					System.out.println("Logout Failed");
				}
			}
			file.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

}