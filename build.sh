#!/bin/bash
#sbt ";project server; dist"
export DOCKER_SCAN_SUGGEST=false
docker build  -t server:v1 -f ./Dockerfile .
