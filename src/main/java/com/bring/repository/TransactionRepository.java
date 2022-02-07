/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.bring.repository;

import java.util.List;
import org.springframework.stereotype.Service;
import com.bring.models.Transaction;
import com.bring.utils.Constants;
import java.io.IOException;
import java.util.ArrayList;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author leonard
 */
@Service
public class TransactionRepository {

//    public List<Transaction> fetchAllTnxs() {
//        List<Transaction> all_tnxs = new ArrayList<>();
//        
//        all_tnxs.add(new Transaction("abah-hcky", "Test-Acc-100", "counterPartyAccount 001", 
//                "counterPartyName 001", "img/img-001.jpg", 8.60, "GBP", 8.60, "GBP", "Deposit", "customer deposit"));
//        all_tnxs.add(new Transaction("abbh-hcky", "Test-Acc-102", "counterPartyAccount 002", 
//                "counterPartyName 002", "img/img-002.jpg", 25.60, "GBP", 25.60, "GBP", "Deposit", "customer deposit"));
//        all_tnxs.add(new Transaction("abch-hcky", "Test-Acc-103", "counterPartyAccount 003", 
//                "counterPartyName 003", "img/img-003.jpg", 6.40, "GBP", 6.40, "GBP", "Withdraw", "customer deposit"));
//        
//        fetchData();
//        
//        return all_tnxs;
//    }
    public List<Transaction> fetchAllTnxs() {
        List<Transaction> all_tnxs = new ArrayList<>();

        String resp = fetchData();
        if (resp != null) {
            JSONObject obj = new JSONObject(resp);
            JSONArray tnx_arr = obj.getJSONArray("transactions");
            for (int i = 0; i < tnx_arr.length(); i++) {
                JSONObject other_acc = tnx_arr.getJSONObject(i).getJSONObject("other_account");
                JSONObject details_obj = tnx_arr.getJSONObject(i).getJSONObject("details");

                String tnx_id = tnx_arr.getJSONObject(i).getString("id");
                String acc_id = tnx_arr.getJSONObject(i).getJSONObject("this_account").getString("id");
                String counter_acc = other_acc.getString("number");
                String counter_name = other_acc.getJSONObject("holder").getString("name");
//            String counter_logo = other_acc.getJSONObject("metadata").getString("image_URL");
                String counter_logo = "";
                String ins_amt_str = details_obj.getJSONObject("value").getString("amount");
                String ins_curr = details_obj.getJSONObject("value").getString("currency");
                String trans_type = details_obj.getString("type");
                String descrip = details_obj.getString("description");

                double ins_amt = Double.parseDouble(ins_amt_str);

                all_tnxs.add(new Transaction(tnx_id, acc_id, counter_acc, counter_name, counter_logo, ins_amt, ins_curr, ins_amt, ins_curr, trans_type, descrip));
            }
        }

        return all_tnxs;
    }

    private String fetchData() {
        String responseBody = null;
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(Constants.REQUEST_TIMEOUT)
                .setConnectionRequestTimeout(Constants.REQUEST_TIMEOUT).build();
        CloseableHttpClient httpClient = HttpClientBuilder.create().setDefaultRequestConfig(config).build();
        try {
            HttpGet request = new HttpGet("https://apisandbox.openbankproject.com/obp/v1.2.1/banks/rbs/accounts/savings-kids-john/public/transactions");
//            String authHeader = "Bearer " + new String(encodedAuth);
//            request.setHeader(HttpHeaders.AUTHORIZATION, authHeader);
            HttpResponse response = httpClient.execute(request);

            int statusCode = response.getStatusLine().getStatusCode();
            responseBody = EntityUtils.toString(response.getEntity());
            Constants.LOGGER.info("Resp Code: {}, \nResponse Body: {}", statusCode, responseBody);

        } catch (IOException ex) {
            Constants.LOGGER.error(ex.toString());
        } finally {
            try {
                httpClient.close();
            } catch (IOException ex) {
                Constants.LOGGER.error(ex.toString());
            }
        }

        return responseBody;
    }

}
