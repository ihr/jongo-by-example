package org.ingini.mongodb.jongo.example.domain.heroes;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Sets;
import org.ingini.mongodb.jongo.example.domain.beasts.Beast;

import javax.annotation.concurrent.Immutable;
import java.util.Set;



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
public class Hero extends Human {

    @JsonCreator
    Hero(@JsonProperty(ID) String _id,
         @JsonProperty(FIRST_NAME) String firstName,
         @JsonProperty(LAST_NAME) String lastName,
         @JsonProperty(ADDRESS) Address address,
         @JsonProperty(CHILDREN) Set<? extends Human> children,
         @JsonProperty(BEASTS) Set<? extends Beast> beasts) {
        super(_id, firstName, lastName, Gender.MALE, address, children, beasts);
    }

    public static Hero createHeroWithoutChildrenAndNoBeasts(String firstName, String lastName, Address address) {
        return new Hero(null, firstName, lastName, address, null, null);
    }

    public static Hero createHeroWithoutBeasts(String firstName, String lastName, Address address, Set<? extends Human> children) {
        return new Hero(null, firstName, lastName, address, children, null);
    }

    public static Hero addBeast(Hero hero, Beast beast) {
        return new Hero(hero.getId(), hero.getFirstName(), hero.getLastName(), hero.getAddress(),
                hero.getChildren(), Sets.newHashSet(beast));
    }

    public static Hero updateChildren(Hero hero, Set<? extends Human> children) {
        return new Hero(hero.getId(), hero.getFirstName(), hero.getLastName(), hero.getAddress(), children, hero.getBeasts());
    }

}
