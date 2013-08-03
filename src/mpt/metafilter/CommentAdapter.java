package mpt.metafilter;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CommentAdapter extends ArrayAdapter<Comment> {
	private final ArrayList<Comment> comments;
	private final Context context;

	public CommentAdapter(Context context, int textViewResourceId,
			ArrayList<Comment> comments) {
		super(context, textViewResourceId, comments);
		this.context = context;
		this.comments = comments;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.comment_item, null);
		}

		Comment comment = comments.get(position);
		((TextView) v.findViewById(R.id.text)).setText(comment.text);
		((TextView) v.findViewById(R.id.author)).setText(comment.author);
		((TextView) v.findViewById(R.id.date)).setText(comment.date);
		((TextView) v.findViewById(R.id.favorites)).setText(comment.favorites);
		return v;
	}
}