package com.animaker.model.util;

import javafx.animation.Interpolator;
import javafx.util.Duration;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by lemmi on 22.03.17.
 */
// TODO: support more complex interpolators (e.g. spline, tangent) Jasper Potts Interpolator editor?
public class InterpolatorXMLAdapter extends XmlAdapter<String, Interpolator> {


    private static final String DISCRETE = "discrete";
    private static final String EASE_BOTH = "ease-both";
    private static final String EASE_IN = "ease-in";
    private static final String EASE_OUT = "ease-out";
    private static final String LINEAR = "linear";

    @Override
    public Interpolator unmarshal(String v) throws Exception {
        if (v == null || v.trim().equals("") || v.trim().equals("unknown")) {
            return null;
        }

        switch (v.trim()) {
            case DISCRETE:
                return Interpolator.DISCRETE;
            case EASE_BOTH:
                return Interpolator.EASE_BOTH;
            case EASE_IN:
                return Interpolator.EASE_IN;
            case EASE_OUT:
                return Interpolator.EASE_OUT;
            case LINEAR:
                return Interpolator.LINEAR;
        }

        return Interpolator.LINEAR;
    }

    @Override
    public String marshal(Interpolator v) throws Exception {
        if (v == null) {
            return "";
        }

        if (v.equals(Interpolator.DISCRETE)) {
            return DISCRETE;
        } else if (v.equals(Interpolator.EASE_BOTH)) {
            return EASE_BOTH;
        } else if (v.equals(Interpolator.EASE_IN)) {
            return EASE_IN;
        } else if (v.equals(Interpolator.EASE_OUT)) {
            return EASE_OUT;
        } else if (v.equals(Interpolator.LINEAR)) {
            return LINEAR;
        }

        return "unknown";
    }
}
