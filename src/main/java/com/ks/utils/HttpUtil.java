package com.ks.utils;

import java.io.BufferedReader;
import java.io.IOException;

import org.json.JSONException;

import com.fasterxml.jackson.databind.ObjectMapper;
public class HttpUtil {
	private String value;

	public HttpUtil(String value) {
		this.value = value;
	}

	public <T> T  toModel(Class<T> tClass) throws JSONException {
		try {
			//JSONデータはモーダルにマッピングする
			return new ObjectMapper().readValue(value,tClass);
		} catch (IOException e) {
			// TODO 自動生成された catch ブロック
			e.printStackTrace();
		}
		return null;
	}


	public static HttpUtil of(BufferedReader reader) {
		StringBuffer sb = new StringBuffer();
		try {
			String line;
			while ((line = reader.readLine()) != null) {
				sb.append(line);//  JSONデータを取って受けて、sb に保持する
			}
		} catch (IOException e) {
			System.out.println(e.getMessage());
		}

		return new HttpUtil(sb.toString());

	}

}
