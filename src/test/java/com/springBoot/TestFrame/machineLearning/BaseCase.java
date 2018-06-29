package com.springBoot.TestFrame.machineLearning;

import com.springBoot.TestFrame.httpUtil.HttpRequestUtil;
import com.springBoot.TestFrame.util.GetAddress;
import com.springBoot.TestFrame.util.GetEvnAddress;

public class BaseCase extends GetAddress {

    GetEvnAddress getEvnAddress = new GetEvnAddress();
    HttpRequestUtil httpRequestUtil = new HttpRequestUtil();

}
