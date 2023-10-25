package pl.akulau.consultest.controller;

import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.akulau.consultest.config.exception.ConsulDecoderException;
import pl.akulau.consultest.model.ModelData;

@RestController
@RequiredArgsConstructor
public class TestConsulController {

    public static final String CURRENCY_DIMENSION = "base.currency";

    private final ConsulClient client;

    private final ObjectMapper mapper;

    @GetMapping("/hello")
    public List<ModelData> greeting() {
        return extractValueFromJsonList(CURRENCY_DIMENSION);
    }

    private String getDecodedValue(String path) {
        return Optional.ofNullable(client.getKVValue(path))
                .map(Response::getValue)
                .map(GetValue::getDecodedValue)
                .orElseThrow(() -> new ConsulDecoderException("Can't get value from consul: " + CURRENCY_DIMENSION));
    }

    private List<ModelData> extractValueFromJsonList(String path) {
        try {
            return mapper.readValue(getDecodedValue(path), new TypeReference<>() {
            });
        } catch (Exception e) {
            throw new ConsulDecoderException("Can't decode value from json list path: " + path);
        }
    }
}
