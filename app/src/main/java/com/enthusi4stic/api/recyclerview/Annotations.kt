package com.enthusi4stic.api.recyclerview

@Target(AnnotationTarget.FIELD)
annotation class BindView(val id: String = DefaultID, val view: View, val field: Field) {
    enum class View {
        TextView, ImageView
    }

    enum class Field {
        Visibility, Enabled, Resource, Text
    }

    companion object {
        const val DefaultID = "None"
    }
}

operator fun BindView.component1() = this.id
operator fun BindView.component2() = this.view
operator fun BindView.component3() = this.field


