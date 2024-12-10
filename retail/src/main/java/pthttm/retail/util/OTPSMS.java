package pthttm.retail.util;

import java.util.Random;

public class OTPSMS {
    private static String NUMBER_STRING="0123456789";

    public static String randomOTP(){
        Random rd = new Random();
        String output="";
        int index;
        for(int i=0;i<4;++i){
            index = rd.nextInt(0,NUMBER_STRING.length());
            output=output + NUMBER_STRING.charAt(index);

        }
        return output;
    }
}
