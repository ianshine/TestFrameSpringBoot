package com.springBoot.TestFrame.machineLearning;

import org.junit.Assert;
import org.junit.Test;

public class TestDa {

    @Test
    public void test01(){
        int b = 3;
        int c = 3;
        System.out.println("test==========");
        Assert.assertEquals(6,b+c);
    }
}
