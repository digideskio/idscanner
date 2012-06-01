#!/bin/bash

if [ X$1 = "Xserver"  ]; then
    java -cp  target/SyncServer.jar:target/dependency/derby-10.8.2.2.jar com.id.scanner.server.Main

elif [ X$1 = "Xreset" ]; then
    java -cp  target/SyncServer.jar:target/dependency/derby-10.8.2.2.jar com.id.scanner.server.Reset

else echo  "Use [server,reset] as arguments"
fi
