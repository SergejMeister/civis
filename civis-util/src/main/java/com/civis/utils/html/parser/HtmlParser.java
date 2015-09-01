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
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * Utility class to parse html.
 */
public class HtmlParser {

    //Patterns
    public static final Pattern REMOVE_TAGS = Pattern.compile("<.+?>");
    public static final Pattern MAIL_PATTERN = Pattern.compile("[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+\\.[a-zA-Z0-9-.]+");
    public static final String RESOURCE_NAME_PATTERN = "[\\w\\d.:#@%/;$()~_?\\+-=&]*";
    public static final String WWW_PATTERN = "www\\." + RESOURCE_NAME_PATTERN;
    public static final String HTTP_PATTERN = "http://" + RESOURCE_NAME_PATTERN;
    public static final String HTTPS_PATTERN = "https://" + RESOURCE_NAME_PATTERN;
    public static final Pattern WEB_URL_PATTERN =
            Pattern.compile(HTTP_PATTERN + "|" + HTTPS_PATTERN + "|" + WWW_PATTERN);

    /**
     * Is content a html or a plain text!
     * Default html content.
     */
    private boolean isHtmlContent;
    private boolean useFilter;
    private String content;
    private HtmlParseFilter htmlParseFilter;

    private List<HtmlLink> links;
    private List<String> urls;
    private String mail;

    /**
     * Constructor to init html content.
     * <p/>
     * <code>isPlainText</code> is false
     *
     * @param content html content.
     */
    public HtmlParser(String content) {
        this(content, Boolean.FALSE);
    }

    /**
     * Constructor to init a html or plain text content
     *
     * @param content        content - html, plain text
     * @param isPlainContent true, if content plain text
     */
    public HtmlParser(String content, boolean isPlainContent) {
        this.isHtmlContent = !isPlainContent;
        this.content = content;
        this.links = new ArrayList<>();
        this.urls = new ArrayList<>();
    }

    public HtmlParser(String content, HtmlParseFilter htmlParseFilter) {
        this(content, Boolean.FALSE);
        useFilter = true;
        this.htmlParseFilter = htmlParseFilter;
    }

    public static String removeTags(String string) {
        if (string == null || string.length() == 0) {
            return string;
        }

        Matcher m = REMOVE_TAGS.matcher(string);
        return m.replaceAll("");
    }

    //TODO: return only one mail, but can be a list of mails!
    private void parseEmail() {
        Matcher m = MAIL_PATTERN.matcher(content);
        while (m.find()) {
            mail = m.group();
        }
    }

    /**
     * Parse html as plain text.
     */
    public HtmlParser toPlainText() {
        Document doc = Jsoup.parse(content);
        String plainTextWithHTags = doc.text();
        String text = removeTags(plainTextWithHTags);
        String[] words = text.split(" ");
        int wordCountPerSentence = 25;
        int wordCount = 0;
        StringBuilder stringBuilder = new StringBuilder();
        for (String word : words) {
            if (word.length() > 1) {
                stringBuilder.append(word);
                stringBuilder.append(" ");
                wordCount++;
            }
            if (wordCount == wordCountPerSentence) {
                stringBuilder.append("\n");
                wordCount = 0;
            }

        }

        //replace all html entity unicode. like &aml etc.
        content = Jsoup.parse(stringBuilder.toString()).text();
        return this;
    }


    private void parseUrl() {
        Set<String> result = new HashSet<>();
        Matcher m = WEB_URL_PATTERN.matcher(content);
        while (m.find()) {
            String urlValue = content.substring(m.start(0), m.end(0));
            if (urlValue.endsWith(")")) {
                urlValue = urlValue.substring(0, urlValue.length() - 1);
            }
            if (useFilter) {
                if (!htmlParseFilter.ignore(urlValue)) {
                    // with filter
                    result.add(urlValue);
                }
            } else {
                //without filter
                result.add(urlValue);
            }
        }

        urls.addAll(result.parallelStream().distinct().collect(Collectors.toList()));
    }

    private void doFilter() {
        if (useFilter) {
            List<HtmlLink> filteredResult =
                    links.stream().filter(htmlParseFilter::matches).collect(Collectors.toList());
            links.clear();
            links.addAll(filteredResult);
        }
    }

    /**
     * Parse content.
     * <p/>
     * Parse href tags, parse urls.
     */
    public HtmlParser parse() {
        parseLinks();
        parseUrl();
        parseEmail();

        return this;
    }

    private void parseLinks() {
        if (isHtmlContent) {
            Document doc = Jsoup.parse(content);
            Elements hrefElements = doc.select("a[href]");
            for (Element hrefElement : hrefElements) {
                String aTag = hrefElement.toString();
                String href = hrefElement.attr("abs:href");
                String text = hrefElement.text().trim();
                links.add(new HtmlLink(aTag, href, text));
            }
            doFilter();
        }
    }

    /**
     * Returns all href links founded in content.
     * Should be execute after parse methods.
     */
    public List<HtmlLink> getLinks() {
        return links;
    }

    /**
     * Returns all urls founded in content.
     * Should be execute after parse methods.
     */
    public List<String> getUrls() {
        return urls;
    }

    public String getMail() {
        return mail;
    }

    public String getContent() {
        return content;
    }
}
