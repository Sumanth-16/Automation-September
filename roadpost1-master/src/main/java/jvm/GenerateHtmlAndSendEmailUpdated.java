package jvm;

import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import freemarker.template.Configuration;
import freemarker.template.Template;


public class GenerateHtmlAndSendEmailUpdated {
	
	@Autowired
	JavaMailSender mailSender;
	
	@Autowired
	Configuration freemarkerConfiguration;
	
	private Properties failures_file = new Properties();
	private  static String fileSeparator = System.getProperty("file.separator");
	public String senderMail, recipientMail, mailSubject, password, ccmail, clientUrl, clientLogoUrl, mailHost, mailPort;
	public static String sendmail;
	private static String sRecipientMail;
	private static String[] sRecipientList;
	private static String sCCMail;
	private static String[] sCCList;
	private static Logger log = LogManager.getLogger("GenerateHtmlAndSendEmailUpdated");
	private static String pathToTemplate = System.getProperty("user.dir") + fileSeparator + "Resources" + fileSeparator
			+ "fmtemplates";
	private static String pathToOverview = System.getProperty("user.dir") + fileSeparator + "Automation Reports" + fileSeparator
			+ "LatestResults" + fileSeparator + "html" + fileSeparator + "overview.html";
	private static String pathToFailures = System.getProperty("user.dir") + fileSeparator + "ConfigFiles" + fileSeparator
			+ "FailedTestCases.properties";
	Properties sys_file = new Properties();
	private static String pathToSys = System.getProperty("user.dir") + fileSeparator + "ConfigFiles" + fileSeparator + "Sys.properties";
    private final static String messageContent = "Please open the attached Report for Results";
    public void sendEmail(String mailContent) {
			final Session session = Session.getInstance(this.getEmailProperties(), new Authenticator() {
				@Override
				protected PasswordAuthentication getPasswordAuthentication() {
					return new PasswordAuthentication(sys_file.getProperty("Email.SenderMail"), sys_file.getProperty("Email.SenderPassword"));
				}
	
			});
			String cc1 = sys_file.getProperty("Email.CCMail");
			String to = sys_file.getProperty("Email.RecipientMail");
			try {
		         BodyPart messageBodyPart = new MimeBodyPart();
		         messageBodyPart.setText(mailContent);
		         Multipart multipart = new MimeMultipart();
		         multipart.addBodyPart(messageBodyPart);
	            final MimeMessage message = new MimeMessage(session);
				try {
					prepare(message);
				} catch (Exception e) {
					e.printStackTrace();
				}
	            message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
	            String[] recipientList = cc1.split(",");
	            InternetAddress[] recipientAddress = new InternetAddress[recipientList.length];
	            int counter = 0;
	            for (String recipient : recipientList) {
	                recipientAddress[counter] = new InternetAddress(recipient.trim());
	                counter++;
	            }
	            message.setRecipients(Message.RecipientType.CC, recipientAddress);
	            message.setFrom(new InternetAddress(sys_file.getProperty("Email.SenderMail")));
	            message.setSubject(mailSubject);
	            message.setSentDate(new Date());
	            if (sendmail.equalsIgnoreCase("true")) {
	            	Transport.send(message);
		            log.info("Mail has been sent successfully");
	            }
	        } catch (final MessagingException ex) {
	            log.error("Exception occured while sending email : " + ex.getMessage());
	        }
    }

