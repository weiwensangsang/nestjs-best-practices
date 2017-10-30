#!/usr/bin/expect -f
set timeout -1
spawn scp target/weiwensangsang-0.0.1-SNAPSHOT.war  root@118.31.79.189:/root/java
expect "*password:"
send "2@bisaibang\r"
expect eof
exit
