package SMS;


import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import utils.StringUtils;

public class SMS {

    private static final String HOST = "https://api.enviarsms.io/v1";
    private static final String SEND_REQUEST = HOST + "/send/";

    private static final String TOKEN = "api?eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiJGYWNlYm9va18xMDY1OTE4ODI2OTIwNDgyIiwiZW1haWwiOiJqYW5haW5hbHVkd2lnQGdtYWlsLmNvbSIsInRva2VuX3VzZSI6ImFwaSIsImNyZWF0ZWRBdCI6MTU0MzEwMDMzNjA2MCwiZXhwIjoxODU4NDYwMzM2LCJpYXQiOjE1NDMxMDAzMzZ9.qA5NqROGfDWIEyOV1HKxkytCpr1d9AjWpNf-s978RO0";

    public void send(String phone, String message) throws Exception {
        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(SEND_REQUEST);

        message = StringUtils.removeAccent(message);

        String json = "{" +
                "\"message\": \"" + message + "\"," +
                "\"message_type\": \"promotional\"," +
                "\"subject\": [\"" + phone + "\"]," +
                "\"subject_type\": \"number\"" +
                "}";

        StringEntity entity = new StringEntity(json);
        httpPost.setEntity(entity);
        httpPost.setHeader("Accept", "application/json");
        httpPost.setHeader("Content-type", "application/json");
        httpPost.setHeader("Authorization", TOKEN);

        CloseableHttpResponse response = client.execute(httpPost);
        System.out.println(response);
        int statusCode = response.getStatusLine().getStatusCode();
        if (statusCode == 200) {
            System.out.println("SMS sent.");
        } else {
            System.out.println("Error trying to send SMS. \nStatus code: " + statusCode);
        }
    }
}