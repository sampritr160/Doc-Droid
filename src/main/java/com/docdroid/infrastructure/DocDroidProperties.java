package com.docdroid.infrastructure;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "docdroid")
@Data
public class DocDroidProperties {
    private List<String> indexedDrives;
    private int searchDepth = 5;
    private int maxSearchResults = 100;
    private String theme = "default";
}
