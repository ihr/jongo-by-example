package org.ingini.mongodb.jongo.example.util;

import com.mongodb.*;
import com.mongodb.util.JSON;
import com.mongodb.util.JSONCallback;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.bson.BSONObject;
import org.bson.types.ObjectId;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.CharBuffer;

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
public class CollectionManager {

    private static final int BUFFER_SIZE = 8192; // 8K bytes / 2 bytes = 4K characters
    private static final int EOF = -1;
    private static final int START = 0;

    public static void cleanAndFill(DB mongoDB, String location, String name) {

        if (mongoDB.collectionExists(name)) {
            mongoDB.getCollection(name).drop();
        }
        DBCollection collection = mongoDB.createCollection(name, new BasicDBObject());
        fill(collection, location);
    }

    private static void fill(DBCollection collection, String collectionContentFilePath) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            InputStreamReader inputStreamReader = new InputStreamReader( //
                    CollectionManager.class.getClassLoader().getResourceAsStream(collectionContentFilePath), "UTF-8");
            CharBuffer buf = CharBuffer.allocate(BUFFER_SIZE);
            for (int read = inputStreamReader.read(buf); read != EOF; read = inputStreamReader.read(buf)) {
                buf.flip();
                stringBuilder.append(buf, START, read);

            }
        } catch (IOException e) {
            System.out.println("Unable to read input stream due to an exception! Exception: " + ExceptionUtils.getStackTrace(e));
            throw new IllegalStateException(e);
        }

        BasicDBList parse = (BasicDBList) JSON.parse(stringBuilder.toString(), new MongoIdTransformerJSONCallback());
        collection.insert(parse.toArray(new DBObject[parse.size()]));
    }

    private static class MongoIdTransformerJSONCallback extends JSONCallback {

        private static final String MONGO_ID_KEY = "_id";

        @Override
        public Object objectDone() {
            BSONObject b = (BSONObject) super.objectDone();
            if (b.containsField(MONGO_ID_KEY) && b.get(MONGO_ID_KEY) instanceof String
                    && ObjectId.isValid(String.valueOf(b.get(MONGO_ID_KEY)))) {
                b.put(MONGO_ID_KEY, new ObjectId(b.get(MONGO_ID_KEY).toString()));
            }
            return b;
        }
    }
}
