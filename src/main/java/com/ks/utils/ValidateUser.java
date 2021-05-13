package com.ks.utils;

import com.ks.model.User;

public class ValidateUser {

	public boolean isNull(String str) {
		return str == null ? true : false;
	}

	public boolean isNullOrBlank(String param) {
		if (isNull(param) || param.trim().length() == 0) {
			return true;
		}
		return false;
	}

	public boolean stringMoreThan(int size, String param) {
		if (param.length() > size)
			return true;
		return false;
	}


	public String validate(User user) {
		String errors = "";
		if (isNullOrBlank(user.getUserId())) {
			errors += "※ユーザーIDが未入力です。";
		} else if (stringMoreThan(8, user.getUserId())) {
			errors += "ユーザーIDは8文字以下。";
		}

		if (isNullOrBlank(user.getPassword())) {
			errors += "※パスワードが未入力です。";
		} else if (stringMoreThan(8, user.getPassword())) {
			errors += "※パスワードは8文字以下。";
		}

		if (isNullOrBlank(user.getFamilyName())) {
			errors += "※姓がが未入力です。";
		} else if (stringMoreThan(10, user.getFamilyName())) {
			errors += "※姓は10文字以です。";
		}

		if (isNullOrBlank(user.getFirstName())) {
			errors += "※名がが未入力です。";
		} else if (stringMoreThan(10, user.getFirstName())) {
			errors += "※名は10文字以下です。";
		}
		return errors;

	}

}
