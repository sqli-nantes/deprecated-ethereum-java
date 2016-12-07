package ethereumjava.solidity;

import com.google.gson.JsonElement;
import junitparams.JUnitParamsRunner;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gunicolas on 06/10/16.
 */
@RunWith(JUnitParamsRunner.class)
public class DecoderTest {

    private Object[] parametersForTestEncoderSimpleType() {
        List<String> list = new ArrayList<>();

        list.add("{ type: 'address', " +
            "expected: '0x407d73d8a49eeb85d32cf465507dd71d507100c1'," +
            "value: '000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c1'}");

        list.add("{ type: 'address[2]', " +
            "expected: ['0x407d73d8a49eeb85d32cf465507dd71d507100c1', '0x407d73d8a49eeb85d32cf465507dd71d507100c3']," +
            "value: '000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c1' + " +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c3' }");

        list.add("{ type: 'address[]', " +
            "expected: ['0x407d73d8a49eeb85d32cf465507dd71d507100c1', '0x407d73d8a49eeb85d32cf465507dd71d507100c3']," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' +" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c1' +" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c3' }");

        list.add("{ type: 'address[][2]', " +
            "expected: [['0x407d73d8a49eeb85d32cf465507dd71d507100c1', " +
            "'0x407d73d8a49eeb85d32cf465507dd71d507100c2'],['0x407d73d8a49eeb85d32cf465507dd71d507100c3', " +
            "'0x407d73d8a49eeb85d32cf465507dd71d507100c4']]," +
            "value: '0000000000000000000000000000000000000000000000000000000000000040' +" +
            "'00000000000000000000000000000000000000000000000000000000000000a0' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' + /* 40 */" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c1' + /* 60 */" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c2' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' + /* a0 */" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c3' +" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c4' }");

        list.add("{ type: 'address[2][]', " +
            "expected: [['0x407d73d8a49eeb85d32cf465507dd71d507100c1', " +
            "'0x407d73d8a49eeb85d32cf465507dd71d507100c2']," +
            "['0x407d73d8a49eeb85d32cf465507dd71d507100c3', '0x407d73d8a49eeb85d32cf465507dd71d507100c4']], " +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' + /* 20 */" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c1' +" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c2' +" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c3' +" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c4' }");

        list.add("{ type: 'address[][]', " +
            "expected: [['0x407d73d8a49eeb85d32cf465507dd71d507100c1', '0x407d73d8a49eeb85d32cf465507dd71d507100c2'], " +
            "['0x407d73d8a49eeb85d32cf465507dd71d507100c3', '0x407d73d8a49eeb85d32cf465507dd71d507100c4']]," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' + /* 20 */" +
            "'0000000000000000000000000000000000000000000000000000000000000080' +" +
            "'00000000000000000000000000000000000000000000000000000000000000e0' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' + /* 80 */" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c1' + /* a0 */" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c2' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' + /* e0 */" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c3' +" +
            "'000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c4' }");

        list.add("{ type: 'bool', " +
            "expected: true, " +
            "value: '0000000000000000000000000000000000000000000000000000000000000001'}");

        list.add("{ type: 'bool', " +
            "expected: false, " +
            "value: '0000000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'bool[2]', " +
            "expected: [true, false]," +
            "value: '0000000000000000000000000000000000000000000000000000000000000001' +" +
            "'0000000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'bool[]', " +
            "expected: [true, true, false]," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000003' +" +
            "'0000000000000000000000000000000000000000000000000000000000000001' +" +
            "'0000000000000000000000000000000000000000000000000000000000000001' +" +
            "'0000000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'int', " +
            "expected: new bn(1),            " +
            "value: '0000000000000000000000000000000000000000000000000000000000000001'}");

        list.add("{ type: 'int', " +
            "expected: new bn(1),            " +
            "value: '0000000000000000000000000000000000000000000000000000000000000001'}");

        list.add("{ type: 'int', " +
            "expected: new bn(16),           " +
            "value: '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ type: 'int', " +
            "expected: new bn(-1),           " +
            "value: 'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff'}");

        list.add("{ type: 'int256', " +
            "expected: new bn(1),         " +
            "value: '0000000000000000000000000000000000000000000000000000000000000001'}");

        list.add("{ type: 'int256', " +
            "expected: new bn(16),        " +
            "value: '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ type: 'int256', " +
            "expected: new bn(-1),        " +
            "value: 'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff'}");

        list.add("{ type: 'int8', " +
            "expected: new bn(16),          " +
            "value: '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ type: 'int8[2]', " +
            "expected: [new bn(16), new bn(2)]," +
            "value: '0000000000000000000000000000000000000000000000000000000000000010' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002'}");

        list.add("{ type: 'int32', " +
            "expected: new bn(16),        " +
            "value: '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ type: 'int64', " +
            "expected: new bn(16),         " +
            "value: '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ type: 'int128', " +
            "expected: new bn(16),        " +
            "value: '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ type: 'int[]', " +
            "expected: [],                 " +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'int[]', " +
            "expected: [new bn(3)],        " +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000001' +" +
            "'0000000000000000000000000000000000000000000000000000000000000003'}");

        list.add("{ type: 'int256[]', " +
            "expected: [new bn(3)],     " +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000001' +" +
            "'0000000000000000000000000000000000000000000000000000000000000003'}");

        list.add("{ type: 'int[]', " +
            "expected: [new bn(1), new bn(2), new bn(3)]," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000003' +" +
            "'0000000000000000000000000000000000000000000000000000000000000001' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' +" +
            "'0000000000000000000000000000000000000000000000000000000000000003'}");

        list.add("{ type: 'int[3][]', " +
            "expected: [[new bn(1), new bn(2), new bn(3)], [new bn(4), new bn(5), new bn(6)]]," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' +" +
            "'0000000000000000000000000000000000000000000000000000000000000001' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' +" +
            "'0000000000000000000000000000000000000000000000000000000000000003' +" +
            "'0000000000000000000000000000000000000000000000000000000000000004' +" +
            "'0000000000000000000000000000000000000000000000000000000000000005' +" +
            "'0000000000000000000000000000000000000000000000000000000000000006'}");

        list.add("{ type: 'uint', " +
            "expected: new bn(1),           " +
            "value: '0000000000000000000000000000000000000000000000000000000000000001'}");

        list.add("{ type: 'uint', " +
            "expected: new bn(1),           " +
            "value: '0000000000000000000000000000000000000000000000000000000000000001'}");

        list.add("{ type: 'uint', expected: new bn(16),          value: '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ type: 'uint', expected: new bn('0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff')," +
            "value: 'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff'}");

        list.add("{ type: 'uint256', " +
            "expected: new bn(1),        " +
            "value: '0000000000000000000000000000000000000000000000000000000000000001'}");

        list.add("{ type: 'uint256', " +
            "expected: new bn(16),       " +
            "value: '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ type: 'uint8', " +
            "expected: new bn(16),         " +
            "value: '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ type: 'uint32', " +
            "expected: new bn(16),        " +
            "value: '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ type: 'uint64', " +
            "expected: new bn(16),        " +
            "value: '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ type: 'uint128', " +
            "expected: new bn(16),       " +
            "value: '0000000000000000000000000000000000000000000000000000000000000010'}");

        list.add("{ type: 'uint[]', " +
            "expected: [],                " +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'uint[]', " +
            "expected: [new bn(3)],       " +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000001' +" +
            "'0000000000000000000000000000000000000000000000000000000000000003'}");

        list.add("{ type: 'uint256[]', " +
            "expected: [new bn(3)],    " +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000001' +" +
            "'0000000000000000000000000000000000000000000000000000000000000003'}");

        list.add("{ type: 'uint[]', " +
            "expected: [new bn(1), new bn(2), new bn(3)]," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000003' +" +
            "'0000000000000000000000000000000000000000000000000000000000000001' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' +" +
            "'0000000000000000000000000000000000000000000000000000000000000003'}");

        list.add("{ type: 'uint[3][]', " +
            "expected: [[new bn(1), new bn(2), new bn(3)], [new bn(4), new bn(5), new bn(6)]]," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' +" +
            "'0000000000000000000000000000000000000000000000000000000000000001' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' +" +
            "'0000000000000000000000000000000000000000000000000000000000000003' +" +
            "'0000000000000000000000000000000000000000000000000000000000000004' +" +
            "'0000000000000000000000000000000000000000000000000000000000000005' +" +
            "'0000000000000000000000000000000000000000000000000000000000000006'}");

        list.add("{ type: 'bytes', " +
            "expected: '0x6761766f66796f726b'," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000009' +" +
            "'6761766f66796f726b0000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'bytes', " +
            "expected: '0x731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'}");

        list.add("{ type: 'bytes', " +
            "expected: '0x131a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b' +" +
            "'231a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b' +" +
            "'331a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000060' +" +
            "'131a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b' +" +
            "'231a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b' +" +
            "'331a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'}");

        list.add("{ type: 'bytes', " +
            "expected: '0x731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b' +" +
            "'731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000040' +" +
            "'731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b' +" +
            "'731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'}");

        list.add("{ type: 'bytes[2]', " +
            "expected: ['0x731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134a'," +
            "'0x731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b']," +
            "value: '0000000000000000000000000000000000000000000000000000000000000040' +" +
            "'0000000000000000000000000000000000000000000000000000000000000080' +" +
            "'0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134a' +" +
            "'0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b'}");

        list.add("{ type: 'bytes[][2]', " +
            "expected: [['0x731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134a']," +
            "['0x731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b' +" +
            "'731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134c'," +
            "'0x731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134d']]," +
            "value: '0000000000000000000000000000000000000000000000000000000000000040' +" +
            "'0000000000000000000000000000000000000000000000000000000000000080' +" +
            "'0000000000000000000000000000000000000000000000000000000000000001' + // 40 //" +
            "'00000000000000000000000000000000000000000000000000000000000000e0' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' + // 80 //" +
            "'0000000000000000000000000000000000000000000000000000000000000120' +" +
            "'0000000000000000000000000000000000000000000000000000000000000180' +" +
            "'0000000000000000000000000000000000000000000000000000000000000020' + // e0 //" +
            "'731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134a' +" +
            "'0000000000000000000000000000000000000000000000000000000000000040' + // 120 //" +
            "'731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134b' +" +
            "'731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134c' +" +
            "'0000000000000000000000000000000000000000000000000000000000000020' + // 180 //" +
            "'731a3afc00d1b1e3461b955e53fc866dcf303b3eb9f4c16f89e388930f48134d'}");

        list.add("{ type: 'bytes1', " +
            "expected: '0xcf'," +
            "value: 'cf00000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'bytes1[4]', " +
            "expected: ['0xcf', '0x68', '0x4d', '0xfb'], " +
            "value: 'cf00000000000000000000000000000000000000000000000000000000000000' +" +
            "'6800000000000000000000000000000000000000000000000000000000000000' +" +
            "'4d00000000000000000000000000000000000000000000000000000000000000' +" +
            "'fb00000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'bytes32', " +
            "expected: '0x6761766f66796f726b0000000000000000000000000000000000000000000000'," +
            "value: '6761766f66796f726b0000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'bytes64', " +
            "expected: '0xc3a40000c3a40000000000000000000000000000000000000000000000000000' +" +
            "'c3a40000c3a40000000000000000000000000000000000000000000000000000'," +
            "value: 'c3a40000c3a40000000000000000000000000000000000000000000000000000' +" +
            "'c3a40000c3a40000000000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'string', " +
            "expected: 'gavofyork',       " +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000009' +" +
            "'6761766f66796f726b0000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'string', " +
            "expected: 'Ã¤Ã¤'," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000008' +" +
            "'c383c2a4c383c2a4000000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'string', " +
            "expected: 'ü'," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' +" +
            "'c3bc000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'string', " +
            "expected: 'Ã'," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000002' +" +
            "'c383000000000000000000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'bytes', " +
            "expected: '0xc3a40000c3a4'," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'0000000000000000000000000000000000000000000000000000000000000006' +" +
            "'c3a40000c3a40000000000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'bytes32', " +
            "expected: '0xc3a40000c3a40000000000000000000000000000000000000000000000000000'," +
            "value: 'c3a40000c3a40000000000000000000000000000000000000000000000000000'}");

        list.add("{ type: 'real', " +
            "expected: new bn(1),           " +
            "value: '0000000000000000000000000000000100000000000000000000000000000000'}");

        list.add("{ type: 'real', " +
            "expected: new bn(2.125),       " +
            "value: '0000000000000000000000000000000220000000000000000000000000000000'}");

        list.add("{ type: 'real', " +
            "expected: new bn(8.5),         " +
            "value: '0000000000000000000000000000000880000000000000000000000000000000'}");

        list.add("{ type: 'real', " +
            "expected: new bn(-1),          " +
            "value: 'ffffffffffffffffffffffffffffffff00000000000000000000000000000000'}");

        list.add("{ type: 'ureal', " +
            "expected: new bn(1),          " +
            "value: '0000000000000000000000000000000100000000000000000000000000000000'}");

        list.add("{ type: 'ureal', " +
            "expected: new bn(2.125),      " +
            "value: '0000000000000000000000000000000220000000000000000000000000000000'}");

        list.add("{ type: 'ureal', " +
            "expected: new bn(8.5),        " +
            "value: '0000000000000000000000000000000880000000000000000000000000000000'}");

        list.add("{ type: 'address', " +
            "expected: '0x407d73d8a49eeb85d32cf465507dd71d507100c1'," +
            "value: '000000000000000000000000407d73d8a49eeb85d32cf465507dd71d507100c1'}");

        list.add("{ type: 'string', " +
            "expected: 'welcome to ethereum. welcome to ethereum. welcome to ethereum.'," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'000000000000000000000000000000000000000000000000000000000000003e' +" +
            "'77656c636f6d6520746f20657468657265756d2e2077656c636f6d6520746f20' +" +
            "'657468657265756d2e2077656c636f6d6520746f20657468657265756d2e0000'}");

        list.add("{ type: 'bytes', " +
            "expected: '0xffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff' +" +
            "'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff' +" +
            "'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff' +" +
            "'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff' +" +
            "'fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff1'," +
            "value: '0000000000000000000000000000000000000000000000000000000000000020' +" +
            "'000000000000000000000000000000000000000000000000000000000000009f' +" +
            "'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff' +" +
            "'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff' +" +
            "'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff' +" +
            "'ffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff' +" +
            "'fffffffffffffffffffffffffffffffffffffffffffffffffffffffffffff100'}");

        return parseTests(list);
    }

    private Object[] parseTests(List<String> list) {
        return null;
    }

    public void testEncoderSimpleType(Object param, String expected) throws Exception {

    }

    private class Test {
        String type;
        JsonElement value;
        String expected;
    }
}
