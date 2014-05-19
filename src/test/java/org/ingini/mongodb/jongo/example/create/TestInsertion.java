package org.ingini.mongodb.jongo.example.create;

import com.google.common.collect.Sets;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import org.ingini.mongodb.jongo.example.domain.characters.*;
import org.ingini.mongodb.jongo.example.domain.weapons.Bow;
import org.ingini.mongodb.jongo.example.domain.weapons.Sword;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.Set;

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
public class TestInsertion {

    public static final String CHARACTERS = "characters";
    public static final String WEAPONS = "weapons";
    public static final String DB_NAME = "cooldb";

    public static MongoCollection characters;
    public static MongoCollection weapons;

    @BeforeClass
    public static void beforeClass() throws UnknownHostException {
        Jongo jongo = new Jongo(new MongoClient("127.0.0.1", 27017).getDB(DB_NAME));
        characters = jongo.getCollection(CHARACTERS);
        weapons = jongo.getCollection(WEAPONS);
    }

    @Test
    public void shouldInsertOneDocumentJustLikeWithinMongoDBShell() {
        characters.insert("{first_name: 'Eddard', second_name: 'Stark'}");
    }

    @Test
    public void shouldInsertOneHeroineWithAutomaticObjectId() {
        //GIVEN
        Heroine aryaStark = Heroine.createHeroineWithoutChildrenAndNoBeasts("Arya", "Stark", //
                new Address("Winterfell", "Westeros", Region.THE_NORTH), new Sword("Needle"));

        //WHEN
        WriteResult insert = characters.insert(aryaStark);

        //THEN
        assertThat(insert.getError()).isNull();
    }

    @Test
    public void shouldInsertOneHeroWithAutomaticObjectId() {
        //GIVEN
        Address castleWinterfell = new Address("Winterfell", "Westeros", Region.THE_NORTH);

        Set<HumanCharacter> children = Sets.newHashSet();
        children.add(Hero.createHeroWithoutChildrenAndNoBeasts("Robb", "Stark", castleWinterfell, new Sword("King in the North")));
        children.add(Heroine.createHeroineWithoutChildrenNoBeastsAndNoWeapon("Sansa", "Stark", castleWinterfell));
        children.add(Heroine.createHeroineWithoutChildrenAndNoBeasts("Arya", "Stark", castleWinterfell, new Sword("Needle")));
        children.add(Hero.createHeroWithoutChildrenNoBeastsAndNoWeapon("Bran", "Stark", castleWinterfell));
        children.add(Hero.createHeroWithoutChildrenNoBeastsAndNoWeapon("Rickon", "Stark", castleWinterfell));
        children.add(Hero.createHeroWithoutChildrenAndNoBeasts("Jon", "Snow", castleWinterfell, new Sword("Longclaw")));

        Hero eddardStark = Hero.createHeroWithoutBeasts("Eddard", "Stark", castleWinterfell, children, new Sword("Ice"));

        //WHEN
        WriteResult insert = characters.insert(eddardStark);

        //THEN
        assertThat(insert.getError()).isNull();
    }

    @Test
    public void shouldInsertOneSwordAndOneBowWithCustomObjectId() {
        //GIVEN
        Sword lightbringer = new Sword("Lightbringer");
        Bow dragonboneBow = new Bow("Dragonbone");

        //WHEN
        WriteResult swordInsertResult = weapons.save(lightbringer);
        WriteResult bowInsertResult = weapons.save(dragonboneBow);

        //THEN
        assertThat(swordInsertResult.getError()).isNull();
        assertThat(bowInsertResult.getError()).isNull();
    }
}