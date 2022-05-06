package cn.prozhu.ssm.util;

import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.task.TaskExecutor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component("mailSenderUtil")
public class MailSenderUtil {
	@Autowired
	private TaskExecutor taskExecutor;
    private JavaMailSender javaMailSender;
	public void setJavaMailSender(JavaMailSender javaMailSender) {
		this.javaMailSender = javaMailSender;
	}
	
	/**
	 * 异步发送邮件
	 * @param to
	 * @param subject
	 * @param content
	 */
    public void send(String to, String subject, String content) {
        this.taskExecutor.execute(new SendMailThread(to, subject, content));
    }

	 //    内部线程类，利用线程池异步发邮件。
    private class SendMailThread implements Runnable {
        private String to;
        private String subject;
        private String content;
        private SendMailThread(String to, String subject, String content) {
            super();
            this.to = to;
            this.subject = subject;
            this.content = content;
        }
        @Override
        public void run() {
            sendMail(to, subject, content);
        }
    }
	

	/**
     * 同步发送邮件（速度非常慢）
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