#!/usr/bin/expect -f

spawn scp target/weiwensangsang-0.0.1-SNAPSHOT.war root@118.190.201.90:/home/xiazhen
expect "*password:"
send "Dwzm1gdqdmm\r"
expect "*#"

#send "mkdir 1"
interact