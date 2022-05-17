# java-I18n-l10n-features
Documentation on findings regarding Internationalization (I18n) and Localization (L10n) features provided by Java.

Internationalization (**I18n**) is the process of designing a software application so that it can be adapted to various languages and regions without engineering changes. Localization (**L10n**) is the process of adapting internationalized software for a specific region or language by translating text and adding locale-specific components. Localization (which is potentially performed multiple times, for different locales) uses the infrastructure or flexibility provided by internationalization (which is ideally performed only once before localization, or as an integral part of ongoing development). The process is depicted on the figure below.

![The I18n and L10n process](https://phrase.com/blog/content/uploads/2016/09/globalization-2.png)

An internationalized program has the following characteristics:
- With the addition of localized data, the same executable can run worldwide. Meaning, an executable can run in a global market with the addition of localized data.
- Textual elements, such as status messages and the GUI component labels, are not hardcoded in the program. Instead they are stored outside the source code and retrieved dynamically.
- Support for new languages does not require recompilation.
- Culturally-dependent data, such as dates and currencies, appear in formats that conform to the end user's region and language.
- It can be localized quickly. 

## I18n and l10n features in Java
Java provides various built in classes such as `Locale`, `ResourceBundle` to achieve localization by having,
- Language Support
- Date & Time
- Number & Currencies


### Initializing `Locale` Class for Localization
Locale class is used to achieve localization by setting the language and region. An object of the Locale class can be initialized as shown below. This object is then used to format and fetch localized content.

```java
Locale locale = new Locale.Builder()
  .setLanguage("fr")
  .setRegion("CA")
  .setVariant("POSIX")
  .setScript("Latn")
  .build();
```

- List of supported language codes: http://www.loc.gov/standards/iso639-2/php/code_list.php
- List of supported region codes: https://en.wikipedia.org/wiki/ISO_3166-1_alpha-2


### Language Support
Language support is given by showing region specific language in the application. This can be achieved using the `ResourceBundle` class with a Properties file. The `ResourceBundle` class enables our application to load data from distinct files containing locale-specific data.

The .properties files need to be provided for each specific supported region and language. The format for naming of the .properties file is `bundleFileName_languageCode_regionCode.properties`. The content of the files would contain key value pairs.

Localization code example:

```Java
Locale locale = new Locale("bn", "BD");
ResourceBundle resourceBundle = ResourceBundle.getBundle("packageName.exampleResourceBundle", locale);

String localizedMessage1 = resourceBundle.getString("localizedMessageKey1");
String localizedMessage2 = resourceBundle.getString("localizedMessageKey2");
```

Inside `exampleResourceBundle_bn_BD.properties`:
```
localizedMessageKey1 = localized message 1
localizedMessageKey2 = localized message 2
```

### Date & Time

Using the LocalDateTime class and the DateTimeFormatter dates can be formatted to achieve localization as shown below,

```java
Locale locale = new Locale("bn", "BD");
		
LocalDateTime localDateTime = LocalDateTime.of(2018, 1, 1, 10, 15, 50);
String pattern = "dd-MMMM-yyyy HH:mm:ss.SSS";

DateTimeFormatter dateTimeFormatter = DateTimeFormatter
	.ofPattern(pattern, locale)
	.withDecimalStyle(DecimalStyle.of(locale));
		
String date = dateTimeFormatter.format(localDateTime);

System.out.println(date);
```

Prints out,
```
০১-জানুয়ারী-২০১৮ ১০:১৫:৫০.০০০
```

The following code shows how to achieve localization using Date and SimpleDateFormat classes,

```java
Locale locale = new Locale.Builder().setLanguage("bn").setRegion("BD")
	.build();

GregorianCalendar gregorianCalendar = new GregorianCalendar(2018, 0, 1,
	10, 15, 20);
Date date = gregorianCalendar.getTime();

DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.FULL,
	DateFormat.MEDIUM, locale);

String dateString = dateFormat.format(date);

System.out.println(dateString);
```

Prints out,
```
সোমবার, ১ জানুয়ারী, ২০১৮ ১০:১৫:২০ AM
```

### Currency Formatting
The following code shows how to achieve localization for showing currency,

```java
Locale locale = new Locale.Builder().setLanguage("bn").setRegion("BD")
	.build();

Double currencyAmount = 9876543.21;
Currency currentCurrency = Currency.getInstance(locale);
NumberFormat currencyFormatter = NumberFormat
	.getCurrencyInstance(locale);

System.out.println(currencyFormatter.format(currencyAmount) + " (in " + currentCurrency.getDisplayName() + ")");
```
Prints out,
```
৯,৮৭৬,৫৪৩.২১৳ (in Bangladeshi Taka)
```

### Numbers Formatting 
The following code shows how to achieve localized number formatting. The example is given for language set to "fr" (french) and region set to "FR" (France).

```java
Locale locale = new Locale.Builder().setLanguage("fr").setRegion("FR")
				.build();

Integer integerNumber = 123456;
Double doubleNumber = 345987.246;

NumberFormat numberFormatter = NumberFormat.getNumberInstance(locale);

String localizedIntegerNumber = numberFormatter.format(integerNumber);
String localizedDoubleNumber = numberFormatter.format(doubleNumber);

System.out.println("Integer Number: " + localizedIntegerNumber
	+ " \nDouble Number: " + localizedDoubleNumber);
```

Prints out,
```
Integer Number: 123 456 
Double Number: 345 987,246
```

### Dealing with Compound Messages
A  *compound message*  contains variable data. In the following list of compound messages, the variable data is underlined:

- The disk named [*MyDisk*] contains [*300*] files.
- The current balance of your account is [*$2,745.72*].
- [*405,390*] people have visited your website since [*January 1, 2009*].

Compound messages may contain different types of variables: dates, times, strings, numbers, currencies, etc. To format a compound message in a locale-independent manner, you construct a pattern that you apply to a `MessageFormat` object, and store this pattern in a `ResourceBundle`. A sample is given below,

Suppose that you want to internationalize by having support for both Bangla (Bangladesh) and English (US) of the following message:

```
At 1:15 on April 13, 1998 we detected 7 spaceships on the planet Mars.
```

First we need to isolate the message in a `ResourceBundle` named `MessageBundle`, which means that we need to provide `MessageBundle_en_US.properties` and `MessageBundle_bn_BD.properties` files.

Content inside the `MessageBundle_en_US.properties` file:
```
template = At {2,time,short} on {2,date,long}, we detected {1,number,integer} spaceships on the planet {0}.

planet = Mars
```

Content inside the `MessageBundle_bn_BD.properties` file:
```
template = {2,time,short} বাজে,{2,date,long} তারিখে আমরা {0} গ্রহে {1,number,integer} টি মহাকাশযান দেখেছি।

planet = মঙ্গল
```

The .properties file contains two key-value pairs. The first one is a template for the compound message with arguments enclosed in braces ('{...}'). Explanation of the arguments are given below.
| Argument in template | Explanation |
|--|--|
| `{2,time,short}` | The time portion of a `Date` object at index 2. The `short` style specifies the `DateFormat.SHORT` formatting style. |
| `{2,date,long}` | The date portion of a `Date` object at index 2. The same `Date` object is used for both the date and time variables. In the `Object` array of arguments the index of the element holding the `Date` object is 2. (This is described in the next step.) |
| `{1,number,integer}` | A `Number` object at index 1, further qualified with the `integer` number style. |
| `{0}` | The `String` at index 0. |

The index mentioned in the above table corresponds to the values inside the `messageArguments` variable shown in the code segment from below.  For a full description of the argument syntax and usage of the styling keywords: `date`, `time`, `number` and formatting keywords: `short`, `long`, `integer`, see the API documentation for the [`MessageFormat`](https://docs.oracle.com/javase/8/docs/api/java/text/MessageFormat.html) class.

The Java code to show the localized compound message using `Locale`, `ResourceBundle` and the `MessageFormat` class is given below,

```java
public class Main{

	public static void showLocalizedCompoundMessage(Locale locale) {

		// get the ResourceBundle to get contest from locale specific .properties file
		ResourceBundle messages = ResourceBundle.getBundle("java_testing.MessageBundle",
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
	
	public static void main(String[] args) {
		
		Locale locale;
		
		locale = new Locale.Builder().setLanguage("en").setRegion("US")
				.build();
		showLocalizedCompoundMessage(locale);
		
		locale = new Locale.Builder().setLanguage("bn").setRegion("BD")
				.build();
		showLocalizedCompoundMessage(locale);
	}

}
```

Prints out,
```
At 12:53 PM on May 17, 2022, we detected 7 spaceships on the planet Mars.
১২:৫৩ PM বাজে, ১৭ মে, ২০২২ তারিখে আমরা মঙ্গল গ্রহে ৭ টি মহাকাশযান দেখেছি।
```

## References
- https://docs.oracle.com/javase/tutorial/i18n/TOC.html
- https://phrase.com/blog/posts/a-beginners-guide-to-java-internationalization/
- https://www.baeldung.com/java-8-localization
