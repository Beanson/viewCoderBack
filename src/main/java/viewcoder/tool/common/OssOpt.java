package viewcoder.tool.common;

import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.*;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import viewcoder.operation.entity.UserUploadFile;
import viewcoder.operation.impl.common.CommonService;
import viewcoder.tool.config.GlobalConfig;
import viewcoder.tool.encrypt.AESEncryptor;
import com.aliyun.oss.OSSClient;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by Administrator on 2018/2/17.
 */
public class OssOpt {

    private static Logger logger = LoggerFactory.getLogger(OssOpt.class.getName());

    //TODO 上ECS后把 END_POINT 设置为ECS处的连接
    // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，
    // 创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建
    private static final String END_POINT_DEV = "com.viewcoder.oss.endpoint.outer";
    private static final String END_POINT_PROD = "com.viewcoder.oss.endpoint.inner";
    private static final String VIEWCODER_BUCKET = "viewcoder-bucket";
    private static final String ALI_OSS_KEY = "q4pjxqabACHK2WE5";
    private static final String ALI_OSS_SECRET = "yF3L6IbHTma6QbgfopLcJ4JF2cvSbJ";

    /************************ 初始化和关闭ossClient流 *********************/
    /**
     * 初始化OSSClient客户端
     *
     * @return 返回OSSClient的实例
     */
    public static OSSClient initOssClient() {
        if (GlobalConfig.isProdEnv()) {
            return new OSSClient(GlobalConfig.getProperties(END_POINT_PROD), ALI_OSS_KEY, ALI_OSS_SECRET);

        } else {
            return new OSSClient(GlobalConfig.getProperties(END_POINT_DEV), ALI_OSS_KEY, ALI_OSS_SECRET);
        }
    }

    /**
     * 对OSSClient客户端进行关闭
     *
     * @param ossClient 客户端句柄
     */
    public static void shutDownOssClient(OSSClient ossClient) {
        if (ossClient != null) {
            ossClient.shutdown();
            ossClient = null;
        }
    }


    /**************************** 上传资源文件方法 *************************/
    /**
     * 上传文件资源
     *
     * @param content  byte类型的上传文件流
     * @param fileName 上传文件名称
     */
    public static void uploadFileToOss(String fileName, byte[] content, OSSClient ossClient) {
        OssOpt.logger.debug("===upload resource file to common: " + fileName);
        PutObjectResult result = ossClient.putObject(VIEWCODER_BUCKET, fileName, new ByteArrayInputStream(content));
    }

    /**
     * 上传文件资源
     *
     * @param inputStream 上传文件流
     * @param fileName    上传文件名称
     */
    public static void uploadFileToOss(String fileName, InputStream inputStream, OSSClient ossClient) {
        OssOpt.logger.debug("===upload resource file to common: " + fileName);
        ossClient.putObject(VIEWCODER_BUCKET, fileName, inputStream);
    }

    /**
     * 上传文件资源
     *
     * @param file     文件类型
     * @param fileName 上传文件名称
     */
    public static void uploadFileToOss(String fileName, File file, OSSClient ossClient) {
        OssOpt.logger.debug("===upload resource file to common: " + fileName);
        ossClient.putObject(VIEWCODER_BUCKET, fileName, file);
    }


    /**************************** 删除资源文件方法 ***************************/
    /**
     * 删除在OSS上对应文件名的的文件
     *
     * @param fileName 要删除的文件名
     */
    public static void deleteFileInOss(String fileName, OSSClient ossClient) {
        OssOpt.logger.debug("===delete resource file in common: " + fileName);
        ossClient.deleteObject(VIEWCODER_BUCKET, fileName);
    }

    /**
     * 级联删除在OSS上对应文件名的的文件
     *
     * @param list 要删除的文件list
     */
    public static void deleteFileInOssBatch(List<String> list, OSSClient ossClient) {
        if (list != null && list.size() > 0) {
            DeleteObjectsRequest request = new DeleteObjectsRequest(VIEWCODER_BUCKET).withKeys(list);
            DeleteObjectsResult deleteObjectsResult = ossClient.deleteObjects(request);
            List<String> deletedObjects = deleteObjectsResult.getDeletedObjects();
            OssOpt.logger.debug("delete batch file: \nlist: " + list + "\n result: " + deletedObjects + "\n");
        }
    }

