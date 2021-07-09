#!/bin/bash
evi_pid=

getPid() {
   evi_pid=`ps aux|grep "evidence-chain-demo-1.0.1.jar" | grep -v grep|awk '{print $2}'|head -1`
}

getPid;

if [ -n "$evi_pid" ];then
   kill -9 $evi_pid
   echo "-----------------------------------------------"
   echo "The evidence chain demo stopped successfully."
   echo "-----------------------------------------------"
else
   echo "-----------------------------------------------"
   echo "The evidence chain demo  already stopped successfully."
   echo "-----------------------------------------------"
fi
