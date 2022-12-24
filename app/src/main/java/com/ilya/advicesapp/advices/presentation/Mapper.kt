package com.ilya.advicesapp.advices.presentation

interface Mapper<S, R> {
    fun map(source: S): R
}
