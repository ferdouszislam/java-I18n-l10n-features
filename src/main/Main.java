package main;

import java.text.DateFormat;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DecimalStyle;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.ResourceBundle;

public class Main {

	public static void showLocalizedLanguageGreeting(Locale locale) {
		
		ResourceBundle resourceBundle = ResourceBundle.getBundle("main.exampleResourceBundle", locale);
		
		System.out.println(resourceBundle.getString("greeting"));
	}
	
	public static void showLocalizedDateTimeFormattingUsingLocalDateTime(Locale locale) {
		
		LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 15, 50);
		String pattern = "dd-MMMM-yyyy HH:mm:ss.SSS";

		DateTimeFormatter dateTimeFormatter = DateTimeFormatter
				.ofPattern(pattern, locale)
				.withDecimalStyle(DecimalStyle.of(locale));
		
		String date = dateTimeFormatter.format(localDateTime);
		System.out.println(date);
	}
	
	public static void showLocalizedDateTimeFormattingUsingDate(Locale locale) {
		
		GregorianCalendar gregorianCalendar = new GregorianCalendar(2018, 0, 1,
				10, 15, 20);
		Date date = gregorianCalendar.getTime();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL,
				DateFormat.MEDIUM, locale);

		String dateString = dateFormat.format(date);
		System.out.println(dateString);
	}
	
	public static void showLocalizedNumberFormatting(Locale locale) {

		Integer integerNumber = 123456;
		Double doubleNumber = 345987.246;

		NumberFormat numberFormatter = NumberFormat.getNumberInstance(locale);

		String localizedIntegerNumber = numberFormatter.format(integerNumber);
		String localizedDoubleNumber = numberFormatter.format(doubleNumber);

		System.out.println(localizedIntegerNumber + " (integer)\n"
				+ localizedDoubleNumber + " (double)");
	}
	
	public static void showLocalizedCurrencyFormatting(Locale locale) {

		Double currencyAmount = 9876543.21;
		Currency currentCurrency = Currency.getInstance(locale);
		NumberFormat currencyFormatter = NumberFormat
				.getCurrencyInstance(locale);

		System.out.println(currencyFormatter.format(currencyAmount) + " (in " + currentCurrency.getDisplayName() + ")");
	}
	
	public static void showLocalizedCompoundMessage(Locale locale) {

		// get the ResourceBundle to get contest from locale specific .properties file
		ResourceBundle messages = ResourceBundle.getBundle("main.MessageBundle",
				locale);

		// set the arguments for the compound message to be shown
		Object[] messageArguments = {
			messages.getString("planet"),
			new Integer(7), 
			new Date()
		};

		// format the compound message using the 'locale' and 'messageArguments' object
		MessageFormat formatter = new MessageFormat("");
		formatter.setLocale(locale);
		formatter.applyPattern(messages.getString("template"));
		String messageToShow = formatter.format(messageArguments);

		System.out.println(messageToShow);
	}
	
	public static void  testLocalization(Locale locale) {

		System.out.println("<---------------------START--------------------->\n");
		System.out.println("Selected locale: " + locale.toString());
		
		showLocalizedLanguageGreeting(locale);
		
		showLocalizedDateTimeFormattingUsingLocalDateTime(locale);
		showLocalizedDateTimeFormattingUsingDate(locale);
		
		showLocalizedNumberFormatting(locale);
		
		showLocalizedCurrencyFormatting(locale);
		
		showLocalizedCompoundMessage(locale);
		
		System.out.println("\n");
	}
	
	
	public static void main(String[] args) {

		Locale locale;
		locale = new Locale.Builder().setLanguage("en").setRegion("US").build();
		testLocalization(locale);

		locale = new Locale.Builder().setLanguage("bn").setRegion("BD").build();
		testLocalization(locale);
	}
	
}
