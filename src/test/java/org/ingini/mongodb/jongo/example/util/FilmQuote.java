package org.ingini.mongodb.jongo.example.util;

import com.fasterxml.jackson.annotation.JsonProperty;

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
public class FilmQuote {

    public static final String MOVIE_NAME = "movie_name";
    public static final String QUOTES = "quotes";
    public static final String LANGUAGE = "language";

    @JsonProperty(MOVIE_NAME)
    private String movieName;

    @JsonProperty(QUOTES)
    private Set<ActorQuote> quotes;

    @JsonProperty(LANGUAGE)
    private String language;

    public FilmQuote(String language, String movieName, Set<ActorQuote> quotes) {
        this.language = language;
        this.movieName = movieName;
        this.quotes = quotes;
    }

    public String getMovieName() {
        return movieName;
    }

    public Set<ActorQuote> getActorQuotes() {
        return quotes;
    }

    public String getLanguage() {
        return language;
    }
}
