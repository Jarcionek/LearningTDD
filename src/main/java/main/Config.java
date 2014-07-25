package main;

public class Config {

    public static Application getApplication() {
        return new Application(new NewsFeedReader());
    }
}
