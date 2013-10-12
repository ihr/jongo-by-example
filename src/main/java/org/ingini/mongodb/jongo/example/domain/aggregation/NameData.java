package org.ingini.mongodb.jongo.example.domain.aggregation;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

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
public class NameData {

    public static final String ID = "_id";
    public static final String COUNTER = "counter";

    private final String _id;
    private final int counter;

    public NameData(@JsonProperty(ID) String _id, @JsonProperty(COUNTER) int counter) {
        this._id = _id;
        this.counter = counter;
    }

    public String get_id() {
        return _id;
    }

    public int getCounter() {
        return counter;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof NameData)) return false;

        NameData nameData = (NameData) o;

        return new EqualsBuilder().append(this._id, nameData._id).append(this.counter, nameData.counter).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 51).append(_id).append(counter).toHashCode();
    }
}
