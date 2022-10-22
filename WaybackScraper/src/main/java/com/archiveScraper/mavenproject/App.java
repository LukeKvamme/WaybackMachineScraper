package com.archiveScraper.mavenproject;

import java.io.IOException;
import java.util.ArrayList;

/**
 * The main for the mini webscraper
 * 
 * @author lukekvamme
 * @modified october 22, 2022
 */
public class App {

	public static void main(String[] args) throws IOException {
		ArrayList<Source> sources = new ArrayList<Source>();

		Cnn cnn = new Cnn();
		sources.add(cnn);

		Fox fox = new Fox();
		sources.add(fox);

		for (Source src : sources) {
			src.fetchHomePages();
		}

	}
}