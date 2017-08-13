package twitterTrends;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.Scanner;
import twitter4j.Location;
import twitter4j.ResponseList;
import twitter4j.Trends;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

import java.rmi.server.UnicastRemoteObject;

public class twitterServant extends UnicastRemoteObject implements twitterData{
	
	public twitterServant() throws RemoteException{}
	
	//Initializing Variables
	ResponseList<Location> locations = null;
	ArrayList<String> places = new ArrayList<String>();
	ArrayList<Integer> placesID = new ArrayList<Integer>();
	Trends trends = null;
	Scanner console = new Scanner(System.in);
	int locationPosition = 0;
	int locationID = 0;
	boolean check = false;
	
	//Method to query the Twitter API for Trends in a particular location.
	public String getResults(String input) throws RemoteException{
		//Variables for method use
		String userInput = input;
		String t = "Trends: ";
		
		try{
			//Setting up Twitter configuration and passing Authentication Keys and Tokens.
			ConfigurationBuilder cb = new ConfigurationBuilder();
	        cb.setDebugEnabled(true)
	                .setOAuthConsumerKey("7X6ksR8FMxh2NKna30LhBe2m3")
	                .setOAuthConsumerSecret("ZKhDXpK69rGCGp6iOtAmmAXC8APrMCjzCfUoRTHxY3cvKRBnjE")
	                .setOAuthAccessToken("3041766346-BaoobdcNvInbODpFr5D9H9d4u7wO0pCKenKvNoq")
	                .setOAuthAccessTokenSecret("NWKhjAOzdaDHAViUG6p3oULNnA9FfnxYp6FAVMCbSswe7");
			
	        //Creating the twitter object to make API calls.
	        TwitterFactory tf = new TwitterFactory(cb.build());
	        Twitter twitter = tf.getInstance();
	        
	        //Retrieving the names of every location and its appropriate World ID and saving each into a ArrayList.
	        locations = twitter.getAvailableTrends();
	        for (Location location : locations) {
				places.add(location.getName());
				placesID.add(location.getWoeid());
				}
	        
	        //Comparing user input to every known stored location. If true - retrieve the World ID and save it into locationID
	        for (int j = 0; j < places.size(); j++){
				if(userInput.equals(places.get(j))){
					locationPosition = j;
					locationID = placesID.get(locationPosition);
					j = places.size();
					t = "Trends: ";
					check = true;
				}
				//If incorrect spelling - return prompt
				else if(userInput.equals(places.get(j)) != true){
					check = false;
					t = "Incorrect Syntax - please try again";
				}
			}
	        
	        //If user entered name correctly.
	        if(check == true){
	        	//Use the stored Location ID to pull the Twitter Trends for that location
	        	trends = twitter.getPlaceTrends(locationID);
	        
	        	for (int i = 0; i < trends.getTrends().length; i++) {
	        		//Append Trends to a single string.
	        		t = t.concat(trends.getTrends()[i].getName() +  ", ");
				}
	        }
		}catch (TwitterException e) {
			  System.out.println(e.getStatusCode());
		}
		
		//Return list of Trends or return Incorrect Syntax
		return t;
	}
}