    public Properties getEmailProperties() {
    	FileInputStream fis, fis1;
		try {
			fis = new FileInputStream(pathToFailures);
			failures_file.load(fis);
			fis.close();
			fis1 = new FileInputStream(pathToSys);
			sys_file.load(fis1);
			fis.close();
			senderMail = sys_file.getProperty("Email.SenderMail");
			recipientMail = sys_file.getProperty("Email.RecipientMail");
			mailSubject = sys_file.getProperty("Email.MailSubject");
			password = sys_file.getProperty("Email.SenderPassword");
			sendmail = sys_file.getProperty("SendEmail");
			ccmail = sys_file.getProperty("Email.CCMail");
			clientUrl = sys_file.getProperty("Email.ClientURL");
			clientLogoUrl = sys_file.getProperty("Email.ClientLogoUrl");
			sRecipientMail = sys_file.getProperty("Email.RecipientMail");
			mailHost = sys_file.getProperty("Email.Host");
			mailPort = sys_file.getProperty("Email.Port");
			sRecipientList = sRecipientMail.split(",");
			sCCMail = sys_file.getProperty("Email.CCMail");
			sCCList = sCCMail.split(",");

		} catch (Exception e) {
			log.error("Some Exception thrown "+e.getMessage());
		}
        final Properties config = new Properties();
        config.put("mail.smtp.auth", "true");
        config.put("mail.smtp.starttls.enable", "true");
        config.put("mail.smtp.host", mailHost);
        config.put("mail.smtp.port", Integer.parseInt(mailPort));
        return config;
    }
    
