package com.home.bgaswipebacklayoutdemo.view

import android.os.Bundle
import com.bumptech.glide.Glide
import com.home.bgaswipebacklayoutdemo.R
import com.home.bgaswipebacklayoutdemo.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_favorite.*

class FavoriteActivity : BaseActivity() {

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_favorite)
        Glide.with(this).load(R.drawable.icon_favorite).into(image_view)
    }

    override fun setListener() {}

    override fun processLogic(savedInstanceState: Bundle?) {}
}
