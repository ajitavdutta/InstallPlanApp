package com.app.util;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.app.models.InstallPlan;
import com.app.models.ObjectList;

import javafx.collections.ObservableList;

public class AppUtility {

	// Pattern to match the Tandem file name in the format
	// \<SYSTEM>.$<VOL>.<SUB-VOL>.<FILE>
	private static Pattern filepattern1 = Pattern
			.compile("(\\\\[A-Za-z0-9]{1,5}).(\\$[A-Za-z0-9]{1,7}).([A-Za-z0-9]{1,8}).([A-Za-z0-9]{1,8})");

	// Pattern to match the Tandem file name in the format
	// $<VOL>.<SUB-VOL>.<FILE>
	private static Pattern filepattern2 = Pattern
			.compile("(\\$[A-Za-z0-9]{1,7}).([A-Za-z0-9]{1,8}).([A-Za-z0-9]{1,8})");

	// Pattern to match the Tandem file name in the format <SUB-VOL>.<FILE>
	private static Pattern filepattern3 = Pattern.compile("([A-Za-z0-9]{1,8}).([A-Za-z0-9]{1,8})");

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
	 * Validates a file name string and if valid will return the file name from
	 * the string.
	 * 
	 * @param file
	 *            - File name string ([\SYSTEM.][$VOLUME.]SUB-VOL.FILENAME)
	 * @return name - File name
	 */
	public static String getFileName(String file) {
		String name = null;
		Matcher matcher;

		file = file.trim().toUpperCase();

		switch (AppUtility.isValidTandemFileName(file)) {
		case 1:
			matcher = filepattern1.matcher(file);
			matcher.matches();
			name = matcher.group(4);
			break;

		case 2:
			matcher = filepattern2.matcher(file);
			matcher.matches();
			name = matcher.group(3);
			break;

		case 3:
			matcher = filepattern3.matcher(file);
			matcher.matches();
			name = matcher.group(2);
			break;

		default:
			break;
		}

		return name;
	}

	public static String getSubVolume(String filename) {
		String subVol = null;
		Matcher matcher;

		filename = filename.trim().toUpperCase();

		switch (AppUtility.isValidTandemFileName(filename)) {
		case 1:
			matcher = filepattern1.matcher(filename);
			matcher.matches();
			subVol = matcher.group(3);
			break;

		case 2:
			matcher = filepattern2.matcher(filename);
			matcher.matches();
			subVol = matcher.group(2);
			break;

		case 3:
			matcher = filepattern3.matcher(filename);
			matcher.matches();
			subVol = matcher.group(1);
			break;

		default:
			break;
		}
		return subVol;
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

	public static String createFUPCommands(InstallPlan plan, String devSys) {
		StringBuilder sb = new StringBuilder();
		ObservableList<ObjectList> objects = plan.getObjList();
		ArrayList<ObjectList> rel5Objects = new ArrayList<>();
		ArrayList<ObjectList> rel6Objects = new ArrayList<>();

		objects.forEach(item -> {
			if (item.getRel().equals("5")) {
				rel5Objects.add(item);
			} else if (item.getRel().equals("6")) {
				rel6Objects.add(item);
			}
		});

		if (!rel5Objects.isEmpty()) {
			rel5Objects.forEach(item -> {
				if (AppUtility.isValidTandemFileName(item.getName()) == 2) {
					item.setName(devSys + "." + item.getName());
				}
			});

			sb = sb.append("\n\nFILEINFO " + devSys + ".$SOURCE.N5" + plan.getPrimeCodeRef() + ".*" + "\nFILEINFO "
					+ plan.getProdSys() + ".$DATA01.N5" + plan.getPrimeCodeRef() + ".*");

			for (ObjectList item : rel5Objects) {
				sb = sb.append("\nFUP DUP " + item.getName() + ", " + plan.getProdSys() + ".$DATA01.N5"
						+ plan.getPrimeCodeRef() + ".*, SOURCEDATE");
			}

			sb = sb.append("\nFUP SECURE " + plan.getProdSys() + ".N5" + plan.getPrimeCodeRef() + ".*, "
					+ AppUtility.getProperty("PROD-RWEP"));

			sb = sb.append("\nFUP GIVE " + plan.getProdSys() + ".N5" + plan.getPrimeCodeRef() + ".*, "
					+ AppUtility.getProperty("PROD-OBJ-USER"));
		}

		if (!rel6Objects.isEmpty()) {
			rel6Objects.forEach(item -> {
				if (AppUtility.isValidTandemFileName(item.getName()) == 2) {
					item.setName(devSys + "." + item.getName());
				}
			});

			sb = sb.append("\n\nFILEINFO " + devSys + ".$SOURCE.N6" + plan.getPrimeCodeRef() + ".*" + "\nFILEINFO "
					+ plan.getProdSys() + ".$DATA01.N6" + plan.getPrimeCodeRef() + ".*");

			for (ObjectList item : rel6Objects) {
				sb = sb.append("\nFUP DUP " + item.getName() + ", " + plan.getProdSys() + ".$DATA01.N6"
						+ plan.getPrimeCodeRef() + ".*, SOURCEDATE");
			}

			sb = sb.append("\nFUP SECURE " + plan.getProdSys() + ".N6" + plan.getPrimeCodeRef() + ".*, "
					+ AppUtility.getProperty("PROD-RWEP"));

			sb = sb.append("\nFUP GIVE " + plan.getProdSys() + ".N6" + plan.getPrimeCodeRef() + ".*, "
					+ AppUtility.getProperty("PROD-OBJ-USER"));
		}

		return sb.toString();
	}
	
	public static void loadInstallPlan(InstallPlan oldPlan, InstallPlan newPlan){
		oldPlan.setSdmNumber(newPlan.getSdmNumber());
		oldPlan.setInstallDate(newPlan.getInstallDate());
		oldPlan.setInstallTime(newPlan.getInstallTime());
		oldPlan.setSdmSummary(newPlan.getSdmSummary());
		oldPlan.setWorkRequest(newPlan.getWorkRequest());
		oldPlan.setPrimeCodeRef(newPlan.getPrimeCodeRef());
		oldPlan.setProdSys(newPlan.getProdSys());
		oldPlan.setDrSys(newPlan.getDrSys());
		newPlan.getRollFileList().forEach(item -> newPlan.getRollFileList().add(item));
		newPlan.getObjList().forEach(item -> oldPlan.getObjList().add(item));
		oldPlan.setPriorToInstall(newPlan.getPriorToInstall());
	}

}
