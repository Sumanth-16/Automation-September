/* ******************************[[ Developed By Phani, Farheen and Vamshi ]]*****************************************************/
package jvm;

import freemarker.template.Configuration;
import freemarker.template.Template;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.io.FileInputStream;
import java.io.StringWriter;
import java.util.*;

public class GenerateHtmlAndSendMail {

	@Autowired
	JavaMailSender mailSender;

	@Autowired
	Configuration freemarkerConfiguration;

	private Properties failures_file = new Properties();
	private static Properties sys_file = new Properties();
	private  static String fileSeparator = System.getProperty("file.separator");
	private String senderMail, recipientMail, mailSubject, password, ccmail, clientUrl, clientLogoUrl;
	private static String sendmail;
	private static String sRecipientMail;
	private static String[] sRecipientList;
	private static String sCCMail;
	private static String[] sCCList;

	private static String pathToTemplate = System.getProperty("user.dir") + fileSeparator + "Resources" + fileSeparator
			+ "fmtemplates";
	private static String pathToOverview = System.getProperty("user.dir") + fileSeparator + "Automation Reports" + fileSeparator
			+ "LatestResults" + fileSeparator + "html" + fileSeparator + "overview.html";
	private static String pathToFailures = System.getProperty("user.dir") + fileSeparator + "ConfigFiles" + fileSeparator
			+ "FailedTestCases.properties";
	private static String pathToSys = System.getProperty("user.dir") + fileSeparator + "ConfigFiles" + fileSeparator
			+ "Sys.properties";
	private static long e;

	public static void main(String[] args) {
		GenerateHtmlAndSendMail obj = new GenerateHtmlAndSendMail();
		obj.sendEmail();

	}

	/**
	 * This method is to load Properties for data.
	 * 
	 */
	private void loadProperties() {
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

			sRecipientList = sRecipientMail.split(",");
			sCCMail = sys_file.getProperty("Email.CCMail");
			sCCList = sCCMail.split(",");

		} catch (Exception e) {
			System.out.print("Some Exception thrown ");
		}
	}

	/**
	 * This method is to configure the javaMailProperties to send mail.
	 * 
	 */
	public JavaMailSender getMailSender() {
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(465);
		mailSender.setUsername(senderMail);
		mailSender.setPassword(password);
		Properties javaMailProperties = new Properties();
		javaMailProperties.put("mail.smtp.auth", "true");
		javaMailProperties.put("mail.transport.protocol", "smtp");
		javaMailProperties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		mailSender.setJavaMailProperties(javaMailProperties);
		return mailSender;
	}

	/**
	 * This method is to send the mail with html content.
	 */
	public void sendEmail() {
		loadProperties();
		if (sendmail.equalsIgnoreCase("true")) {
			mailSender=getMailSender();
			try {
				mailSender.send(getMessagePreparator());
				System.out.println("Mail has been sent.....");
			} catch (MailException ex) {
				System.err.println(ex.getMessage());
			}
		}
	}

	/**
	 * This method is to create the html content using freemarker Template.
	 */
	private MimeMessagePreparator getMessagePreparator() {
		MimeMessagePreparator preparator = new MimeMessagePreparator() {
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
											+ Integer.toString(TCcounter) + "</font></html>";
									formatkey = formatedKey;
									f.add(dataProvider);
								}
								if (formatkey.equals(formatedKey)) {
									f.remove(dataProvider);
									TCcounter = TCcounter + 1;
									dataProvider = formatedKey + "<html><font color=\"white\">----------</font></html>"
											+ "<html><font color=\"red\">Info: DataProvider Failures "
											+ Integer.toString(TCcounter) + "</font></html>";
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
		};
		return preparator;
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
}
