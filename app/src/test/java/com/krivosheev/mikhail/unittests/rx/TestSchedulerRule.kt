package com.krivosheev.mikhail.unittests.rx

import io.reactivex.Scheduler
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.internal.schedulers.TrampolineScheduler
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class TestSchedulerRule : TestRule {
    private val immediate = object : Scheduler() {
        override fun createWorker(): Worker {
            return ExecutorScheduler.ExecutorWorker(Runnable::run, true)
        }
    }

    val testScheduler = TestScheduler()
    val trampolineScheduler = Schedulers.trampoline()

    override fun apply(base: Statement, description: Description?): Statement {
        return object : Statement() {
            @Throws(Throwable::class)
            override fun evaluate() {
                RxJavaPlugins.setIoSchedulerHandler { _ -> trampolineScheduler }
                RxJavaPlugins.setComputationSchedulerHandler { _ -> testScheduler }
                RxJavaPlugins.setNewThreadSchedulerHandler { _ -> testScheduler }
                RxAndroidPlugins.setMainThreadSchedulerHandler { _ -> immediate }
                try {
                    base.evaluate()
                } finally {
                    RxJavaPlugins.reset()
                    RxAndroidPlugins.reset()
                }
            }
        }
    }
}