package dk.sdu.petni23.common.gamelogging;

import dk.sdu.petni23.common.GameData;

import java.util.ArrayList;
import java.util.List;

public class GameLog {

    private long timeVisible = 6000; // in milliseconds
    private List<Message> messages = new ArrayList<>();

    public void write(String msg) {
        messages.add(new Message(msg, GameData.getCurrentMillis()));
    }

    /**
     * returns number of messages specified in parameter, if they within the timeVisible cutoff
     * **/
    public List<Message> getMessages(int maxAmount) {
        List<Message> r = new ArrayList<>();
        for (var m : messages.reversed()) {
            if (m.createdAt + timeVisible > GameData.getCurrentMillis()) {
                r.add(m);
                if (r.size() >= maxAmount) break;
            }
        }
        return r;
    }

    public void setTimeVisible(long timeVisible) {
        this.timeVisible = timeVisible;
    }

    public long getTimeVisible() {
        return timeVisible;
    }

    public record Message(String msg, long createdAt) {
    }
}
