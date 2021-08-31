package com.hoopow.apps.infra.base

import com.hoopow.apps.infra.base.Environment.DEV

fun String.asEnvironment() = Environment.values().find { it.flavor == this } ?: DEV