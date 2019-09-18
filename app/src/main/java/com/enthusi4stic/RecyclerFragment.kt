package com.enthusi4stic

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.enthusi4stic.api.recyclerview.BindView
import com.enthusi4stic.api.recyclerview.bind
import com.google.android.material.button.MaterialButton
import kotlinx.android.synthetic.main.recycler_fragment.*

class RecyclerFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View
            = inflater.inflate(R.layout.recycler_fragment, container, false)

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

    val LOGTAG = "RecyclerViewTest"

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_fragment_recycler.bind(
            User::class,
            users,
            context!!,
            R.layout.listitem_recycler_view_fragment
        )
            .setHeaderLayout(R.layout.recycler_footer)
            .setOnHeaderClickListener {
                it.findViewById<MaterialButton>(R.id.recycler_footer_show_more).setOnClickListener {
                    Log.d("Header", "Ha ha! Footer was clicked!")
                }
            }
            .apply()
    }

}