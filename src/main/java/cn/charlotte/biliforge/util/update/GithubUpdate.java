package cn.charlotte.biliforge.util.update;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
@EqualsAndHashCode
@JsonIgnoreProperties(ignoreUnknown = true)
public class GithubUpdate {

    @JsonProperty("name")
    public String name;

    @JsonProperty("tag_name")
    public String tag;

    @JsonProperty("target_commitish")
    public String branch;

    @JsonProperty("body")
    public String description;

    @JsonProperty("draft")
    public Boolean draft;

    @JsonProperty("prerelease")
    public Boolean preRelease;

}
