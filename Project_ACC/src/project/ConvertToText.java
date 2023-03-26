package project;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class ConvertToText {
	public static int flag=0;

	public static void HTMLtoTextFile(String country)
	{
		System.out.println("\nFetch and convert all .html files to .txt files");
		try {
			String source_path = "src/resources/htmlFiles/"+country+'/';
			String dest_path = "src/resources/textFiles/"+country;
			File dir = new File(source_path);
			String files_list[] = dir.list();
			String courseName, collegeName, courseType, courseLocation, courseDuration, modeOfStudy;
			File temp = new File(dest_path);
			boolean fileExist = temp.exists();
			if(!fileExist) {


				for(String filename: files_list) {

					File currentFile = new File(source_path + filename);
					Document doc = Jsoup.parse(currentFile, "UTF-8");
					Elements colleges = doc.getElementsByClass("emg-serp__row emg-serp__row-has-compare");
					for(Element college: colleges) {
						//System.out.println(college);
						courseName = college.getElementsByClass("emg-serp-item__title-text").text();
						collegeName = college.getElementsByClass("emg-serp-item__subtitle").text();
						courseType = college.getElementsByClass("emg-serp-item__flag emg-serp-item__flag-type").text();
						courseLocation = college.getElementsByClass("emg-serp-item__flag emg-serp-item__flag-place").text();
						courseDuration = college.getElementsByClass("emg-serp-item__flag emg-serp-item__flag-length").text();
						modeOfStudy = college.getElementsByClass("emg-serp-item__flag emg-serp-item__flag-deliverymethod").text();
						//					System.out.println("Coursname:"+ courseName);
						//					System.out.println("Collegename:"+ collegeName);
						//					System.out.println("CourseType:"+ courseType);
						//					System.out.println("courseLocation:"+ courseLocation);
						//					System.out.println("courseDuration:"+ courseDuration);
						//					System.out.println("modeOfStudy:"+ modeOfStudy);
						String print_value = courseName+";"+collegeName+";"+courseType+";"+courseLocation+";"+courseDuration+";"+modeOfStudy;
						String fileName = filename.replace(".html", ".txt");
						File tmpFile = new File(dest_path);  
						tmpFile.mkdir();

						BufferedWriter out = new BufferedWriter(new FileWriter(dest_path +'/'+ fileName, true));
						out.write(print_value + " \n");
						out.close();
					}
				}
			}

		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void main(String[] args) {
		HTMLtoTextFile("canada");
	}

}
