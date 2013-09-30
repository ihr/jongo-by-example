package org.ingini.jongo.example.read;

import com.google.common.collect.Lists;
import com.mongodb.*;
import org.ingini.jongo.example.model.weapons.Sword;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.List;
import java.util.regex.Pattern;

import static org.fest.assertions.Assertions.assertThat;

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
public class TestFind {
    public static final String WEAPONS = "weapons";

    public static MongoCollection weapons;
    private static DB mongoDB;

    @BeforeClass
    public static void beforeClass() throws UnknownHostException {
        mongoDB = new MongoClient("127.0.0.1", 27017).getDB("game_of_thrones");

        Jongo jongo = new Jongo(mongoDB);
        weapons = jongo.getCollection(WEAPONS);
    }

    @Test
    public void shouldFindWithRegexOperator() {
        //GIVEN
        weapons.remove();

        weapons.insert("{ \"_id\" : \"Lightbringer\" }");
        weapons.insert("{ \"_id\" : \"Longclaw\", material: \"Valyrian steel\" }");
        weapons.insert("{ \"_id\" : \"Dark Sister\", material: \"Valyrian steel\" }");
        weapons.insert("{ \"_id\" : \"Ice\", material: \"Valyrian steel\" }");

        //WHEN
        List<Sword> swordsOfSteel = Lists.newArrayList(weapons
                .find("{" + Sword.MATERIAL + ": {$regex: #}}", "steel.*").as(Sword.class));

        //THEN
        assertThat(swordsOfSteel).isNotEmpty();
        assertThat(swordsOfSteel).hasSize(3);

        System.out.println("weapons count: " + weapons.count());

        DBCollection weaponsMongoDBCollection = mongoDB.getCollection("weapons");
        weaponsMongoDBCollection.find(new BasicDBObject("$where", "db.weapons.insert({'id': 'test'})"));

        System.out.println("weapons count: " + weapons.count());
    }

    @Test
    public void shouldFindWithPatternCompile() {
        //GIVEN
        weapons.remove();

        weapons.insert("{ \"_id\" : \"Lightbringer\" }");
        weapons.insert("{ \"_id\" : \"Longclaw\", material: \"Valyrian steel\" }");
        weapons.insert("{ \"_id\" : \"Dark Sister\", material: \"Valyrian steel\" }");
        weapons.insert("{ \"_id\" : \"Ice\", material: \"Valyrian steel\" }");

        //WHEN
        List<Sword> swordsOfSteel = Lists.newArrayList(weapons
                .find("{" + Sword.MATERIAL + ": #}", Pattern.compile("steel.*")).as(Sword.class));

        //THEN
        assertThat(swordsOfSteel).isNotEmpty();
        assertThat(swordsOfSteel).hasSize(3);
    }
}
