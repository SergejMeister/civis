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

package com.civis.utils.html.parser;

import com.civis.utils.html.models.HtmlLink;
import org.apache.commons.lang3.StringUtils;

import java.util.Collections;
import java.util.List;

/**
 * This is a help model to filter the links.
 */
public class HtmlParseFilter {

    /**
     * LinkValue is value between a tags.
     * <p/>
     * Example: <a href="link">linkValue</a>.
     */
    private boolean linkValueCanBeNull;
    private List<String> linkMatcherList;
    private List<String> ignoreList;

    public HtmlParseFilter() {
        setNullableText(Boolean.TRUE);
        setLinkMatcherList(Collections.emptyList());
        setIgnore(Collections.emptyList());
    }

    public void setNullableText(Boolean textCanBeNull) {
        this.linkValueCanBeNull = textCanBeNull;
    }

    public void setLinkMatcherList(List<String> linkMatcherList) {
        this.linkMatcherList = linkMatcherList;
    }

    public Boolean matches(HtmlLink htmlLink) {
        Boolean textFilterStatus = matchText(htmlLink.getValue());
        if (!textFilterStatus) {
            return Boolean.FALSE;
        }

        return matchLink(htmlLink.getHref());
    }

    public Boolean matchText(String linkText) {
        if (linkValueCanBeNull) {
            //can be null, that means this filter is deactivated and always true.
            return Boolean.TRUE;
        }

        return StringUtils.isNotBlank(linkText);
    }

    public Boolean matchLink(String link) {
        if (linkMatcherList.isEmpty()) {
            //is empty, that means this filter is deactivated and always true.
            return Boolean.TRUE;
        }

        for (String linkMatcher : linkMatcherList) {
            if (link.contains(linkMatcher)) {
                return Boolean.TRUE;
            }
        }

        return Boolean.FALSE;
    }


    public void setIgnore(List<String> ignore) {
        this.ignoreList = ignore;
    }

    public Boolean ignore(String value) {
        if (value == null) {
            return Boolean.TRUE;
        }
        for (String ignoreValue : ignoreList) {
            if (value.contains(ignoreValue)) {
                return Boolean.TRUE;
            }
        }
        return Boolean.FALSE;
    }
}
