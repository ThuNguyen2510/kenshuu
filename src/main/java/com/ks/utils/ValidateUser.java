package com.ks.utils;

import com.ks.model.User;

public class ValidateUser  {

	public static boolean isNull(String str) {
        return str == null ? true : false;
    }
	public static boolean isNullOrBlank(String param) {
        if (isNull(param) || param.trim().length() == 0) {
            return true;
        }
        return false;
    }
	private static boolean stringMoreThan(int size, String param) {
		if(param.length()>size) return true;
		return false;
	}
	public String validate(User user) {
		String errors="";
		if(isNullOrBlank(user.getUserId())!=true) {
			errors+="※ユーザーIDが未入力です。";
		}else {
			if(stringMoreThan(8,user.getUserId())!=true)
			errors+="ユーザーIDは8文字以下。"	;
		}

		if(isNullOrBlank(user.getPassword())!=true) {
			errors+="※パスワードが未入力です。";
		}else {
			if(stringMoreThan(8,user.getPassword())!=true)
			errors+="※パスワードは8文字以下。"	;
		}


		if(isNullOrBlank(user.getFamilyName())!=true) {
			errors+="※姓がが未入力です。";
		}else {
			if(stringMoreThan(10,user.getFamilyName())!=true)
			errors+="※姓は10文字以下"	;
		}


		if(isNullOrBlank(user.getFirstName())!=true) {
			errors+="※名がが未入力です。";
		}else {
			if(stringMoreThan(10,user.getFirstName())!=true)
			errors+="※名は10文字以下"	;
		}


		return errors;

	}


}
