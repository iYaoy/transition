package com.iyao.transition

import android.os.Bundle
import android.support.transition.*
import android.support.transition.TransitionSet.ORDERING_TOGETHER
import android.support.v7.app.AppCompatActivity
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
//            runnableSceneA.run()
        }
    }

    private val transitionSet = TransitionSet().apply {
        addTransition(RectTransition())
        addTransition(ChangeBounds())
        ordering = ORDERING_TOGETHER
        duration = 3000
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
        sceneA = Scene(layoutSceneRoot, layoutSceneA)
        sceneB = Scene.getSceneForLayout(layoutSceneRoot, R.layout.scene_b, this)
    }
}