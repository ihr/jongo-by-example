package org.ingini.jongo.example.model.heroes;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.ingini.jongo.example.model.beasts.Beast;
import org.jongo.marshall.jackson.oid.ObjectId;

import java.util.Set;

import static com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import static com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

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

@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = Human.GENDER)
@JsonSubTypes({
        @JsonSubTypes.Type(value = Heroine.class, name = Gender.Constants.FEMALE_VALUE),
        @JsonSubTypes.Type(value = Hero.class, name = Gender.Constants.MALE_VALUE)
})
public abstract class Human {

    public static final String ID = "_id";
    public static final String FIRST_NAME = "first_name";
    public static final String LAST_NAME = "last_name";
    public static final String GENDER = "gender";
    public static final String ADDRESS = "address";
    public static final String CHILDREN = "children";
    public static final String BEASTS = "beasts";

    @ObjectId
    private String _id;

    private final String firstName;
    private final String lastName;
    private final Gender gender;
    private final Address address;
    private final Set<? extends Human> children;
    private final Set<? extends Beast> beasts;

    protected Human(String firstName, String lastName, Gender gender, Address address,
                    Set<? extends Human> children, Set<? extends Beast> beasts) {
       this(null, firstName, lastName, gender, address, children, beasts);
    }

    protected Human(String _id, String firstName, String lastName, Gender gender, Address address,
                    Set<? extends Human> children, Set<? extends Beast> beasts) {
        this._id = _id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.address = address;
        this.children = children;
        this.beasts = beasts;
    }

    @JsonProperty(ID)
    public String getId() {
        return _id;
    }

    @JsonProperty(FIRST_NAME)
    public String getFirstName() {
        return firstName;
    }

    @JsonProperty(LAST_NAME)
    public String getLastName() {
        return lastName;
    }

    @JsonProperty(GENDER)
    public Gender getGender() {
        return gender;
    }

    @JsonProperty(ADDRESS)
    public Address getAddress() {
        return address;
    }

    @JsonProperty(CHILDREN)
    public Set<? extends Human> getChildren() {
        return children;
    }

    @JsonProperty(BEASTS)
    public Set<? extends Beast> getBeasts() {
        return beasts;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Human human = (Human) o;

        return new EqualsBuilder().append(this.firstName, human.firstName).append(this.lastName, human.lastName)
                .append(this.gender, human.gender).append(this.address, this.address)
                .append(this.beasts, human.beasts).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(7, 61).append(firstName).append(lastName)
                .append(gender).append(address).append(beasts).toHashCode();
    }
}
