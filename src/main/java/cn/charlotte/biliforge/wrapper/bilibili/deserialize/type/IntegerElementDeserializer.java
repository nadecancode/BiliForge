package cn.charlotte.biliforge.wrapper.bilibili.deserialize.type;

import cn.charlotte.biliforge.wrapper.bilibili.deserialize.AccountElementDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;

public class IntegerElementDeserializer extends AccountElementDeserializer<Integer> {

    @Override
    public Integer deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            return Integer.parseInt(jp.getText());
        } catch (Exception ex) {
            return -1;
        }
    }

}
