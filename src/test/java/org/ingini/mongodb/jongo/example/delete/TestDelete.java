package org.ingini.mongodb.jongo.example.delete;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import org.ingini.mongodb.jongo.example.domain.weapons.Sword;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.BeforeClass;
import org.junit.Test;

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
public class TestDelete {

    public static final String WEAPONS = "weapons";
    public static final String DB_NAME = "db_for_jongo";

    public static DB mongoDB;

    public static MongoCollection weapons;

    @BeforeClass
    public static void beforeClass() throws UnknownHostException {
        mongoDB = new MongoClient("127.0.0.1", 27017).getDB(DB_NAME);

        Jongo jongo = new Jongo(mongoDB);
        weapons = jongo.getCollection(WEAPONS);
    }


    @Test
    public void shouldDeleteSingleDocumentById() {
        //GIVEN
        String name = "Lightbringer";
        weapons.save(new Sword(name));

        //WHEN
        WriteResult result = weapons.remove("{id: #}", name);

        //THEN
        assertThat(result.getError()).isNull();


    }
}
