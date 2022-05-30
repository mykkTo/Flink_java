package com.kk.model2.pojo;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Event {
    public String user;
    public String url;
    public Long timestamp;
}
