package org.ingini.mongodb.jongo.example.model.aggregation;

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
public class StateData {

    public static final String ID = "_id";
    public static final String TOTAL_POPULATION = "totalPop";

    @Id
    public final String _id;
    public final int totalPopulation;

    public StateData(@JsonProperty(ID) String _id, @JsonProperty(TOTAL_POPULATION) int totalPopulation) {
        this._id = _id;
        this.totalPopulation = totalPopulation;
    }

    public String getId() {
        return _id;
    }

    public int getTotalPopulation() {
        return totalPopulation;
    }
}
