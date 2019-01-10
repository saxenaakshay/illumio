The program does the following :
1. Reads input from csv file in the constructor, populates rules.
2. For each accept_packet request, runs parallely by executing streams in parallel. (Requires Java 8)
3. For any packet that matches the rules, it returns true. Otherwise, false.

The Rule class is used to store rule parameters, ip address is broken in individual octet and a long value is formed by shifting.

Interested in the following roles:
1. Platform 
2. Policy
