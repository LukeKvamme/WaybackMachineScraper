package com.archiveScraper.mavenproject;

import java.io.IOException;

import com.gargoylesoftware.htmlunit.BrowserVersion;
//only uses HTML UNIT
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

/**
 * Connects to the website, disables css and js, returns the page of the url
 * 
 * @author lukekvamme
 * @modified october 22, 2022
 */
public class WebScraper {

	public WebScraper() {
	}

	/**
	 * gets the html from the input url, sleeps for 5 seconds upon making connection
	 * to avoid IP banning
	 * 
	 * @param url
	 * @return
	 */
	public HtmlPage getDocument(String url) {

		HtmlPage page = null;
		try (final WebClient webClient = new WebClient(BrowserVersion.FIREFOX)) {
			webClient.getOptions().setUseInsecureSSL(true);
			webClient.getOptions().setCssEnabled(false);
			webClient.getOptions().setJavaScriptEnabled(false);

			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			page = webClient.getPage(url);

		} catch (IOException e) {
			e.printStackTrace();
		}
		return page;
	}

}
