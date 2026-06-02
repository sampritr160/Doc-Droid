package com.docdroid.application.models;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NavigationRequest {
    private String path;
    private Integer depth;
}
