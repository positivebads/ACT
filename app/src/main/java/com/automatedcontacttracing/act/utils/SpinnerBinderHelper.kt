package com.automatedcontacttracing.act.utils

import android.view.View
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.databinding.adapters.AdapterViewBindingAdapter.*


@BindingAdapter("android:selectedItemPosition")
fun setSelectedItemPosition(view: AdapterView<*>, position: Int) {
    if (view.selectedItemPosition != position) {
        view.setSelection(position)
    }
}

@BindingAdapter(
    value = ["android:onItemSelected", "android:onNothingSelected", "android:selectedItemPositionAttrChanged"],
    requireAll = false
)
fun setOnItemSelectedListener(
    view: AdapterView<*>,
    selected: OnItemSelected?,
    nothingSelected: OnNothingSelected?,
    attrChanged: InverseBindingListener?
) {
    if (selected == null && nothingSelected == null && attrChanged == null) {
        view.setOnItemSelectedListener(null)
    } else {
        view.setOnItemSelectedListener(
            OnItemSelectedComponentListener(selected, nothingSelected, attrChanged)
        )
    }
}

@BindingAdapter("android:selectedValueAttrChanged")
fun setSelectedValueListener(
    view: AdapterView<*>,
    attrChanged: InverseBindingListener?
) {
    if (attrChanged == null) {
        view.setOnItemSelectedListener(null)
    } else {
        view.setOnItemSelectedListener(object : OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                attrChanged.onChange()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                attrChanged.onChange()
            }
        })
    }
}

@BindingAdapter("android:selectedValue")
fun setSelectedValue(view: AdapterView<*>, selectedValue: Any) {
    val adapter = view.adapter ?: return
    // I haven't tried this, but maybe setting invalid position will
    // clear the selection?
    var position = AdapterView.INVALID_POSITION
    for (i in 0 until adapter.count) {
        if (adapter.getItem(i) === selectedValue) {
            position = i
            break
        }
    }
    view.setSelection(position)
}