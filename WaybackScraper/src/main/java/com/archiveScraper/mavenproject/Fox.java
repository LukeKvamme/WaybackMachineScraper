package com.archiveScraper.mavenproject;

import java.io.IOException;
import java.time.LocalDate;

public class Fox extends Source {
	// https://web.archive.org/web/20150101163714/http://www.foxnews.com/

	public Fox() {
		super("https://web.archive.org/web/", "163714/http://www.foxnews.com/",
				" -- YOUR PATH HERE -- ",
				LocalDate.parse("2021-01-01"), LocalDate.parse("2022-10-21"));
	}

	public Fox(String urlFirstHalf, String urlSecondHalf, String outputFolderPath, String startDate, String endDate) {
		super(urlFirstHalf, urlSecondHalf, outputFolderPath, LocalDate.parse(startDate), LocalDate.parse(endDate));
	}

	public void fetchHomePages() throws IOException {
		super.fetchHomePages();
	}

}
