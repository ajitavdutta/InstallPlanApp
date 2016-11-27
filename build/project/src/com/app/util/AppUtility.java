package com.app.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.app.models.ApplicationInfo;
import com.app.models.InstallConfig;
import com.app.models.PrimeCodeObjects;
import com.app.models.Session;
import com.app.models.TandemObject;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class AppUtility {

	// Pattern to match the Tandem file name in the format
	// \<SYSTEM>.$<VOL>.<SUB-VOL>.<FILE>
	private static Pattern filepattern1 = Pattern
			.compile("(\\\\[A-Za-z0-9]{1,5}.)(\\$[A-Za-z0-9]{1,7}.)([A-Za-z0-9]{1,8}.)([A-Za-z0-9]{1,8})");

	// Pattern to match the Tandem file name in the format
	// $<VOL>.<SUB-VOL>.<FILE>
	private static Pattern filepattern2 = Pattern
			.compile("(\\$[A-Za-z0-9]{1,7}.)([A-Za-z0-9]{1,8}.)([A-Za-z0-9]{1,8})");

	// Pattern to match the Tandem file name in the format <SUB-VOL>.<FILE>
	private static Pattern filepattern3 = Pattern.compile("([A-Za-z0-9]{1,8}.)([A-Za-z0-9]{1,8})");

	// Application config.properties file
	private static String configFile = "config\\app-config.properties";

	/**
	 * Attempts to match the string to specific Tandem File name pattern. If it
	 * matched any of the Tandem File name pattern it will return the file
	 * format i.e. 1 - \<SYSTEM>.$<VOL>.<SUB-VOL>.<FILE> 2 - $<VOL>.<SUB-VOL>.
	 * <FILE> 3 - <SUB-VOL>.<FILE>
	 * 
	 * If it does not matches any pattern then it will return 0.
	 * 
	 * @param file
	 *            - Name of the file
	 * @return frmt - File format
	 */
	public static int isValidTandemFileName(String file) {
		Matcher matcher = filepattern1.matcher(file);
		if (matcher.matches()) {
			return 1;
		}

		matcher = filepattern2.matcher(file);
		if (matcher.matches()) {
			return 2;
		}

		matcher = filepattern3.matcher(file);
		if (matcher.matches()) {
			return 3;
		}

		return 0;
	}

	/**
	 * Extracts the Tandem file name from the Tandem file path. It assumes that
	 * the file path has already been validated and at least sub-volume and the
	 * file-name is present in the path.
	 * 
	 * @param path
	 *            - File Path in Tandem ( [\SYSTEM.][$VOLUME.]SUB-VOL.FILENAME )
	 * 
	 * @return File name from the path.
	 */
	public static String getFileName(String path) {
		String[] parts = path.split("\\.");
		return parts[parts.length - 1];
	}

	/**
	 * Extracts the Tandem sub-volume name from the Tandem file path. It assumes
	 * that the file path has already been validated and at least sub-volume and
	 * the file-name is present in the path.
	 * 
	 * @param path
	 *            - File Path in Tandem ( [\SYSTEM.][$VOLUME.]SUB-VOL.FILENAME )
	 * @return Sub-Volume name from the Tandem File Path.
	 */
	public static String getSubVolume(String path) {
		String[] parts = path.split("\\.");
		return parts[parts.length - 2];
	}

	public static String getSubVolFileName(String filename) {
		String val = null;
		Matcher matcher;

		filename = filename.trim().toUpperCase();

		switch (AppUtility.isValidTandemFileName(filename)) {
		case 1:
			matcher = filepattern1.matcher(filename);
			matcher.matches();
			val = matcher.group(3) + "." + matcher.group(4);
			break;

		case 2:
			matcher = filepattern2.matcher(filename);
			matcher.matches();
			val = matcher.group(2) + "." + matcher.group(3);
			break;

		default:
			break;
		}
		return val;
	}

	/**
	 * Replaces multiple instance of the new line character with a single new
	 * line character.
	 * 
	 * @param txt
	 *            - Input Text
	 * @return frmtTxt - Formatted Output Text.
	 * 
	 */
	public static String removeMultipleNewLine(String txt) {
		String frmtTxt = txt.trim();
		frmtTxt = frmtTxt.replaceAll("(\r?\n)+", System.getProperty("line.separator"));
		frmtTxt = frmtTxt.replaceAll("( )+", System.getProperty("line.separator"));
		return frmtTxt;
	}

	/**
	 * Returns the value of the config ID by reading the app-config.properties
	 * file.
	 * 
	 * @param id
	 *            - Config ID
	 * @return val - Value of the config ID present in the config file.
	 */
	public static String getProperty(String id) {
		String val = null;
		Properties prop = new Properties();
		InputStream input = null;

		try {
			input = new FileInputStream(configFile);
			prop.load(input);

			if (prop.getProperty(id) != null) {
				val = prop.getProperty(id);
			}
			return val;
		} catch (Exception e) {
			e.printStackTrace();
			return val;
		}
	}

	public static ZonedDateTime convertASTtoIST(LocalDateTime ldt) {
		ZonedDateTime currZDT = ldt.atZone(ZoneId.of("Australia/Sydney"));
		ZonedDateTime convZDT = currZDT.withZoneSameInstant(ZoneId.of("Asia/Kolkata"));
		return convZDT;
	}

	public static TandemObject searchObject(ObservableList<TandemObject> mainList, String object, int version) {
		for (TandemObject obj : mainList) {
			if (obj.getName().trim().equalsIgnoreCase(object) && obj.getVersion() == version) {
				return obj;
			}
		}

		return null;
	}
	
	public static ObservableList<TandemObject> loadPrimeCodeDB(String filename) throws JAXBException{
		
		if (filename == null) {
			return null;
		}
		
		File file = new File(filename);
		if ( !file.exists()){
			return null;
		}
		
		JAXBContext jaxbContext = JAXBContext.newInstance(PrimeCodeObjects.class);
		Unmarshaller unMarshaller = jaxbContext.createUnmarshaller();
		PrimeCodeObjects objs = (PrimeCodeObjects) unMarshaller.unmarshal(file);
		
		return objs.getObjects();
	}

	public static void loadSystemConfig(Session session) {
		try {
			ApplicationInfo appInfo = session.getAppInfo();
			InstallConfig installConfig = session.getInstallConfig();

			// Open and load the Application Config properties file.
			Properties prop = new Properties();
			InputStream input = new FileInputStream("config/app-config.properties");
			prop.load(input);

			// Load the Session
			session.setUserName(System.getProperty("user.name"));
			//appInfo.setParentObj(parent);
			//appInfo.setParentStage(stage);
			appInfo.setPCXmlFile(prop.getProperty("XML-PRIMECODE"));
			appInfo.setProducts(FXCollections.observableArrayList(prop.getProperty("B24-PRODUCTS").split(",")));

			installConfig.setDevSys(prop.getProperty("DEV-SYS"));
			installConfig.setProdSys(prop.getProperty("PROD-SYS"));
			installConfig.setDrSys(prop.getProperty("DR-SYS"));
			//installConfig.setFUPCmdFile(prop.getProperty("FUP-OUT-FILE"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
