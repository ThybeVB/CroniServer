package com.monstahhh.croniserver.http;

import com.monstahhh.croniserver.plugin.mrworldwide.MrWorldWide;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class HttpRequestFactory {

    public HttpRequest getRequest(HttpMethod method, String url,
                                  HttpParameterSet parameters) throws IOException {
        HttpRequest request;

        if (method == HttpMethod.GET) {
            request = requestGET(url, parameters);
        } else {
            request = requestPOST(url, parameters);
        }

        return request;
    }

    public HttpRequest getRequestGateway(HttpMethod method, String url,
                                         HttpParameterSet parameters, JSONObject json) throws IOException {
        HttpRequest request;

        if (method == HttpMethod.GET) {
            request = requestGET(url, parameters);
        } else {
            requestPOSTApi(url, parameters, json);
            return null;
        }

        return request;
    }

    private HttpRequest requestGET(String url, HttpParameterSet parameters)
            throws IOException {
        StringBuilder request = new StringBuilder(url);
        String paramaters = new HttpParameterSetParser(parameters).asString();

        request.append(paramaters);

        HttpURLConnection httpConnection = getDefaultConnection(request
                .toString());

        httpConnection.setRequestMethod(HttpMethod.GET.asString());
        httpConnection.setRequestProperty("Content-Length",
                String.valueOf(paramaters.getBytes()));

        return new HttpRequest(httpConnection);
    }

    private HttpRequest requestPOST(String url, HttpParameterSet parameters)
            throws IOException {
        String paramaters = new HttpParameterSetParser(parameters).asString();

        HttpURLConnection httpConnection = getDefaultConnection(url);
        httpConnection.setRequestMethod(HttpMethod.POST.asString());
        httpConnection.setRequestProperty("Content-Length",
                String.valueOf(paramaters.getBytes()));

        httpConnection.setDoOutput(true);
        OutputStreamWriter writer = new OutputStreamWriter(httpConnection.getOutputStream());

        writer.write(paramaters);
        writer.flush();

        return new HttpRequest(httpConnection);
    }

    private void requestPOSTApi(String url, HttpParameterSet parameters, JSONObject json)
            throws IOException {

        try {
            URL urlObj = new URL(url);
            HttpURLConnection httpConnection = (HttpURLConnection) urlObj.openConnection();
            httpConnection.setRequestMethod(HttpMethod.POST.asString());
            httpConnection.setRequestProperty("Authorization", MrWorldWide.serverToken);
            httpConnection.setRequestProperty("Content-Type", "application/json");
            httpConnection.setRequestMethod("POST");
            httpConnection.setDoOutput(true);
            httpConnection.setDoInput(true);

            OutputStream os = httpConnection.getOutputStream();
            os.write(json.toString().getBytes(StandardCharsets.UTF_8));
            os.close();

            // read the response
            InputStream in = new BufferedInputStream(httpConnection.getInputStream());
            String result = IOUtils.toString(in, StandardCharsets.UTF_8);
            System.out.println(result);
            JSONObject myResponse = new JSONObject(result);

            System.out.println(myResponse.toString());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    private HttpURLConnection getDefaultConnection(String request)
            throws IOException {
        URL urlRequest = new URL(request);
        HttpURLConnection httpConnection = (HttpURLConnection) urlRequest.openConnection();
        httpConnection.addRequestProperty("User-Agent", HttpClient.USERAGENT);
        httpConnection.setRequestProperty("Accept-Charset", HttpClient.CHARSET);
        httpConnection.setRequestProperty("Content-Type",
                "application/x-www-form-urlencoded");
        httpConnection.setRequestProperty("Content-Language", "en-US");

        return httpConnection;
    }

}
