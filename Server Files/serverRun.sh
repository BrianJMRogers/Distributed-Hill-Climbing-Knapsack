#!/bin/bash
# $1=computerName (ie, Brians-MacBook-Pro.local)
# $2=number of nodes
java HillClimbingServer $1 224.0.0.35 1234 $2 ../data/hugeRequirements.csv 1000
