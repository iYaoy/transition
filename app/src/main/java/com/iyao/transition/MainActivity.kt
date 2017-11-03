package com.iyao.transition

import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.transition.*
import android.transition.TransitionSet.ORDERING_TOGETHER
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var sceneA : Scene? = null
    private var sceneB : Scene? = null

    private val runnableSceneA: Runnable = Runnable {
        TransitionManager.go(sceneA!!, transitionSet.addListener(listenerA))
    }

    private val runnableSceneB: Runnable = Runnable {
        TransitionManager.go(sceneB!!, transitionSet.addListener(listenerB))
    }

    private val listenerA = object : TransitionListenerAdapter() {
        override fun onTransitionEnd(transition: Transition) {
            transition.removeListener(this)
            runnableSceneB.run()
        }
    }

    private val listenerB = object : TransitionListenerAdapter() {
        override fun onTransitionEnd(transition: Transition) {
            transition.removeListener(this)
            runnableSceneA.run()
        }
    }

    private val transitionSet = TransitionSet().apply {
        addTransition(ChangeRectRadiusAndColor())
        addTransition(ChangeBounds())
        ordering = ORDERING_TOGETHER
        duration = 3000
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            pathMotion = ArcMotion()
        }
        interpolator = AccelerateDecelerateInterpolator()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initial()
        layoutSceneRoot.postDelayed({
            runnableSceneB.run()
        }, 1000)
    }

    private fun initial() {
        @Suppress("DEPRECATION")
        sceneA = Scene(layoutSceneRoot, layoutSceneA as ViewGroup)
        sceneB = Scene.getSceneForLayout(layoutSceneRoot, R.layout.scene_b, this)
    }

    /**
     * 	android.transition.TransitionListenerAdapter added in API level 26, so
     * 	i have to implement one
     */
    open class TransitionListenerAdapter : Transition.TransitionListener {
        override fun onTransitionEnd(transition: Transition) {
        }

        override fun onTransitionResume(transition: Transition) {
        }

        override fun onTransitionPause(transition: Transition) {
        }

        override fun onTransitionCancel(transition: Transition) {
        }

        override fun onTransitionStart(transition: Transition) {
        }

    }
}