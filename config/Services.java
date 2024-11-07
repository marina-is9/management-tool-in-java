import java.util.Map;

public class ThirdPartyServicesConfig {

    // Postmark service configuration
    public static class Postmark {
        public static final String TOKEN = System.getenv("POSTMARK_TOKEN");
    }

    // SES service configuration
    public static class SES {
        public static final String KEY = System.getenv("AWS_ACCESS_KEY_ID");
        public static final String SECRET = System.getenv("AWS_SECRET_ACCESS_KEY");
        public static final String REGION = System.getenv("AWS_DEFAULT_REGION") != null 
                                           ? System.getenv("AWS_DEFAULT_REGION") 
                                           : "us-east-1";
    }

    // Resend service configuration
    public static class Resend {
        public static final String KEY = System.getenv("RESEND_KEY");
    }

    // Slack service configuration
    public static class Slack {
        public static class Notifications {
            public static final String BOT_USER_OAUTH_TOKEN = System.getenv("SLACK_BOT_USER_OAUTH_TOKEN");
            public static final String CHANNEL = System.getenv("SLACK_BOT_USER_DEFAULT_CHANNEL");
        }
    }

    // Example usage
    public static void main(String[] args) {
        System.out.println("Postmark Token: " + Postmark.TOKEN);
        System.out.println("SES Key: " + SES.KEY);
        System.out.println("SES Secret: " + SES.SECRET);
        System.out.println("SES Region: " + SES.REGION);
        System.out.println("Resend Key: " + Resend.KEY);
        System.out.println("Slack Bot OAuth Token: " + Slack.Notifications.BOT_USER_OAUTH_TOKEN);
        System.out.println("Slack Channel: " + Slack.Notifications.CHANNEL);
    }
}
