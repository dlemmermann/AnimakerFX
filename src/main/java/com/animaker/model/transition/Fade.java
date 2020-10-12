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
package com.animaker.model.transition;

/**
 * Created by lemmi on 23.03.17.
 */
public abstract class Fade extends Transition {

    public enum TextDelivery {
        BY_WORD,
        BY_OBJECT,
        BY_CHARACTER
    }

    public enum TextDirection {
        FORWARD,
        BACKWARD,
        FROM_CENTER,
        FROM_EDGES,
        RANDOM
    }

    protected Fade() {
        super();
    }

    protected Fade(String name) {
        super(name);
    }
}
