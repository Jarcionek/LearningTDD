package system;

public class Config {

    public static Application application() {
        return new Application(newsFeedDbAdapter(), newsFeedPaginator());
    }

    private static NewsFeedPaginator newsFeedPaginator() {
        return new NewsFeedPaginator(newsFeedDbAdapter());
    }

    public static NewsFeedDbAdapter newsFeedDbAdapter() {
        return new SortingNewsFeedDbAdapter(new ThirdPartyNewsFeedDbAdapter());
    }

}
