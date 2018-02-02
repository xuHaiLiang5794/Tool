package com.xuhailiang5794.email;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.util.Date;
import java.util.Properties;

/**
 * <pre>
 * 邮件发送器
 * </pre>
 *
 * @author hailiang.xu
 * @version 1.0
 * @since 2018/2/2 18:20
 */
class MailSender {

    /**
     * <pre>
     * 说明:以文本格式发送邮件
     * </pre>
     *
     * @param mailInfo 待发送的邮件的信息
     * @return
     * @throws MessagingException
     * @author hailiang.xu
     * @since 2018年02月02日 下午6:40:31
     */
    public boolean sendTextMail(MailSenderInfo mailInfo) throws MessagingException {
        return sendTextMessage(buildMessage(mailInfo), mailInfo.getContent());
    }

    /**
     * <pre>
     * 说明:以HTML格式发送邮件
     * </pre>
     *
     * @param mailInfo 待发送的邮件的信息
     * @return
     * @throws MessagingException
     * @author hailiang.xu
     * @since 2018年02月02日 下午6:41:12
     */
    public boolean sendHtmlMail(MailSenderInfo mailInfo) throws MessagingException {
        return sendHtmlMessage(buildMessage(mailInfo), mailInfo.getContent());
    }

    /**
     * <pre>
     * 说明:构建邮件消息
     * </pre>
     *
     * @param mailInfo 待发送的邮件的信息
     * @return
     * @throws MessagingException
     * @author hailiang.xu
     * @since 2018年02月02日 下午6:42:03
     */
    public Message buildMessage(MailSenderInfo mailInfo) throws MessagingException {
        /**
         * 判断是否需要身份认证
         */
        UserAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            /**
             * 如果需要身份认证，则创建一个密码验证器
             */
            authenticator = new UserAuthenticator(mailInfo.getUserName(),
                    mailInfo.getPassword());
        }
        /**
         * 根据邮件会话属性和密码验证器构造一个发送邮件的session
         */
        Session sendMailSession = Session
                .getDefaultInstance(pro, authenticator);
        try {
            /**
             * 根据session创建一个邮件消息
             */
            Message mailMessage = new MimeMessage(sendMailSession);
            /**
             * 创建邮件发送者地址
             */
            Address from = new InternetAddress(mailInfo.getFromAddress());
            /**
             * 设置邮件消息的发送者
             */
            mailMessage.setFrom(from);
            /**
             * 创建邮件的接收者地址，并设置到邮件消息中
             */
            Address to = new InternetAddress(mailInfo.getToAddress());
            mailMessage.setRecipient(Message.RecipientType.TO, to);
            /**
             * 设置邮件消息的主题
             */
            mailMessage.setSubject(mailInfo.getSubject());
            /**
             * 设置邮件消息发送的时间
             */
            mailMessage.setSentDate(new Date());

            return mailMessage;
        } catch (MessagingException ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    /**
     * <pre>
     * 说明:以文本格式发送邮件
     * </pre>
     *
     * @param mailMessage 邮件消息
     * @param mailContent 邮件内容
     * @return
     * @throws MessagingException
     * @author hailiang.xu
     * @since 2018年02月02日 下午6:42:55
     */
    public boolean sendTextMessage(Message mailMessage, String mailContent) throws MessagingException {
        /**
         * 设置邮件内容
         */
        mailMessage.setText(mailContent);
        /**
         * 发送邮件
         */
        Transport.send(mailMessage);
        return true;
    }

    /**
     * <pre>
     * 说明:以HTML格式发送邮件
     * </pre>
     *
     * @param mailMessage 邮件消息
     * @param mailContent 邮件内容
     * @return
     * @throws MessagingException
     * @author hailiang.xu
     * @since 2018年02月02日 下午6:43:42
     */
    public boolean sendHtmlMessage(Message mailMessage, String mailContent) throws MessagingException {
        /**
         * MiniMultipart类是一个容器类，包含MimeBodyPart类型的对象
         */
        Multipart mainPart = new MimeMultipart();
        /**
         * 创建一个包含HTML内容的MimeBodyPart
         */
        BodyPart html = new MimeBodyPart();
        /**
         * 设置HTML内容
         */
        html.setContent(mailContent, "text/html; charset=utf-8");
        mainPart.addBodyPart(html);
        /**
         * 将MiniMultipart对象设置为邮件内容
         */
        mailMessage.setContent(mainPart);
        /**
         * 发送邮件
         */
        Transport.send(mailMessage);

        return true;
    }
}
