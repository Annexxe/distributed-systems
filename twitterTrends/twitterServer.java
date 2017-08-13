package twitterTrends;
//import java.util.Scanner;
//import java.util.ArrayList;
//import twitter4j.*;
//import twitter4j.conf.*;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
//import twitter4j.util.*;
//import twitter4j.management.*;
//import twitter4j.api.*;
//import twitter4j.json.*;
//import twitter4j.auth.*;


public class twitterServer {
	private twitterServer() {};
	
	public static void main(String args[]) {
		
		String host = "localhost";
		try{
			//Setting up server
			System.setProperty("java.rmi.server.hostname", host);
			twitterData servant = new twitterServant();
			//Bind the remote objects stub into the registry
			Registry registry;
			
			try{
				registry = LocateRegistry.getRegistry();
				registry.list();
			}catch(RemoteException e){
				System.out.println("RMI registry cannot be located at port " + Registry.REGISTRY_PORT);
				registry = LocateRegistry.createRegistry(Registry.REGISTRY_PORT);
				System.out.println("RMI registry created at port " + Registry.REGISTRY_PORT);
			}
			
			registry.rebind("twitterData", servant);
			System.out.println("Twitter Server Ready");
		}catch(Exception e){
			System.err.println("TwitterServer exception: " + e.toString());
			e.printStackTrace();
		}
	}
}