package ESTG.IPVC.safecity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity

class AddNota: AppCompatActivity() {

    private lateinit var editNotaView: EditText
    private lateinit var editNotaView2: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_nota)

        editNotaView = findViewById(R.id.edit_nota)
        editNotaView2 = findViewById(R.id.edit_nota2)
        val button = findViewById<Button>(R.id.button_save)
        button.setOnClickListener {
            val replyIntent = Intent()
            if (TextUtils.isEmpty(editNotaView.text)) {
                setResult(Activity.RESULT_CANCELED, replyIntent)
            }else{

                val titulo = editNotaView.text.toString()
                val corpo = editNotaView2.text.toString()

                replyIntent.putExtra(EXTRA_REPLY_TITULO,titulo)
                replyIntent.putExtra(EXTRA_REPLY_CORPO,corpo)

                setResult(Activity.RESULT_OK,replyIntent)
            }
            finish()
        }
    }
    companion object{
        const val EXTRA_REPLY_TITULO = "titulo"
         const val EXTRA_REPLY_CORPO = "corpo"
    }
}