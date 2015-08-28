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

package com.civis.utils.html.models;

/**
 * Model html a-tag <code><a/></code>.
 */
public class HtmlLink {

    /**
     * This is full html a-tag.
     * <p>
     * Example: <a href="testhref">DemoLinkValue</a>.
     */
    private String atag;

    /**
     * This is href link in html a tag.
     * <p>
     * Example: <a href="testhref">DemoLinkValue</a>.
     * href=testhref.
     */
    private String href;

    /**
     * This is value in html a tag.
     * <p>
     * Example: <a href="testhref">DemoLinkValue</a>.
     * label=DemoLinkValue.
     */
    private String value;

    /**
     * Constructor to set all parameters.
     *
     * @param atag  a-tag
     * @param href  a-href
     * @param value a-text
     */
    public HtmlLink(String atag, String href, String value) {
        this.atag = atag;
        this.href = href;
        this.value = value;
    }

    public String getAtag() {
        return atag;
    }

    public void setAtag(String atag) {
        this.atag = atag;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "HtmlLink{" +
                "atag='" + atag + '\'' +
                ", href='" + href + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
