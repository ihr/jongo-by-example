package org.ingini.mongodb.jongo.example.util;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class ActorQuote {

    public static final String CHARACTER = "character";
    public static final String QUOTE = "quote";

    @JsonProperty(CHARACTER)
    private String character;

    @JsonProperty(QUOTE)
    private String quote;

    public ActorQuote(String character, String quote) {
        this.character = character;
        this.quote = quote;
    }

    public String getActorName() {
        return character;
    }

    public String getQuote() {
        return quote;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ActorQuote)) return false;

        ActorQuote that = (ActorQuote) o;

        if (character != null ? !character.equals(that.character) : that.character != null) return false;
        if (quote != null ? !quote.equals(that.quote) : that.quote != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = character != null ? character.hashCode() : 0;
        result = 31 * result + (quote != null ? quote.hashCode() : 0);
        return result;
    }
}
