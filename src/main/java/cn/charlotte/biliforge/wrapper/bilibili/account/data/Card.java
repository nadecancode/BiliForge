package cn.charlotte.biliforge.wrapper.bilibili.account.data;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class Card {

    @JsonProperty("mid")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Long uid;

    @JsonProperty("name")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String name;

    @JsonProperty("approve")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Boolean approve;

    @JsonProperty("sex")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String gender;

    @JsonProperty("rank")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String rank;

    @JsonProperty("face")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private String faceUrl;

    @JsonProperty("fans")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer subscribers;

    @JsonProperty("attention")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private Integer following;

}
