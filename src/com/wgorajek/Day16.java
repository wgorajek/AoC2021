package com.wgorajek;

import java.util.ArrayList;
import java.util.BitSet;
import java.util.List;

public class Day16 extends Solution{
    @Override
    public Object getPart1Solution() {
        var packetLoader = getInput();
        packetLoader.load();
        return packetLoader.countVersions();
    }

    @Override
    public Object getPart2Solution() {
        var packetLoader = getInput();
        packetLoader.load();
        return packetLoader.packet.getValue();
    }

    private class Packet {
        Integer version;
        Integer type;
        Long literalvalue;
        List<Packet> subpackets = new ArrayList<Packet>();

        public Integer countVersions() {
            var count = 0;
            for (var subP: subpackets) {
                count += subP.countVersions();
            }
            return version + count;
        }

        public Long getValue() {
            var value = 0L;
            if (type.equals(0)) {
                for (var subP: subpackets) {
                    value += subP.getValue();
                }
            }
            else if (type.equals(1)) {
                value = 1;
                for (var subP: subpackets) {
                    value *= subP.getValue();
                }
            }
            else if (type.equals(2)) {
                value = 999999999L;
                for (var subP: subpackets) {
                    value = Long.min(subP.getValue(), value);
                }
            }
            else if (type.equals(3)) {
                for (var subP: subpackets) {
                    value = Long.max(subP.getValue(), value);
                }
            }
            else if (type.equals(4)) {
                value = literalvalue;
            }
            else if (type.equals(5)) {
                if (subpackets.get(0).getValue() > subpackets.get(1).getValue()) {
                    return 1L;
                }
                else {
                    return 0L;
                }
            }
            else if (type.equals(6)) {
                if (subpackets.get(0).getValue() < subpackets.get(1).getValue()) {
                    return 1L;
                }
                else {
                    return 0L;
                }
            }
            else if (type.equals(7)) {
                if (subpackets.get(0).getValue().equals(subpackets.get(1).getValue())) {
                    return 1L;
                }
                else {
                    return 0L;
                }
            }
            return value;
        }
    }

    private class PacketLoader {
        String packetBit;
        Integer position;
        Packet packet;

        public PacketLoader(String packetBit) {
            this.packetBit = packetBit;
            position = 0;
        }

        private Integer readPacketBit(Integer length) {
            var value = bitStrToInt(packetBit.substring(position, position + length));
            position += length;
            return value;
        }

        private Packet loadPacket() {
            var newPacket = new Packet();
            newPacket.version = readPacketBit(3);
            newPacket.type = readPacketBit(3);
            if (newPacket.type.equals(4)) {
                var literalValue = 0L;
                while (packetBit.charAt(position) == '1') {
                    position++;
                    literalValue = 16 * literalValue + readPacketBit(4);
                }
                position++;
                literalValue = 16 * literalValue + readPacketBit(4);
                newPacket.literalvalue = literalValue;
            }
            else {
                var typeId = readPacketBit(1);
                if (typeId.equals(0)) {
                    var numberOfBitsSubPacket = readPacketBit(15);
                    var positionEnd =  position + numberOfBitsSubPacket;
                    while (position < positionEnd) {
                        newPacket.subpackets.add(loadPacket());
                    }
                }
                else
                {
                    var numberOfSubPackets = readPacketBit(11);
                    while (newPacket.subpackets.size() < numberOfSubPackets) {
                        newPacket.subpackets.add(loadPacket());
                    }
                }
            }

            return newPacket;
        }

        public void load() {
            packet = loadPacket();
        }

        public Integer countVersions() {
            return packet.countVersions();
        }

    }


    public Integer bitStrToInt(String str) {
        var value = 0;
        for (var c : str.toCharArray()) {
            if (c == '0') {
                value = 2 * value;
            }
            else {
                value = 2 * value + 1;
            }
        }
        return value;
    }



    public PacketLoader getInput() {
        var inputHex = getInpuString();
        var inputBit = new StringBuilder();
        for (var c: inputHex.toCharArray()) {
            inputBit.append(hexToBit(c));
        }
        return new PacketLoader(inputBit.toString());
    }

    private String hexToBit(char c) {
        switch (c) {
            case '0' : return "0000";
            case '1' : return "0001";
            case '2' : return "0010";
            case '3' : return "0011";
            case '4' : return "0100";
            case '5' : return "0101";
            case '6' : return "0110";
            case '7' : return "0111";
            case '8' : return "1000";
            case '9' : return "1001";
            case 'A' : return "1010";
            case 'B' : return "1011";
            case 'C' : return "1100";
            case 'D' : return "1101";
            case 'E' : return "1110";
            case 'F' : return "1111";
        }
        return "";
    }
}
