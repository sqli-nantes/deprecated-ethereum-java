package web3j.solidity.coder.encoder;

import web3j.solidity.SolidityUtils;
import web3j.solidity.types.SBytes;

/**
 * Created by gunicolas on 05/10/16.
 */

public class SBytesEncoder implements SEncoder<SBytes> {

    @Override
    public String encode(SBytes toEncode) {
        return SolidityUtils.padRightWithZeros(toEncode.asString(),64);
    }
}
