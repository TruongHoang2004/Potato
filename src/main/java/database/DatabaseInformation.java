package database;

public interface DatabaseInformation {

    String HOST_NAME = "localhost";
    String PASS_WORD = "truonghaioop2023";
    String DB_NAME = "word";
    String USER_NAME = "Potato";
    String PORT = "3306";
    String MYSQL_URL =
            "jdbc:mysql://" + HOST_NAME + ":" + PORT + "/" + DB_NAME;
}
