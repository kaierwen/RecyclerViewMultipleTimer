package github.kaierwen.recyclerviewmultipletimer

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import java.util.*

/**
 * @author qiaofeng
 * @since 2021/5/31
 */
class Adapter2 : RecyclerView.Adapter<Adapter2.Holder2>() {

    private val TAG = Adapter2::class.java.simpleName
    private var data: List<CountDownBean> = ArrayList()
    private var handler = Handler(Looper.getMainLooper())

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder2 {
        Log.d(TAG, "onCreate() viewType = $viewType")
        return Holder2(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_timer, parent, false)
        )
    }

    override fun onBindViewHolder(holder: Holder2, position: Int) {
        Log.d(TAG, "onBind() position = $position")
        holder.bind(data[position], position)
    }

    override fun onViewRecycled(holder: Holder2) {
        Log.d(TAG, "onViewRecycled() position = ${holder.adapterPosition}")
        super.onViewRecycled(holder)
    }

    override fun getItemCount() = data.size

    fun swapData(data: List<CountDownBean>) {
        this.data = data
        notifyDataSetChanged()
    }

    fun clearAll() {
        handler.removeCallbacksAndMessages(null)
    }

    inner class Holder2(itemView: View) : RecyclerView.ViewHolder(itemView) {

        //        var customRunnable = CustomRunnable()
        lateinit var tvNum: TextView
        lateinit var tvCountDown: TextView
        lateinit var btn: Button

        fun bind(item: CountDownBean, position: Int) = with(itemView) {
            tvNum = itemView.findViewById(R.id.tvNum)
            tvNum.apply {
                text = position.toString()
            }
            tvCountDown = itemView.findViewById(R.id.tvCountDown)
            tvCountDown.text = (item.duration / 1000).toString()
            btn = itemView.findViewById(R.id.btn)
//            itemView.findViewById<TextView>(R.id.tvCountDown).apply {
//
//            }
//            itemView.findViewById<Button>(R.id.btn).apply {
//
//            }
//            handler.removeCallbacks(customRunnable)

        }
    }

    inner class CustomRunnable(var countDownTv: TextView) : Runnable {

//        var tv: cou

        override fun run() {

        }
    }
}