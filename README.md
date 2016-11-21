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

## License

```
The MIT License (MIT)

Copyright (c) 2015 Damien Lecan

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```