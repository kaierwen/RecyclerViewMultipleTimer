package github.kaierwen.recyclerviewmultipletimer.ref;/**
 * Created by mani on 03/09/17.
 */

import android.content.Context;
import android.os.Handler;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import github.kaierwen.recyclerviewmultipletimer.R;

import java.util.List;

//import butterknife.BindView;
//import butterknife.ButterKnife;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private static final String TAG = RecyclerViewAdapter.class.getSimpleName();

    private Context context;
    private List<CustomTimer> list;
    private OnItemClickListener onItemClickListener;
    private Handler handler = new Handler();

    public RecyclerViewAdapter(Context context, List<CustomTimer> list,
                               OnItemClickListener onItemClickListener) {
        this.context = context;
        this.list = list;
        this.onItemClickListener = onItemClickListener;
    }

    public void clearAll() {
        handler.removeCallbacksAndMessages(null);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView timeStamp;
        ImageView imageView;
        CustomRunnable customRunnable;

        public ViewHolder(View itemView) {
            super(itemView);
            timeStamp = itemView.findViewById(R.id.timestamp);
            imageView = itemView.findViewById(R.id.bck);
            customRunnable = new CustomRunnable(handler, timeStamp, 10000, imageView);
        }

        public void bind(final CustomTimer model, final OnItemClickListener listener) {
      /*itemView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          listener.onItemClick(getLayoutPosition());
        }
      });*/

            handler.removeCallbacks(customRunnable);
            customRunnable.holder = timeStamp;
            customRunnable.millisUntilFinished = 10000 * getAdapterPosition(); //Current time - received time
            handler.postDelayed(customRunnable, 100);

        }
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View view = inflater.inflate(R.layout.recycler_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        //CustomTimer item = list.get(position);

        //Todo: Setup viewholder for item
        holder.bind(null, onItemClickListener);
    }

    @Override
    public int getItemCount() {
//        return 100;
        return list.size();
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
}
