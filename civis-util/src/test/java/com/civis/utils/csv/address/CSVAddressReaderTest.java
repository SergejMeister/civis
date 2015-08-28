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

import java.util.List;


public class CSVAddressReaderTest extends TestCase {

    public void testReadDataSize() throws Exception {
        List<CSVAddressData> addressDataList = CSVAddressReader.read();
        Assert.assertEquals(58022, addressDataList.size());
    }

    public void testRead() throws Exception {
        List<CSVAddressData> addressDataList = CSVAddressReader.read();
        int defaultIndex = 210;
        CSVAddressData csvAddressData = addressDataList.get(defaultIndex);
        Assert.assertEquals("Stammheim", csvAddressData.getCity());
        Assert.assertEquals("70435", csvAddressData.getZip());
        Assert.assertEquals("Baden-Württemberg", csvAddressData.getRegion());
    }
}