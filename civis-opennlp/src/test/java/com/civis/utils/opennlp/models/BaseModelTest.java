package com.civis.utils.opennlp.models;

import junit.framework.TestCase;
import org.apache.commons.io.IOUtils;
import org.junit.Assert;

import java.io.IOException;
import java.io.InputStream;

/**
 * Base methods used for testing models.
 */
public abstract class BaseModelTest extends TestCase {

    protected static String getTextExample(String fileName) {
        String content = "";
        try (InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream(fileName)) {
            content = IOUtils.toString(inputStream, "UTF-8");
            inputStream.close();
        } catch (IOException e) {
            Assert.fail();
        }

        return content;
    }
}
