package com.boluo.blog.enums;

public enum SystemResultCode {

    // 成功
    SUCCESS("200", "成功"),
    // 参数不能为空
    SYS_PARAM_IS_NULL("00001", "参数不能为空"),
    //
    SYS_FILE_IS_NULL("00002", "文件不能为空"),
    //
    SYS_PARAM_IS_NOT_LEGAL("00003", "参数不合法"),
    //
    SYS_TOKEN_IS_NULL("00004", "token为空"),

    SYS_API_SECURITY_VERIFY_ERROR("00005", "接口安全校验不通过"),

    SYS_AUTHENTICATION_ERROR("00006", "无权操作"),

    //
    SYSTEM_ERROR("500", "系统错误"),
    //
    SYSTEM_DATA_EXIST("501", "数据已存在"),
    //
    SYSTEM_USER_EXIST("502", "用户已存在"),
    //
    SYSTEM_CODE_ERROR("503", "验证码错误"),
    //
    SYSTEM_USER_IS_NOT_REGISTER("504", "该用户尚未注册"),
    //
    SYSTEM_WECHAT_GROUP_NOT_EXIST("505", "不存在该群组"),

    SYSTEM_AREA_NOT_EXISTS("506", "区域不存在"),

    SYSTEM_USER_AREA_NOT_SCOPE("507", "当前区域不在用户的区域范围内"),

    SYSTEM_DEPT_NOT_EXISTS("508", "单位不存在"),
    //
    ACCOUNT_OLD_PASSWORD_ERROR("599", "原始密码错误"),
    //
    ACCOUNT_PASSWORD_ERROR("600", "账号或密码错误"),
    //
    USER_INFO_NOT_PERFECT("601", "用户信息未完善"),
    //
    USER_STATUS_DISABLE("602", "账号被禁用"),
    //
    USER_NOT_LOGIN("603", "用户未登录"),
    //
    USER_AUDIT_NOT_OK("604", "账号审核未通过"),
    //
    USER_IN_AUDIT("605", "账号审核中"),
    //
    WECHAT_GROUP_ERR_PIC_IS_EMPTY("610", "未上传截图"),

    WECHAT_AREA_IS_EMPTY("611", "管理员对应的区域不存在"),

    WECHAT_ONLY_VELLAGE_CAN_CREATE_GROUP("612", "只能村管理员才能创建群众群"),

    WECHAT_GROUP_HOME_COUNT_ERROR("613", "群覆盖的户数不正确"),

    WECHAT_GROUP_IS_NOT_EXISTS("614", "微信群信息不存在"),

    WECHAT_GROUP_IS_NOT_OWNER("615", "不是微信群的创建者或管理单位，无权修改微信群信息"),

    WECHAT_GROUP_TOTAL_USER_ERROR("616", "微信群总用户数需要0-500的值"),

    WECHAT_GROUP_DELETE_FAIL("617", "删除微信群失败"),

    WECHAT_GROUP_UPDATE_FAIL("618", "修改群组信息失败"),

    WECHAT_GROUP_CREATE_GROUP_ERROR("619", "创建群组失败"),

    WECHAT_GROUP_GROUP_TYPE_ERROR("620", "群组类型错误"),
    //
    USER_ADD_ERROR("604", "用户添加错误"),
    //
    USER_INFO_NOT_EXIST("605", "用户信息不存在"),
    //
    VERSION_EXISTS("606", "版本号已经存在"),
    //
    INTERIOR_VERSION_EXISTS("607", "内部版本号已经存在"),
    //
    VERSION_IS_PUBLISH("608", "版本已发布"),
    //
    DATA_OPERA_ERROR("650", "数据库操作异常"),
    //
    OPERA_PERMISSION_ERROR("651", "没有权限"),
    /**
     *
     */
    UPDATE_ROLE_ERROR("506", "没有修改权限"),

    TRAIN_NO_EXIST("507", "培训信息不存在"),

    TRAIN_ADD_ERROR("508", "培训信息添加失败"),

    GET_VERIFY_CODE_ERROR("509", "获取验证码错误"),

    AREA_ID_NOT_EXISTS_ADMIN_SUB("510", "所选区域不可操作"),

    CONTENT_NOT_EXISTS("511", "文稿不存在"),
    //
    DATA_NOT_EXISTS("512", "数据不存在"),

    CONTENT_NOT_EXISTS_ERROR("620", "文稿不存在"),

    CONTENT_NOT_PUBLISH("621", "文稿未发布"),

    CONTENT_NOT_PRIVILEGE("622", "无权修改内容"),

    CONTENT_SHARE_ADD_ERROR("623", "添加分享记录失败"),

    CONTENT_SHARE_URL_ERROR("624", "文稿分享地址有误"),

    USER_COLUMN_IS_EMPTY("630", "栏目不能为空"),

    USER_COLUMN_CHOICENESS_COLUMN_ERROR("631", "精选栏目必需固定在最前边"),

    WECHAT_GROUP_HOME_COUNT__GREATER_THAN_ERROR("632", "覆盖户数不能大于村庄总户数"),

    WECHAT_GROUP_USER_COUNT__GREATER_THAN_ERROR("633", "入群人数不能大于村庄总人数"),

    REGISTET_ERROR("513", "注册失败 "),

    UPDATE_PASSWORD_ERROR("514", "修改密码失败 "),

