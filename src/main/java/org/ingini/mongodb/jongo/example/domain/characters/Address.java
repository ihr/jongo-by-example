package org.ingini.mongodb.jongo.example.domain.characters;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.annotation.concurrent.Immutable;

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
@Immutable
public class Address {

    public static final String CASTLE = "castle";
    public static final String CONTINENT = "continent";
    public static final String REGION = "region";

    private final String castle;
    private final String continent;
    private final Region region;

    @JsonCreator
    public Address(@JsonProperty(CASTLE) String castle, @JsonProperty(CONTINENT) String continent,
                   @JsonProperty(REGION) Region region) {
        this.castle = castle;
        this.continent = continent;
        this.region = region;
    }

    @JsonProperty(CASTLE)
    public String getCastle() {
        return castle;
    }

    @JsonProperty(CONTINENT)
    public String getContinent() {
        return continent;
    }

    @JsonProperty(REGION)
    public Region getRegion() {
        return region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Address address = (Address) o;

        return new EqualsBuilder()
                .append(this.castle, address.castle)
                .append(this.continent, address.continent)
                .append(this.region, this.region).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(11, 43).append(castle).append(continent).append(region).toHashCode();
    }
}
