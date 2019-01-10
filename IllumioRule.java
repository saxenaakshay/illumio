package Illumio;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

public class IllumioRule {
	
	public String direction;
	public String protocol;
	
	public boolean isSinglePort;
	public int onlyPort;
	public List<Long> portRange = new ArrayList<Long>();
	
	public boolean isSingleIP;
	public long onlyIP;
	public List<Long> IPRange = new ArrayList<Long>();
	
	public IllumioRule(String[] rules) {
		this.direction 	= rules[0];
		this.protocol 	= rules[1];
		if (rules[2].contains("-")) {
			this.isSinglePort = false;
			String[] range = rules[2].split("-");
			this.portRange.add(Long.parseLong(range[0]));
			this.portRange.add(Long.parseLong(range[1]));
		} else {
			this.isSinglePort = true;
			this.onlyPort = Integer.parseInt(rules[2]);
		}
		if (rules[3].contains("-")) {
			this.isSingleIP = false;
			String[] range = rules[3].split("-");
			this.IPRange.add(getIPValue(range[0]));
			this.IPRange.add(getIPValue(range[1]));
		} else {
			this.isSingleIP = true;
			this.onlyIP = getIPValue(rules[3]);
		}
	}

	private long getIPValue(String ipAddress) {
		// TODO Auto-generated method stub
		long ret = 0;
		InetAddress byName;
		try {
			byName = InetAddress.getByName(ipAddress);
			for (byte octet : byName.getAddress()) {
				ret <<= 8;
				ret |= octet & 0xff;
			}
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return ret;
	}

	public boolean isContainsPort(int port) {
		if (this.isSinglePort) {
			return (this.onlyPort == port) ? true : false;
		} else {
			return (port >= this.portRange.get(0) && port <= this.portRange.get(1)) ? true : false;
		}
	}
	
	public boolean isContainsIP(String ipAddress) {
		long ipValue = getIPValue(ipAddress);
		if (this.isSingleIP) {
			return (this.onlyIP == ipValue) ? true : false;
		} else {
			return (ipValue >= this.IPRange.get(0) && ipValue <= this.IPRange.get(1)) ? true : false;
		}
	}
	
}
