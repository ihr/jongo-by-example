package org.ingini.mongodb.jongo.example.domain.weapons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

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
public class WeaponDetails {

    public static final String PROPHECY = "prophecy";
    public static final String CREATOR = "creator";
    private final String prophecy;
    private final String creator;

    @JsonCreator
    public WeaponDetails(@JsonProperty(PROPHECY) String prophecy, @JsonProperty(CREATOR) String creator) {
        this.prophecy = prophecy;
        this.creator = creator;
    }

    @JsonProperty(PROPHECY)
    public String getProphecy() {
        return prophecy;
    }

    @JsonProperty(CREATOR)
    public String getCreator() {
        return creator;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .appendSuper(super.toString())
                .append("prophecy", prophecy)
                .append("creator", creator)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WeaponDetails)) return false;

        WeaponDetails that = (WeaponDetails) o;

        return new EqualsBuilder().append(this.prophecy, that.prophecy).append(this.creator, that.creator).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(59, 43).append(prophecy).append(creator).toHashCode();
    }
}
