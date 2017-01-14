# What is this? #

Great question. You're looking at a [hill climbing](https://en.wikipedia.org/wiki/Hill_climbing) algorithm which solves the [knapsack problem](https://en.wikipedia.org/wiki/Knapsack_problem). This program distributes it's workload to client nodes thereby allowing a large number of tests to be run quickly and returning the best possible knapsack.

# Contributors #

[Jake Ballinger](http://ballingerj.github.io/)

1. History.java
2. Item.java
3. Knapsack.java
4. Node.java
5. Quick.java
6. readCSV.java

[Brian Rogers](brianjmrogers.github.io)

1. csvDataParser.java
2. HillClimbingClient.java
3. HillClimbingServer.java

# Running the program #

## Pull ##

You can clone the repo by:
1. Clicking the "Clone or download" button and copying the now visible URL
2. running the following command from the command line ```git clone <copied URL> ```

Now that you have cloned the repository, you can get into running the program.

## The server ##

The server is fairly straightforward. Navigating to the Server Files folder you'll find two files:

1. ```HillClimbingServer.java```
2. ```serverRun.sh```

Compile HillClimbingServer.java from the command line by running ```javac HillClimbingServer.java```

After this is compiled, you're ready to run the program. It is helpful to run the program with the provided script.

### serverRun.sh ###

If you aren't familiar with running a script then you should do a quick google search on how to run a bash script on your operating system.


**Script arguments**
HillClimbingServer.java requires 6 command line arguments and the script takes care of 4 of those for you. You will need to provide two command line arguments when running the scripts:

1. Your computer name. You can locate this by a quick google search for "What is my computer name <myComputer>". For instance mine is Brians-MacBook-Pro.local
2. How many clients you will be running

**HillClimbingServer.java arguments**
You may need to edit the script to fit your network. The arguments are as follows:

1. Your computer name
2. The second argument is the IP over which you will be multicasting.
3. The port you will be communicating through
4. The number of client nodes
5. The path from **HillClimbingClient.java** to the items file (more on what this file should be like later)
6. The maximum size of the knapsack.

## The Client ##

The client is more involved than the server and is responsible for almost all the work of the program.

### Main ###

Compile the java files by running the command ``` javac *.java ``` while in the "main" directory

### clientRun.sh ###

Similar to the server, the client is also run with a script. In contrast, however, clientRun.sh does not take any arguments. The script takes care of the arguments itself. Those arguments are as follows:
1. The IP address on which the client will listen. This must be the same as the server's.
2. The port on which the client will listen. This must be the same as the server's.
3. The number of test runs you would like the client to make.

### data ###

You'll notice that there are already example files in this folder. This is the place you would want to place your own file. It should follow the same pattern as the files in this folder. That is: ``` itemName, cost, value, "itemName" ```. It is important that you ensure that the path to your file from HillClimbingClient.java is correctly written in serverRun.sh's script.

# "I just ran the program. What am I looking at?" #

Looking at the server's console, you are looking at the best knapsack found between all the client(s)'s runs. It was found by minimizing the cost/value ratio. You will see that knapsack listed and can then use this information to your liking. Thanks for using the program!
