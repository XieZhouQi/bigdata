package com.technology.flink.util;

/**
 * Description TODO
 *
 * @Author shenlian
 * @Date 2019/4/25 9:05
 * @Version 1.0
 */
public class Constants {

    /**
     * rocketMQ topic
     */
    public static final String DEVICE_TO_ATHENA_TOPIC = "device-to-athena-topic";

    public static final String ATHENA_TO_BUSINESS_TOPIC = "athena-to-business-topic";

    /**
     * 分流模块kafka topic
     */
    public static final String KAFKA_CONSUME_TOPIC = "wave-athena-shunt-topic";

    /**
     * 接受告警信息的kafka topic
     */
    public static final String UPLOAD_VIDEO_TOPIC = "wave-athena-video-topic";

    /**
     * 人脸检测算法topic
     */
    public static final String FACE_DETECT_TYPE = "wave-athena-faceDetect-topic";

    /**
     * 活体检测算法类型
     */
    public static final String LIVEN_ESS_DETECT_TYPE = "wave-athena-livenEssDetect-topic";

    /**
     * 人脸对比算法topic
     */
    public static final String FACE_COMPARE_TYPE = "wave-athena-faceIdentification-stream-topic";

    /**
     * 踩点topic
     */
    public static final String PATH_FINDER_TOPIC = "wave-athena-pathFinder-stream-topic";

    /**
     * 人脸姿态判定算法topic
     */
    public static final String FACE_POSE_TYPE = "wave-athena-facePose-topic";

    /**
     * 人脸质量判断算法topic
     */
    public static final String FACE_QUALITY_TYPE = "wave-athena-faceQuality-topic";

    /**
     * 人脸属性算法topic
     */
    public static final String FACE_ATTRIBUTE_TYPE = "wave-athena-faceAttribute-stream-topic";

    /**
     * 人脸分割算法类型
     */
    public static final String FACE_SEGMENTATION_TYPE = "wave-athena-faceSegmentation-topic";

    /**
     * 眼相算法类型
     */
    public static final String EYE_ALGORITHM_TYPE = "wave-athena-eyeAlgorithm-topic";

    /**
     * 三庭判定算法类型
     */
    public static final String THREE_COURT_TYPE = "wave-athena-threeCourt-topic";

    /**
     * 人脸老化算法类型
     */
    public static final String FACE_AGING_TYPE = "wave-athena-faceAging-topic";

    /**
     * 嘴相算法类型
     */
    public static final String MOUTH_PHASE_TYPE = "wave-athena-mouthPhase-topic";

    /**
     * 面相五行算法类型
     */
    public static final String FACE_FIVE_LINES_OF = "wave-athena-faceFiveLinesOf-topic";

    /**
     * 异常行为检测算法类型
     */
    public static final String ABNORMAL_BEHAVIOR_TYPE = "wave-athena-behaviorAnalysis-stream-topic";

    /**
     * 烟火检测
     */
    public static final String FIRE_SMOKE_DETECT_TYPE = "wave-athena-fireAndSmokeDetection-stream-topic";

    /**
     * 挖掘机检测topic
     */
    public static final String GRAB_DETECT_TYPE = "wave-athena-grabDetect-stream-topic";

    /**
     * 配置类型
     */
    public static final String CONFIG_TYPE = "config.flag";

    /**
     * 配置类型
     */
    public static final String CONFIG_TYPE_INTRANET = "intranet";

    public static final String CONFIG_TYPE_TEST = "test-online";

    public static final String CONFIG_TYPE_ONLINE = "online";

    /**
     * jvm 参数
     */
    public static final String ENVIRONMENT = "environment";

    /**
     * 视频最大长度10m
     */
    public static final int MAX_VIDEO_SIZE = 10485760;

    /**
     * 接收avPacket最大长度8m
     */
    public static final int MAX_VIDEO_DATA_SIZE = 8388608;

    /**
     * 人脸对比算法标识
     */
    public static final String FACE_COMPARE_NUM = "100";

    /**
     * 踩点算法标识
     */
    public static final String PATH_FINDER_NUM = "103";

    /**
     * 非人脸对比算法标识
     */
    public static final String NON_FACE_COMPARE_NUM = "102";

    /**
     * 抓拍算法标识
     */
    public static final String FACE_ATTRIBUTE_NUM = "101";

    /**
     * 挖掘机算法标识
     */
    public static final String GRAB_DETECT_NUM = "22";

    /**
     * 安全帽检测
     */
    public static final String HELMET = "19";

    /**
     * 安全绳
     */
    public static final String SAFETY_ROPE = "23";

    /**
     * 系好安全绳
     */
    public static final String FASTEN_SAFETY_ROPE  = "24";

    /**
     * 工作手套
     */
    public static final String GRAB_WORK_GLOVE = "25";

    /**
     * 绝缘手套
     */
    public static final String GRAB_INSULATING_GLOVE = "26";

    /**
     * 反光背心
     */
    public static final String GRAB_REFLECTIVE_VEST  = "27";

    /**
     * 长袖工作服
     */
    public static final String GRAB_LONG_SLEEVE_COVERALLS = "28";

    /**
     * 电焊服
     */
    public static final String GRAB_WELDING_SUIT = "29";

    /**
     * 紧扣工作服
     */
    public static final String GRAB_FASTEN_CLOTHES  = "30";

    /**
     * 防滑鞋
     */
    public static final String GRAB_ANTISKID_SHOE  = "31";

    /**
     * 绝缘鞋（靴）
     */
    public static final String GRAB_INSULATED_SHOES   = "32";

    /**
     * 护目镜
     */
    public static final String GRAB_SAFETY_GOGGLES = "33";

    /**
     * 面罩
     */
    public static final String GRAB_VEIL = "34";




    /**
     * 模块名称
     */
    public static final String MODULE_NAME = "wave-athena-seperateStream";

}
