package mpt.metafilter;

import java.util.ArrayList;

import android.content.Context;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class PostAdapter extends ArrayAdapter<Post> {
	private final ArrayList<Post> posts;
	private final Context context;

	public PostAdapter(Context context, int textViewResourceId,
			ArrayList<Post> posts) {
		super(context, textViewResourceId, posts);
		this.context = context;
		this.posts = posts;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.post_item, null);
		}

		Post post = posts.get(position);
		TextView title = (TextView) v.findViewById(R.id.title);
		TextView description = (TextView) v.findViewById(R.id.description);
		title.setText(post.title);
		String text = post.description;
		//text = text.substring(0,
				//text.indexOf("<div class=\"feedflare\">"));
		description.setText(Html.fromHtml(text));
		return v;
	}
}