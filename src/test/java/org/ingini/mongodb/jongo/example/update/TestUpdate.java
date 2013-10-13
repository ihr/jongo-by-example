package org.ingini.mongodb.jongo.example.update;

import com.google.common.collect.Sets;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.ingini.mongodb.jongo.example.domain.beasts.DireWolf;
import org.ingini.mongodb.jongo.example.domain.characters.Hero;
import org.ingini.mongodb.jongo.example.domain.characters.Heroine;
import org.ingini.mongodb.jongo.example.domain.characters.HumanCharacter;
import org.ingini.mongodb.jongo.example.domain.weapons.Weapon;
import org.ingini.mongodb.jongo.example.domain.weapons.WeaponDetails;
import org.ingini.mongodb.jongo.example.util.CollectionManager;
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
    public static final String CHARACTERS = "characters";
    public static final String DB_NAME = "db_for_jongo";

    public static DB mongoDB;

    public static MongoCollection weapons;

    public static MongoCollection characters;

    @BeforeClass
    public static void beforeClass() throws UnknownHostException {
        mongoDB = new MongoClient("127.0.0.1", 27017).getDB(DB_NAME);

        CollectionManager.cleanAndFill(mongoDB, "weapons.json", WEAPONS);

        Jongo jongo = new Jongo(mongoDB);
        weapons = jongo.getCollection(WEAPONS);
        characters = jongo.getCollection(CHARACTERS);
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
        Weapon weapon = weapons.findOne("{_id: 'Lightbringer'}").as(Weapon.class);


        //THEN
        assertThat(weapon).isNotNull();
    }

    @Test
    public void shouldAddADireWolfForEachStarkChild() {
        //GIVEN
        Hero eddardStark = characters.findOne(new ObjectId("5259a7fd3004e5974542c5e9")).as(Hero.class);

        Set<HumanCharacter> updatedChildren = Sets.newHashSet();

        for (HumanCharacter child : eddardStark.getChildren()) {
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
        WriteResult save = characters.save(updatedEddardStark);

        //THEN
        assertThat(save.getError()).isNull();

    }
}
