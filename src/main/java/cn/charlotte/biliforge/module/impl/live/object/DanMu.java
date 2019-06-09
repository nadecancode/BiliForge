package cn.charlotte.biliforge.module.impl.live.object;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DanMu extends LiveObject {
    private String username, message;
    private String userRank, userLevel;
}
