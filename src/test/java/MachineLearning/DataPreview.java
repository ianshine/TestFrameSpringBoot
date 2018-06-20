package MachineLearning;

import com.alibaba.fastjson.JSON;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.log4j.Logger;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;

/**
 * 数据预览接口
 */
public class DataPreview extends BaseCase{

    String DataPreviewUrl = getEvnAddress.getDataPreviewUrl();

//    public static Logger logger = Logger.getLogger(DataPreview.class);
    private static final Log log = LogFactory.getLog(DataPreview.class);

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
//        System.out.println(DataJson);
        log.info(DataJson);

//        httpRequestUtil.sendPost("DataPreviewUrl",DataJson);

    }


}
