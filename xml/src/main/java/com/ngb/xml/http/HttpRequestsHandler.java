//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ngb.xml.http;

import com.google.gson.*;
import com.ngb.xml.bean.ExcelConsumer;
import com.ngb.xml.dto.*;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import okhttp3.Call;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Request.Builder;
import org.apache.commons.lang3.StringUtils;
import utils.MeterMakeCodeMapping;

public class HttpRequestsHandler {
    private static OkHttpClient httpClient;
    private static final String NGB_HOST = "ngbtest.mpwin.co.in";
    private static final String USER_AGENT_HEADER = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36";
    private static final String LOGIN_API = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/authentication/login";
    private static final String CONSUMER_METER_MAPPING_API = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/mapping/identifier/$meterIdentifier$/status/$status$";
    private static final String LATEST_READ_API = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/read/latest/consumer-no/$consumerNo$";
    private static final String READ_MASTER_API = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/read";
    private final SimpleDateFormat oldDateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private final SimpleDateFormat newDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    private String consumerNumber;
    private String str;
    private JsonObject latestReadFormJson;
    private JsonObject latestScheduleJson;
    private List <ExcelConsumer> excelGlobalConsumer;

    public HttpRequestsHandler() {
    }

    public static void initializeHttpRequestHandler() {
        httpClient = new OkHttpClient();

    }

    public String sendParsedDataToServer(ParsedXmlData parsedXmlData, String authToken) throws Exception {
        str="MIG";
        if (!MeterMakeCodeMapping.meterMakeCodeMapping.containsKey(parsedXmlData.getMeterMakeCode().trim())) {
            throw new Exception("Couldn't find the meter make code mapping");
        } else {
            String meterIdentifier = ((String)MeterMakeCodeMapping.meterMakeCodeMapping.get(parsedXmlData.getMeterMakeCode())).concat(parsedXmlData.getMeterNumber());
            String consumerNumber = this.getConsumerNumber(authToken, meterIdentifier);

            if (consumerNumber == null) {
                meterIdentifier = str.concat((String)MeterMakeCodeMapping.meterMakeCodeMapping.get(parsedXmlData.getMeterMakeCode())).concat(parsedXmlData.getMeterNumber());
                consumerNumber = this.getConsumerNumber(authToken, meterIdentifier);

                 }

            if (consumerNumber == null)
                throw new Exception("Couldn't find the consumer number corresponding to meter identifier");

            else {
                this.consumerNumber = consumerNumber;
                BigDecimal latestReading = this.getLatestReading(authToken);
                if (latestReading.compareTo(new BigDecimal(parsedXmlData.getB3Value())) > 0) {
                    throw new Exception("Reading which has to be inserted can't be less than the latest reading found");
                } else {
                    String readMasterResponse = this.sendReadMasterRequest(authToken, parsedXmlData, latestReading);
                    return readMasterResponse;
                }
            }
        }
    }

