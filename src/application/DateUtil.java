package application;

import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DateUtil {
    private static final String DATE_PATTERN = "dd.MM.yyyy";
   

    private static final DateTimeFormatter DATE_FORMATTER = 
            DateTimeFormatter.ofPattern(DATE_PATTERN);
    
    public static String format(LocalDate date) {
        if (date == null) {
            return null;
        }
        return DATE_FORMATTER.format(date);
    }
    
  
    public static LocalDate parse(String dateString) {
        try {
            return DATE_FORMATTER.parse(dateString, LocalDate::from);
        } catch (DateTimeParseException e) {
            return null;
        }
        
    }
    
    public static boolean isValidEmailAddress(String email) {
    	if(email == null) {
    		return false;
    	}else {
    	String emailFormat = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
    			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    	Pattern pattern = Pattern.compile(emailFormat);
    	Matcher matcher = pattern.matcher(email);
    	
    	return matcher.matches();
    	}
    }
    private static boolean alreadyFormat = false;
    public static boolean isValidPhone(String phone) {
    	if(phone == null) {
    		return false;
    	}else if(phone.matches("[0-9]+") && phone.length() == 10) {
    		alreadyFormat = false;
    		return true;
    	}else if(phone.length()==13
    			&&phone.charAt(0)=='(' 
    			&& phone.substring(1, 4).matches("[0-9]+")
    			&& phone.charAt(4)==')'
    			&& phone.substring(5,8).matches("[0-9]+")
    			&& phone.charAt(8)== '-'
    			&& phone.substring(9).matches("[0-9]+")){
    		alreadyFormat = true;
    		return true;
    		
    	}else {
    		
    		return false;
    	}
    }
    
    
    public static String parserPhone(String phone) {
    	if(!isValidPhone(phone) && !alreadyFormat) {
    		return "";
    	}else if(isValidPhone(phone) && !alreadyFormat){
    		String temp = phone;
    		temp = "(" +temp.substring(0, 3);
    		temp = temp + ")";
    		String temp1 = phone.substring(3, 6);
    		temp1 = temp1 + "-";
    		String temp2 = temp + temp1 +  phone.substring(6);
    		return temp2;
    	}else {
    		return phone;
    	}
    	
    }
    public static boolean validDate(String dateString) {
        return DateUtil.parse(dateString) != null;
    }
    public static boolean isValidUrl(String url)
    {
        try {
            new URL(url).toURI();
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }
    
}
