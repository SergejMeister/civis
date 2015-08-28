/*
 * Copyright 2015 Sergej Meister
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.civis.utils.csv.names;

import junit.framework.TestCase;
import org.junit.Assert;

import java.util.List;

/**
 * Test class for testing csv names readers.
 */
public class TestCSVNamesReader extends TestCase {

    public void testDefaultRead() {
        List<CSVNameData> nameDataList = CSVNamesReader.read("names/names.csv");
        Assert.assertEquals(18499, nameDataList.size());
    }

    public void testGermanyNamesRead() {
        List<CSVNameData> nameDataList = CSVNamesReader.read("names/de_names.csv");
        Assert.assertEquals(18484, nameDataList.size());
    }

}
