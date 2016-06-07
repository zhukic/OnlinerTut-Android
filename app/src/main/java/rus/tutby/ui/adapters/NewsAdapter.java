package rus.tutby.ui.adapters;

import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import rus.tutby.App;
import rus.tutby.R;
import rus.tutby.utils.DateTimeFormatter;
import rus.tutby.entity.News;


public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private Typeface ROBOTO_TYPEFACE;

    private ArrayList<News> newsList;

    public interface OnItemClickListener {
        void onItemClicked(int position);
    }

    private OnItemClickListener onItemClickListener;

    public class ViewHolder extends RecyclerView.ViewHolder {

        @Bind(R.id.textDate) TextView textDate;
        @Bind(R.id.textTitle) TextView textTitle;
        @Bind(R.id.imageView) ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public NewsAdapter(ArrayList<News> newsList) {
        this.newsList = newsList;
    }

    @Override
    public NewsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.news_item, parent, false));
    }

    @Override
    public void onBindViewHolder(final NewsAdapter.ViewHolder holder, int position) {

        final News news = newsList.get(position);

        ROBOTO_TYPEFACE = Typeface.createFromAsset(holder.itemView.getContext().getAssets(), "Roboto-Regular.ttf");

        holder.textDate.setTypeface(ROBOTO_TYPEFACE);
        holder.textTitle.setTypeface(ROBOTO_TYPEFACE);
        holder.textDate.setText(DateTimeFormatter.Companion.getShortFormattedDate(news.getDate()));
        holder.textTitle.setText(news.getTitle());

        if(onItemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onItemClickListener.onItemClicked(holder.getAdapterPosition());
                }
            });
        }

        App.getImageLoader().displayImage(news.getImageURL(), holder.imageView,
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
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

}
