package cn.itcast.ssm.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("mailSenderUtil")
public class MailSenderUtil {
	
    private JavaMailSender javaMailSender;
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}


	/**
     * 发送邮件
     * @param to        收件人邮箱
     * @param subject   邮件主题
     * @param content   邮件内容
     */
    public void sendMail(String to, String subject, String content) {
    	
    	Properties pro = new Properties();
    	try {
			pro.load(this.getClass().getClassLoader().getResourceAsStream("mail.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}
    	SimpleMailMessage message = new SimpleMailMessage();
    	message.setFrom(pro.getProperty("mail.username"));
    	message.setTo(to);
    	message.setSubject(subject);
    	message.setText(content);
    	javaMailSender.send(message);
    }
}