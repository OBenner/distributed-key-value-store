#!/bin/bash

echo "Please enter nodes number "
read input_variable
echo "count nodes: $input_variable"

for (( c=0; c<$input_variable; c++ ))
do
   S_PORT=$((9000+$c))
   echo "current port $S_PORT"
   docker run -i -d  -p $S_PORT:$S_PORT -e HOST_PORT=0.0.0.0:$S_PORT server:v1
done