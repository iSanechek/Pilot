package com.isanechek.pilotproject.data.source.remote.parser;

import android.support.annotation.NonNull;
import android.util.Log;

import com.annimon.stream.Stream;
import com.annimon.stream.function.Consumer;
import com.isanechek.pilotproject.data.Article;
import com.isanechek.pilotproject.data.source.remote.network.OkHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static com.isanechek.pilotproject.utils.Utils.tryUrl;

/**
 * Created by isanechek on 26.04.16.
 */

public class Parser {
    public static List<Article> parseList(@NonNull String url) {
        msg("Parser List -> Start");
        final List<Article> cache = new ArrayList<>();
        String body = OkHelper.getBody(url);
        if (body != null) {
            Document document = Jsoup.parse(body);
            Elements rootElements = document.getElementsByClass("article-inner");
            Stream.of(rootElements).forEach(new Consumer<Element>() {
                @Override
                public void accept(Element element) {
                    Element link = element.select("a").first();
                    String articleUrl = tryUrl(link.attr("href"));
                    String imageUrl = tryUrl(link.select("img[src]").attr("src"));
                    String title = element.getElementsByClass("article-title").text();
                    String date = element.getElementsByClass("article-date").text();
                    String description = element.getElementsByClass("article-entry").select("p").first().text();
                    cache.add(new Article(UUID.randomUUID().toString(), title, description, articleUrl, imageUrl, date, false));
                }
            });
        }
        msg("Parser List -> Finish");
        return cache;
    }

    public static String getDetailArticleContentBody(@NonNull String url) {
        return OkHelper.getBody(url);
    }

    private static void msg(String text) {
        Log.e("PARSER", text);
    }
}
