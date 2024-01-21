package com.bluejay.controller;

import java.util.Scanner;

import com.bluejay.dao.Operation;

public class MainController {
static Scanner scan=new Scanner(System.in);
	
	public static void main(String[] args) {
		System.out.println("Enter the option for the below questions :");
		System.out.println("1) who has worked for 7 consecutive days.");
		System.out.println("2) who have less than 10 hours of time between shifts but greater than 1 hour");
		System.out.println("3) Who has worked for more than 14 hours in a single shift");
		System.out.println("Enter the key :");
		int key=scan.nextInt();
		switch (key) {
		case 1:
			// 1st problem
			Operation.workingForSevenConsecutiveDays();
			break;
		case 2:
			// 2nd problem
			Operation.timeBetweenShifts();;
			break;
		case 3:
			// 3rd problem
			Operation.shiftGreaterThan14();
			break;

		default:
			System.out.println("Invalid Operation / key");
			break;
		}

	}

}