    VERIFY_PASSWORD_ERROR("515", "密码长度请在6-16位之间 "),

    MOBILE_IS_EXIST("516", "该手机号已注册 "),
    CHANGE_VILLAGE_INFO_ERROR("517", "修改村庄信息失败 "),

    SYS_CONTENT_INFO_ERROR("518", "查看文稿详情失败 "),

    UPDATE_MOBILE_ERROR("519", "修改手机号码失败 "),

    ACCOUNT_NOT_EXISTS("520", "用户不存在"),

    SEND_VERIFY_CODE_ERROR("513", "验证码发送失败， 请稍后重试"),

    SEND_COUNT_IS_EXCESS("514", "今日短信发送量已达上限"),

    SEND_TIME_IS_ILLEGALITY("515", "短信发送间隔过快， 请稍后重试"),

    ACCOUNT_CODE_ERROR("606", "无效的验证码"),

    TOKEN_LOGIN_ERROR("609", "无效的token"),

    DEPT_IS_NULL("631", "单位为空"),

    USER_IS_NULL("823", "研判组人员为空"),

    DEPT_NOT_EXISTS("632", "单位不存在"),

    ALREADY_AUDIT("633", "内容已审核"),

    COLUMN_NAME_EXISTS("634", "栏目名称已存在"),

    COLUMN_TYPE_EXISTS("641", "栏目已存在"),

    COLUMN_NAME_NOT_SUPPORT_EMOTICON("637", "栏目名称不能存在表情符号"),

    CONTENT_TITLE_NOT_SUPPORT_EMOTICON("635", "标题不能存在表情符号"),

    COLUMN_NAME_NOT_SUPPORT("636", "栏目名称只支持中文"),

    HTTP_URL_CONFIG_NOT_FOUND("637", "http请求参数未配置"),

    ADMIN_PUBLISH_ERROR("638", "该账号操作文稿数据将影响系统文稿权限, 请使用非管理员账号操作文稿数据!"),

    COLUMN_TYPE_NOT_DELETE("639", "此类型的栏目不能删除"),

    STS_ERROR_ALIYUN_ERROR("640", "阿里云STS请求返回异常"),

    CONSENSUS_ADD_ROLE_ERROR("703", "区县级以上权限不可添加舆情"),

    CONSENSUS_IS_PARENT_AUDIT("702", "无权限修改"),

    CONSENSUS_ROLE_ERROR("704", "没有菜单权限"),

    CONSENSUS_AUDIT_AUDITED_ERROR("705", "舆情已审核"),

    ADMIN_LIMIT_IS_MAX("706", "管理员人数已达上限"),

    ALREADY_DISPOSE("707", "已处理"),

    NOT_SUPPORT_OPE("708", "不支持该操作"),

    ALREADY_REJECTED("710", "已驳回"),

    ALREADY_APPEND("711", "已补充"),

    ALREADY_FINISH("712", "已完结"),

    ALREADY_ADD_SCORE("713", "已打分"),

    ACCOUNT_FORMAT_ERROR("709", "请输入正确的账号"),

    SYNC_CODE_ERROR("800", "同步标识错误"),

    SYNC_TIME_IS_NOT_LEGAL("801", "同步时间错误"),

    SYNC_SIGN_IS_NOT_LEGAL("802", "同步签名错误"),

    SCORE_CONFIG_EXIST("803", "本区域积分配置已存在"),

    CREATE_MEETING_ERROR("810", "创建会议失败"),
    UPDATE_MEETING_ERROR("811", "修改会议信息失败"),
    END_MEETING_ERROR("812", "终止会议失败"),
    DELETE_MEETING_ERROR("813", "删除会议失败"),
    HOST_USER_INFO_ERROR("814", "会议主持人信息未知"),
    NOT_MEETING_MENMBER_ERROR("815", "不是会议成员，不能进入会议"),
    MEETING_CAN_NOT_DELETE_ERROR01("816", "已开始的会议不能删除"),
    MEETING_CAN_NOT_DELETE_ERROR02("817", "已开始的会议不能删除"),

    WALL_TABLE_EXISTS("819", "该月份数据表已存在"),
    HANDLER_LIMIT_TIME("820", "已经超过处理时限, 无法处理!"),

    SIGN_IS_NOT_LEGAL("821", "签名错误"),

    MONITOR_SYNC_OVER("822", "实时监测数据已同步到舆情!"),

    CONSENT_IS_NULL("823", "没查到该舆情!"),
    TEAM_ADD_ONE("823", "添加群组失败，一条舆情只能添加一个群组!"),
    TEAM_IS_NULL("825", "研判群组不存在！必须先创建好群组！"),
    NOT_IS_USER("826", "拉人入群失败！不是群主不能拉人入群！"),
    USER_TEAM_ADD_FALL("827", "拉人入群失败，请联系管理员!"),
    USER_TEAM_DELETE_FALL("827", "踢人出群失败，请联系管理员!"),
    USER_TEAM_DELETE_FALL_NOT_ISOWNER("828", "踢人出群失败，不是群主不能拉人入群！"),
    TEAM_ADD_FALL("824", "添加网易云信群组失败!"),
    DATA_NOT_PERMISSION("828", "没有权限！"),
    TEAM_COUNT_EXCEED("829", "群员人数超出了最大限制！");
    private String code;

    private String message;

    SystemResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}

