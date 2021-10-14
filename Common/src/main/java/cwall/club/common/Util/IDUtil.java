package cwall.club.common.Util;


import cwall.club.common.Enum.ExceptionCode;
import cwall.club.common.Exception.SalaryException;

import java.net.Inet4Address;
import java.net.UnknownHostException;
import java.util.Random;

public class IDUtil {
    final static long base = 1629353671111l;
    final static long timeMul = Long.parseLong("10000000000000000000000",2);
    final static long ipMul = Long.parseLong("100000000000000000",2);
    final static long nameMul = Long.parseLong("1000000000000",2);
    final static long fiveMod = Long.parseLong("11111",2);
    static long last = 0;
    static int rank = 1000;

    public static Long generateID(){
        long id = System.currentTimeMillis()-base;
        id *= timeMul;
        byte[] hostName;
        byte[] hostIp;
        try {
            hostName = Inet4Address.getLocalHost().getHostName().getBytes();
            hostIp = Inet4Address.getLocalHost().getHostAddress().getBytes();
            id += change(hostIp)*ipMul;
            id += change(hostName)*nameMul;
            synchronized ((Object) rank){
                if(last!=id){
                    last = id;
                    rank = 1000;
                }
                if(rank>=4096){
                    throw new UnknownHostException();
                }
                id += rank;
                rank++;
            }
            return id;
        } catch (UnknownHostException e) {
            throw new SalaryException(ExceptionCode.GENERATE_ERROR);
        }
    }

    private static long change(byte[] host) {
        long r = 0;
        for (byte b : host) {
            r += b;
        }
        r %= fiveMod;
        return r;
    }

    public static String generatePWD() {
        int len = 8+new Random().nextInt(10);
        StringBuilder sb = new StringBuilder();
        for(int i=0;i<len;i++) {
            int k = new Random().nextInt(3);
            if (k==0) {
                sb.append((char) ('a' + new Random().nextInt(26)));
            }else if (k==1) {
                sb.append((char) ('A' + new Random().nextInt(26)));
            }else{
                sb.append((char) ('0' + new Random().nextInt(10)));
            }
        }
        return sb.toString();
    }
}
