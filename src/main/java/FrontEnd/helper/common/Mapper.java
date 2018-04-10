package FrontEnd.helper.common;

/**
 * Created by Administrator on 2018/2/24.
 */
public class Mapper {

    //project表的数据库操作mapper********************************************
    //select
    public static final String GET_PROJECT_LIST_DATA="getProjectListData";
    public static final String GET_PROJECT_DATA="getProjectData";
    public static final String GET_PROJECT_RESOURCE_SIZE="getProjectResourceSize";
    public static final String GET_TARGET_STORE_DATA="getTargetStoreData";

    //insert
    public static final String CREATE_EMPTY_PROJECT="createEmptyProject";
    public static final String CREATE_PSD_PROJECT="createPSDProject";
    public static final String CREATE_COPY_PROJECT="createCopyProject";

    //delete
    public static final String DELETE_PROJECT_BY_ID="deleteProjectById";
    public static final String DELETE_PROJECT_BY_FILE_NAME="deleteProjectByFileName";

    //update
    public static final String SAVE_PROJECT_DATA="saveProjectData";
    public static final String UPDATE_PSD_PROJECT_DATA="updatePSDProjectData";
    public static final String MODIFY_PROJECT_NAME="modifyProjectName";
    public static final String UPDATE_PROJECT_RESOURCE_SIZE="updateProjectResourceSize";
    public static final String UPDATE_PROJECT_OPENNESS="updateProjectOpenness";



    //user_upload_file表的数据库操作mapper********************************
    //select
    public static final String GET_ALL_RESOURCE_BY_PROJECT_ID="getAllResourceByProjectId";
    public static final String GET_RESOURCE_DATA="getResourceData";
    public static final String GET_RESOURCE_BY_USERID_AND_FILETYPE="getResourceByUserIdAndFileType";
    public static final String GET_RESOURCE_REF_COUNT="getResourceRefCount";
    public static final String GET_FOLDER_SUB_RESOURCE="getFolderSubResource";

    //delete
    public static final String DELETE_RESOURCE_BY_ID="deleteResourceById";
    public static final String DELETE_RESOURCE_BY_PROJECT_ID="deleteResourceByProjectId";

    //insert
    public static final String INSERT_NEW_RESOURCE="insertNewResource";

    //update
    public static final String UPDATE_RESOURCE_TIMESTAMP="updateResourceTimeStamp";
    public static final String RENAME_RESOURCE_BY_ID="renameResourceById";
    public static final String UPDATE_VIDEO_IMAGE="updateVideoImage";



    //user 表数据库操作mapper********************************************
    //select
    public static final String GET_USER_DATA="getUserData";
    public static final String LOGON_VALIDATION="loginValidation";
    public static final String REGISTER_ACCOUNT_CHECK="registerAccountCheck";
    public static final String GET_ORIGIN_PORTRAIT_NAME="getOriginPortraitName";
    public static final String GET_USER_RESOURCE_SPACE_REMAIN="getUserResourceSpaceRemain";

    //insert
    public static final String REGISTER_NEW_ACCOUNT="registerNewAccount";

    //delete
    public static final String DELETE_USER_INFO="deleteUserInDb";

    //update
    public static final String UPDATE_USER_INFO="updateUserInfo";
    public static final String UPDATE_USER_RESOURCE_SPACE_REMAIN="updateUserResourceSpaceRemain";



    //order表数据库操作mapper********************************************
    //select
    public static final String GET_ORDER_LIST="getOrderList";
    public static final String GET_TARGET_ORDER_LIST="getTargetOrderList";

    //insert
    public static final String INSERT_NEW_ORDER_ITEM="insertNewOrderItem";

    //update
    //public static final String UPDATE_ORDER_PAYMENT="updateOrderPayment";

    //delete
    public static final String DELETE_ORDER_ITEM="deleteOrderItem";
}













