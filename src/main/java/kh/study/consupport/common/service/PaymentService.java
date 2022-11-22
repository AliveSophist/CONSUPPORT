package kh.study.consupport.common.service;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import lombok.Data;

@Service
public class PaymentService {

	private String impKey = "5087550547163300";
	private String impSecret = "758WClQo3JisunfaAdOynj8MsXfFtU57Nh0qFKE1Toz5IaStXd4jkO4yiHbGSF334ycoJD1wqXwF67SO";

	void setImpKey(String impKey) {
		this.impKey = impKey;
	}

	void setImpSecret(String impSecret) {
		this.impSecret = impSecret;
	}

	@Data
	private class Response {
		private PaymentInfo response;
	}

	@Data
	private class PaymentInfo {
		private int amount;
	}

	public String getToken() throws IOException {

		HttpsURLConnection conn = null;

		URL url = new URL("https://api.iamport.kr/users/getToken");

		conn = (HttpsURLConnection) url.openConnection();

		conn.setRequestMethod("POST");
		conn.setRequestProperty("Content-type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		conn.setDoOutput(true);
		JsonObject json = new JsonObject();

		json.addProperty("imp_key", impKey);
		json.addProperty("imp_secret", impSecret);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

		bw.write(json.toString());
		bw.flush();
		bw.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

		Gson gson = new Gson();

		String response = gson.fromJson(br.readLine(), Map.class).get("response").toString();

		System.out.println(response);

		String token = gson.fromJson(response, Map.class).get("access_token").toString();

		br.close();
		conn.disconnect();

		return token;
	}

	public int paymentInfo(String merchant_uid, String access_token) throws IOException {

		HttpsURLConnection conn = null;

		URL url = new URL("https://api.iamport.kr/payments/" + merchant_uid);

		conn = (HttpsURLConnection) url.openConnection();

		conn.setRequestMethod("GET");
		conn.setRequestProperty("Authorization", access_token);
		conn.setDoOutput(true);

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

		Gson gson = new Gson();

		Response response = gson.fromJson(br.readLine(), Response.class);

		br.close();
		conn.disconnect();

		return response.getResponse().getAmount();
	}

	public void paymentCancle(/* String access_token, */ String merchant_uid, int cancel_request_amount/* , String reason */)
			throws IOException {

		String access_token = getToken();
		String reason = "테스트";
		
		System.out.println("결제 취소");
		System.out.println(access_token);
		System.out.println(merchant_uid);

		
		HttpsURLConnection conn = null;
		URL url = new URL("https://api.iamport.kr/payments/cancel");

		conn = (HttpsURLConnection) url.openConnection();

		conn.setRequestMethod("POST");

		conn.setRequestProperty("Content-type", "application/json");
		conn.setRequestProperty("Accept", "application/json");
		conn.setRequestProperty("Authorization", access_token);

		conn.setDoOutput(true);

		JsonObject json = new JsonObject();

		// 이거 작성한 새끼는 도대체 언제적 코드를 쓴거냐...
//		json.addProperty("m_uid", m_uid);
//		json.addProperty("amount", amount);
//		json.addProperty("checksum", amount);
//		json.addProperty("reason", reason);

		json.addProperty("merchant_uid", merchant_uid);
		json.addProperty("cancel_request_amount", cancel_request_amount);
		json.addProperty("reason", reason);

		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream()));

		bw.write(json.toString());
		bw.flush();
		bw.close();

		BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));

		br.close();
		conn.disconnect();
	}

}
