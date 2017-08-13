package twitterTrends;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class twitterClient {
	private twitterClient() {};
	
	public static void main(String args[]){
		
		//Querying user for the registry port.
		System.out.println("Enter the server port:");
		Scanner console = new Scanner(System.in);
		String host = "localhost";
		String port = console.nextLine();
		int portNumber = Integer.parseInt(port);
	
		try{
			//Connecting to the registry, creating the stub and querying user for Twitter Trend input.
			Registry registry = LocateRegistry.getRegistry(host, portNumber);
			twitterData stub = (twitterData) registry.lookup("twitterData");
			System.out.println("-Client Connected-");
			System.out.println("Please Enter a Country Name below with proper syntax. Example: -Russia-");
			
			while(true){
				String input = console.nextLine();
				if(input!= null){
					String response = stub.getResults(input);
					System.out.println(response);
					input = null;
					//console.close();
				}
			}
			
			
			
		}catch(Exception e){
			System.err.println("TwitterClient exception " + e.toString());
			e.printStackTrace();
		}
	}
}
