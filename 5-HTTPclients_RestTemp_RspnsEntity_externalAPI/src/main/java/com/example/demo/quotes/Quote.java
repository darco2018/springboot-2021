package com.example.demo.quotes;

import java.util.ArrayList;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

 /* json of the only element of quotes key:
 {
    quote: "Climb the mountains and get their good tidings. Nature's peace will flow into you as sunshine flows into trees. The winds will blow their own freshness into you, and the storms their energy, while cares will drop away from you like the leaves of Autumn.",
    length: "253",
    author: "John Muir",
    tags: [
    "autumn",
    "inspire",
    "life",
    "mountaineering",
    "peace"
    ],
    category: "inspire",
    language: "en",
    date: "2021-10-24",
    permalink: "https://theysaidso.com/quote/john-muir-climb-the-mountains-and-get-their-good-tidings-natures-peace-will-flow",
    id: "AplDeYJXEVuZ8_Qsl4d7QweF",
    background: "https://theysaidso.com/img/qod/qod-inspire.jpg",
    title: "Inspiring Quote of the day"
    }*/

@JsonIgnoreProperties( ignoreUnknown = true)
public class Quote {

    private String id;
    private String quote = "No quote by default";
    private String length;
    private String author;
    private ArrayList<String> tags;
    private String category;
    private Date date;
    private String title;
    private String background;

    public Quote() { }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getQuote() {
        return quote;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArrayList<String> getTags() {
        return tags;
    }

    public void setTags(ArrayList<String> tags) {
        this.tags = tags;
    }

}