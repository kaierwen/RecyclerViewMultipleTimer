package github.kaierwen.recyclerviewmultipletimer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<RecyclerView>(R.id.recyclerView).apply {
            with(CountDownAdapter()) {
                val arrayListOf = arrayListOf<CountDownBean>()
                for (i in 0..20) {
                    arrayListOf.add(CountDownBean(i, (i * 2000 + 300).toLong()))
                }
                swapData(arrayListOf)
                adapter = this
            }
        }
    }
}