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

package com.civis.utils.csv.address;


import junit.framework.TestCase;
import org.junit.Assert;

import java.util.Set;


public class CountryReaderTest extends TestCase {

    public void testReadDefaultDataSize() throws Exception {
        Set<String> countries = CountryReader.read();
        Assert.assertEquals(237, countries.size());
    }

    public void testRead() throws Exception {
        Set<String> countries = CountryReader.read();
        Assert.assertTrue(countries.contains("Deutschland"));
        Assert.assertTrue(countries.contains("Russland"));
    }
}