/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package com.zxy.commons.email;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.activation.URLDataSource;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import com.google.common.base.Strings;
import com.zxy.commons.email.conf.SmtpConfigProperties;
import com.zxy.commons.lang.idgenerator.IdUtils;
import com.zxy.commons.lang.utils.RegexUtils;

/**
 * 发送邮件工具类
 * 
 * <p>
 * <a href="MailMessageUtils.java"><i>View Source</i></a>
 * 
 * @author zhaoxunyong@qq.com
 * @version 1.0
 * @since 1.0
 */
public final class MailMessageUtils {
    private final static SmtpConfigProperties PROP = SmtpConfigProperties.getInstance();
    private final static String SMTP_HOST = PROP.getSmtpHost();
    private final static int SMTP_PORT = PROP.getSmtpPort();
    private final static String SMTP_USERNAME = PROP.getSmtpUserName();
    private final static String SMTP_PASSWORD = PROP.getSmtpPassword();
    /** 发送邮件的编码:UTF-8 */
    private final static String SMTP_MAIL_CHARSET = "UTF-8";

    private MailMessageUtils() {
    }

    /**
     * 得到HtmlEmail对象
     * 
     * @return HtmlEmail
     */
    @SuppressWarnings("PMD.EmptyCatchBlock")
    private static HtmlEmail getEmail() {
        HtmlEmail email = new HtmlEmail();
        email.setHostName(SMTP_HOST);
        email.setSmtpPort(SMTP_PORT);
        if (StringUtils.isNotBlank(SMTP_USERNAME) && StringUtils.isNotBlank(SMTP_PASSWORD)) {
            email.setAuthentication(SMTP_USERNAME, SMTP_PASSWORD);
        }
        try {
            Session session = email.getMailSession();
            session.getProperties().setProperty("mail.smtp.ehlo", "true");
        } catch (EmailException e) {
            // do nothing
        }
        if (StringUtils.isNotBlank(SMTP_MAIL_CHARSET)) {
            email.setCharset(SMTP_MAIL_CHARSET);
        }
        return email;
    }

    /**
     * 发送eml邮件
     * 
     * @param inputStream inputStream
     * @param from from
     * @param tos tos
     * @param properties 自定义属性
     * @param isCloseInputStream 发送完邮件是否关闭InputStream
     * @throws EmailException EmailException
     * @throws MessagingException MessagingException
     */
    @SuppressWarnings("PMD.AvoidInstantiatingObjectsInLoops")
    public static void sendEml(InputStream inputStream, String from, List<String> tos, Map<String, String> properties,
            boolean isCloseInputStream) throws EmailException, MessagingException {
        try {
            // inputStream = new SharedFileInputStream(mailPath);
            Session session = getEmail().getMailSession();
            // session.getProperties().setProperty("mail.smtp.ehlo", "true");
            MimeMessage message = new MimeMessage(session, inputStream);
            if (!Strings.isNullOrEmpty(from)) {
                message.setFrom(new InternetAddress(from));
            }
            // 加入自定义属性
            for(Map.Entry<String, String> entry : properties.entrySet()) {
                String name = entry.getKey();
                String value = entry.getValue();
                message.setHeader(name, value);
            }
  
            if (tos == null || tos.isEmpty()) {
                Transport.send(message);
            } else {
                InternetAddress[] internetAddresses = new InternetAddress[tos.size()];
                int index = 0;
                for (String to : tos) {
                    internetAddresses[index] = new InternetAddress(to);
                    index++;
                }
                Transport.send(message, internetAddresses);
            }
        } finally {
            if (isCloseInputStream) {
                IOUtils.closeQuietly(inputStream);
            }
        }
    }

    /**
     * 通过inputStream发送邮件
     * @param inputStream InputStream
     * @throws EmailException EmailException
     * @throws MessagingException MessagingException
    */
    public static void sendEml(InputStream inputStream) throws EmailException, MessagingException {
        try {
            Session session = getEmail().getMailSession();
            MimeMessage message = new MimeMessage(session, inputStream);

            Transport.send(message, message.getAllRecipients());
        } finally {
            IOUtils.closeQuietly(inputStream);
        }
    }

    /**
     * 通过smtp发送邮件
     * 
     * @param subject subject
     * @param htmlBody htmlBody
     * @param properties properties
     * @param from from
     * @param toList toList
     * @param ccList ccList
     * @param bccList bccList
     * @throws EmailException EmailException
     */
    public static void sendMail(String subject, String htmlBody, Map<String, String> properties, String from,
            List<String> toList, List<String> ccList, List<String> bccList) throws EmailException {
        sendMail(subject, htmlBody, properties, from, toList, ccList, bccList, null);
    }

