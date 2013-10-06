package org.ingini.mongodb.jongo.example.model.heroes;

import static org.ingini.mongodb.jongo.example.model.heroes.Gender.Constants.*;

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
public enum Gender {
    MALE(MALE_VALUE), FEMALE(FEMALE_VALUE);

    Gender(String genderString) {
    }


    public static class Constants {
        public static final String MALE_VALUE = "MALE";
        public static final String FEMALE_VALUE = "FEMALE";
    }
}
