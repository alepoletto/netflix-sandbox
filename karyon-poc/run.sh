#!/bin/bash
java -DKARYON_THREAD_POOL=200 -server -Xmx2048m -Xms128m -jar /home/dopstest/netflix-sandbox/karyon-poc/build/libs/karyon-poc.jar

