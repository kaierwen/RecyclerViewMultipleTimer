package github.kaierwen.recyclerviewmultipletimer

import android.graphics.Color
import android.os.CountDownTimer
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
 * @since 2021/5/28
 */
class CountDownAdapter : RecyclerView.Adapter<CountDownAdapter.CountDownViewHolder>() {

    private var data: List<CountDownBean> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CountDownViewHolder {
        return CountDownViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_timer, parent, false)
        )
    }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: CountDownViewHolder, position: Int) {
        holder.bind(data[position])
        val duration = data[holder.adapterPosition].duration
        if (holder.timer != null) holder.timer?.cancel()
        if (holder.timer == null) {
            holder.timer = object : CountDownTimer(duration, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    if (holder.adapterPosition in data.indices) {
                        val down = millisUntilFinished / 1000
                        data[holder.adapterPosition].duration = down
                        notifyItemChanged(holder.adapterPosition)
                        Log.d(
                            "CountDown",
                            "onTick , millisUntilFinished = $millisUntilFinished , down = $down , position = ${holder.adapterPosition}"
                        )
                    }
                }

                override fun onFinish() {
                    Log.d("Timer", "onFinish")
                    holder.tvCountDown.apply {
                        text = "Finish"
                        setTextColor(Color.GREEN)
                    }
                }
            }
            holder.timer?.start()
        }
    }

    fun swapData(data: List<CountDownBean>) {
        this.data = data
        notifyDataSetChanged()
    }

    class CountDownViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        lateinit var tvNum: TextView
        lateinit var tvCountDown: TextView
        lateinit var btn: Button
        var countDown: CountDown? = null
        var timer: CountDownTimer? = null
        fun bind(item: CountDownBean) = with(itemView) {
            tvNum = itemView.findViewById(R.id.tvNum)
            tvCountDown = itemView.findViewById(R.id.tvCountDown)
            btn = itemView.findViewById(R.id.btn)
            tvNum.text = item.num.toString()
            tvCountDown.apply {
                text = when {
                    item.duration <= 0 -> {
                        setTextColor(Color.GREEN)
                        "Finish"
                    }
                    else -> {
                        setTextColor(Color.RED)
                        item.duration.toString()
                    }
                }
            }

//            btn.setOnClickListener {
//                if (countDown == null) {
//                    countDown = CountDown(item.duration, 1000)
//                }
//                countDown?.start()
//            }
        }

        inner class CountDown(var millisInFuture: Long, var countDownInterval: Long) :
            CountDownTimer(millisInFuture, countDownInterval) {

            override fun onTick(millisUntilFinished: Long) {
                val down = millisUntilFinished / countDownInterval
                Log.d(
                    "CountDown",
                    "onTick , millisUntilFinished = $millisUntilFinished , down = $down"
                )
                tvCountDown.apply {
                    text = down.toString()
                    setTextColor(Color.RED)
                }
            }

            override fun onFinish() {
                Log.d("CountDown", "onFinish")
                tvCountDown.apply {
                    text = "Finish"
                    setTextColor(Color.GREEN)
                }
            }
        }
    }
}

