package org.idelgado.retrofit2soup.model;

public class Repo {
    public final String name;

    public Repo(String name, String url) {
        this.name = name;
    }

    @Override
    public String toString() {
       return String.format("%s", name);
    }
}
