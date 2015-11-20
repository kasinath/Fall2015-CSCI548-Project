package edu.usc.csci548.project.travelapp.crawler.htmlunit;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.logging.Level;
import org.apache.commons.logging.LogFactory;
import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import net.minidev.json.JSONObject;


public class JavascriptScraper 
{
	WebClient webClient;
	String url;
	private static final int RETRIES = 100;

	public JavascriptScraper(String url)
	{
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		this.url = url; 

		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.SEVERE); 
		java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.SEVERE);

	}

	public ArrayList<JSONObject> scrape() throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{
		ArrayList<JSONObject> result = new ArrayList<>();
		HtmlPage page = webClient.getPage(url);
		ArrayList<DomNode> reviews = (ArrayList<DomNode>) page.getByXPath("//div[starts-with(@id, 'review_')]");

		for(DomNode review : reviews)
		{
			JSONObject _review = new JSONObject();

			String reviewID = ((DomElement) review).getId();
			ArrayList<DomNode> reviewTxt = (ArrayList<DomNode>) review.getByXPath("//div[@id = '" + reviewID + "']//div[contains(@class,'basic_review')]//div[@class='wrap']//div[@class='entry']/p[@class='partial_entry']");
			ArrayList<DomNode> moreTxt = (ArrayList<DomNode>) review.getByXPath("//div[@id = '" + reviewID + "']//div[contains(@class,'dyn_full_review')]//div[@class='entry']");
			ArrayList<DomNode> moreSpanClick = (ArrayList<DomNode>) review.getByXPath(".//div[@class='wrap']//div[@class='entry']/p[@class='partial_entry']/span[@class='partnerRvw']/span");

			if(moreTxt.size() > 0)
			{
				System.out.println(reviewID + " :  " + moreTxt.get(0).asText());
				_review.put("review", moreTxt.get(0).asText());
			}
			else if(moreSpanClick.size()>0)
			{
				((HtmlElement) moreSpanClick.get(0)).click();
				int i;
				for (i = 0; i < RETRIES; i++) 
				{
					moreTxt = (ArrayList<DomNode>) page.getByXPath("//div[@id = '" + reviewID + "']//div[contains(@class,'dyn_full_review')]//div[@class='entry']/p"); //   /div[contains(@class,'dyn_full_review')]//div[@class='entry']/p");
					if (moreTxt.size()>0)
					{
						System.out.println(reviewID + "  : " + moreTxt.get(0).asText());
						_review.put("review", moreTxt.get(0).asText());

						break;
					}
					synchronized (page) {
						page.wait(500);
					}
				}

				if(i==RETRIES)
				{
					_review.put("review", "NO REVIEW");
					System.out.println(reviewID + " : " + "TIMEDOUT");
				}


			}
			else
			{
				System.out.println(reviewID + " : " + reviewTxt.get(0).asText());
				_review.put("review", reviewTxt.get(0).asText());
			}

			result.add(_review);
		}
		return result;
	}
	public static void main( String[] args ) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{
		JavascriptScraper jsScraper = new JavascriptScraper("http://www.tripadvisor.com/Attraction_Review-g187791-d192285-Reviews-Colosseum-Rome_Lazio.html");
		jsScraper.scrape();
	}
}
