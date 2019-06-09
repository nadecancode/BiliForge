package cn.charlotte.biliforge.wrapper.bilibili.account.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@lombok.Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Data {

    @JsonProperty("card")
    public Card card;

}
