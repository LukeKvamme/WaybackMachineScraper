package com.archiveScraper.mavenproject;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Period;

/**
 * Takes as input: - the first half of an Archive.org url (up to the year
 * integer) - the second half of an Archive.org url (after the day integer) -
 * the file to create the .txt files inside of - the date to start
 * (year-month-day format) - the date to end (also uses DateTime format)
 * 
 * Loops through specified range of dates, grabs the html from the Archive.org
 * website, and outputs the findings into individual .txt files
 * 
 * @author lukekvamme
 * @modified october 22, 2022
 */
public class Source {
	public static final WebScraper WS = new WebScraper();

	private String urlFirstHalf;
	private String urlSecondHalf;
	private String outputFolderPath;

	private LocalDate startDate;
	private LocalDate endDate;
	private BufferedWriter bw;

	public Source(String first, String second, String outputFolder, LocalDate startDate, LocalDate endDate) {
		this.urlFirstHalf = first;
		this.urlSecondHalf = second;
		this.outputFolderPath = outputFolder;
		this.startDate = startDate;
		this.endDate = endDate;
	}

	/**
	 * for 365 (arbitrary, just what I need atm. Change the loop number for the
	 * number of days needed): Find the date, create the next file, increment the
	 * url, then grab the html at the specified url and check if it contains the
	 * Archive.org keyword, then clip what is wanted and write it in a .txt file.
	 * 
	 * @throws IOException
	 */
	public void fetchHomePages() throws IOException {
		DayOfWeek day = LocalDate.parse(this.startDate.toString()).getDayOfWeek();
		String[] dateArr;
		String htmlText;
		String incrementedURL;
		File file = new File("null file instantiated");
		;

		for (int i = 0; i < 365; i++) {
			dateArr = getDate(i, day);
			file = createNextFile(dateArr, file);

			incrementedURL = incrementUrl(dateArr);

			htmlText = WS.getDocument(incrementedURL).asNormalizedText();

			if (noTimeStamp(htmlText, dateArr[0])) {
				file.delete();
				continue;
			}
			clipHtml(htmlText, dateArr[0]);
			// testing purposes : bw.write(htmlText);

			System.err.println((i + 1) + " files have been created");

		}
	}

	/**
	 * Returns an array with the first element being the incremented date and the
	 * second element being the day of the week
	 * 
	 * @param incrementor
	 * @param day
	 * @return
	 */
	private String[] getDate(int incrementor, DayOfWeek day) {
		this.endDate = this.startDate.plus(Period.ofDays(incrementor));
		day = LocalDate.parse(this.endDate.toString()).getDayOfWeek();

		String[] arr = { this.endDate.toString(), day.toString() };

		return arr;
	}

	/**
	 * Creates a new .txt file in the specified output folder with the name being
	 * the day of the week and date the web page occurred on
	 * 
	 * @param dateArr
	 * @param file
	 * @return
	 */
	private File createNextFile(String[] dateArr, File file) {
		file = new File(this.outputFolderPath + dateArr[1] + " " + dateArr[0]);
		try {
			this.bw = new BufferedWriter(new FileWriter(file));
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}

		return file;
	}

	/**
	 * Updates the date inside of the url, writes it to the file, then returns it
	 * 
	 * @param dateArr
	 * @return
	 * @throws IOException
	 */
	private String incrementUrl(String[] dateArr) throws IOException {
		String incrementedURL;
		incrementedURL = this.urlFirstHalf + dateArr[0].toString().replaceAll("-", "") + this.urlSecondHalf;

		this.bw.write(incrementedURL);
		this.bw.newLine();

		return incrementedURL;
	}

	/**
	 * Some files dont have Archive.org's "TIMESTAMPS" keyword, returns true if it
	 * is a bad file or false if it is normal
	 * 
	 * @param htmlText
	 * @param date
	 * @return
	 */
	private boolean noTimeStamp(String htmlText, String date) {
		if (htmlText.split("TIMESTAMPS").length != 2) {
			System.out.println("bad file date: " + date);
			return true;
		}
		return false;
	}

	/**
	 * Only outputs the text that comes after Archive.org's website part,
	 * effectively clipping just the true web page, by using one of their keywords
	 * "TIMESTAMPS"
	 * 
	 * @param text
	 * @param date
	 */
	private void clipHtml(String text, String date) {
		String[] lineArr;
		lineArr = text.split("TIMESTAMPS");

		try {
			this.bw.write(lineArr[1]);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
