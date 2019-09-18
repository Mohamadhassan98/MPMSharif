package com.enthusi4stic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.beust.klaxon.Json
import com.beust.klaxon.Klaxon
import com.enthusi4stic.api.networktask.NetworkTask
import com.enthusi4stic.api.recyclerview.BindView
import com.enthusi4stic.api.recyclerview.bind
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.recycler_fragment.*
import org.json.JSONArray
import org.json.JSONObject

class RecyclerFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.recycler_fragment, container, false)

    /*
    data class User(
        @BindView(id = "listitem_recycler_view_fragment_name", view = BindView.View.TextView, field = BindView.Field.Text)
        val name: String,
        @BindView(id = "listitem_recycler_view_fragment_last_message", view = BindView.View.TextView, field = BindView.Field.Text)
        val lastMessage: String,
        @BindView(id = "listitem_recycler_view_fragment_profile_image", view = BindView.View.ImageView, field = BindView.Field.URL)
        val profilePhoto: String)

    private val users = listOf(
        User(
            "محسن",
            "سلام علیکم و رحمت الله.",
            "https://mohsenyousefian.com/img/Mohsen_Yousefian_1.jpg"
        ),
        User("محمد حسن", "طوری نیست.", "https://static.independent.co.uk/s3fs-public/thumbnails/image/2015/09/28/10/ioan-gruffudd.jpg?w968"))
    */

    data class User(
        @BindView(
            id = "listitem_recycler_view_fragment_text_view_email",
            view = BindView.View.TextView,
            field = BindView.Field.Text
        )
        val email: String,
        @BindView(
            id = "listitem_recycler_view_fragment_text_view_phone",
            view = BindView.View.TextView,
            field = BindView.Field.Text
        )
        val phone: String,
        @BindView(
            id = "listitem_recycler_view_fragment_circle_image_view",
            view = BindView.View.ImageView,
            field = BindView.Field.URL
        )
        @Json(name = "pic")
        val profilePhoto: String
    )


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        NetworkTask(
            "http://017067d1.ngrok.io/users/?limit=10&offset=0",
            ctx = activity,
            waitingMessage = "لطفا کمی صبر کنید..."
        ).setOnCallBack {it, str ->
            if (it?.isSuccessful == true) {
               val users = Klaxon().parseArray<User>(JSONObject(str)["results"].toString())
                recycler_fragment_recycler.bind(
                    User::class,
                    users ?: emptyList(),
                    context!!,
                    R.layout.listitem_recycler_view_fragment
                ).apply()
            }
        }.send()


        /*
        .setHeaderLayout(R.layout.recycler_footer)
        .setOnHeaderClickListener {
              it.findViewById<MaterialButton>(R.id.recycler_footer_show_more).setOnClickListener {
                  Log.d("Header", "Ha ha! Footer was clicked!")
              }
       }*/
    }

}