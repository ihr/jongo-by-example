package org.ingini.mongodb.jongo.example.geospatial;

import com.google.common.collect.Lists;
import com.mongodb.DB;
import com.mongodb.MongoClient;
import org.ingini.mongodb.jongo.example.domain.zip.ZipData;
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
public class TestGeoSpatial {

    public static final String DB_NAME = "aggregation_test_db";
    public static final String ZIP_CODES_COLLECTION_NAME = "zip_codes";

    public static DB mongoDB;

    public static MongoCollection zipCodes;

    @BeforeClass
    public static void beforeClass() throws UnknownHostException {
        mongoDB = new MongoClient("127.0.0.1", 27017).getDB(DB_NAME);

        Jongo jongo = new Jongo(mongoDB);
        zipCodes = jongo.getCollection(ZIP_CODES_COLLECTION_NAME);
        CollectionManager.cleanAndFill(mongoDB, "zips.json", "zip_codes");
        zipCodes.ensureIndex("{loc: '2dsphere'}");
    }

    /**
     * Command line import: mongoimport --drop -d aggregation_test_db -c zipcodes zips.json
     * <p>
     * <p/>
     * db.zip_codes.find({loc: {$near : {$geometry : {type: 'Point', coordinates: [-122.252696, 37.900933] },
      $maxDistance: 10*1000 }}});
     * </p>
     */
    @Test
    public void shouldFindAllTownsWithinRadius10km() {
        //GIVEN
        int lowerLimit = 10 * 1000; //in meters

        //WHEN
        List<ZipData> results = Lists.newArrayList(zipCodes.find("{loc: {$near : {$geometry : {type: 'Point', " +
                "coordinates: [-122.252696, 37.900933] }, $maxDistance: # }}}", lowerLimit)
                .as(ZipData.class).iterator());

        //THEN
        assertThat(results).hasSize(19);
    }

    /**
     * Command line import: mongoimport --drop -d aggregation_test_db -c zipcodes zips.json
     * <p>
     * db.zip_codes.find({loc: {$near : {$geometry : {type: 'Point', coordinates: [-122.252696, 37.900933] },
     * $maxDistance: 10*1000 }}, pop : {$gt: 10*1000}});
     * </p>
     */
    @Test
    public void shouldFindAllNearbyTownsWithPopulationOver20Thousands() {
        //GIVEN
        int lowerLimit = 10 * 1000;
        int population = 20 * 1000;

        //WHEN
        List<ZipData> results = Lists.newArrayList(zipCodes.find("{loc: {$near : {$geometry : {type: 'Point', " +
                "coordinates: [-122.252696, 37.900933] }, $maxDistance: # }}, pop : {$gt: #}}", lowerLimit, population)
                .as(ZipData.class).iterator());

        //THEN
        assertThat(results).hasSize(8);
    }
}
