package mpt.metafilter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.AdapterView;
import android.view.View;
import android.widget.ListView;
import android.widget.AdapterView.OnItemClickListener;

public class MetaFilterActivity extends Activity {
	/** Called when the activity is first created. */
	ListView lv;
	boolean item = false;
	public ArrayList<Post> list = new ArrayList<Post>();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		lv = (ListView) findViewById(R.id.posts);

		lv.setOnItemClickListener(new OnItemClickListener() {
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(MetaFilterActivity.this,
						PostActivity.class);
				intent.putExtra("title", list.get(position).title);
				intent.putExtra("description", list.get(position).description);
				intent.putExtra("url", list.get(position).url);
				startActivity(intent);
			}
		});

		new DownloadFeedTask().execute();

	}

	private class RSSHandler extends DefaultHandler {
		private Post post = new Post();
		StringBuffer chars = new StringBuffer();

		public void startElement(String uri, String localName, String qName,
				Attributes atts) {
			chars = new StringBuffer();
		}

		public void endElement(String namespaceURI, String localName,
				String qName) throws SAXException {
			if (localName.equals("title"))
				post.title = chars.toString();
			if (localName.equals("description"))
				post.description = chars.toString();
			if (localName.equals("link"))
				post.url = chars.toString();
			if (localName.equals("item")) {
				list.add(post);
				post = new Post();
			}
		}

		public void characters(char ch[], int start, int length) {
			chars.append(new String(ch, start, length));
		}

	}

	class DownloadFeedTask extends AsyncTask<Void, Void, ArrayList<Post>> {

		protected ArrayList<Post> doInBackground(Void... voids) {
			try {
				XMLReader xmlReader = SAXParserFactory.newInstance()
						.newSAXParser().getXMLReader();
				RSSHandler rssHandler = new RSSHandler();
				xmlReader.setContentHandler(rssHandler);
				InputSource inputSource = new InputSource(new URL(
						"http://feeds.feedburner.com/Metafilter").openStream());
				xmlReader.parse(inputSource);
			} catch (IOException e) {
				Log.e("IOException", e.getMessage());
			} catch (SAXException e) {
				Log.e("SAXException", e.getMessage());
			} catch (ParserConfigurationException e) {
				Log.e("ParserConfigurationException", e.getMessage());
			}
			return list;
		}

		protected void onPostExecute(ArrayList<Post> list) {
			lv.setAdapter(new PostAdapter(MetaFilterActivity.this,
					R.layout.post_item, list));
		}
	}
}