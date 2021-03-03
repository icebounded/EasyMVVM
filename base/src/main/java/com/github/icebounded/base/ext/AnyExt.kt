package com.github.icebounded.base.ext

/**
 * Created by liuyanxi
 * on 2021/2/25
 */
val Any.simpleName: String
    get() = javaClass.simpleName

val Any.logTag: String
    get() = "LOG_${javaClass.simpleName}"