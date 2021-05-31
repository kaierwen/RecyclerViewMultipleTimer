package github.kaierwen.recyclerviewmultipletimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private val TAG = MainActivity::class.java.simpleName

    private lateinit var recyclerView: RecyclerView
    private lateinit var r: Runnable//定时器线程
    private var timerIsInit = false //判断数据是否初始化
    private var arrayList = arrayListOf<CountDownBean>()
    private var adapter2 = Adapter2()
    var handler = object : Handler(Looper.getMainLooper()) {
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)
            for (i in arrayList.indices) {
                val bean = arrayList[i]
                if (bean.duration > 1000) {
                    bean.duration -= 1000
                } else if (bean.duration in 1..1000) {
                    bean.duration = 0
                } else {
                    arrayList.removeAt(i)
                    adapter2.notifyItemRemoved(i)
                    break
                }
                val holder2 =
                    recyclerView.findViewHolderForAdapterPosition(i)
                Log.d(TAG, "handleMessage: holder2 = $holder2")
                if (holder2 != null) {
                    holder2 as Adapter2.Holder2
                    holder2.tvCountDown.text = (bean.duration / 1000).toString()
                    val text = holder2.tvNum.text
                    val threadName = Thread.currentThread().name
                    Log.d(TAG, "handleMessage: text = $text , threadName = $threadName")
                    timerIsInit = true
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        for (i in 0..50) {
            arrayList.add(CountDownBean(i, (i * 5000 + 300).toLong()))
        }

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.apply {
            with(adapter2) {
                swapData(arrayList)
                adapter = this
            }

//            val arrayListOf = arrayListOf<CustomTimer>()
//            for (i in 0..20) {
//                val customTimer = CustomTimer()
//                customTimer.startTime = (i * 1000 * 10).toLong()
//                arrayListOf.add(customTimer)
//            }
//            with(RecyclerViewAdapter(context, arrayListOf, null)) {
//                adapter = this
//            }
        }

        r = Runnable {
            handler.sendEmptyMessage(1)
            if (timerIsInit) {
                handler.postDelayed(r, 1000)
            } else {
                handler.post(r)
            }
        }
        handler.post(r)
    }

    override fun onDestroy() {
        handler.removeCallbacks(r)
        super.onDestroy()
    }
}