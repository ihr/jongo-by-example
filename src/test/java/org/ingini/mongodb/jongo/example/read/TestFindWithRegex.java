package org.ingini.mongodb.jongo.example.read;

import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.ingini.mongodb.jongo.example.domain.weapons.Sword;
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
public class TestFindWithRegex {
    public static final String WEAPONS = "weapons";
    public static final String DB_NAME = "db_for_jongo";

    public static MongoCollection weapons;

    @BeforeClass
    public static void beforeClass() throws UnknownHostException {
        DB mongoDB = new MongoClient("127.0.0.1", 27017).getDB(DB_NAME);

        Jongo jongo = new Jongo(mongoDB);
        weapons = jongo.getCollection(WEAPONS);
    }

    @Test
    public void shouldFindWithRegexOperator() {
        //GIVEN
        weapons.remove();

        weapons.insert("{ \"_id\" : \"Lightbringer\", \"_class\" : \".Sword\" }");
        weapons.insert("{ \"_id\" : \"Longclaw\", \"_class\" : \".Sword\", material: \"Valyrian steel\" }");
        weapons.insert("{ \"_id\" : \"Dark Sister\", \"_class\" : \".Sword\", material: \"Valyrian steel\" }");
        weapons.insert("{ \"_id\" : \"Ice\", \"_class\" : \".Sword\", material: \"Valyrian steel\" }");

        //WHEN
        List<Sword> swordsOfSteel = Lists.newArrayList(weapons
                .find("{" + Sword.MATERIAL + ": {$regex: #}}", "steel.*").as(Sword.class).iterator());

        //THEN
        assertThat(swordsOfSteel).isNotEmpty();
        assertThat(swordsOfSteel).hasSize(3);
    }

    @Test
    public void shouldFindWithPatternCompile() {
        //GIVEN
        weapons.remove();

        weapons.insert("{ \"_id\" : \"Lightbringer\", \"_class\" : \".Sword\" }");
        weapons.insert("{ \"_id\" : \"Longclaw\", \"_class\" : \".Sword\", material: \"Valyrian steel\" }");
        weapons.insert("{ \"_id\" : \"Dark Sister\", \"_class\" : \".Sword\", material: \"Valyrian steel\" }");
        weapons.insert("{ \"_id\" : \"Ice\", \"_class\" : \".Sword\", material: \"Valyrian steel\" }");

        //WHEN
        List<Sword> swordsOfSteel = Lists.newArrayList(weapons
                .find("{" + Sword.MATERIAL + ": #}", Pattern.compile("steel.*")).as(Sword.class).iterator());

        //THEN
        assertThat(swordsOfSteel).isNotEmpty();
        assertThat(swordsOfSteel).hasSize(3);
    }
}
