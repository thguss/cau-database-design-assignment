import java.sql.*;
import java.util.*;

public class market {
    public static void main(String[] argv) {
        DatabaseAuthInformation db_info = new DatabaseAuthInformation(); //디비정보 호출
        db_info.parse_auth_info("auth/mysql.auth"); //sql 정보 파싱

        String connection_url = String.format("jdbc:mysql://%s:%s/%s?useUnicode=true&" +
                "useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC", db_info.getHost(), db_info.getPort(), db_info.getDatabase_name());

        Create_Table(db_info.getUsername(), db_info.getPassword(), connection_url);    //테이블 생성
        Create_Tuple(connection_url, db_info.getUsername(), db_info.getPassword());    //튜플 생성

        Search(connection_url, db_info.getUsername(), db_info.getPassword());
    }

    public static void Search(String url, String userid, String passwd){
        Scanner sc = new Scanner(System.in);
        String where;
        System.out.print("조건문: "); where = sc.next();
        System.out.println();

        String query = String.format("select * from member where "+where); //where문에서 조건문 입력받아 쿼리문 실행


        try (Connection db_connection = DriverManager.getConnection(url,userid, passwd);
             Statement db_statement = db_connection.createStatement()){

            ResultSet stm = db_statement.executeQuery(query); //select
            while (stm.next()) {
                System.out.println("< " + stm.getString(1) + ", " + stm.getString(2)+ ", "+
                        stm.getFloat(3)+", "+stm.getString(4)+", "+stm.getString(5)+" >");
            }
            //stm.close();

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void Create_Tuple(String url, String userid, String passwd){
        //동적
        try{
            Connection db_connection = DriverManager.getConnection(url, userid, passwd);

            String query = "insert into member (ID, name, phone, town, level) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement st2 = db_connection.prepareStatement(query);
            st2.setString(1,"sohyeon0530");
            st2.setString(2, "김소현");
            st2.setFloat(3, 9217.1982f);
            st2.setString(4, "서울 동작구");
            st2.setString(5, "골드");
            st2.executeUpdate();
            st2.setString(1,"jiin0620");
            st2.setString(2, "강지인");
            st2.setFloat(3, 1234.5678f);
            st2.setString(4, "서울 서초구");
            st2.setString(5, "플래티넘");
            st2.executeUpdate();

        }catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public static void Create_Table(String userid, String passwd, String url){
        //디비 연결

        //쿼리 작성(테이블 생성문)
        String query = "CREATE TABLE member(ID VARCHAR(50) NOT NULL, name VARCHAR(50) NOT NULL, phone FLOAT(8,4) NOT NULL, "+
                "town VARCHAR(50) NOT NULL, level VARCHAR(50) NOT NULL, primary key(ID))";

        //정적 sql
        try (Connection db_connection = DriverManager.getConnection(url,userid, passwd);
             Statement db_statement = db_connection.createStatement()){ //상태 객체 만들고
            db_statement.executeUpdate(query); //쿼리문 실행

        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

}