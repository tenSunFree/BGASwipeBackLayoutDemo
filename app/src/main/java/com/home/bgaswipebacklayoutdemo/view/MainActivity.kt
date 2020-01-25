package com.home.bgaswipebacklayoutdemo.view

import android.os.Bundle
import com.bumptech.glide.Glide
import com.home.bgaswipebacklayoutdemo.R
import com.home.bgaswipebacklayoutdemo.common.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity() {

    /**
     * 主界面不需要支持滑动返回, 重写该方法永久禁用当前界面的滑动返回功能
     */
    override fun isSupportSwipeBack(): Boolean {
        return false
    }

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_main)
        Glide.with(this).load(R.drawable.icon_mine).into(image_view)
        view_favorite.setOnClickListener { mSwipeBackHelper.forward(FavoriteActivity::class.java) }
        view_album.setOnClickListener { mSwipeBackHelper.forward(AlbumActivity::class.java) }
    }

    override fun setListener() {}

    override fun processLogic(savedInstanceState: Bundle?) {}

    override fun onBackPressed() {
        finish()
    }
}
