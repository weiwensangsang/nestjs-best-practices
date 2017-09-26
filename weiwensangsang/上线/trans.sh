#!/usr/bin/expect -f
set timeout -1
spawn scp target/weiwensangsang-0.0.1-SNAPSHOT.war  root@118.190.202.224:/root/java
expect "*password:"
send "Dwzm1gdqdmm\r"
expect eof
exit
