package hbw.controller.hearing.request.model;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
"SummonsNumber",
"Token"
})
public class DeleteSummon {

@JsonProperty("SummonsNumber")
private String summonsNumber;
@JsonProperty("Token")
private String token;
@JsonIgnore
private Map<String, Object> additionalProperties = new HashMap<String, Object>();

@JsonProperty("SummonsNumber")
public String getSummonsNumber() {
return summonsNumber;
}

@JsonProperty("SummonsNumber")
public void setSummonsNumber(String summonsNumber) {
this.summonsNumber = summonsNumber;
}

@JsonProperty("Token")
public String getToken() {
return token;
}

@JsonProperty("Token")
public void setToken(String token) {
this.token = token;
}

@JsonAnyGetter
public Map<String, Object> getAdditionalProperties() {
return this.additionalProperties;
}

@JsonAnySetter
public void setAdditionalProperty(String name, Object value) {
this.additionalProperties.put(name, value);
}

}