    /**
     * 通过smtp发送邮件
     * 
     * @param subject subject
     * @param htmlBody htmlBody
     * @param properties properties
     * @param from from
     * @param toList toList
     * @param ccList ccList
     * @param bccList bccList
     * @param embedUrls 内嵌图片
     * @throws EmailException EmailException
     */
    @SuppressWarnings({ "PMD.AvoidInstantiatingObjectsInLoops", "PMD.UseStringBufferForStringAppends" })
    public static void sendMail(String subject, String htmlBody, Map<String, String> properties, String from,
            List<String> toList, List<String> ccList, List<String> bccList, Map<String, URL> embedUrls)
                    throws EmailException {
        HtmlEmail htmlEmail = getEmail();
        // from处理
        if (!Strings.isNullOrEmpty(from)) {
            Address fromMailbox = parseMailbox(from);
            if (fromMailbox != null && StringUtils.isNotBlank(from)) {
                htmlEmail.setFrom(fromMailbox.getAddress(), fromMailbox.getName());
            }
        }
        // to处理
        if (toList != null && !toList.isEmpty()) {
            for (String to : toList) {
                if (StringUtils.isNotBlank(to)) {
                    Address toMailbox = parseMailbox(to);
                    htmlEmail.addTo(toMailbox.getAddress(), toMailbox.getName());
                }
            }
        }
        // cc处理
        if (ccList != null && !ccList.isEmpty()) {
            for (String cc : ccList) {
                if (StringUtils.isNotBlank(cc)) {
                    Address ccMailbox = parseMailbox(cc);
                    htmlEmail.addCc(ccMailbox.getAddress(), ccMailbox.getName());
                }
            }
        }
        // bcc处理
        if (bccList != null && !bccList.isEmpty()) {
            for (String bcc : bccList) {
                if (StringUtils.isNotBlank(bcc)) {
                    Address bccMailbox = parseMailbox(bcc);
                    htmlEmail.addBcc(bccMailbox.getAddress(), bccMailbox.getName());
                }
            }
        }
        // 邮件主旨
        htmlEmail.setSubject(subject);
        htmlEmail.setHtmlMsg(htmlBody);
        htmlEmail.setSentDate(new Date());
        // 加入自定义属性
        if (properties != null) {
            htmlEmail.setHeaders(properties);
        }
        // 内嵌图片
        if (embedUrls != null && !embedUrls.isEmpty()) {
            for(Map.Entry<String, URL> entry : embedUrls.entrySet()) {
                String cid = entry.getKey();
                URL url = entry.getValue();
                String fileName = StringUtils.substringAfterLast(url.getPath(), "/");
                if (StringUtils.isBlank(fileName)) {
                    fileName = cid;
                } else {
                    fileName += IdUtils.genStringId();
                }
                htmlEmail.embed(new URLDataSource(url), fileName, cid);
            }
        }
        htmlEmail.send();
    }

    /**
     * 将邮件地址解析成对象
     * 
     * @param mailAddr 邮件地址
     * @return Mailbox对象
     */
    public static Address parseMailbox(final String mailAddr) {
        Address addr = new Address();
        if (StringUtils.isBlank(mailAddr)) {
            return addr;
        }
        String mailaddress = mailAddr.replaceAll("\n+", "").trim();
        String regex = "<.+?>";
        if (RegexUtils.match(regex, mailaddress)) {
            mailaddress = mailaddress.replaceAll("^<|>$", "");
        }
        if (RegexUtils.find(regex, mailaddress)) {
            String name = mailaddress.replaceAll("<.+?>", "").replaceAll("[\"]", "").trim();
            String address = mailaddress.replaceAll(".*<(.+?)>", "$1").trim();
            addr = new Address(mailaddress, name, address);
        } else {
            String name = mailaddress.replaceAll("@.+$", "").replaceAll("[\"]", "").trim();
            String address = mailaddress;
            addr = new Address(mailaddress, name, address);
        }
        return addr;
    }
    
    /**
     * 富文本转纯文本
     * 
     * @param htmlBody 富文本内容
     * @return 纯文本内容
     */
    public static String htmlToPlain(String htmlBody) {
        if(StringUtils.isBlank(htmlBody)) {
            return "";
        }
        try {
            String plainBody = htmlBody
                    .replaceAll(
                            "(?ims)<\\s*head.*?>.+?<\\s*/\\s*head\\s*>|<\\s*style.*?>.+?<\\s*/\\s*style\\s*>|<\\s*script.*?>.+?<\\s*/\\s*script\\s*>|<!--.+?-->",
                            "").replaceAll("(?ims)\n+", "")
                    .replaceAll("(?ims)<\\s*/\\s*tr\\s*>|<\\s*/\\s*(?:tr|th|p|li|div)\\s*>|<\\s*br\\s*/?\\s*>", "\n")
                    .replaceAll("(?ims)<\\s*/\\s*(?:td|span)\\s*>", "  ").replaceAll("(?ims)<\\s*/?\\s*.+?>+", "")
                    .replaceAll("&amp;", "&").replaceAll("&quot;", "\"").replaceAll("&lt;", "<")
                    .replaceAll("&gt;", ">").replaceAll("&nbsp;", " ");
            return plainBody;
        } catch (Exception e) {
            // do nothing
            return "";
        }
    }

    /**
     * 纯文本转富文本
     * 
     * @param plainBody 纯文本内容
     * @return 富文本内容
     */
    @SuppressWarnings("PMD")
    public static String plainToHtml(String plainBody) {
        try{
            plainBody = plainBody.replace("&", "&amp;").replace("\"", "&quot;").replace("'", "&quot;").replace(
                    "<", "&lt;").replace(">", "&gt;").replace(" ", "&nbsp;").replace("\t", "&nbsp;&nbsp;")
                    .replace("\r\n", "<br/>").replace("\n", "<br/>").replaceAll("$", "<br/>");
            String htmlBody = new StringBuilder("<html><body><pre>").append(plainBody).append("</pre></body></html>")
                    .toString();
            return htmlBody;
        }catch(Exception e){
            //do nothing
        }
        return plainBody;
    }
}