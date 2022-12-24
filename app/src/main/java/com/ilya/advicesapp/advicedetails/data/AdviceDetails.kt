package com.ilya.advicesapp.advicedetails.data

/**
 * Created by HP on 25.11.2022.
 **/
interface AdviceDetails {

    interface Read<T>{
        fun read(): T
    }

    interface Save<T>{
        fun save(data: T)
    }

    interface Mutable<T>: Read<T>,Save<T>

    class Base<T> (
        private var data: T
    ): Mutable<T>{

        override fun read(): T = data

        override fun save(data: T){this.data = data}

    }
}

