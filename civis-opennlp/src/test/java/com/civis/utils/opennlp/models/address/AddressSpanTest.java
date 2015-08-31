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

package com.civis.utils.opennlp.models.address;

import junit.framework.Assert;
import org.junit.Test;


public class AddressSpanTest {

    @Test
    public void testIsValid() {
        AddressSpan addressSpan = new AddressSpan();
        Assert.assertFalse("Should be not valid!", addressSpan.isValid());
        addressSpan.setStreet("Teststr.");
        Assert.assertFalse("Should be not valid!", addressSpan.isValid());
        addressSpan.setStreetNumber("12");
        Assert.assertFalse("Should be not valid!", addressSpan.isValid());
        addressSpan.setZip("12345");
        Assert.assertFalse("Should be not valid!", addressSpan.isValid());
        addressSpan.setCity("");
        Assert.assertFalse("Should be not valid, because city is empty!", addressSpan.isValid());
        addressSpan.setCity("Berlin");
        Assert.assertTrue("Is Valid", addressSpan.isValid());
        addressSpan.setCountry("");
        Assert.assertTrue("Is Valid", addressSpan.isValid());
        addressSpan.setCountry("Deutschland");
        Assert.assertTrue("Is Valid", addressSpan.isValid());
    }

}