    private String sendReadMasterRequest(String authToken, ParsedXmlData parsedXmlData, BigDecimal latestReading) throws IOException, ParseException {
        String meterIdentifier = ((String)MeterMakeCodeMapping.meterMakeCodeMapping.get(parsedXmlData.getMeterMakeCode())).concat(parsedXmlData.getMeterNumber());
        BigDecimal readingToBeInserted = new BigDecimal(parsedXmlData.getB3Value());
        BigDecimal difference = readingToBeInserted.subtract(latestReading);
        ReadMasterKW readMasterKW = new ReadMasterKW(new BigDecimal(parsedXmlData.getB5Value()), BigDecimal.ZERO, BigDecimal.ZERO);
        ReadMasterPF readMasterPf = new ReadMasterPF(new BigDecimal(parsedXmlData.getB9Value()), BigDecimal.ZERO);
        Read read = new Read(this.consumerNumber, meterIdentifier, this.newDateFormat.format(this.oldDateFormat.parse(parsedXmlData.getReadingDate())), "NORMAL", "WORKING", "NR", "XML_FILE", readingToBeInserted, difference, new BigDecimal("1"), new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), false, readMasterKW, readMasterPf);
        String readJson = (new Gson()).toJson(read);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), readJson);
        Request request = (new Builder()).url("https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/read").addHeader("Authorization", authToken).post(requestBody).build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        if (response.code() == 201) {
            String responseJson = response.body().string();
            response.body().close();
            return responseJson;
        } else {
            Map<String, String> errorMessage = new HashMap();
            errorMessage.put("Response code", String.valueOf(response.code()));
            errorMessage.put("Message", response.message());
            errorMessage.put("Response body", response.body().string());
            errorMessage.put("Error source", "Read master insertion API");
            response.body().close();
            throw new IOException(errorMessage.toString());
        }
    }

    public String sendMeterMaster(String authToken) throws IOException, ParseException {

        MeterMaster meterMaster;
        meterMaster = (MeterMaster) (new Gson()).fromJson(latestReadFormJson.getAsJsonObject("meterMaster"), MeterMaster.class);
        meterMaster.setIdentifier("MONTEL854599");
        meterMaster.setSerialNo("854599");
        meterMaster.setMake("MONTEL");


        String meterMasterJson = (new Gson()).toJson(meterMaster);
        System.out.println(meterMasterJson);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), meterMasterJson);
        Request request = (new Builder()).url("https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/meter").addHeader("Authorization", authToken).post(requestBody).build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        System.out.println("This is meter master response "+response.code());
        if (response.code() == 201) {
            String responseJson = response.body().string();
            response.body().close();
            return responseJson;
        } else {
            Map<String, String> errorMessage = new HashMap();
            errorMessage.put("Response code", String.valueOf(response.code()));
            errorMessage.put("Message", response.message());
            errorMessage.put("Response body", response.body().string());
            errorMessage.put("Error source", "Read master insertion API");
            response.body().close();
            throw new IOException(errorMessage.toString());
        }
    }
     public void sendMeterReplacementRequest(String authToken, List<ExcelConsumer> excelConsumer) throws IOException, ParseException {
         excelGlobalConsumer=excelConsumer;
         String meterIdentifier;
         meterIdentifier = "MONTEL854596";
         for (ExcelConsumer e : excelConsumer)
         {
             System.out.println("from Inside"+e);
             System.out.println("Json Date"+latestScheduleJson.get("startReadingDate").toString());
//             System.out.println(((String) MeterMakeCodeMapping.meterMakeCodeMapping.get(e.getMeterMakeCode())).concat(e.getSerialNo()));

             //    meterIdentifier = ((String) MeterMakeCodeMapping.meterMakeCodeMapping.get(e.getMeterMakeCode())).concat(e.getSerialNo());
             //  BigDecimal readingToBeInserted = new BigDecimal(parsedXmlData.getB3Value());
             //  BigDecimal difference = readingToBeInserted.subtract(latestReading);

            // ReadMasterKW readMasterKW = new ReadMasterKW(new BigDecimal(0.1), BigDecimal.ZERO, BigDecimal.ZERO);
            // ReadMasterPF readMasterPf = new ReadMasterPF(new BigDecimal(0.8), BigDecimal.ZERO);
              ReadMasterKW readMasterKW = new ReadMasterKW( BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO);
             ReadMasterPF readMasterPf = new ReadMasterPF( BigDecimal.ZERO, BigDecimal.ZERO);
             MeterMaster meterMaster;
             meterMaster = (MeterMaster) (new Gson()).fromJson(latestReadFormJson.getAsJsonObject("meterMaster"), MeterMaster.class);
             meterMaster.setIdentifier("MONTEL854596");
             meterMaster.setSerialNo("854596");
             meterMaster.setMake("MONTEL");
             // Read finalRead = new Read(this.consumerNumber, meterIdentifier, this.newDateFormat.format(this.oldDateFormat.parse(parsedXmlData.getReadingDate())), "NORMAL", "WORKING", "NR", "XML_FILE", readingToBeInserted, difference, new BigDecimal("1"), new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), new BigDecimal("0"), false, readMasterKW, readMasterPf);
             Read finalRead = (Read) (new Gson()).fromJson(latestReadFormJson.getAsJsonObject("readMaster"), Read.class);
             finalRead.setReplacementFlag("FR");
             finalRead.setAssessment(BigDecimal.valueOf(0.0));
             finalRead.setReadMasterKW(readMasterKW);
             finalRead.setReadMasterPF(readMasterPf);
             finalRead.setReadingDate(this.newDateFormat.format(this.newDateFormat.parse("2020-06-11")));


             Read startRead = (Read) (new Gson()).fromJson(latestReadFormJson.getAsJsonObject("readMaster"), Read.class);
             startRead.setReplacementFlag("SR");
             startRead.setMeterIdentifier(meterIdentifier);
             startRead.setReadingDate(this.newDateFormat.format(this.newDateFormat.parse("2020-06-11")));

             Read currentRead = startRead;

            // ConsumerMeterMapping consumerMeterMapping = new ConsumerMeterMapping(this.consumerNumber, meterIdentifier, meterMaster.getSerialNo(), meterMaster.getMake(), startRead.getReading(), finalRead.getReading(), "ACTIVE", this.newDateFormat.parse(latestScheduleJson.get("startReadingDate").toString()),latestScheduleJson.get("billMonth").toString());
            // ConsumerMeterMapping consumerMeterMapping = new ConsumerMeterMapping(this.consumerNumber, meterIdentifier, meterMaster.getSerialNo(), meterMaster.getMake(), startRead.getReading(), finalRead.getReading(), "ACTIVE",this.newDateFormat.format(this.newDateFormat.parse("2020-06-21")) ,latestScheduleJson.get("billMonth").toString());
             ConsumerMeterMapping consumerMeterMapping = new ConsumerMeterMapping("3477006836", meterIdentifier, meterMaster.getSerialNo(), meterMaster.getMake(), startRead.getReading(), finalRead.getReading(), "ACTIVE",this.newDateFormat.format(this.newDateFormat.parse("2020-06-11")) ,this.newDateFormat.format(this.newDateFormat.parse("2020-06-11")));
             String str = "{\"finalRead\":{\"readMasterKW\":{},\"readMasterPF\":{},\"reading\":2237.48,\"assessment\":0,\"mf\":1,\"consumerNo\":\"3477006836\",\"readingDate\":\"2020-06-13\",\"meterIdentifier\":\"MONTEL854595\",\"readingType\":\"NORMAL\",\"meterStatus\":\"WORKING\",\"replacementFlag\":\"FR\"},\"startRead\":{\"reading\":2237.48,\"mf\":1,\"consumerNo\":\"3477006836\",\"readingDate\":\"2020-06-13\",\"meterIdentifier\":\"MONTEL854599\",\"assessment\":0,\"readingType\":\"NORMAL\",\"meterStatus\":\"WORKING\",\"replacementFlag\":\"SR\"},\"meterCTRMapping\":null,\"consumerMeterMapping\":{\"mappingStatus\":\"ACTIVE\",\"consumerNo\":\"3477006836\",\"meterMake\":\"MONTEL\",\"meterSerialNo\":\"854599\",\"meterIdentifier\":\"MONTEL854599\",\"finalRead\":2237.48,\"startRead\":2237.48,\"installationDate\":\"2020-06-13\",\"removalDate\":\"2020-06-13\"}}";

           //  String finalReadJson = (new Gson()).toJson(finalRead);
            // String startReadJson = (new Gson()).toJson(startRead);
           //  String currentReadJson = (new Gson()).toJson(currentRead);

             PostMeterReplacement postMeterReplacement = new PostMeterReplacement(consumerMeterMapping, finalRead, startRead, currentRead,null);
             String postMeterReplacementJson = str;
             //String postMeterReplacementJson = (new Gson()).toJson(postMeterReplacement);
             System.out.println(postMeterReplacementJson);
             RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), postMeterReplacementJson);
             Request request = (new Builder()).url("https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/mapping/consumer-no/$consumerNo$".replace("$consumerNo$",e.getConsumerNo())).addHeader("Authorization", authToken).post(requestBody).build();
             Call call = httpClient.newCall(request);
             System.out.println(request.headers().toString());
            // System.out.println(request.header().toString());
             System.out.println(requestBody.toString());
             System.out.println(request.toString());
             Response response = call.execute();
             if (response.code() == 201) {
                 String responseJson = response.body().string();
                 response.body().close();
                 //return responseJson;
             } else {
                 Map<String, String> errorMessage = new HashMap();
                 errorMessage.put("Response code", String.valueOf(response.code()));
                 errorMessage.put("Message", response.message());
                 errorMessage.put("Response body", response.body().string());
                 errorMessage.put("Error source", "Read master insertion API");
                 response.body().close();
                 throw new IOException(errorMessage.toString());
             }
         }
     }

    public String getConsumerNumber() {
        return this.consumerNumber;
    }



    private String getConsumerNumber(String authToken, String meterIdentifier) throws IOException {
        String url = this.buildConsumerMeterMappingApiUrl(meterIdentifier);
        Request request = (new Builder()).url(url).addHeader("Authorization", authToken).build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        if (response.code() == 200) {
            JsonArray responseJsonArray = (JsonArray)(new Gson()).fromJson(response.body().string(), JsonArray.class);
            JsonObject consumerMeterMappingJson = responseJsonArray.get(0).getAsJsonObject();
            response.body().close();
            if (consumerMeterMappingJson == null) {
                throw new IOException("Consumer meter mapping JSON was null");
            } else {
                return consumerMeterMappingJson.get("consumerNo").getAsString();
            }
        } else {
            Map<String, String> errorMessage = new HashMap();
            errorMessage.put("Response code", String.valueOf(response.code()));
            errorMessage.put("Message", response.message());
            errorMessage.put("Response body", response.body().string());
            errorMessage.put("Error source", "Consumer Meter Mapping API");
            response.body().close();
            throw new IOException(errorMessage.toString());
        }
    }

    private BigDecimal getLatestReading(String authToken) throws IOException {
        String url = this.buildLatestReadApiUrl();
        Request request = (new Builder()).url(url).addHeader("Authorization", authToken).build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        if (response.code() == 200) {
            JsonObject latestReadMasterJson = (JsonObject)(new Gson()).fromJson(response.body().string(), JsonObject.class);
            response.body().close();
            if (latestReadMasterJson == null) {
                throw new IOException("Latest read master JSON was null");
            } else {
                return latestReadMasterJson.get("reading").getAsBigDecimal();
            }
        } else {
            Map<String, String> errorMessage = new HashMap();
            errorMessage.put("Response code", String.valueOf(response.code()));
            errorMessage.put("Message", response.message());
            errorMessage.put("Response body", response.body().string());
            errorMessage.put("Error source", "Latest Read API");
            response.body().close();
            throw new IOException(errorMessage.toString());
        }
    }

    public void getMeterReplacementForm(String authToken) throws IOException {
        String url = this.buildMeterReplacementFormUrl();
        System.out.println(url);
        Request request = (new Builder()).url(url).addHeader("Authorization", authToken).build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        if (response.code() == 200) {
            latestReadFormJson = (JsonObject)(new Gson()).fromJson(response.body().string(), JsonObject.class);
            response.body().close();
            if (latestReadFormJson == null) {
                throw new IOException("Meter Replacement Form JSON was null");
            } else {
               // return latestReadMasterJson.get("reading").getAsBigDecimal();
                System.out.println("This is Json Body"+latestReadFormJson);
            }
        } else {
            Map<String, String> errorMessage = new HashMap();
            errorMessage.put("Response code", String.valueOf(response.code()));
            errorMessage.put("Message", response.message());
            errorMessage.put("Response body", response.body().string());
            errorMessage.put("Error source", "Latest Read API");
            response.body().close();
            throw new IOException(errorMessage.toString());
        }
    }

    public void getLatestSchedule(String authToken) throws IOException {
        String url = this.buildLatestScheduleUrl();
        System.out.println(url);
        Request request = (new Builder()).url(url).addHeader("Authorization", authToken).build();
        Call call = httpClient.newCall(request);
        Response response = call.execute();
        if (response.code() == 200) {
           latestScheduleJson = (JsonObject)(new Gson()).fromJson(response.body().string(), JsonObject.class);
            response.body().close();
            if (latestReadFormJson == null) {
                throw new IOException("Schedule JSON was null");
            } else {
                // return latestReadMasterJson.get("reading").getAsBigDecimal();
                System.out.println("This is Schedule Json Body"+latestScheduleJson);
            }
        } else {
            Map<String, String> errorMessage = new HashMap();
            errorMessage.put("Response code", String.valueOf(response.code()));
            errorMessage.put("Message", response.message());
            errorMessage.put("Response body", response.body().string());
            errorMessage.put("Error source", "Latest Read API");
            response.body().close();
            throw new IOException(errorMessage.toString());
        }
    }

    private String buildLatestReadApiUrl() {
        String url = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/read/latest/consumer-no/$consumerNo$".replace("$consumerNo$", this.consumerNumber);
        return url;
    }

    private String buildMeterReplacementFormUrl() {
        String str1 = "3477006836";
        //String url = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/mapping//consumer-no/$consumerNo$/replacement".replace("$consumerNo$", this.consumerNumber);
        String url = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/mapping/consumer-no/$consumerNo$/replacement".replace("$consumerNo$", str1);
        return url;
    }

    private String buildLatestScheduleUrl() {
        String str1 = "GPH59";
        //String url = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/mapping//consumer-no/$consumerNo$/replacement".replace("$consumerNo$", this.consumerNumber);
        String url = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1//schedule/latest/group-no/$groupNo$".replace("$groupNo$", str1);
        return url;
    }

    private String buildConsumerMeterMappingApiUrl(String meterIdentifier) {
        String url = "https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/consumer/meter/mapping/identifier/$meterIdentifier$/status/$status$".replace("$meterIdentifier$", meterIdentifier).replace("$status$", "ACTIVE");
        return url;
    }

    public static HttpHandlerResponse loginUser(String username, String password) throws IOException {
        PingResponse pingResponse = pingHost();
        if (!pingResponse.getHostReachable()) {
            return new HttpHandlerResponse(Boolean.FALSE, pingResponse.getMessage());
        } else {
            LoginBody loginBody = new LoginBody(username, password);
            String loginBodyJson = (new Gson()).toJson(loginBody);
            RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), loginBodyJson);
            Request request = (new Builder()).url("https://ngbtest.mpwin.co.in/mppkvvcl/nextgenbilling/backend/api/v1/authentication/login").post(requestBody).addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36").build();
            Call call = httpClient.newCall(request);
            Response response = call.execute();
            response.body().close();
            return response.code() == 200 && !StringUtils.isBlank(response.headers().get("authorization")) ? new HttpHandlerResponse(Boolean.TRUE, response.headers().get("authorization")) : new HttpHandlerResponse(Boolean.FALSE, "Authentication falied! Wrong username/password.");
        }
    }

    private static PingResponse pingHost() {
        PingResponse pingResponse = null;

        try {
            InetAddress.getByName("ngbtest.mpwin.co.in").isReachable(1000);
            pingResponse = new PingResponse(Boolean.TRUE, "Host reachable!");
            return pingResponse;
        } catch (UnknownHostException var6) {
            pingResponse = new PingResponse(Boolean.FALSE, "Couldn't connect to the NGB server! Please check your internet connection.");
            return pingResponse;
        } catch (IOException var7) {
            pingResponse = new PingResponse(Boolean.FALSE, "Some IO exception occurred! Can't process the request.");
            return pingResponse;
        } finally {
            ;
        }
    }
}
