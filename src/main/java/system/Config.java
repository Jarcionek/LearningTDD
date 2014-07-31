package system;

public class Config {

    public static Application application() {
        return new Application(newsFeedDbAdapter());
    }

    public static NewsFeedDbAdapter newsFeedDbAdapter() {
        return new ThirdPartyNewsFeedDbAdapter();
    }

}
