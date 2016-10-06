package ethereumjava.solidity;

import ethereumjava.solidity.types.SInt;

/**
 * Created by gunicolas on 30/08/16.
 */
public interface MyContract extends ContractType {

    SolidityFunction<Void> foo(SInt a);
    SolidityFunction<SInt.SInt8> bar();

    SolidityFunction<SInt> i();

}