    public static void main(String [] args) {
    	
    	GenerateHtmlAndSendEmailUpdated sendEmailOffice365 = new GenerateHtmlAndSendEmailUpdated();
    	sendEmailOffice365.sendEmail(messageContent);
    }
    
    
	/**
	 * This method is to generate the final html by integrating freemarker
	 * Template and model.
	 * 
	 */
	public String geFreeMarkerTemplateContent(Map<String, Object> model) {
		StringBuffer content = new StringBuffer();
		Configuration configuration = new Configuration(Configuration.VERSION_2_3_23);
		try {
			configuration.setDirectoryForTemplateLoading(new File(pathToTemplate));
			Template template = configuration.getTemplate("fmTemplate.txt");
			StringWriter writer = new StringWriter();

			template.process(template, writer);
			content.append(FreeMarkerTemplateUtils.processTemplateIntoString(template, model));
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		return content.toString();
	}
    
	/**
	 * This method is to configure the javaMailProperties to send mail.
	 * 
	 */
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost(mailHost);
		mailSender.setPort(Integer.parseInt(mailPort));
		mailSender.setUsername(senderMail);
		mailSender.setPassword(password);
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", "false");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void prepare(MimeMessage mimeMessage) throws Exception {
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

		helper.setSubject(mailSubject);
		helper.setTo(sRecipientList);
		helper.setCc(sCCList);

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("clientUrl", clientUrl);
		model.put("clientLogoUrl", clientLogoUrl);

		LinkedHashMap<String, List<String>> failedTestNames = new LinkedHashMap<String, List<String>>();
		String Tpass = "", Tfail = " ", Tskip = " ", Ttotal = " ", timestring = " ";

		// Loading the "overview.html" file to load results.
		File reader = new File(pathToOverview);
		Document doc = Jsoup.parse(reader, "UTF-8", "");

		// Assigning Time
		Element time = doc.getElementById("meta");
		int lastIndexOfYear = time.text()
				.lastIndexOf(Integer.toString(Calendar.getInstance().get(Calendar.YEAR)));
		timestring = time.text().substring(6, lastIndexOfYear + 4);
		model.put("time", timestring);

		// Loading TestNames
		Elements testnames = doc.getElementsByTag("a");
		ArrayList<String> testnameList = new ArrayList<String>();
		for (Element testname : testnames) {
			if (testname.text().equals("Groups")) {
			} else {
				testnameList.add(testname.text());
			}
		}

		// Loading TestCount
		ArrayList<String> countList = new ArrayList<String>();
		Elements count = doc.getElementsByAttributeValueEnding("class", "number");
		for (Element eachcount : count) {
			countList.add(eachcount.text());
		}

		// Loading Execution Time
		String duration = failures_file.getProperty("ExecutionTime");
		model.put("etime", duration);
		failures_file.remove("ExecutionTime");

		// Assigning Testcount(pass, skip, fail) to their respective
		// Testnames.
		LinkedHashMap<String, ArrayList> testsWithCount = new LinkedHashMap<String, ArrayList>();
		int j = 0, k = 0;
		for (int i = 0; i < testnameList.size(); i++) {
			ArrayList eachCountList = new ArrayList<String>();
			for (j = k; j < k + 3; j++) {
				eachCountList.add(countList.get(j));
			}
			k = j;
			testsWithCount.put(testnameList.get(i), eachCountList);
		}
		model.put("testsCount", testsWithCount);

		// Values to generate the PIECHART
		ArrayList totalCount = new ArrayList<String>();
		Tpass = countList.get(j);
		Tskip = countList.get(j + 1);
		Tfail = countList.get(j + 2);
		Ttotal = Integer.toString(Integer.parseInt(Tpass) + Integer.parseInt(Tfail) + Integer.parseInt(Tskip));
		totalCount.add(Tpass);
		totalCount.add(Tskip);
		totalCount.add(Tfail);
		model.put("totalCount", totalCount);
		model.put("Tpass", Tpass);
		model.put("Tfail", Tfail);
		model.put("Tskip", Tskip);
		model.put("Ttotal", Ttotal);

		// List out the Failed TestNames and attach to the mail body
		Set<Object> keys = failures_file.keySet();
		List sortedList = new ArrayList(keys);
		Collections.sort(sortedList);
		List list_for_no_of_failures = new ArrayList();
		for (int i = 0; i < testnameList.size(); i++) {
			String formatedKey = null, formatkey = "";
			int TCcounter = 0;
			String dataProvider = null;
			List<String> f = new ArrayList<String>();
			for (Object k1 : sortedList) {

				String key = (String) k1;
				if (failures_file.getProperty(key).equals(testnameList.get(i))) {
					formatedKey = key.substring(0, key.lastIndexOf("$$"));
					if (f.contains(formatedKey) || formatkey.equals(formatedKey)) {
						if (f.contains(formatedKey)) {
							f.remove(formatedKey);
							TCcounter = TCcounter + 1;
							dataProvider = formatedKey + "<html><font color=\"white\">----------</font></html>"
									+ "<html><font color=\"red\">Info: DataProvider Failures "
									+ TCcounter + "</font></html>";
							formatkey = formatedKey;
							f.add(dataProvider);
						}
						if (formatkey.equals(formatedKey)) {
							f.remove(dataProvider);
							TCcounter = TCcounter + 1;
							dataProvider = formatedKey + "<html><font color=\"white\">----------</font></html>"
									+ "<html><font color=\"red\">Info: DataProvider Failures "
									+ TCcounter + "</font></html>";
							formatkey = formatedKey;
							f.add(dataProvider);
						}
					} else {
						TCcounter = 0;
						f.add(formatedKey);
					}
					list_for_no_of_failures.add(i);
				}
			}
			if (f.size() > 0) {
				failedTestNames.put(testnameList.get(i), f);
			}
		}
		if (list_for_no_of_failures.size() > 0
				&& sys_file.getProperty("Display_failuresTC_in_emailbody").equalsIgnoreCase("true")) {
			list_for_no_of_failures.clear();
			list_for_no_of_failures.add("sample");
		} else {
			list_for_no_of_failures.clear();
		}
		model.put("failureCount", list_for_no_of_failures);
		model.put("failureSuites", failedTestNames);

		// Generating the html using fmtemplate and model
		String text = geFreeMarkerTemplateContent(model);
		
		for (Map.Entry<String, Object> entry : model.entrySet()) {
		    log.info(entry.getKey()+" : "+entry.getValue());
		}
		helper.setText(text, true);
		MimeMultipart multipart = new MimeMultipart();
		MimeBodyPart bodyPart = new MimeBodyPart();
		bodyPart.setText(text, "US-ASCII", "html");
		multipart.addBodyPart(bodyPart);
		
		// Attaching zip folder to body part
		if (sys_file.getProperty("Attach_zip_folder_to_emailbody").equalsIgnoreCase("true")) {
			GenerateZip obj = new GenerateZip();
			MimeBodyPart zPart = new MimeBodyPart();
			zPart.attachFile(obj.addZipAndImage());
			multipart.addBodyPart(zPart);
		}
		mimeMessage.setContent(multipart);
	}
    
}