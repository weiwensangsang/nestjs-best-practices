#!/usr/bin/expect -f
set timeout -1
spawn ssh -p 22 root@118.190.202.224
expect "*password:"
send "Dwzm1gdqdmm\r"
expect "#"
send "bash run.sh\r"
expect eof

