package system;

public class NewsFeedPaginator {

    public NewsFeed fetch(UserId userId, PageSize pageSize, PageNumber pageNumber) {
        Message d = new Message(4, "d-4");
        Message e = new Message(5, "e-5");
        return new NewsFeed(d, e);
    }

}
