package com.archiveScraper.mavenproject;

import java.io.IOException;
import java.time.LocalDate;

public class Cnn extends Source {
	// https://web.archive.org/web/x/http://www.cnn.com/"

	public Cnn() {
		super("https://web.archive.org/web/", "01041727/http://www.cnn.com/",
				" -- YOUR PATH HERE -- ",
				LocalDate.parse("2021-01-01"), LocalDate.parse("2022-10-21"));
	}

	public Cnn(String urlFirstHalf, String urlSecondHalf, String outputFolderPath, String startDate, String endDate) {
		super(urlFirstHalf, urlSecondHalf, outputFolderPath, LocalDate.parse(startDate), LocalDate.parse(endDate));
	}

	public void fetchHomePages() throws IOException {
		super.fetchHomePages();
	}

}
