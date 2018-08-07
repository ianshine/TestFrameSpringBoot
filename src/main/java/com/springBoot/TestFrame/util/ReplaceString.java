package com.springBoot.TestFrame.util;

public class ReplaceString {
    /**
     * 说明
     * @param arr  : 字符串需要替换的特殊字符串数组
     * @param ReplacedStr 需要替换的字符串
     * @param Symbol 特殊字符替换后按什么符号分隔成数组
     * @return   
     */

    public String[] StrReplace(String[] arr, String ReplacedStr, String Symbol) {
        String DateTest = null;
        DateTest = ReplacedStr;

        for (int i = 0; i <arr.length ; i++) {
            String NewDateTest = DateTest.replace(arr[i],"");
            DateTest = NewDateTest;
        }
        String[] SplitData = DateTest.split(Symbol);

//        System.out.println("NewDateTest = "+DateTest);
//        //遍历所有字符
//        for (String data : SplitData) {
//            System.out.println(data);
//        }
        return SplitData;
    }
}
