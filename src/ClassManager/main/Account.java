package ClassManager.main;

import java.util.Objects;

public class Account{
    private String username;
    private String password;

    public Account(){
        username = "";
        password = "";
    }

    public Account(String username, String password){
        this.username = username;
        this.password = password;
    }

    public Account(String str){
        int pos = str.indexOf(' ');
        this.username = str.substring(0, pos);
        this.password = str.substring(pos + 1, str.length());
    }

    @Override
    public String toString() {
        return username + " " + password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(username, account.username) &&
                Objects.equals(password, account.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, password);
    }
}