package web3j.solidity;

import com.google.gson.Gson;
import com.google.gson.JsonElement;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import junitparams.JUnitParamsRunner;
import junitparams.Parameters;
import web3j.solidity.coder.SCoder;
import web3j.solidity.types.SAddress;
import web3j.solidity.types.SBool;
import web3j.solidity.types.SInt;
import web3j.solidity.types.SString;
import web3j.solidity.types.SUInt;

import static junit.framework.TestCase.assertEquals;


/**
 * Created by gunicolas on 07/09/16.
 */
 @RunWith(JUnitParamsRunner.class)
public class EncoderTest {


    private Object[] parametersForTestEncoderSimpleType(){

        List<String> list = new ArrayList<>();

        list.add("{ 'type' : 'SAddress', " +
                "'value' : '0x407d73d8a49eeb85d32cf465507dd71d507100c1'," +
                "'expected': '000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c1'}");

        list.add("{ 'type': 'SBool', " +
                "'value': true," +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000001'}");

        list.add("{ 'type': 'SBool', " +
                "'value': false,          " +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SInt', " +
                "'value': 1," +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000001'}");

        list.add("{ 'type': 'SInt', " +
                "'value': 16,              " +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ 'type': 'SInt256', " +
                "'value': 1,            " +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000001'}");

        list.add("{ 'type': 'SInt256'," +
                "'value': 16,           " +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ 'type': 'SUInt', " +
                "'value': 1,               " +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000001'}");

        list.add("{ 'type': 'SUInt', " +
                "'value': 16,              " +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ 'type': 'SUInt256', " +
                "'value': 1,            " +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000001'}");

        list.add("{ 'type': 'SUInt256', " +
                "'value': 16,           " +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ 'type': 'SBytes32', " +
                "'value': '0x6761766f66796f726b'," +
                "'expected': '6761766f66796f726b0000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SBytes32', " +
                "'value': '0x731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'," +
                "'expected': '731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'}");

        list.add("{ 'type': 'SBytes32', " +
                "'value': '0x02838654a83c213dae3698391eabbd54a5b6e1fb3452bc7fa4ea0dd5c8ce7e29'," +
                "'expected': '02838654a83c213dae3698391eabbd54a5b6e1fb3452bc7fa4ea0dd5c8ce7e29'}");

        list.add("{ 'type': 'SBytes', " +
                "'value': '0x6761766f66796f726b'," +
                "'expected': '000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000096761766f66796f726b0000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SBytes', " +
                "'value': '0x731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'," +
                "'expected': '00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000020731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'}");

        list.add("{ 'type': 'SBytes', " +
                "'value': '0xfffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1'," +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000009ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff100'}");

        list.add("{ 'type': 'SString', " +
                "'value': 'gavofyork',  " +
                "'expected': '000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000096761766f66796f726b0000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SBytes', " +
                "'value': '0xc3a40000c3a4'," +
                "'expected': '00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000006c3a40000c3a40000000000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SBytes32', " +
                "'value': '0xc3a40000c3a4'," +
                "'expected': 'c3a40000c3a40000000000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SBytes64', " +
                "'value': '0xc3a40000c3a40000000000000000000000000000000000000000000000000000c3a40000c3a40000000000000000000000000000000000000000000000000000'," +
                "'expected': 'c3a40000c3a40000000000000000000000000000000000000000000000000000c3a40000c3a40000000000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SString', " +
                "'value': 'Ã¤Ã¤'," +
                "'expected': '00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000008c383c2a4c383c2a4000000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SString', " +
                "'value': 'ü'," +
                "'expected': '00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000002c3bc000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SString', " +
                "'value': 'Ã'," +
                "'expected': '00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000002c383000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SBytes', " +
                "'value': '0x131a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b231a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'," +
                "'expected': '00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000040131a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b231a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'}");

        list.add("{ 'type': 'SBytes', " +
                "'value': '0x131a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b231a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b331a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'," +
                "'expected': '00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000060131a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b231a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b331a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'}");

        list.add("{ 'type': 'SString', " +
                "'value': 'welcome to ethereum. welcome to ethereum. welcome to ethereum.'," +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000003e77656c636f6d6520746f20657468657265756d2e2077656c636f6d6520746f20657468657265756d2e2077656c636f6d6520746f20657468657265756d2e0000'}");

        return parseTests(list);

    }

    private Object[] parametersForTestEncoderComplexType(){
        List<String> list = new ArrayList<>();

        list.add("{ 'type': 'SAddress[2]', " +
                "'value': ['0x407d73d8a49eeb85d32cf465507dd71d507100c1', '0x407d73d8a49eeb85d32cf465507dd71d507100c3']," +
                "'expected': '000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c1000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c3' }");

        list.add("{ 'type': 'SAddress[]', " +
                "'value': ['0x407d73d8a49eeb85d32cf465507dd71d507100c1', '0x407d73d8a49eeb85d32cf465507dd71d507100c3']," +
                "'expected' : '00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000002000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c1000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c3' }");

        list.add("{ 'type': 'SAddress[][2]', " +
                "'value': [['', ''], ['', '']], " +
                "'expected': '000000000000000000000000000000000000000000000000000000000000004000000000000000000000000000000000000000000000000000000000000000a00000000000000000000000000000000000000000000000000000000000000002000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c1000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c20000000000000000000000000000000000000000000000000000000000000002000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c3000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c4' }");

        list.add("{ 'type': 'SAddress[2][]', " +
                "'value': [['0x407d73d8a49eeb85d32cf465507dd71d507100c1', '0x407d73d8a49eeb85d32cf465507dd71d507100c2'], ['0x407d73d8a49eeb85d32cf465507dd71d507100c3', '0x407d73d8a49eeb85d32cf465507dd71d507100c4']], " +
                "'expected': '00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000002000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c1000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c2000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c3000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c4' }");

        list.add("{ 'type': 'SBool[1][2]', " +
                "'value': [[false], [false]]," +
                "'expected': '00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SBool[2]', " +
                "'value': [true, false]," +
                "'expected': '00000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SBool[]', " +
                "'value': [true, true, false]," +
                "'expected': '00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000003000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SInt[]', " +
                "'value': [],            " +
                "'expected': '00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SInt[]', " +
                "'value': [3],           " +
                "'expected': '000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000003'}");

        list.add("{ 'type': 'SInt256[]', " +
                "'value': [3],        " +
                "'expected': '000000000000000000000000000000000000000000000000000000000000002000000000000000000000000000000000000000000000000000000000000000010000000000000000000000000000000000000000000000000000000000000003'}");

        list.add("{ 'type': 'SInt[]', " +
                "'value': [1,2,3],       " +
                "'expected': '00000000000000000000000000000000000000000000000000000000000000200000000000000000000000000000000000000000000000000000000000000003000000000000000000000000000000000000000000000000000000000000000100000000000000000000000000000000000000000000000000000000000000020000000000000000000000000000000000000000000000000000000000000003'}");

        list.add("{ 'type': 'SBytes1[4]', " +
                "'value': ['0xcf', '0x68', '0x4d', '0xfb']," +
                "'expected': 'cf0000000000000000000000000000000000000000000000000000000000000068000000000000000000000000000000000000000000000000000000000000004d00000000000000000000000000000000000000000000000000000000000000fb00000000000000000000000000000000000000000000000000000000000000'}");

        return list.toArray();
    }

    private Object[] parametersForTestEncodeSimpleTypeFail(){
        /*List<String> list = new ArrayList<>();

        list.add("{ 'type': 'USInt', " +
                "'value': 0.1,             " +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'USInt', " +
                "'value': 3.9,             " +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000003'}");

        list.add("{ 'type': 'SInt', " +
                "'value': 0.1,             " +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ 'type': 'SInt', " +
                "'value': 3.9,             " +
                "'expected': '0000000000000000000000000000000000000000000000000000000000000003'}");

        list.add("{ 'type': 'SInt256', " +
                "'value': -1,           " +
                "'expected': 'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff'}");

        list.add("{ 'type': 'SUInt256'," +
                "'value': '0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff'," +
                "'expected': 'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff'}");

        list.add("{ 'type': 'SInt', " +
                "'value': -1,             " +
                "'expected': 'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff'}");
        return null;*/
        return null;
    }

    private class Test {
        String type;
        JsonElement value;
        String expected;
    }

    private Object[] parseTests(List<String> list) {

        List<Object> ret = new ArrayList<>();
        Gson gson = new Gson();

        for(String s : list){
            List<Object> listI = new ArrayList<>();
            Test test = gson.fromJson(s,Test.class);
            Object input = null;
            if( test.type.compareTo("SBool") == 0 ){
                input = SBool.fromBoolean(test.value.getAsBoolean());
            } else if( test.type.compareTo("SString") == 0 ){
                input = SString.fromString(test.value.getAsString());
            } else if( test.type.compareTo("SAddress ") == 0  ){
                input = SAddress.fromString(test.value.getAsString());
            } else if( test.type.contains("SInt") ){
                String size = test.type.substring(4);
                if( size.length()==0 || Integer.parseInt(size) == 256) input = SInt.fromBigInteger256(test.value.getAsBigInteger());
                else if( Integer.parseInt(size) == 8) input = SInt.fromByte(test.value.getAsByte());
                else if( Integer.parseInt(size) == 16) input = SInt.fromShort(test.value.getAsShort());
                else if( Integer.parseInt(size) == 32) input = SInt.fromInteger(test.value.getAsInt());
                else if( Integer.parseInt(size) == 64) input = SInt.fromLong(test.value.getAsLong());
                else if( Integer.parseInt(size) == 128) input = SInt.fromBigInteger128(test.value.getAsBigInteger());
            } else if( test.type.contains("SUInt") ){
                String size = test.type.substring(5);
                if( size.length()==0 || Integer.parseInt(size) == 256) input = SUInt.fromBigInteger256(test.value.getAsBigInteger());
                else if( Integer.parseInt(size) == 8) input = SUInt.fromShort(test.value.getAsShort());
                else if( Integer.parseInt(size) == 16) input = SUInt.fromInteger(test.value.getAsInt());
                else if( Integer.parseInt(size) == 32) input = SUInt.fromLong(test.value.getAsLong());
                else if( Integer.parseInt(size) == 64) input = SUInt.fromBigInteger64(test.value.getAsBigInteger());
                else if( Integer.parseInt(size) == 128) input = SUInt.fromBigInteger128(test.value.getAsBigInteger());
            }
            if(input != null) {
                listI.add(input);
                listI.add(test.expected);
                ret.add(listI.toArray());
            }
        }
        return ret.toArray();
    }


    @org.junit.Test
    @Parameters
    public void testEncoderSimpleType(Object param,String expected) throws Exception{
        String result = SCoder.encodeParams(new Object[]{param});
        assertEquals(expected,result);
    }

/*    @org.junit.Test
    @Parameters
    public void testEncodeSimpleTypeFail() throws Exception{
       *//* String result = SCoder.encodeParams(new Object[]{param});
        assertEquals(expected,result);*//*
    }*/



}
