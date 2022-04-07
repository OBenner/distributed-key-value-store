#!/bin/bash
echo "Please enter servers address "
read input_variable
echo "Connect to: $input_variable"

sbt ";project client; run $input_variable"
