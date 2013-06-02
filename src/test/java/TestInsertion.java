import com.google.common.collect.Sets;
import com.mongodb.DB;
import com.mongodb.DBCollection;
import com.mongodb.Mongo;
import com.mongodb.WriteResult;
import org.fest.assertions.Assertions;
import org.ingini.jongo.example.model.heroes.*;
import org.ingini.jongo.example.model.weapons.Sword;
import org.ingini.monogo.testbed.MongoManager;
import org.ingini.monogo.testbed.annotation.MongoTestBedCollection;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.BeforeClass;
import org.junit.ClassRule;
import org.junit.Test;

import javax.inject.Inject;
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
public class TestInsertion {

    public static final String HEROES = "heroes";
    public static final String WEAPONS = "weapons";

    @ClassRule
    public static MongoManager mongoManager = MongoManager.mongoConnect("mongodb://127.0.0.1:27017");

    @MongoTestBedCollection(name = HEROES, location = "heroes.json")
    public static DBCollection collection;

    @Inject
    public static Mongo mongo;

    @Inject
    public static DB mongoDB;

    public static MongoCollection heroes;
    public static MongoCollection weapons;

    @BeforeClass
    public static void beforeClass() {
        Jongo jongo = new Jongo(mongoDB);
        heroes = jongo.getCollection(HEROES);
        weapons = jongo.getCollection(WEAPONS);
    }

    @Test
    public void shouldInsertOneHeroineWithAutomaticObjectId() {
        //GIVEN
        Heroine aryaStark = Heroine.createHeroineWithoutChildrenAndNoBeasts("Arya", "Stark", //
                new Address("Winterfell", "Westeros", Region.THE_NORTH));

        //WHEN
        WriteResult insert = heroes.insert(aryaStark);

        //THEN
        Assertions.assertThat(insert.getError()).isNull();
    }

    @Test
    public void shouldInsertOneHeroWithAutomaticObjectId() {
        //GIVEN
        Address castleWinterfell = new Address("Winterfell", "Westeros", Region.THE_NORTH);

        Set<Human> children = Sets.newHashSet();
        children.add(Hero.createHeroWithoutChildrenAndNoBeasts("Robb", "Stark", castleWinterfell));
        children.add(Heroine.createHeroineWithoutChildrenAndNoBeasts("Sansa", "Stark", castleWinterfell));
        children.add(Heroine.createHeroineWithoutChildrenAndNoBeasts("Arya", "Stark", castleWinterfell));
        children.add(Hero.createHeroWithoutChildrenAndNoBeasts("Bran", "Stark", castleWinterfell));
        children.add(Hero.createHeroWithoutChildrenAndNoBeasts("Rickon", "Stark", castleWinterfell));
        children.add(Hero.createHeroWithoutChildrenAndNoBeasts("Jon", "Snow", castleWinterfell));

        Hero eddardStark = Hero.createHeroWithoutBeasts("Eddard", "Stark", castleWinterfell, children);

        //WHEN
        WriteResult insert = heroes.insert(eddardStark);

        //THEN
        Assertions.assertThat(insert.getError()).isNull();
    }

    @Test
    public void shouldInsertOneSwordWithCustomObjectId() {
        //GIVEN
        Sword lightbringer = new Sword("Lightbringer", null, null);

        //WHEN
        WriteResult insert = weapons.save(lightbringer);

        //THEN
        Assertions.assertThat(insert.getError()).isNull();
    }
}