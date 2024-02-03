package test;

import com.sun.xml.internal.messaging.saaj.util.Base64;

import java.nio.charset.Charset;
import java.util.Random;

public class TestTOEKN {
    public static void main(String[] args){
        new TestTOEKN().sample();
    }


    public byte[] randomBytes(int len, Random random){
        byte[] res = new byte[len];
        random.nextBytes(res);
        for(int i = 0; i < len; i++){
            if(res[i]<0) res[i] = (byte) (-res[i]);
        }
        return res;
    }

    public byte[] getTime(){
        long t = System.currentTimeMillis();
        return new byte[]{(byte) (t >> 28 & 0x7f),
                        (byte) (t >> 21 & 0x7f),
                        (byte) (t >> 14 & 0x7f),
                        (byte) (t >> 7 & 0x7f),
                        (byte) (t & 0x7f)};
    }

    public byte[] genTOKEN(int len, Random random){
        byte[] rcode = randomBytes(len, random);
        byte[] tcode = getTime();
        byte[] code = new byte[rcode.length + tcode.length];
        for(int i = 0; i < rcode.length; i++)
            code[i] = rcode[i];
        for(int j = 0; j < tcode.length; j++)
            code[rcode.length + j] = tcode[j];
        byte[] res = Base64.encode(code);
        return res;
    }

    public void sample(){
        int len = 32;
        Random random = new Random();
        byte[] token = genTOKEN(len, random);
        String token_str = new String(token, Charset.forName("utf8"));
        System.out.println(token_str);
    }

}
