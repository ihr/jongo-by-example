package org.ingini.jongo.example.update;

import com.google.common.collect.Sets;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.ingini.jongo.example.model.beasts.DireWolf;
import org.ingini.jongo.example.model.heroes.Hero;
import org.ingini.jongo.example.model.heroes.Heroine;
import org.ingini.jongo.example.model.heroes.Human;
import org.ingini.jongo.example.model.weapons.Sword;
import org.ingini.jongo.example.model.weapons.WeaponDetails;
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
public class TestUpdate {

    public static final String WEAPONS = "weapons";
    public static final String HEROES = "heroes";

    public static DB mongoDB;

    public static MongoCollection weapons;

    public static MongoCollection heroes;

    @BeforeClass
    public static void beforeClass() throws UnknownHostException {
        mongoDB = new MongoClient("127.0.0.1", 27017).getDB("game_of_thrones");

        Jongo jongo = new Jongo(mongoDB);
        weapons = jongo.getCollection(WEAPONS);
        heroes = jongo.getCollection(HEROES);
    }

    @Test
    public void shouldAddFieldToTheLightbringer() {
        //GIVEN
        WeaponDetails details = new WeaponDetails("The one who pulls out this sword from fire will be named Lord's Chosen ...", "Azor Ahai");

        //WHEN
        WriteResult lightbringer = weapons.update("{_id: #}", "Lightbringer").with("{$set: {details: #}}", details);

        //THEN
        assertThat(lightbringer.getError()).isNull();

        //AND WHEN
        Sword sword = weapons.findOne("{_id: 'Lightbringer'}").as(Sword.class);


        //THEN
        assertThat(sword).isNotNull();
    }

    @Test
    public void shouldAddADireWolfForEachStarkChild() {
        //GIVEN
        Hero eddardStark = heroes.findOne(new ObjectId("5186a0da21622e48542ea6af")).as(Hero.class);

        Set<Human> updatedChildren = Sets.newHashSet();

        for (Human child : eddardStark.getChildren()) {
            if (child.getFirstName().equals("Robb")) {
                updatedChildren.add(Hero.addBeast((Hero) child, new DireWolf("Grey Wind")));
            }

            if (child.getFirstName().equals("Sansa")) {
                updatedChildren.add(Heroine.addBeast((Heroine) child, new DireWolf("Lady")));
            }

            if (child.getFirstName().equals("Arya")) {
                updatedChildren.add(Heroine.addBeast((Heroine) child, new DireWolf("Nymeria")));
            }

            if(child.getFirstName().equals("Bran")) {
                updatedChildren.add(Hero.addBeast((Hero) child, new DireWolf("Summer")));
            }

            if(child.getFirstName().equals("Rickon")) {
                updatedChildren.add(Hero.addBeast((Hero) child, new DireWolf("Shaggydog")));
            }

            if(child.getFirstName().equals("Jon")) {
                updatedChildren.add(Hero.addBeast((Hero) child, new DireWolf("Ghost")));
            }
        }

        Hero updatedEddardStark = Hero.updateChildren(eddardStark, updatedChildren);

        //WHEN
        WriteResult save = heroes.save(updatedEddardStark);

        //THEN
        assertThat(save.getError()).isNull();

    }
}
