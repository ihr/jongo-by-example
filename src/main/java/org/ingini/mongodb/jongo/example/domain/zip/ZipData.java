package org.ingini.mongodb.jongo.example.domain.zip;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.Id;

/**
 * Copyright (c) 2013 Ivan Hristov
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZipData {

    public static final String ID = "_id";
    public static final String CITY = "city";
    public static final String LOCATION = "loc";
    public static final String POPULATION = "pop";
    public static final String STATE = "state";
    @Id
    private final String _id;
    private final String city;
    private final double[] location;
    private final int population;
    private final String state;

    public ZipData(@JsonProperty(ID) String _id, @JsonProperty(CITY) String city, @JsonProperty(LOCATION) double[] location,
                   @JsonProperty(POPULATION) int population, @JsonProperty(STATE) String state) {
        this._id = _id;
        this.city = city;
        this.location = location;
        this.population = population;
        this.state = state;
    }

    public String get_id() {
        return _id;
    }

    public String getCity() {
        return city;
    }

    public double[] getLocation() {
        return location;
    }

    public int getPopulation() {
        return population;
    }

    public String getState() {
        return state;
    }

}
