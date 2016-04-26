package org.idelgado.retrofit2soup.model;

public class Contributor {
    public final String login;
    public final int contributions;

    public Contributor(String login, int contributions) {
        this.login = login;
        this.contributions = contributions;
    }

    @Override
    public String toString() {
       return String.format("%5d %s", contributions, login);
    }
}
