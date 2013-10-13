package org.ingini.mongodb.jongo.example.domain.weapons;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Copyright (c) 2013 Ivan Hristov
 * <p/>
 * Licensed under the Apa   che License, Version 2.0 (the "License");
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
public class Sword extends Weapon {

    public Sword(String name) {
        this(name, null, null);
    }

    public Sword(@JsonProperty(ID) String name, @JsonProperty(MATERIAL) String material,
                 @JsonProperty(DETAILS) WeaponDetails details) {
        super(name, material, details);
    }
}
