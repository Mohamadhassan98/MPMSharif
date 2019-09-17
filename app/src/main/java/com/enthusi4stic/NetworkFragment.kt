package com.enthusi4stic

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

class NetworkFragment: Fragment(){

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(R.layout.network_fragment, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        /*
        DownloadTask("https://hw14.cdn.asset.aparat.com/aparat-video/3559c9d13cd2ffac4b29eda195a5117016977884-480p__42863.mp4", File(Environment.getExternalStorageDirectory(), "vid.mp4"), this){
            Snackbar.make(activity_main_frame_layout, "Download completed ${if(!it){"un"}else{""}}successfully.", Snackbar.LENGTH_LONG).show()
        }.download()
        */

        /*
        UploadTask("http://cb152177.ngrok.io/files/", File(Environment.getExternalStorageDirectory(), "vid.mp4"), "vid.mp4", this){
            Snackbar.make(activity_main_frame_layout, "Upload completed ${if(!it){"un"}else{""}}successfully.", Snackbar.LENGTH_LONG).show()
        }.upload()
        */
    }

}