package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


import org.jsoup.Jsoup;

import org.jsoup.nodes.*;
import org.jsoup.select.*;
import java.io.*;
import java.util.*;

public class WebCrawl {
	static HashSet<String> uniqueLinks = new HashSet<String>(); 
	private static List<String> articles;
	private static int max_pages = 10;
	public static int c=1;

	public static void  crawl(String url, String country) {

		try {
			if(!uniqueLinks.contains(url) && uniqueLinks.size() < max_pages) {
				Document document= Jsoup.connect(url).get();

				String pattern = "a[href^=\"/search/"+country+"?page=\"]:not([href*=\"&sort=\"])";
				//System.out.println(pattern);
				Elements pageLinks= document.select(pattern);
				if(pageLinks.size()==0) {
					uniqueLinks.add(url);
					System.out.println("Page 1 crawled");
				}
				else {
					for (Element page : pageLinks) {
						//if(!matcher.find()) {
						if (uniqueLinks.add(url)) {
							//Remove the comment from the line below if you want to see it running on your editor
							System.out.println("Page "+c+" crawled");
							c++;
						}
						//}
						crawl(page.attr("abs:href"), country);
					}
					c=1;
				}	
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	public static void iterateAndWrite(String country){
		uniqueLinks.forEach(x -> {
			Document document;
			try {
				document = Jsoup.connect(x).get();
				Elements articleLinks = document.select("div.emg-serp__serp-container");
				//System.out.println("links "+ articleLinks);

				String html = articleLinks.html();
				//System.out.println("links "+ html);
				String filePath = "src/resources/htmlFiles/"+country;
				File tmpFile = new File(filePath);  
				tmpFile.mkdir();
				//String filePath = "src/resources/htmlFiles/"+country;

				String fileName = x.replaceAll("[^a-zA-Z0-9_-]", "") + ".html";
				tmpFile = new File(filePath+'/'+fileName);
				//System.out.println("FileName:" + filePath+ " "+ fileName);
				boolean fileExists = tmpFile.exists();
				if(!fileExists) {
					//System.out.println("working ");
					// Write the formatted data into a html file.
					BufferedWriter out = new BufferedWriter(new FileWriter(filePath +'/' +fileName, true));
					out.write(x + " " + html);
					out.close();
				}
			} catch (IOException e) {
				System.err.println(e.getMessage());
			}
		});
	}
}