    /**
     * 查看在数据库中该文件的引用如果为0则删除该资源在OSS中的文件
     *
     * @param userUploadFile 上传的文件
     */
    public static void addToOssDeleteList(UserUploadFile userUploadFile, List<String> widgetList) {
        //如果数据库中无该字段的其他引用，则删除OSS对应文件
        String deleteFileName = GlobalConfig.getOssFileUrl(Common.UPLOAD_FILES) + userUploadFile.getTime_stamp()
                + Common.DOT_SUFFIX + userUploadFile.getSuffix();
        widgetList.add(deleteFileName);

        //如果是video组件，则添加对应的video_image_name到删除列表
        if (CommonService.checkNotNull(userUploadFile.getVideo_image_name()) && userUploadFile.getFile_type() == 2) {
            String deleteVideoName = GlobalConfig.getOssFileUrl(Common.UPLOAD_FILES) + userUploadFile.getVideo_image_name();
            widgetList.add(deleteVideoName);
        }
    }


    /**************************** 拷贝资源文件方法 ***************************/
    /**
     * 拷贝OSS文件
     *
     * @param ossClient      oss句柄
     * @param sourceFileName 原文件名
     * @param destFileName   目标文件名
     */
    public static void copyObjectOss(OSSClient ossClient, String sourceFileName, String destFileName) {
        String message = "";
        if (getObjectExist(ossClient, sourceFileName)) {
            CopyObjectResult result = ossClient.copyObject(VIEWCODER_BUCKET, sourceFileName, VIEWCODER_BUCKET, destFileName);
            message = "Copy oss, source: " + sourceFileName + ", dest: " + destFileName +
                    "ETag: " + result.getETag() + ", LastModified: " + result.getLastModified();
            OssOpt.logger.debug(message);
        } else {
            message = "Resource " + sourceFileName + " not exist, could not copy";
            OssOpt.logger.warn(message);
        }

    }

    /**
     * 拷贝OSS文件
     *
     * @param ossClient      oss句柄
     * @param sourceFileName 原文件名
     * @param destFileName   目标文件名
     */
    public static void copyProject(OSSClient ossClient, String sourceFileName, String destFileName) {
        OssOpt.copyObjectOss(ossClient, sourceFileName, destFileName);
    }


    /*************************** 查看资源文件是否存在方法 *************************/
    /**
     * 查看资源是否存在方法
     *
     * @param ossClient oss句柄
     * @param fileName  存储在oss中的文件名
     * @return
     */
    public static boolean getObjectExist(OSSClient ossClient, String fileName) {
        boolean found = ossClient.doesObjectExist(VIEWCODER_BUCKET, fileName);
        OssOpt.logger.debug("===get Object" + fileName + " exist: " + found);
        return found;
    }


    /***************************** 设置文件的ACK操作 *********************************/

    /**
     * 更新文件的ACL设置
     *
     * @param ossClient
     * @param file
     * @param ack
     */
    public static void updateAclConfig(OSSClient ossClient, String file, boolean ack) {
        //根据是否公开设置其访问权限
        if (ack) {
            ossClient.setObjectAcl(VIEWCODER_BUCKET, file, CannedAccessControlList.PublicRead);
        } else {
            ossClient.setObjectAcl(VIEWCODER_BUCKET, file, CannedAccessControlList.Private);
        }
    }


    /***************************** 下载资源文件的操作 *********************************/

    /**
     * 从oss系统中获取该文件
     *
     * @param ossClient oss访问句柄
     * @param fileName  文件名
     * @return
     */
    public static String getOssFile(OSSClient ossClient, String fileName) {
        //如果存在该媒体对象则获取，否则返回Null，若不check直接获取不存在的oss文件会报错
        if (getObjectExist(ossClient, fileName)) {
            OSSObject ossObject = ossClient.getObject(VIEWCODER_BUCKET, fileName);
            BufferedReader reader = new BufferedReader(new InputStreamReader(ossObject.getObjectContent()));
            StringBuilder builder = new StringBuilder();
            try {
                //从数据流中读取字符流并用builder装载
                String line = null;
                line = reader.readLine();
                while (CommonService.checkNotNull(line)) {
                    builder.append(line);
                    line = reader.readLine();
                }

                //数据读取完成后，获取的流一定要显示Close，否则会造成资源泄露。
                reader.close();

            } catch (IOException e) {
                OssOpt.logger.error("getOssFile error: ", e);
            }
            return builder.toString();
        } else {
            return null;
        }
    }
}








