package com.home.bgaswipebacklayoutdemo.common.base

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.IdRes
import androidx.annotation.IntRange
import androidx.appcompat.app.AppCompatActivity
import com.home.bgaswipebacklayoutdemo.R
import com.home.bgaswipebacklayoutdemo.common.component.swipebacklayout.BGASwipeBackHelper
import com.jaeger.library.StatusBarUtil

/**
 * 必须在Application的onCreate方法中执行BGASwipeBackHelper.init来初始化滑动返回
 */
@Suppress("UNCHECKED_CAST")
abstract class BaseActivity : AppCompatActivity(), BGASwipeBackHelper.Delegate,
    View.OnClickListener {

    protected lateinit var mSwipeBackHelper: BGASwipeBackHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 在 super.onCreate(savedInstanceState) 之前调用该方法
        initSwipeBackFinish()
        super.onCreate(savedInstanceState)
        initView(savedInstanceState)
        setListener()
        processLogic(savedInstanceState)
        // 讓狀態欄的文字顏色變成黑色
        com.home.bgaswipebacklayoutdemo.common.util.StatusBarUtil.darkMode(this)
    }

    /**
     * 初始化滑动返回, 在super.onCreate(savedInstanceState)之前调用该方法
     */
    private fun initSwipeBackFinish() {
        mSwipeBackHelper = BGASwipeBackHelper(this, this)
        // 「必须在 Application 的 onCreate 方法中执行 BGASwipeBackHelper.init 来初始化滑动返回」
        // 设置滑动返回是否可用。默认值为 true
        mSwipeBackHelper.setSwipeBackEnable(true)
        // 设置是否仅仅跟踪左侧边缘的滑动返回。默认值为 true
        mSwipeBackHelper.setIsOnlyTrackingLeftEdge(true)
        // 设置是否是微信滑动返回样式。默认值为 true
        mSwipeBackHelper.setIsWeChatStyle(true)
        // 设置阴影资源 id。默认值为 R.drawable.bga_sbl_shadow
        mSwipeBackHelper.setShadowResId(R.drawable.bga_sbl_shadow)
        // 设置是否显示滑动返回的阴影效果。默认值为 true
        mSwipeBackHelper.setIsNeedShowShadow(true)
        // 设置阴影区域的透明度是否根据滑动的距离渐变。默认值为 true
        mSwipeBackHelper.setIsShadowAlphaGradient(true)
        // 设置触发释放后自动滑动返回的阈值，默认值为 0.3f
        mSwipeBackHelper.setSwipeBackThreshold(0.3f)
        // 设置底部导航条是否悬浮在内容上，默认值为 false
        mSwipeBackHelper.setIsNavigationBarOverlap(false)
    }

    /**
     * 是否支持滑动返回。这里在父类中默认返回 true 来支持滑动返回，如果某个界面不想支持滑动返回则重写该方法返回 false 即可
     */
    override fun isSupportSwipeBack(): Boolean {
        return true
    }

    /**
     * 正在滑动返回
     *
     * @param slideOffset 从 0 到 1
     */
    override fun onSwipeBackLayoutSlide(slideOffset: Float) {}

    /**
     * 没达到滑动返回的阈值，取消滑动返回动作，回到默认状态
     */
    override fun onSwipeBackLayoutCancel() {}

    /**
     * 滑动返回执行完毕，销毁当前 Activity
     */
    override fun onSwipeBackLayoutExecuted() {
        mSwipeBackHelper.swipeBackward()
    }

    override fun onBackPressed() {
        // 正在滑动返回的时候取消返回按钮事件
        if (mSwipeBackHelper.isSliding) {
            return
        }
        mSwipeBackHelper.backward()
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     */
    protected fun setStatusBarColor(@ColorInt color: Int) {
        setStatusBarColor(color, StatusBarUtil.DEFAULT_STATUS_BAR_ALPHA)
    }

    /**
     * 设置状态栏颜色
     *
     * @param color
     * @param statusBarAlpha 透明度
     */
    fun setStatusBarColor(@ColorInt color: Int, @IntRange(from = 0, to = 255) statusBarAlpha: Int) {
        StatusBarUtil.setColorForSwipeBack(this, color, statusBarAlpha)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            onBackPressed()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    /**
     * 初始化布局以abstract及View控件
     */
    protected abstract fun initView(savedInstanceState: Bundle?)

    /**
     * 给View控件添加事件监听器
     */
    protected abstract fun setListener()

    /**
     * 处理业务逻辑，状态恢复等操作
     *
     * @param savedInstanceState
     */
    protected abstract fun processLogic(savedInstanceState: Bundle?)

    /**
     * 需要处理点击事件时，重写该方法
     *
     * @param v
     */
    override fun onClick(v: View) {}

    /**
     * 查找View
     *
     * @param id   控件的id
     * @param <VT> View类型
     * @return
    </VT> */
    protected fun <VT : View> getViewById(@IdRes id: Int): VT {
        return findViewById<View>(id) as VT
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(this.javaClass.simpleName, "onDestroy")
    }

    override fun onStop() {
        super.onStop()
        Log.i(this.javaClass.simpleName, "onStop")
    }
}
