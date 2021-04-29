package com.technology.flink.util;

import com.aliyun.openservices.ons.api.PropertyKeyConst;

import java.util.Properties;

/**
 * @author
 */
public class ConfigUtil {

    private static Properties properties;

    public synchronized static Properties getProperties() {
        if (null != properties) {
            return properties;
        }
        //获取配置文件kafka.properties的内容
        Properties athenaProperties = new Properties();
        try {
            if (Utils.checkNull(System.getProperty(Constants.ENVIRONMENT))) {
                athenaProperties.load(ConfigUtil.class.getClassLoader().getResourceAsStream("unified-config.properties"));
            } else {
                athenaProperties.load(ConfigUtil.class.getClassLoader().getResourceAsStream("/opt/flink/conf/unified-config.properties"));
            }
            StringBuilder pro = new StringBuilder();
            for (String propertyName : athenaProperties.stringPropertyNames()) {
                pro.append(propertyName).append("=").append(athenaProperties.get(propertyName)).append("\n");
            }
//            LogsUtil.setLogInfo("", "", LogEnum.OUT, Constants.MODULE_NAME,
//                    "athenaProperties", pro.toString());
        } catch (Exception e) {
            //没加载到文件，程序要考虑退出
//            LogsUtil.setBugInfo("", "", e, Constants.MODULE_NAME,
//                    Constants.MODULE_NAME, "没加载到配置文件");
            e.printStackTrace();
        }
        properties = athenaProperties;
        return athenaProperties;
    }

    public static Properties getRocketMqConfiguration() {
        Properties properties = new Properties();
        Properties props = ConfigUtil.getProperties();
        String accesskey = null;
        String secret = null;
        String addr = null;
        if (Constants.CONFIG_TYPE_INTRANET
                .equals(props.getProperty(Constants.CONFIG_TYPE))) {
            accesskey = props.getProperty("intranet.mq.accesskey");
            secret = props.getProperty("intranet.mq.secret.key");
            addr = props.getProperty("intranet.mq.name.srv.addr");
        } else if (Constants.CONFIG_TYPE_TEST
                .equals(props.getProperty(Constants.CONFIG_TYPE))) {
            accesskey = props.getProperty("test.mq.accesskey");
            secret = props.getProperty("test.mq.secret.key");
            addr = props.getProperty("test.mq.name.srv.addr");
        } else {
            accesskey = props.getProperty("mq.accesskey");
            secret = props.getProperty("mq.secret.key");
            addr = props.getProperty("mq.name.srv.addr");
        }
        if (accesskey == null || secret == null || addr == null) {
            System.out.println("+==============rocketMq配置不正确");
        } else {
            // AccessKey 阿里云身份验证，在阿里云服务器管理控制台创建
            properties.put(PropertyKeyConst.AccessKey, accesskey);
            // SecretKey 阿里云身份验证，在阿里云服务器管理控制台创建
            properties.put(PropertyKeyConst.SecretKey, secret);
            // 设置 TCP 接入域名，到控制台的实例基本信息中查看
            properties.put(PropertyKeyConst.NAMESRV_ADDR, addr);
        }
        return properties;
    }

    public static Properties getKafkaConfiguration() {
        Properties props = new Properties();
        Properties properties = ConfigUtil.getProperties();

        String servers = properties.getProperty("kafka.servers");
        String groupId = properties.getProperty("kafka.group.id");

        if (servers == null || groupId == null) {
            System.out.println("+==============kafka配置不正确");

        } else {
            props.setProperty("bootstrap.servers", servers);
            props.setProperty("group.id", groupId);
        }
        return props;
    }

    public static String getZkConfig() {
        Properties properties = ConfigUtil.getProperties();
        String servers = properties.getProperty("zk.servers");
        if (servers == null) {
            System.out.println("+==============zk配置不正确");
            return null;
        } else {
            return servers;
        }
    }

    public static String getDecodeConfig() {
        Properties properties = ConfigUtil.getProperties();
        return properties.getProperty("decode.config") == null ? "2" : properties.getProperty("decode.config");
    }

    public static String getDomainConfig() {
        Properties properties = ConfigUtil.getProperties();
        String domain = properties.getProperty("domain");
        if (domain == null) {
            System.out.println("+==============domain配置不正确");
            return null;
        } else {
            return domain;
        }
    }

}
