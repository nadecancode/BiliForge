package cn.charlotte.biliforge.wrapper.bilibili.deserialize.type;

import cn.charlotte.biliforge.wrapper.bilibili.deserialize.AccountElementDeserializer;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;

import java.io.IOException;

public class DoubleElementDeserializer extends AccountElementDeserializer<Double> {

    @Override
    public Double deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        try {
            return Double.parseDouble(jp.getText());
        } catch (Exception ex) {
            return -1.0D;
        }
    }

}
