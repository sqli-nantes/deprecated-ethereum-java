# Ethereum-java

[![Build Status](https://travis-ci.org/sqli-nantes/ethereum-java.svg?branch=master)](https://travis-ci.org/sqli-nantes/ethereum-java)

Java API to communicate with an Ethereum Node via RPC or IPC


## How to execute tests ?

Run Geth:

```
geth --datadir data/ --networkid "0x64" init ethereum-java-core/src/test/resources/genesis.json
geth --datadir data/ --networkid "0x64" --port "30301" --rpc --rpcaddr "0.0.0.0" --rpcport "8547" --rpcapi "admin,eth,net,web3,personal" --rpccorsdomain "*" console
```

Then run test suite:

```
./gradlew test
```
