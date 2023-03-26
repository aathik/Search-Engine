package project;

import java.util.ArrayList;
import java.util.Scanner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import project.WebCrawl;
import project.ConvertToText;
import project.GetWords;

public class Main {
	public static String country;
	public static String city;
	public static ArrayList<String> wordCorrection = new ArrayList<String>();

	//get the country's name
	public static String get_country() {
		return country.toLowerCase();
	}

	public static void main(String[] args) throws Exception {

		Scanner scan = new Scanner(System.in);

		//setting the webdriver properties
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"/src/bundles/chromedriver.exe");
		ChromeOptions options=new ChromeOptions();
		//options.setBinary("C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe");
		options.addArguments("--remote-allow-origins=*");
		options.addArguments("--headless=new");
		//WebDriver driver = new ChromeDriver();

		WebDriver driver=new ChromeDriver(options);

		//menu driven program
		int choice = 0;
		while(true) {
			System.out.println("------------------------------------------------------------");
			System.out.println("Welcome to Educations.com");
			System.out.println("------------------------------------------------------------");
			System.out.println();
			System.out.println("1. Which Country?");
			System.out.println("2. Search based on Location");
			System.out.println("3. Search based on Course Type");
			System.out.println("4. Exit");
			System.out.println();
			System.out.println("Enter your choice:");

			choice = scan.nextInt();
			scan.nextLine();
			switch (choice) {
			case 1:
				System.out.println("Enter the name of the country, you wish to search:");
				country = scan.nextLine(); 

				driver.get("https://www.educations.com/search");
				WebElement searchBar = driver.findElement(By.id("search-header-form-filter-freetext"));
				WebElement searchBtn = driver.findElement(By.className("search-header-form-button-search"));			
				searchBar.clear();
				searchBar.sendKeys(country);
				searchBtn.click();
				//driver.quit();
				String currUrl = driver.getCurrentUrl();
				System.out.println("Current URL to be crawled: "+currUrl);
				System.out.println();

				String urlCountry = currUrl.substring(34);

				System.out.println("Crawling the website");
				WebCrawl.crawl(currUrl,urlCountry);
				//driver.close();
				WebCrawl.iterateAndWrite(country);
				System.out.println("Crawling Done....");

				System.out.println("Converting to Text Files");
				ConvertToText.HTMLtoTextFile(country);
				System.out.println("Text Files are created....");

				GetWords.scrape(country);
				break;
			case 2:
				System.out.println("Enter the city: ");
				city = scan.nextLine();
				city=city.toLowerCase();
				wordCorrection = SpellCheck.correction(city);

				// Ask user to enter correct spelling if the search word spelling is incorrect. 
				while (wordCorrection.size() > 0) {
					System.out.println("Are you sure the spelling is correct?! Please select a word from the given list");
					System.out.println(wordCorrection);

					System.out.print("Enter a city name: ");
					city = "";
					city = scan.nextLine();

					wordCorrection = SpellCheck.correction(city);
				}
				BruteForce_PageRank.page_rank(city);
				break;
			case 3:
				BoyerSearch.search_course();
				break;
			case 4:
				System.out.println("GoodBye.....");
				System.exit(0);

				//default case to display the message invalid choice made by the user
			default:
				System.out.println("Invalid choice!!! Please make a valid choice. \\n\\n");
			}

		}

	}

}
