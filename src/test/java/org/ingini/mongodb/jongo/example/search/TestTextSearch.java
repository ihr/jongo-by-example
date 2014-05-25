package org.ingini.mongodb.jongo.example.search;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.UnknownHostException;

import static org.fest.assertions.Assertions.assertThat;

/**
 * Copyright (c) 2013 Ivan Hristov
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
public class TestTextSearch {

    public static final String DB_NAME = "movie_db";
    public static final String QUOTES_COLLECTION_NAME = "quotes";

    public static DB mongoDB;

    public static MongoCollection quotes;

    @BeforeClass
    public static void beforeClass() throws UnknownHostException {
        mongoDB = new MongoClient("127.0.0.1", 27017).getDB(DB_NAME);

        Jongo jongo = new Jongo(mongoDB);
        quotes = jongo.getCollection(QUOTES_COLLECTION_NAME);
        quotes.ensureIndex("{movie_name: 'text', 'quotes.quote': 'text', 'quotes.character': 'text'}");
    }

    @Test
    public void french_stop_word_text_search() {
        //GIVEN database pre-loaded with 91'630 entries

        //WHEN
        long numberOfFrenchDocuments = quotes.count("{$text: {$search: 'le', $language: 'french'}}");

        //THEN
        assertThat(numberOfFrenchDocuments).isZero();

        //AND WHEN
        long numberOfEnglishDocuments = quotes.count("{$text: {$search: 'le', $language: 'english'}}");

        //THEN
        assertThat(numberOfEnglishDocuments).isEqualTo(389);
    }

    @Test
    public void phrase_text_search_in_english() {
        //GIVEN database pre-loaded with 91'630 entries

        //WHEN
        long smallCaseDocumentCount = quotes.count("{$text: {$search: '\"pulp fiction\"'}}");

        //THEN
        assertThat(smallCaseDocumentCount).isEqualTo(14);

        //AND WHEN
        long mixedCaseDocumentCount = quotes.count("{$text: {$search: '\"puLp fIcTiOn\"'}}");

        //THEN
        assertThat(smallCaseDocumentCount).isEqualTo(mixedCaseDocumentCount);
    }

    @Test
    public void multiple_words_search_in_english() {
        //GIVEN database pre-loaded with 91'630 entries

        //WHEN
        long count = quotes.count("{$text: {$search: 'pulp fiction'}}");

        //THEN
        assertThat(count).isEqualTo(410);
    }
}
