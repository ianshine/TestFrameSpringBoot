package MachineLearning;

import com.alibaba.fastjson.JSON;
import com.springBoot.TestFrame.httpUtil.HttpRequestUtil;
import com.springBoot.TestFrame.util.GetEvnAddress;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据预览接口
 */
public class DataPreview extends HttpRequestUtil{
    GetEvnAddress getEvnAddress = new GetEvnAddress();

        String DataPreviewUrl = getEvnAddress.getDataPreviewUrl();

    @Test
    public void DataPreviewTest(){
        Map<String, Object> dbMap = new HashMap<>();
        dbMap.put("colDelimiter","0x2C");
        dbMap.put("encoding","UTF-8");
        dbMap.put("firstLineSchema",true);
        dbMap.put("format","csv");
        dbMap.put("lineNumbers",100);
        dbMap.put("path",DataPreviewUrl);
        dbMap.put("protocol","LOCAL");
        dbMap.put("randomPreview",false);
        dbMap.put("rowDelimiter","自动识别处理");

        String DataJson = JSON.toJSONString(dbMap);
        System.out.println(DataJson);
//        sendGet("DataPreviewUrl","json");

    }


}
