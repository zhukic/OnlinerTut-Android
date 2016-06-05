package rus.tutby.ui.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import rus.tutby.App;
import rus.tutby.R;
import rus.tutby.utils.DateTimeFormatter;
import rus.tutby.entity.News;


public class NewsAdapter extends ArrayAdapter<News> {

    private final Typeface ROBOTO_TYPEFACE;

    public NewsAdapter(Context context, List objects) {
        super(context, 0, objects);
        ROBOTO_TYPEFACE = Typeface.createFromAsset(getContext().getAssets(), "Roboto-Regular.ttf");
    }

    private static class ViewHolder{
        public TextView textDate;
        public TextView textTitle;
        public ImageView imageView;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        final News news = getItem(position);

        if(convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textDate = (TextView) convertView.findViewById(R.id.textDate);
            viewHolder.textTitle = (TextView) convertView.findViewById(R.id.textTitle);
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.imageView);
            viewHolder.textDate.setTypeface(ROBOTO_TYPEFACE);
            viewHolder.textTitle.setTypeface(ROBOTO_TYPEFACE);
            convertView.setTag(viewHolder);
        }
        else viewHolder = (ViewHolder) convertView.getTag();

        viewHolder.textDate.setText(DateTimeFormatter.Companion.getShortFormattedDate(news.getDate()));
        viewHolder.textTitle.setText(news.getTitle());

        App.getImageLoader().displayImage(news.getImageURL(), viewHolder.imageView,
                App.getDisplayImageOptions(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                AlphaAnimation alphaAnimation = new AlphaAnimation(0, 1);
                alphaAnimation.setDuration(1000);
                ImageView imageView = (ImageView) view;
                imageView.setImageBitmap(bitmap);
                imageView.startAnimation(alphaAnimation);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
            }
        });

        return convertView;
    }
}
