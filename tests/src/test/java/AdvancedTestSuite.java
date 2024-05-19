import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Request.Builder;
import okhttp3.Response;

import org.json.*;

import pages.*;

import java.io.IOException;
import java.util.*; 


public class AdvancedTestSuite extends BaseTestSuite {
    private OkHttpClient client = new OkHttpClient();

    @Test
    public void testResetPasswordEmailDelivery() {
        LoginPage loginPage = new LoginPage(this.driver);
        loginPage.sendResetPasswordEmail(configProperties.getProperty("userEmail"));

        Optional<String> mailId = Optional.empty();
        try {
            mailId = checkIfLetterHasArrivedAndReturnUID();
        } catch (IOException ex) {
            Assertions.fail("Couldn't send request to API to retrieve inbox messages");
        }

        Assertions.assertTrue(mailId.isPresent());
        try {
            this.cleanUpMailMessages(mailId.get());
        } catch (IOException ex) {
            System.err.println("Couldn't delete the message. Subsequent tests may not pass");
        }
    }

    private Optional<String> checkIfLetterHasArrivedAndReturnUID() throws IOException {
        String getInboxUrl = String.format("%sinboxes/%s",
            configProperties.getProperty("mailapi.url"),
            configProperties.getProperty("userEmail"));

        Response response = buildRequestAndExecute(getInboxUrl, ReqestType.GET);
        JSONArray jsonArray = new JSONArray(response.body().string());

        if (jsonArray.length() < 1) {
            return Optional.empty();
        }
        String uid = jsonArray.getJSONObject(0).getString("uid");
        return Optional.of(uid);
    }

    private Boolean cleanUpMailMessages(String messageId) throws IOException {
        String deleteMsgUrl = String.format("%smessages/%s",
            configProperties.getProperty("mailapi.url"), messageId);
        
        Response response = buildRequestAndExecute(deleteMsgUrl, ReqestType.DELETE);
        JSONObject jsonObject = new JSONObject(response.body().string());
        return jsonObject.getBoolean("deleted");
    }

    private Response buildRequestAndExecute(String url, ReqestType type) throws IOException {
        Builder requestBuilder = new Request.Builder()
            .url(url)
            .addHeader("X-RapidAPI-Key", configProperties.getProperty("mailapi.key"))
            .addHeader("X-RapidAPI-Host", configProperties.getProperty("mailapi.host"));
        switch (type) {
            case DELETE:
                requestBuilder.delete(null);
                break;
            default:
                requestBuilder.get();
        }

        Request request = requestBuilder.build();
        return client.newCall(request).execute();
    }

    private static enum ReqestType {
        GET, DELETE
    }
}
