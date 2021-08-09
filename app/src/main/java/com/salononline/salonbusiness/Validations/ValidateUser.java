package com.salononline.salonbusiness.Validations;

import android.util.Patterns;

import java.util.HashMap;
import java.util.Map;

public class ValidateUser {
    public static String validatePhone(String phone){
        if(phone.isEmpty()){
            return "Field can't be empty";
        }
        else if( phone.length() != 10){
            return "Phone Number should contain 10 digits";
        }
        else if (Integer.parseInt(String.valueOf(phone.charAt(0)))<6 ){
            return "Please enter valid phone number";
        }
        return "";

    }

    public static String convertToDate(String date){
        String[] arr,new_arr;
        arr = date.split(":", 2);
        new_arr = arr[1].split(" ", 2);
        if (new_arr[1].equals("PM")) {
            arr[0] =String.valueOf(Integer.parseInt(arr[0])+ 12);
        }
        if (arr[0].length() == 1) {
            arr[0] = "0" + arr[0];
        }
        if (new_arr[0].length() == 1) {
            new_arr[0] = "0" + new_arr[0];
        }
        date= arr[0] + ":" + new_arr[0] + ":00";
        return date;
    }
    public static String validatePinCode(String pincode){
        if(pincode.isEmpty()){
            return "Field can't be empty";
        }
        else if( pincode.length() != 6){
            return "Pin Code should contain 6 digits";
        }

        return "";

    }
    public static String validateStreet(String Street){
        if(Street.isEmpty()){
            return "Field can't be empty";
        }
        return "";
    }
    public static String validateLocality(String locality){
        if(locality.isEmpty()){
            return "Field can't be empty";
        }
        return "";
    }
    public static String validateSalonName(String salonName){
        if(salonName.isEmpty()){
            return "Field can't be empty";
        }
        return "";
    }
    public static String validateEmail(String email){
        if(email.isEmpty()){
            return "Field can't be empty";
        }
        else if(!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            return "Enter valid Email Address";
        }
        return "";
    }
    public static String validateFullName(String name){
        if (name.length()>0 && name.length() <= 60){
            int space=0;
            int alpha=0;
            for(int i=0;i<name.length();i++){
                if (Character.isAlphabetic(name.charAt(i))){
                    alpha+=1;
                }
                else if(name.charAt(i)==' '){
                    space+=1;
                }
            }
            if ((alpha+space)!=name.length()){
                return "fullname consists of alphabets and spaces only";
            }
        }
        else {
            return "Field can't be empty";
        }
        return "";
    }
    public static String validateServiceName(String name){
        if (name.isEmpty()){
            return "Service name can't be empty";
        }
        else if(name.length()>60){
            return "Service name can't be greater then 60 characters";
        }
        return "";
    }
    public static String validateServiceDescription(String name){
        if(name.length()>200){
            return "Service Description can't be greater then 200 characters";
        }
        return "";
    }
    public static String validateFeedback(String name){
        if (name.isEmpty()){
            return "Field can't be empty";
        }
        else if(name.length()>500){
            return "Query length can't be greater then 500 characters";
        }
        return "";
    }
    public static String validatePassword(String Password){
        int upper=0,lower=0,symbol=0,number=0;
        if (Password.length()>=5 && Password.length()<=20)
        {
            for (int i=0; i<Password.length();i++){
                if (Character.isUpperCase(Password.charAt(i))){
                    upper+=1;
                }
                else if (Character.isLowerCase(Password.charAt(i))){
                    lower+=1;
                }
                else if (Character.isDigit(Password.charAt(i))){
                    number+=1;
                }
                else{
                    symbol+=1;
                }

            }
            if (upper==0 || lower==0 || number==0 || symbol==0){
                return "Password should contain at least one uppercase letter, one lowercase letter, one number and one special character";
            }

            return "";


        }
        return "Password length should be between 5-20";
    }

    public static String validateConfirm(String confirmPassword,String Password){
        if (confirmPassword.isEmpty()){
            return "Field can't be empty";
        }
        if (!Password.equals(confirmPassword)){
            return "Password do not match";
        }
        return "";
    }
    public static String convertTextTime(String Time){
        String[] arr=Time.split(":",3);
        int hour=Integer.parseInt(arr[0]);
        int min=Integer.parseInt(arr[1]);
        String format="AM";
        if(hour>=12){
            hour=hour-12;
            format="PM";
        }
        return String.valueOf(hour)+":"+String.valueOf(min)+" "+format;


    }
    public static String convertTextDate(String Date){
        String[] arr=Date.split("-",3);
        Map<String,String> dateMap=new HashMap<>();
        dateMap.put("01","January");
        dateMap.put("02","February");
        dateMap.put("03","March");
        dateMap.put("04","April");
        dateMap.put("05","May");
        dateMap.put("06","June");
        dateMap.put("07","July");
        dateMap.put("08","August");
        dateMap.put("09","September");
        dateMap.put("10","October");
        dateMap.put("11","November");
        dateMap.put("12","December");
        int day=Integer.parseInt(arr[2]);
        return String.valueOf(day)+" "+dateMap.get(arr[1])+" "+arr[0];

    }
}
