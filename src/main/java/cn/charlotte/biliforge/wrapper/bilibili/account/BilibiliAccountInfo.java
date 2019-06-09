package cn.charlotte.biliforge.wrapper.bilibili.account;

import cn.charlotte.biliforge.wrapper.bilibili.account.data.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@JsonIgnoreProperties(ignoreUnknown = true)
@ToString
public class BilibiliAccountInfo {

    @JsonProperty("data")
    private Data data;

}

