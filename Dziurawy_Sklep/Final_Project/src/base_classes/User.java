package base_classes;

public class User {
    private static int id;
    private static String nickname;
    private static int credentials;
    private static boolean logged=false;

    public boolean isLogged() {
        return logged;
    }

    public void setLogged(boolean logged) {
        this.logged = logged;
    }

    public User(){}
    public User(int id, String nickname, int credentials, boolean logged){
        this.id=id;
        this.nickname=nickname;
        this.credentials=credentials;
        this.logged=logged;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getCredentails() {
        return credentials;
    }

    public void setCredentails(int credentails) {
        this.credentials = credentails;
    }


}
