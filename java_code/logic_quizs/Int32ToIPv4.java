package logic_quizs;

/*
    Take the following IPv4 address: 128.32.10.1

    This address has 4 octets where each octet is a single byte (or 8 bits).

    1st octet 128 has the binary representation: 10000000
    2nd octet 32 has the binary representation: 00100000
    3rd octet 10 has the binary representation: 00001010
    4th octet 1 has the binary representation: 00000001
    So 128.32.10.1 == 10000000.00100000.00001010.00000001

    Because the above IP address has 32 bits, we can represent it as the unsigned 32 bit number: 2149583361

    Complete the function that takes an unsigned 32 bit number and returns a string representation of its IPv4 address.
 */

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Int32ToIPv4 {

    public static String longToIP(long ip) {
        return String.format("%d.%d.%d.%d", ip>>>24, (ip>>16)&0xff, (ip>>8)&0xff, ip&0xff);
    }

    @Test
    public void sampleTest() {
        assertEquals("128.114.17.104", longToIP(2154959208L));
        assertEquals("0.0.0.0", longToIP(0));
        assertEquals("128.32.10.1", longToIP(2149583361L));
    }
}

/*  another possible solution */

/*
    public static String longToIP(long ip) {
        List<Long> str = new ArrayList<>();

        while(ip > 0){
            str.add(ip % 256);
            ip /= 256;
        }

        for (int i= str.size(); i < 4; ++i){
            str.add(0L);
        }

        return str.get(3) + "." +  str.get(2) + "." + str.get(1) + "." + str.get(0);
    }

 */


/*  another possible solution */


/*
    public static String longToIP(long ip) {
        StringBuilder binary = new StringBuilder(Long.toBinaryString(ip));
        fillMissingZeros(binary);

        return Long.parseLong(binary.substring(0,8), 2) + "." +
                Long.parseLong(binary.substring(8,16), 2) + "." +
                Long.parseLong(binary.substring(16,24), 2) + "." +
                Long.parseLong(binary.substring(24,32), 2);
    }

    private static void fillMissingZeros(StringBuilder binary){
        char[] chars = new char[32 - binary.length()];
        Arrays.fill(chars, '0');
        binary.insert(0, chars);
    }
*/