package org.ingini.mongodb.jongo.example.util;

import com.mongodb.MongoClient;
import org.apache.commons.lang3.StringUtils;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.Test;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

import static java.nio.charset.Charset.forName;

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
public class ImportQuotes {

    @Test
    public void importData() throws IOException, URISyntaxException {
        Jongo jongo = new Jongo(new MongoClient("127.0.0.1", 27017).getDB("movie_db"));
        MongoCollection quoteCollection = jongo.getCollection("quotes");

        URI uri = ImportQuotes.class.getClassLoader().getResource("raw/quotes.list").toURI();
        Stream<String> lines = Files.lines(Paths.get(uri), forName("windows-1252"));

        List<List<String>> quotesList = new ArrayList<>();

        lines.skip(14).forEach(s -> {
            List<String> quotes = new ArrayList<>();
            if (quotesList.size() == 0) {
                quotesList.add(quotes);
            }
            quotes = quotesList.get(quotesList.size() - 1);

            if (s.isEmpty() && quotes.get(quotes.size() - 1).isEmpty()) {
                quotesList.add(new ArrayList<>());
            }

            quotes.add(s);
        });

        System.out.println("Quotes lists: " + quotesList.size());

        List<FilmQuote> filmQuotes = new ArrayList<>();
        for (List<String> quotes : quotesList) {
            Set<ActorQuote> actorQuotes = new LinkedHashSet<>();
            Iterator<String> iterator = quotes.subList(1, quotes.size()).iterator();

            String actorName = null;
            String quote = null;
            while (iterator.hasNext()) {
                String s = iterator.next();
                if (s.contains(":")) {
                    if(actorName != null && quote != null) {
                        actorQuotes.add(new ActorQuote(actorName, quote));
                    }
                    actorName = StringUtils.substringBefore(s, ":");
                    quote = StringUtils.substringAfter(s, ":");
                } else if(s.isEmpty()) {
                    actorQuotes.add(new ActorQuote(actorName, quote));
                } else {
                    quote += StringUtils.substringAfter(s, " ");
                }

            }
            FilmQuote filmQuote = new FilmQuote("english", quotes.get(0), actorQuotes);
            filmQuotes.add(filmQuote);
            quoteCollection.insert(filmQuote);
        }

        System.out.println("Film Quotes lists: " + filmQuotes.size());

    }

}
