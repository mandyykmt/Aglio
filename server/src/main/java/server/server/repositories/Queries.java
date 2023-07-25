package server.server.repositories;

public class Queries {

        public static final String SQL_GET_USER_BY_EMAIL = """
                select * from users where email = ?
                """;

        public static final String SQL_INSERT_USER = """
                insert into users
                (username, password, firstName, lastName, email, phone, country, postal)
                values (?,?,?,?,?,?,?,?)
                """;

        public static final String SQL_POST_LOGIN_ACTIVITY = """
                insert into login_activity
                (email, password, dateTime)
                values (?,?,?)
                """;
}
