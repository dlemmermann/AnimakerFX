package com.animaker.model.transition;

import javax.xml.bind.annotation.XmlType;

/**
 * Created by lemmi on 19.12.16.
 */
@XmlType(name = "blur")
public class Blur extends Transition {

    public Blur() {
        super("Blur");
    }
}

