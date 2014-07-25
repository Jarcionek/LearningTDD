package main;

public class Message {

    private final long time;
    private final String msg;

    public Message(long time, String msg) {
        this.time = time;
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public String getMsg() {
        return msg;
    }

}
