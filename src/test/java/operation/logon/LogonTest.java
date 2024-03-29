package operation.logon;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import viewcoder.tool.util.MybatisUtils;
import viewcoder.operation.impl.common.CommonService;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2018/2/19.
 */
public class LogonTest {

    private static Logger logger = LoggerFactory.getLogger(LogonTest.class);

    /**
     * 登录测试
     */
    @Test
    public void ViewCoderLoginTest() {
        Map<String, Object> map = new HashMap<>();
        //成功返回数据
        map.put("email", "2920248385@qq.com");
        map.put("password", "39de7e9b601d94f8266778376120ea88");
        CommonService.junitReqRespVerify(map,"viewCoderLogin",200);

        //账号存在但是密码错误
        map.put("email", "2920248385@qq.com");
        map.put("password", "39de7e9b601d94f8266778376120ea87");
        CommonService.junitReqRespVerify(map,"viewCoderLogin",401);

        //账号尚未注册
        map.put("email", "333920248385@qq.com");
        map.put("password", "39de7e9b601d94f8266778376120ea88");
        CommonService.junitReqRespVerify(map,"viewCoderLogin",402);
    }


    /**
     * 注册的测试
     */
    @Test
    public void viewCoderRegisterTest(){
        //成功进行注册
        Map<String, Object> map = new HashMap<>();
        String email= CommonService.getTimeStamp()+"@qq.com";
        map.put("user_name", "beanson");
        map.put("email", email);
        map.put("password", "39de7e9b601d94f8266778376120ea88");
        CommonService.junitReqRespVerify(map,"viewCoderRegister",200);
        //删除新注册成功并插入数据库的user记录
        String statement="deleteUserInDb";
        SqlSession sqlSession = MybatisUtils.getSession();
        sqlSession.delete(statement, email);
        sqlSession.commit();
        sqlSession.close();

        //账号之前已经存在，注册失败
        map.put("email", "2920248385@qq.com");
        map.put("password", "39de7e9b601d94f8266778376120ea88");
        CommonService.junitReqRespVerify(map,"viewCoderRegister",401);
    }


}
