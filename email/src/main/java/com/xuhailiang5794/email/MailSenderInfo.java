package com.xuhailiang5794.email;

import lombok.Getter;
import lombok.Setter;

import java.util.Properties;

/**
 * <pre>
 * 封装邮件主体信息：发送者、接收者、内容等
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/2 18:17
 */
@Getter
@Setter
class MailSenderInfo {
    /**
     * 发送邮件的服务器的IP和端口
     */
    private String mailServerHost;
    private String mailServerPort = "25";
    /**
     * 邮件发送者的地址
     */
    private String fromAddress;
    /**
     * 邮件接收者的地址
     */
    private String toAddress;
    /**
     * 发送者的用户名和密码
     */
    private String userName;
    private String password;
    /**
     * 是否需要身份验证
     */
    private boolean validate = false;
    /**
     * 邮件主题
     */
    private String subject;
    /**
     * 邮件的文本内容
     */
    private String content;
    /**
     * 邮件附件的文件名
     */
    private String[] attachFileNames;

    /**
     * <pre>
     * 说明:获得邮件会话属性
     * </pre>
     *
     * @author hailiang.xu
     * @since 2018年02月02日 下午6:44:10
     */
    public Properties getProperties() {
        Properties p = new Properties();
        p.put("mail.smtp.host", this.mailServerHost);
        p.put("mail.smtp.port", this.mailServerPort);
        p.put("mail.smtp.auth", validate ? "true" : "false");
        return p;
    }

}
