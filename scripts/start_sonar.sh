#!/bin/bash

./Tools/Sonar/bin/linux-x86-64/sonar.sh start &
sleep 12
xdg-open http://localhost:9000

