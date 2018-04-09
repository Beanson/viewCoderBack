package FrontEnd.myBatis.operation.overall;

import FrontEnd.helper.common.Assemble;
import FrontEnd.helper.common.Common;
import FrontEnd.helper.common.Mapper;
import FrontEnd.helper.parser.form.FormData;
import FrontEnd.myBatis.MybatisUtils;
import FrontEnd.myBatis.entity.User;
import FrontEnd.myBatis.entity.response.ResponseData;
import FrontEnd.myBatis.operation.common.CommonService;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

/**
 * Created by Administrator on 2018/2/28.
 */
public class Overall {

    private static Logger logger = Logger.getLogger(Overall.class);


    public static ResponseData getUserInfoById(Object msg) {
        ResponseData responseData = new ResponseData();
        SqlSession sqlSession = MybatisUtils.getSession();

        try {
            String userId = FormData.getParam(msg, Common.USER_ID);
            //根据userId从数据库中查找对应的user信息
            User user = sqlSession.selectOne(Mapper.GET_USER_DATA, Integer.parseInt(userId));
            if (user != null && user.getId() > 0) {
                user.setPassword(null);
                Assemble.responseSuccessSetting(responseData, user);
            } else {
                Assemble.responseErrorSetting(responseData, 401,
                        "Get user info from database null error");
            }

        } catch (Exception e) {
            Overall.logger.debug("Get User info with exception: ", e);
            Assemble.responseErrorSetting(responseData, 500,
                    "Server error");

        } finally {
            CommonService.databaseCommitClose(sqlSession, responseData, false);
        }
        return responseData;
    }
}
