package twitterTrends;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface twitterData extends Remote{
	//Creating the interface method to get the Twitter Results
	String getResults(String n) throws RemoteException;
}
