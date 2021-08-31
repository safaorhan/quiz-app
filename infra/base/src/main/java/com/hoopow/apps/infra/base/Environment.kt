package com.hoopow.apps.infra.base

enum class Environment(val flavor: String) {
    DEV(flavor = "dev"),
    QA(flavor = "qa"),
    PROD(flavor = "prod")
}