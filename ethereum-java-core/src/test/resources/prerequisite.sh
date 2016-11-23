#!/bin/bash

addr=$(geth --datadir ./data --password <(echo "admin") account new | grep -o -P '(?<={).*(?=})')
sed -i -- "s/TEST_USER_ADDRESS/$addr/g" genesis.json

geth --datadir ./data init genesis.json

geth --datadir ./data  --networkid 0x64 --rpc --rpcaddr "0.0.0.0" --rpcport "8547" --rpcapi "admin,eth,net,web3,personal" --rpccorsdomain "*" --preload "mineOnlyWhenTx.js" console
