import java.io.*;

public class DatabaseAuthInformation {
    private String host;
    private String port;
    private String database_name;
    private String username;
    private String password;

    public DatabaseAuthInformation() {
        this.host = null;
        this.port = null;
        this.database_name = null;
        this.username = null;
        this.password = null;
    }

    public boolean parse_auth_info(String auth_filepath) {
        String host = null;
        String port = null;
        String database_name = null;
        String username = null;
        String password = null;

        /* Parse */
        try {
            File file = new File(auth_filepath);
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                if(line.length() == 0) continue;
                if(line.charAt(0) == '#') continue;

                int line_length = line.length();
                if(line.substring(0, 4).equals("host")) host = line.substring(5, line_length);
                else if(line.substring(0, 4).equals("port")) port = line.substring(5, line_length);
                else if(line.substring(0, 8).equals("database")) database_name = line.substring(9, line_length);
                else if(line.substring(0, 8).equals("username")) username = line.substring(9, line_length);
                else if(line.substring(0, 8).equals("password")) password = line.substring(9, line_length);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        /* Verification */
        boolean flag_verified = true;
        if(host == null) flag_verified = false;
        if(port == null) flag_verified = false;
        if(database_name == null) flag_verified = false;
        if(username == null) flag_verified = false;
        if(password == null) flag_verified = false;
        if(!flag_verified) {
            return false;
        }

        /* Apply parsed values */
        this.host = host;
        this.port = port;
        this.database_name = database_name;
        this.username = username;
        this.password = password;

        return true;
    }


    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getDatabase_name() {
        return database_name;
    }


    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }


    public void debug_print() {
        System.out.println("Host: " + this.host + ":" + this.port + "/" + this.database_name + "@" + this.username + ":" + this.password);
    }

}