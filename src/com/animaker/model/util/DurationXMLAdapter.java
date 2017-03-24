package com.animaker.model.util;

import javafx.util.Duration;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by lemmi on 22.03.17.
 */
public class DurationXMLAdapter extends XmlAdapter<Long, Duration> {

    @Override
    public Duration unmarshal(Long v) throws Exception {
        return Duration.millis(v);
    }

    @Override
    public Long marshal(Duration v) throws Exception {
        return (long) v.toMillis();
    }
}
