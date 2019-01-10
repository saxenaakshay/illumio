package Illumio;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IllumioFirewall {
	
	List<IllumioRule> rules = new ArrayList<IllumioRule>();
	
	public static void main(String[] args) {
		IllumioFirewall fw = new IllumioFirewall("/Users/akshaysaxena/Desktop/test.csv");
		System.out.println("Ans: " + fw.accept_packet("inbound", "tcp", 80, "192.168.1.2"));
		System.out.println("Ans: " + fw.accept_packet("inbound", "udp", 53, "192.168.2.1"));
		System.out.println("Ans: " + fw.accept_packet("outbound", "tcp", 10234, "192.168.10.11"));
		System.out.println("Ans: " + fw.accept_packet("inbound", "tcp", 81, "192.168.1.2"));
		System.out.println("Ans: " + fw.accept_packet("inbound", "udp", 24, "52.12.48.92"));
	}
	
	public IllumioFirewall(String filePath) {
		try {
			BufferedReader  br = new BufferedReader(new FileReader(filePath));
			String line = "";
			String delimitier = "\t";
			try {
				while ((line = br.readLine()) != null) {
				    String[] input = line.split(delimitier);
				    IllumioRule rule = new IllumioRule(input);
				    rules.add(rule);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	public boolean accept_packet(String direction, String protocol, int port, String ipAddress) {		
		return rules.parallelStream().anyMatch(rule -> this.isMatch(rule, direction, protocol, port, ipAddress));
	}
	
	private boolean isMatch(IllumioRule rule, String direction, String protocol, int port, String ipAddress) {
		if (	rule.direction.equals(direction) && 
				rule.protocol.equals(protocol) && 
				rule.isContainsPort(port) &&
				rule.isContainsIP(ipAddress)	) {
			return true;
		}
		return false;
	}
	
}
