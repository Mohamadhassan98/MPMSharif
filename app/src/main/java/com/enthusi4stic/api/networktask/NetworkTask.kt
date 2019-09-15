package com.enthusi4stic.api.networktask

import android.app.ProgressDialog
import android.content.Context
import android.os.AsyncTask
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okhttp3.internal.EMPTY_REQUEST

open class NetworkTask(
    protected val url: String,
    protected val method: Method = Method.GET,
    protected val ctx: Context? = null,
    protected val body: RequestBody? = null
) {

    inner class Task : AsyncTask<Request, Int, Response>() {

        override fun onPreExecute() {
            super.onPreExecute()
            request.url(url).let {
                when (method) {
                    Method.GET -> it.get()
                    Method.POST -> it.post(body!!)
                    Method.DELETE -> it.delete(body ?: EMPTY_REQUEST)
                    Method.PUT -> it.put(body!!)
                    Method.PATCH -> it.patch(body!!)
                    Method.HEAD -> it.head()
                }
            }
            dialog = ProgressDialog.show(
                ctx,
                progress.title ?: "Loading",
                progress.message ?: "Please wait..."
            )
        }

        override fun doInBackground(vararg params: Request?) =
            OkHttpClient().newCall(request.build()).execute()

        override fun onPostExecute(result: Response) {
            super.onPostExecute(result)
            dialog?.dismiss()
            onCallBack(result)
        }

        private var onCallBack: (Response) -> Unit = {}

        fun setOnCallback(callback: (Response) -> Unit): Task {
            onCallBack = callback
            return this
        }

        private var dialog: ProgressDialog? = null
    }

    class Progress {
        var title: String? = null
        var message: String? = null
        var maxProgress: Int? = null
        var progressStyle: ProgressStyle? = null
        var indeterminate: Boolean? = null
    }

    protected val progress = Progress()

    fun setProgressStyle(style: Progress.() -> Unit) {
        style(progress)
    }

    enum class ProgressStyle {
        Spinner, Horizontal
    }

    enum class Method {
        GET, POST, PUT, PATCH, DELETE, HEAD
    }

    open val task: Task
        get() {
            validateParams()
            return Task()
        }

    private fun validateParams() {
        when (method) {
            Method.GET, Method.HEAD -> {
                if (body != null) {
                    throw IllegalArgumentException("method ${method.name} cannot have body")
                }
            }
            Method.PATCH, Method.PUT, Method.POST -> {
                if (body == null) {
                    throw IllegalArgumentException("method ${method.name} must have a body")
                }
            }
        }
    }

    var encoder = "utf-8"

    enum class MediaType(private val mimeType: String) {
        JSON("application/json"), PlainText("text/plain");

        fun getMediaType(encoder: String) = "${this.mimeType}; charset=$encoder".toMediaType()
    }

    protected var request = Request.Builder()

}


/**
 * Send and receive a message.
 */
class MessageNetworkTask(
    url: String,
    method: Method = Method.GET,
    ctx: Context? = null,
    private val bodyMessage: String? = null,
    private val mediaType: MediaType
) : NetworkTask(url, method, ctx) {
    inner class Task : AsyncTask<Request, Int, Pair<Int, String>>() {

        override fun onPreExecute() {
            super.onPreExecute()
            request.url(url).let {
                when (method) {
                    Method.GET -> it.get()
                    Method.POST -> it.post(
                        bodyMessage!!.toRequestBody(
                            mediaType.getMediaType(
                                encoder
                            )
                        )
                    )
                    Method.DELETE -> it.delete(
                        bodyMessage?.toRequestBody(
                            mediaType.getMediaType(
                                encoder
                            )
                        ) ?: EMPTY_REQUEST
                    )
                    Method.PUT -> it.put(bodyMessage!!.toRequestBody(mediaType.getMediaType(encoder)))
                    Method.PATCH -> it.patch(
                        bodyMessage!!.toRequestBody(
                            mediaType.getMediaType(
                                encoder
                            )
                        )
                    )
                    Method.HEAD -> it.head()
                }
            }
            dialog = ProgressDialog.show(
                ctx,
                progress.title ?: "Loading",
                progress.message ?: "Please wait..."
            )
        }

        override fun doInBackground(vararg params: Request?): Pair<Int, String> {
            val response = OkHttpClient().newCall(request.build()).execute()
            successful = response.isSuccessful
            return (response.code) to (response.body?.string() ?: "")
        }

        private var successful = false

        override fun onPostExecute(result: Pair<Int, String>) {
            super.onPostExecute(result)
            dialog?.dismiss()
            val (code, message) = result
            if (successful) {
                onSuccess(code, message)
            } else {
                onFailure(code, message)
            }
        }

        private var dialog: ProgressDialog? = null

        private var onSuccess: (statusCode: Int, message: String) -> Unit = { _, _ -> }
        private var onFailure: (statusCode: Int, message: String) -> Unit = { _, _ -> }
    }
}