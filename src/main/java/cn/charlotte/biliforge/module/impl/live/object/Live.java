package cn.charlotte.biliforge.module.impl.live.object;

import com.google.common.collect.Lists;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
public class Live {

    private List<LiveObject> liveObjects = Lists.newCopyOnWriteArrayList();
    private int watching = 0;
    private int objectLimit = 10;
    private LiveStatus liveStatus = LiveStatus.WAITING;

    public void addLiveObject(LiveObject liveObject) {
        if (this.liveObjects.size() > this.objectLimit) {
            this.liveObjects.remove(0);
        }

        this.liveObjects.add(liveObject);
    }

    public void reset() {
        this.liveObjects.clear();
        this.watching = 0;
        this.objectLimit = 10;
        this.liveStatus = LiveStatus.WAITING;
    }

    public enum LiveStatus {
        CONNECTED,
        ERROR,
        WAITING
    }

}
