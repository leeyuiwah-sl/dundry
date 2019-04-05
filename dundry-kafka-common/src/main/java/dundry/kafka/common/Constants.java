package dundry.kafka.common;

public interface Constants {

    public static String    CLIENT_ID                   ="clifton";
    public static String    GROUP_ID_CONFIG             ="cliftonGroup1";
    public static String    KAFKA_BROKERS               = "localhost:9092";
    public static Integer   MAX_NO_MESSAGE_FOUND_COUNT  =100;
    public static Integer   MAX_POLL_RECORDS            =1;
    public static Integer   MESSAGE_COUNT               =3;
    public static String    OFFSET_RESET_LATEST         ="latest";
    public static String    OFFSET_RESET_EARLIER        ="earliest";
    public static String    TOPIC_NAME                  ="southwest2";
}
