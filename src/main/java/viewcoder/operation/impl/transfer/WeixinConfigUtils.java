package viewcoder.operation.impl.transfer;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Administrator on 2018/10/29.
 */
public class WeixinConfigUtils {
    private static final Log log = LogFactory.getLog(WeixinConfigUtils.class);
    public static String appid;
    public static String mch_id;
    public static String notify_url;
    public static String order_notify_url;
    public static String doctor_notify_url;
    static {
        try{
            InputStream is = WeixinConfigUtils.class.getResourceAsStream("/weixin.properties");
            Properties properties = new Properties();
            properties.load(is);
            appid = properties.getProperty("weixin.appid");
            mch_id = properties.getProperty("weixin.mch_id");
            notify_url = properties.getProperty("weixin.notify_url");
            order_notify_url = properties.getProperty("weixin.order_notify_url");
            doctor_notify_url = properties.getProperty("weixin.doctor_notify_url");
        }catch(Exception ex){
            log.debug("加载配置文件："+ex.getMessage());
        }
    }
}
