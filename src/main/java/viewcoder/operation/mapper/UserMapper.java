package viewcoder.operation.mapper;

/**
 * Created by Administrator on 2017/2/8.
 */

import viewcoder.operation.entity.Instance;
import viewcoder.operation.entity.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

//@CacheNamespace(flushInterval = 3000)
public interface UserMapper {

    /********************以下是选择user操作***********************/
    //根据user_id获得该user的全部信息
    @Select("select * from user where id=#{user_id}")
    public User getUserData(int user_id);

    //登录验证
    @Select("select * from user where email=#{email} and password=#{password}")
    public List<User> loginValidation(User user);

    //注册验证
    @Select("select * from user where email=#{email}")
    public List<User> registerAccountCheck(User user);

    //获取用户原portrait数据信息
    @Select("select portrait from user where id=#{id}")
    public String getOriginPortraitName(String user_id);

    //根据user_id获取用户剩余使用空间
    @Select("select resource_remain from user where id=#{userId}")
    public String getUserResourceSpaceRemain(int userId);

    //根据user_id获取用户最新的total_points数据
    @Select("select total_points from user where id=#{userId}")
    public int getTotalPoints(int userId);

    //根据user_id获取用户space的数据
    @Select("select resource_remain, resource_used, timestamp from user where id=#{userId}")
    public User getUserSpaceInfo(int userId);




    /********************以下是插入user操作***********************/
    //注册操作
    @Insert("insert into user(portrait,user_name,email,password) values(#{portrait},#{user_name},#{email},#{password})")
    @Options(useGeneratedKeys = true, keyProperty = "id", keyColumn = "id")
    public int registerNewAccount(User user);



    /********************以下是删除user操作***********************/
    //测试Junit时删除新添加的user数据
    @Delete("delete from user where email=#{email}")
    public int deleteUserInDb(String email);



    /********************以下是更新user操作***********************/
    //更新用户个人信息
    @Update("update user set user_name=#{user_name},role=#{role},phone=#{phone},nation=#{nation},portrait=#{portrait} where id=#{id}")
    public int updateUserInfo(User user);

    //更新用户新的可用resource空间
    @Update("update user set resource_remain=#{resource_remain} where id=#{id}")
    public String updateUserResourceSpaceRemain(User user);

    //更新用户最新使用的industry和sub_industry信息
    @Update("update user set last_store_code=#{last_store_code}, last_store_sub_code=#{last_store_sub_code} where id=#{user_id}")
    public String updateLastSelectedIndustry(@Param("user_id") Integer user_id, @Param("last_store_code") String last_store_code,
                                             @Param("last_store_sub_code") String last_store_sub_code);

    //更新用户积分数值
    @Update("update user set total_points=#{total_points} where id=#{id}")
    public int updateUserTotalPoints(User user);

    //释放用户表中过期的实例的空间
    @Update("update user set resource_remain=resource_remain-#{space_expire} where user_id=#{user_id}")
    public int removeExpireInstanceSpace(Instance instance);


}