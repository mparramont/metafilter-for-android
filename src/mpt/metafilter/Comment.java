package mpt.metafilter;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.util.Log;

public class Comment {
	public Comment(Element comment) {
		try {
			Elements timestamp = comment.select(".smallcopy");
			Elements links = timestamp.first().select("a");
			this.text = comment.text().substring(0,
					comment.text().lastIndexOf("posted by"));
			this.author = links.get(0).text();
			this.authorLink = links.first().attr("href");
			this.link = links.get(1).attr("href");
			this.date = links.get(1).text()
					+ comment.text().substring(
							comment.text().lastIndexOf("on") + 2);
			this.favorites = links.get(2).text();
			this.favoritesLink = links.get(2).attr("href");
		} catch (Exception e) {
			Log.e("IOException", e.getStackTrace().toString());
		}
	}

	@Override
	public String toString() {
		return "Comment [author=" + author + ", date=" + date + ", link="
				+ link + ", text=" + text + ", authorLink=" + authorLink + "]";
	}

	public String author;
	public String date;
	public String link;
	public String text;
	public String authorLink;
	public String favoritesLink;
	public String favorites;
}
