package com.xecoder.common.utils;


/**
 * Created by luxp on 2015/10/21.
 */
public class ParamStringUtils {
    public static boolean isAvailable(String str) {
        String regex = "[\\u4e00-\\u9fa5\\w]+";
        return str.matches(regex);
    }

    public static void main(String[] args) {
        boolean result = isAvailable("=-");
        System.out.println(result);
    }

    public static boolean isRepeat(String[] strings){
        for(int i=0;i<strings.length;i++){
            int count = 0;
            for(int j=0;j<strings.length;j++){
                if(strings[i].equals(strings[j])){
                    count++;
                }
                if(count>1){
                    return true;
                }
            }
        }
        return false;
    }

}
