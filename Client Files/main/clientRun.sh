#!/bin/bash
rm output.csv
touch output.csv
java -Djava.net.preferIPv4Stack=true HillClimbingClient 224.0.0.35 1234 30
