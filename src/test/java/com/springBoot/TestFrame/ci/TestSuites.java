package com.springBoot.TestFrame.ci;

import com.springBoot.TestFrame.machineLearning.DataSource.*;
import junit.framework.Test;
import junit.framework.TestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({
        //机器学习
        DataSaveTest.class,
        DbConnectionTest.class,
        DbPreviewTest.class,
        DelSourceDataTest.class,
        LocalFileUploadTest.class,
        PreviewTest.class,
        ShowLogTest.class,
        TableListTest.class,
        TablePreviewTest.class
        })

public class TestSuites {
    public static Test suite() {
        TestSuite suite = new TestSuite("Test of machineLearning");
        return suite;
    }


}
