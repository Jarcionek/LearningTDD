package main;

public class Config {

    public static NewsFeedEndpoints getNewsFeedEndpoints() {
        return new Application(new SortingNewsFeedReader(new ThirdPartyNewsFeedReader()), new NewsFeedReducer(), new NewsFeedPaginator());
    }

}
