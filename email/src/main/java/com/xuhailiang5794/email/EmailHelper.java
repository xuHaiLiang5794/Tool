package com.xuhailiang5794.email;

import javax.mail.MessagingException;

/**
 * <pre>
 * email发送工具
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/2 18:11
 */
public class EmailHelper {
    private static final String usernmae = "service@yuntucredit.com";// 登陆邮件发送服务器的用户名
    private static final String userPassword = "Passw0rd#";// 登陆邮件发送服务器的密码
    private static final String mailServerHost = "smtp.ym.163.com";

    public static void main(String[] args) throws MessagingException {
        EmailHelper.sendMail("xuhailiang@yuntucredit.com", "账户异常登录通知", "IP异常登录");
    }

    /**
     * <pre>
     * 说明:发送邮件
     * </pre>
     *
     * @param toMail  邮箱地址
     * @param subject 邮件名
     * @param content 邮件内容
     * @author hailiang.xu
     * @since 2018年02月02日 下午6:39:32
     */
    public static void sendMail(String toMail, String subject, String content) throws MessagingException {
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost(mailServerHost);
        mailInfo.setMailServerPort("25");
        mailInfo.setValidate(true);
        mailInfo.setUserName(usernmae);
        mailInfo.setPassword(userPassword);
        mailInfo.setFromAddress(usernmae);
        mailInfo.setToAddress(toMail);
        mailInfo.setSubject(subject);
        mailInfo.setContent(content);

        MailSender sms = new MailSender();
        sms.sendTextMail(mailInfo);
    }


}
