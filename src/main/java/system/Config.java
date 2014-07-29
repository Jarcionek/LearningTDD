package system;

public class Config {

    public static Application application() {
        return new Application(newsFeedReader());
    }

    public static NewsFeedReader newsFeedReader() {
        return new SortingNewsFeedReader(new ThirdPartyNewsFeedReader());
    }

}
