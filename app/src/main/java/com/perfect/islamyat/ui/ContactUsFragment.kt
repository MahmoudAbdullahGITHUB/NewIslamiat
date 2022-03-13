package com.perfect.islamyat.ui

import android.app.AlertDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.perfect.islamyat.R
import kotlinx.android.synthetic.main.fragment_contact_us.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ContactUsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ContactUsFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val root : View = inflater.inflate(R.layout.fragment_contact_us, container, false)
        val gmail_img : ImageView = root.findViewById(R.id.gmail_img)
        val send_btn : Button = root.findViewById(R.id.send_btn)
        send_btn.setOnClickListener {
            if (!contact_text_view.text.isEmpty()) {
//                val emailIntent = Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "islamicapp216@gmail.com", null))
//                val email = Intent(Intent.ACTION_SENDTO)
//                val TO = arrayOf("islamicapp216@gmail.com")
//                emailIntent.putExtra(Intent.EXTRA_EMAIL, TO)
//                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "إسلاميات")
//                emailIntent.putExtra(Intent.EXTRA_TEXT, contact_text_view.text)
//                emailIntent.type = "message/rfc822"
////                email.setData(Uri.parse("mail"))
//                startActivity(Intent.createChooser(emailIntent, "Choose an Email client :"))
                val intent = Intent(Intent.ACTION_VIEW);
                val data = Uri.parse("mailto:islamicapp216@gmail.com;islamiyatapp@gmail.com?subject=إسلاميات&body="+contact_text_view.text);
                intent.setData(data);
                startActivity(intent)
            } else {
                val alertDialog =
                    AlertDialog.Builder(activity)
                alertDialog.setTitle("تحذير")
                alertDialog.setMessage("أدخل الرسالة المراد إرسالها")
                alertDialog.setPositiveButton(
                    "حسنا"
                ) { dialog, which ->
                    dialog.cancel()
                }
                alertDialog.show()
            }
        }
        return root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ContactUsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ContactUsFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}