package com.sinothk.android.utils;

import java.util.Date;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * <pre>
 *  @author 梁玉涛 (https://github.com/sinothk)
 *  @Create 2018/1/18 16:57
 *  @Project: OUtilsLib
 *  @Description:
 *  @Update:
 * <pre>
 */
public class EmailUtil {

    public static void main(String[] args) throws Exception {
        String fromName = "简爱";
        String toEMail = "sinothk@126.com";
        String eMailTitle = "密码找回 By Sinothk Center";
        String eMailTxt = "用户密码：" + 10000 + ", 密码：123456";
        sendSimpleEMail(fromName, toEMail, eMailTitle, eMailTxt);
    }

    /**
     * @param fromName    :发送人昵称
     * @param toEmail     ：收件人邮箱
     * @param subject     ：主题
     * @param content ：html内容（文本也可）
     * @throws Exception
     */
    public static boolean sendSimpleEMail(String fromName, String toEmail, String subject, String content) {
        try {

            // 接收对象
            // final String toEmail = "sinothk@126.com";

            // 发送对象
            final String host = "smtp.qq.com";
            final String fromEmail = "381518188@qq.com";
            final String userPwd = "usuxkybzmmqsbhec";// 使用QQ邮箱给的第三方登录密码

            // 设置昵称
            fromName = fromName == null || fromName.length() == 0 ? "SINOTHK CENTER" : fromName;

            Properties props = new Properties();
            props.put("mail.smtp.host", host);
            props.put("mail.smtp.auth", "true");

            props.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.setProperty("mail.smtp.port", "465");
            props.setProperty("mail.smtp.socketFactory.port", "465");

            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(fromEmail, userPwd);
                }
            });

            MimeMessage message = new MimeMessage(session);
            message.setContent(content, "text/plain");

            message.setSubject(subject, "utf-8");// 设置邮件主题

            message.setSentDate(new Date());// 设置邮件发送时期

            Address address = new InternetAddress(fromEmail, fromName);
            message.setFrom(address);// 设置邮件发送者的地址

            Address toaddress = new InternetAddress(toEmail);// 设置邮件接收者的地址
            message.addRecipient(Message.RecipientType.TO, toaddress);

//            // 创建一个包含HTML内容的MimeBodyPart
//            Multipart mainPart = new MimeMultipart();
//            BodyPart html = new MimeBodyPart();
//            html.setContent(htmlContent, "text/html; charset=utf-8");
//            mainPart.addBodyPart(html);
            // 将MiniMultipart对象设置为邮件内容
//            message.setContent(mainPart);
            try {
                Transport.send(message);
                return true;

            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
