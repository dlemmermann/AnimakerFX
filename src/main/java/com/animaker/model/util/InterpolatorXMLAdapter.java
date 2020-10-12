/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.animaker.model.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import javafx.animation.Interpolator;

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
