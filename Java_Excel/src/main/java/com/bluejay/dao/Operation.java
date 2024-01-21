package com.bluejay.dao;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class Operation {
	// excel file path Assignment_Timecard OfAG
	static String excelFilePath = "./excelData/Assignment_Timecard.xlsx";
	// object of the excel file workbook
	static XSSFWorkbook workbook;
	// object of particular sheet in the workbook
	static XSSFSheet sheet;
	// object for formatting any type of return type
	static DataFormatter formatter = new DataFormatter();
	// set of unique employee name
	static Set<String> emp = new LinkedHashSet<String>();
	//array of unique employee name
	static String[] empNames;
	//set of unique dates of single employee
	static Set<Integer> dates = new LinkedHashSet<Integer>();
	//array of unique dates of single employee
	static Integer[] empDates;
	//list of the employee for Problem 1
	static List<String> listOfEmp = new ArrayList<String>();
	//set of employee for Problem 2
	static Set<String> listofEmp2=new LinkedHashSet<String>();

	
	static {
		configurationExcel();
	}
	/**
	 * This method is used to create the configuration of the worksheet and return
	 * the reference of the sheet for various operation
	 * 
	 * try catch block:- used ,if the file is not found
	 */
	public static void configurationExcel() {
		try {
			workbook = new XSSFWorkbook(excelFilePath);
			sheet = workbook.getSheetAt(0);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Empty excel file");
		}
	}

//	public static void main(String[] args) {
//		configurationExcel();
//		// 1st problem
//		workingForSevenConsecutiveDays();
//		System.out.println();
//		// 2nd problem
//		timeBetweenShifts();
//		System.out.println();
//		// 3rd problem
//		shiftGreaterThan14();
//	}

	/**
	 * Problem number 1
	 */
	public static void workingForSevenConsecutiveDays() {
		employeeDetails();
		for (int i = 0; i <empNames.length; i++) {
			dateFormating(i);
			if (empDates.length >= 7) {
				for (int j = 0; j < empDates.length - 7; j++) {
					if (empDates[j] + 6 == empDates[j + 6]) {
						listOfEmp.add(empNames[i]);					//sheet.getRow(j).getCell(7).getStringCellValue()
						break;
					} else {
						continue;
					}
				}
			} else {
				continue;
			}
			dates.clear();
		}
		
		System.out.println("1 ] There are "+listOfEmp.size()+" employees working consecutive for 7 days : ");
		for (String s : listOfEmp) {
			System.out.println("Employee Position :"+employeeID(s)+"     "+"Employee name :"+s);
		}
		dates.clear();
		emp.clear();
	}
	
	/**
	 * Problem 2
	 */
	public static void timeBetweenShifts() {
		listofEmp2.clear();
		for (int i = 1; i <sheet.getPhysicalNumberOfRows()-1; i++) {		
			XSSFRow Row1=sheet.getRow(i);
			XSSFRow Row2=sheet.getRow(i+1);
			int timeOutDate=rowObjectTodate(Row1,3);
			int timeInDate=rowObjectTodate(Row2,2);
			int timeOutTime=rowObjectToTime(Row1, 3);
			int timeInTime=rowObjectToTime(Row2, 2);
			if(timeOutDate==timeInDate) {
				int timediff=timeInTime-timeOutTime;
				if(timediff>1 && timediff<10) {
					listofEmp2.add(Row1.getCell(7).getStringCellValue());
				}
			}
		}
		System.out.println("2 ] There are "+listofEmp2.size()+" employees who have less than 10 hours of time between shifts but greater than 1 hour : ");
		for(String row:listofEmp2) {
			System.out.println("Employee Position :"+employeeID(row)+"     "+"Employee name :"+row);
		}
	}
	
	/**
	 * Problem 3
	 */
	public static void shiftGreaterThan14() {
		System.out.println("3 ] Who has worked for more than 14 hours in a single shift ");
		employeeDetails();
		for(int i=0;i<empNames.length;i++) {
			for(int j=1;j<sheet.getPhysicalNumberOfRows();j++) {
				if(sheet.getRow(j).getCell(7).getStringCellValue().equals(empNames[i]) && !(sheet.getRow(j).getCell(7).getStringCellValue().equals("")) ) {
					String shiftHours=sheet.getRow(j).getCell(4).getStringCellValue();
					if (shiftHours.equals("")) {
						continue;
					} else {
						String resHour[]=shiftHours.split(":");
						if(Integer.parseInt(resHour[0])>=14) {
							System.out.println("Employe Position :"+sheet.getRow(j).getCell(0).getStringCellValue()+"     "+"Employee name :"+sheet.getRow(j).getCell(7).getStringCellValue());
						}
					}
				}
			}
		}
		emp.clear();
	}

	
	
	/**
	 * Used to add all the employee name uniquely to emp Set uniqely
	 */
	public static void employeeDetails() {
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			String employee_Names = formatter.formatCellValue(sheet.getRow(i).getCell(7));
			emp.add(employee_Names);
		}
		empNames = emp.toArray(new String[0]);
	}
	/**
	 * Used to order the set of days a single employee has been working
	 * @param employeeID is the index of the employee in emplNames set
	 */
	public static void dateFormating(int employeeID) {
		for (int i = 1; i < sheet.getPhysicalNumberOfRows(); i++) {
			if ((sheet.getRow(i).getCell(7).getStringCellValue()).equals(empNames[employeeID])) {
				String val = formatter.formatCellValue(sheet.getRow(i).getCell(2));
				if (val.length() > 5) {
					dates.add(Integer.parseInt(val.substring(3, 5)));
				} else {
					continue;
				}
			}
		}
		empDates = dates.toArray(new Integer[0]);
	}
	/**
	 * used to return the emp ID by emp Name
	 * @param empName for returning the particular id
	 * @return the id of the employee
	 */
	public static String employeeID(String empName) {
		String empID="";
		for (int i = 1; i <sheet.getPhysicalNumberOfRows(); i++) {
			if(sheet.getRow(i).getCell(7).getStringCellValue().equals(empName) && !(sheet.getRow(i).getCell(7).getStringCellValue().equals("")) ) {
				empID=sheet.getRow(i).getCell(0).getStringCellValue();
				break;
			}
		}
		return empID;
	}
	/**
	 * Used to return the date of a particular column
	 * @param row 
	 * @param ColumnIndex
	 * @return date
	 */
	public static int rowObjectTodate(XSSFRow row,int ColumnIndex) {
		int res=0;
		String s=formatter.formatCellValue(row.getCell(ColumnIndex));
		if (s.length() > 5) {
			res=(Integer.parseInt(s.substring(3, 5)));
		}
		return res;
	}
	/**
	 * Used to return the time of a particular column
	 * @param row
	 * @param ColumnIndex
	 * @return time
	 */
	public static int rowObjectToTime(XSSFRow row,int ColumnIndex) {
		int res=0;
		String s=formatter.formatCellValue(row.getCell(ColumnIndex));
		if (s.length() > 5) {
			res=(Integer.parseInt(s.substring(11, 13)));
		}
		return res;
	}
}
