#!/usr/bin/expect -f
set timeout -1
spawn ssh -p 22 root@118.31.79.189
expect "*password:"
send "2@bisaibang\r"
expect "#"
send "bash run.sh\r"
expect eof

