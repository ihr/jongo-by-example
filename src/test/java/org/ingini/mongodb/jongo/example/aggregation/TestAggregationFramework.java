package org.ingini.mongodb.jongo.example.aggregation;

import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.ingini.mongodb.jongo.example.model.aggregation.NameData;
import org.ingini.mongodb.jongo.example.model.aggregation.StateData;
import org.ingini.mongodb.jongo.example.util.CollectionManager;
import org.jongo.Jongo;
import org.jongo.MongoCollection;
import org.junit.BeforeClass;
import org.junit.Test;

import java.net.UnknownHostException;
import java.util.List;

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
public class TestAggregationFramework {

    public static final String DB_NAME = "aggregation_test_db";
    public static final String ZIP_CODES_COLLECTION_NAME = "zip_codes";
    public static final String NAME_DAYS_COLLECTION_NAME = "name_days";

    public static DB mongoDB;

    public static MongoCollection zipCodes;
    public static MongoCollection nameDays;

    @BeforeClass
    public static void beforeClass() throws UnknownHostException {
        mongoDB = new MongoClient("127.0.0.1", 27017).getDB(DB_NAME);

        Jongo jongo = new Jongo(mongoDB);
        zipCodes = jongo.getCollection(ZIP_CODES_COLLECTION_NAME);
        nameDays = jongo.getCollection(NAME_DAYS_COLLECTION_NAME);
    }

    /**
     * Example taken from http://docs.mongodb.org/manual/tutorial/aggregation-examples/#aggregations-using-the-zip-code-data-set
     * Command line import: mongoimport --drop -d aggregation_test_db -c zipcodes zips.json
     * <p>
     *      db.zipcodes.aggregate(  { $group : { _id : "$state", totalPop : { $sum : "$pop" } } },
     *                              { $match : {totalPop : { $gt : 10*1000*1000 } } } )
     * </p>
     */
    @Test
    public void shouldFindAllStatesWithPopulationOver10Millions() {
        //GIVEN
        CollectionManager.cleanAndFill(mongoDB, "zips.json", "zip_codes");
        int lowerLimit = 10 * 1000 * 1000;

        //WHEN
        List<StateData> results = zipCodes.aggregate("{ $group : { _id : '$state', totalPop : { $sum : '$pop' } } }")
                .and("{ $match : { totalPop : { $gt : # } } }", lowerLimit).as(StateData.class);

        //THEN
        assertThat(results).hasSize(7);
    }

    /**
     * Example taken from https://github.com/mongodb/mongo-ruby-driver/wiki/Aggregation-Framework-Examples
     * Command line import: mongoimport --drop --db aggregation_test_db --collection name_days name_days.json
     * <p>
     *      db.name_days.aggregate({$project : {names : 1, _id : 0}},
     *                             {$unwind : '$names'},
     *                             {$group : {_id: '$names', counter: {$sum: 1}}},
     *                             {$sort : {counter: -1}},
     *                             {$limit : 10});
     * </p>
     */
    @Test
    public void shouldFindThe10MostCommonNames() {
        //GIVEN
        CollectionManager.cleanAndFill(mongoDB, "name_days.json", "name_days");
        int limit = 10;

        //WHEN
        List<NameData> results = nameDays.aggregate("{$project : {names : 1, _id : 0}}")
                .and("{$unwind : '$names'}")
                .and("{$group : {_id: '$names', counter: {$sum: 1}}}")
                .and("{$sort : {counter: -1}}")
                .and("{$limit : #}", limit)
                .as(NameData.class);

        //THEN
        assertThat(results).hasSize(10);
        assertThat(results.get(0)).isEqualTo(new NameData("Jana", 21));

    }
}
