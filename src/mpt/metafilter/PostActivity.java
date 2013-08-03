package mpt.metafilter;

import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ListView;
import android.widget.TextView;

public class PostActivity extends Activity {

	ListView lv;
	String url;
	public ArrayList<Comment> list = new ArrayList<Comment>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.post);
		lv = (ListView) findViewById(R.id.comments);

		String title = getIntent().getStringExtra("title");
		String description = getIntent().getStringExtra("description");
		((TextView) findViewById(R.id.title)).setText(title);
		((TextView) findViewById(R.id.description)).setText(Html
				.fromHtml(description));

		url = getIntent().getStringExtra("url");

		new GetCommentsTask().execute();
	}

	class GetCommentsTask extends AsyncTask<Void, Void, ArrayList<Comment>> {

		protected ArrayList<Comment> doInBackground(Void... voids) {
			Document doc;
			try {
				doc = Jsoup.connect(url).timeout(10000).get();
				Elements comments = doc.select(".comments");
				for (Element comment : comments)
					list.add(new Comment(comment));
			} catch (Exception e) {
				Log.e("IOException", e.getStackTrace().toString());
			}
			return list;
		}

		protected void onPostExecute(ArrayList<Comment> list) {
			lv.setAdapter(new CommentAdapter(PostActivity.this,
					R.layout.comment_item, list));
		}
	}
}
