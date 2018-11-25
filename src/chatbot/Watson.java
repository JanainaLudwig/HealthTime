package chatbot;

import com.ibm.watson.developer_cloud.assistant.v1.model.Context;
import com.ibm.watson.developer_cloud.assistant.v1.model.InputData;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageOptions;
import com.ibm.watson.developer_cloud.assistant.v1.model.MessageResponse;
import com.ibm.watson.developer_cloud.assistant.v1.Assistant;
import dashboard.User;

import java.util.ArrayList;

public class Watson {
    private User user;
    private Assistant assistant;
    private Context context;
    private String VERSION_DATE = "2018-11-14",
                   USERNAME = "apikey",
                   PASSWORD = "q_2jXRWVPWFQGJo6lyXz2rDpAEACAYAh7UhoxSsD36Ds",
                   WORKSPACE_ID = "b827fb7f-cfdd-4aa7-907c-f5a8c5653ebc";


    public Watson(User user) {
        this.user = user;
        assistant = new Assistant(VERSION_DATE);
        assistant.setUsernameAndPassword(USERNAME, PASSWORD);
        context = null;
    }


    public ArrayList<String> sendMessage(String userMessage) {
        if (context == null) {
            context = new Context();
            //Send user's first name
            context.put("username", user.getUserName().split(" ")[0]);
        }

        //Create user input
        InputData input = new InputData.Builder(userMessage).build();

        // Options of API request
        MessageOptions options = new MessageOptions.Builder(WORKSPACE_ID)
                .input(input)
                .context(context)
                .build();

        // Send request
        MessageResponse response = this.assistant.message(options).execute();

        context = response.getContext();
        //Send user's first name
        context.put("username", user.getUserName().split(" ")[0]);
        // If context is null, set the context with response so the conversation does not start again
//        if (context == null) {
//
//            context.put("username", 'b');
//        }

        return (ArrayList<String>) response.getOutput().get("text");
    }
}

