package org.ingini.mongodb.jongo.example.domain.weapons;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.jongo.marshall.jackson.oid.Id;

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
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weapon {

    public static final String ID = "_id";
    public static final String MATERIAL = "material";

    public static final String DETAILS = "details";

    @Id
    private final String _id;

    private final String material;

    private final WeaponDetails details;

    @JsonCreator
    public Weapon(@JsonProperty(ID) String _id, @JsonProperty(MATERIAL) String material, @JsonProperty(DETAILS) WeaponDetails details) {
        this._id = _id;
        this.material = material;
        this.details = details;
    }

    @JsonProperty(ID)
    public String getId() {
        return _id;
    }

    @JsonProperty(MATERIAL)
    public String getMaterial() {
        return material;
    }

    @JsonProperty(DETAILS)
    public WeaponDetails getDetails() {
        return details;
    }
}
