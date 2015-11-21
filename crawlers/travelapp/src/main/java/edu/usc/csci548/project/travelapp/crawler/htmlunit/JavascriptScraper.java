package edu.usc.csci548.project.travelapp.crawler.htmlunit;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import org.apache.commons.logging.LogFactory;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.DomNode;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;



public class JavascriptScraper 
{
	WebClient webClient;
	String url;
	private static final int RETRIES = 2;

	public JavascriptScraper(String url)
	{
		webClient = new WebClient(BrowserVersion.CHROME);
		webClient.setAjaxController(new NicelyResynchronizingAjaxController());
		this.url = url; 

		LogFactory.getFactory().setAttribute("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
		java.util.logging.Logger.getLogger("com.gargoylesoftware.htmlunit").setLevel(Level.OFF); 
		java.util.logging.Logger.getLogger("org.apache.commons.httpclient").setLevel(Level.OFF);

	}

	@SuppressWarnings("unchecked")
	public JSONArray scrape() throws FailingHttpStatusCodeException, IOException 
	{
		JSONArray result = new JSONArray();


		boolean onceMore = false;
		do
		{
			try
			{
				HtmlPage page = webClient.getPage(url);
				ArrayList<DomNode> reviews = (ArrayList<DomNode>) page.getByXPath("//div[starts-with(@id, 'review_')]");
				ArrayList<DomNode> tryAgain = new ArrayList<>();
				int cnt=0;
				do
				{
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
								tryAgain.add(review);
							}
						}
						else
						{
							System.out.println(reviewID + " : " + reviewTxt.get(0).asText());
							_review.put("review", reviewTxt.get(0).asText());
						}
						result.add(_review);
					}

					reviews = tryAgain;
					cnt++;
				}while(cnt<2);

				onceMore = false;	

			} catch (InterruptedException e)
			{
				onceMore = !onceMore;
				System.err.println(e.getMessage());
			}
			catch(Exception e)
			{
				onceMore = !onceMore;
				System.err.println(e.getMessage());
			}


		}while(onceMore);

		return result;
	}
	public static void main( String[] args ) throws FailingHttpStatusCodeException, MalformedURLException, IOException, InterruptedException
	{
		JavascriptScraper jsScraper = new JavascriptScraper("http://www.tripadvisor.com/Attraction_Review-g187791-d192285-Reviews-Colosseum-Rome_Lazio.html");
		jsScraper.scrape();
	}
}
