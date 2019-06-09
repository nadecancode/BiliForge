package cn.charlotte.biliforge.module.impl.live.object;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Gift extends LiveObject {
    private String username, giftName, uid;
    private int count, price;
}
