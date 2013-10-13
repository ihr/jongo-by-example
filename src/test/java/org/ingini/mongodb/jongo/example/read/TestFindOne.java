package org.ingini.mongodb.jongo.example.read;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mongodb.DB;
import com.mongodb.DBObject;
import com.mongodb.MongoClient;
import org.bson.LazyDBList;
import org.bson.types.ObjectId;
import org.ingini.mongodb.jongo.example.domain.characters.Gender;
import org.ingini.mongodb.jongo.example.domain.characters.Hero;
import org.ingini.mongodb.jongo.example.domain.characters.Heroine;
import org.ingini.mongodb.jongo.example.domain.characters.HumanCharacter;
import org.ingini.mongodb.jongo.example.util.CollectionManager;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.jongo.ResultHandler;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.net.UnknownHostException;

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
public class TestFindOne {

    public static final String CHARACTERS = "characters";
    public static final String DB_NAME = "db_for_jongo";

    public static DB mongoDB;

    public static MongoCollection characters;

    @BeforeClass
    public static void beforeClass() throws UnknownHostException {
        mongoDB = new MongoClient("127.0.0.1", 27017).getDB(DB_NAME);

        CollectionManager.cleanAndFill(mongoDB, "characters.json", CHARACTERS);

        Jongo jongo = new Jongo(mongoDB);
        characters = jongo.getCollection(CHARACTERS);
    }

    @Test
    public void shouldFindOneEntryBasedOnOIDOperator() {
        //GIVEN

        //WHEN
        Hero hero = characters.findOne("{_id : {$oid: #}}", "52516b563004ba6b745e864f").as(Hero.class);

        //THEN
        assertThat(hero).isNotNull();

    }

    @Test
    public void shouldFindOneEntryBasedOnObjectId() {
        //GIVEN

        //WHEN
        Hero hero = characters.findOne(new ObjectId("52516b563004ba6b745e864f")).as(Hero.class);

        //THEN
        assertThat(hero).isNotNull();

    }


    @Test
    public void shouldFindOneEntryBasedOnGenderAndFirstName() {
        //GIVEN

        //WHEN
        Heroine heroine = characters.findOne("{" + HumanCharacter.GENDER + ": #, " + HumanCharacter.FIRST_NAME + ": #}", Gender.FEMALE, "Arya")//
                .as(Heroine.class);

        //THEN
        assertThat(heroine).isNotNull();

    }

    @Test
    public void shouldFindOneArrayElement() {
        //GIVEN

        //WHEN
        Heroine heroine = characters.findOne("{_id : {$oid: #}}", "52516b563004ba6b745e864f").projection("{children: {$elemMatch: {" + HumanCharacter.FIRST_NAME + ": #, " + //
                HumanCharacter.LAST_NAME + ": #}}}", "Sansa", "Stark").map(new ResultHandler<Heroine>() {
            @Override
            public Heroine map(DBObject result) {
                LazyDBList o = (LazyDBList) result.get(HumanCharacter.CHILDREN);
                DBObject basicDbObject = (DBObject) o.get(0);
                ObjectMapper objectMapper = new ObjectMapper();
                String content = basicDbObject.toString();
                try {
                    return objectMapper.readValue(content, Heroine.class);
                } catch (IOException e) {
                    throw new IllegalStateException("Unable to deserialize " + content);
                }
            }
        });

        //THEN
        assertThat(heroine).isNotNull();

    }
}
