package com.fullcycle.imersaoms.model;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PubSubMessage {

    String key;
    String value;
}
