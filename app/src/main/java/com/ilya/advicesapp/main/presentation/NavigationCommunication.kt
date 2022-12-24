package com.ilya.advicesapp.main.presentation

import javax.inject.Inject

/**
 * Created by HP on 15.11.2022.
 **/
interface NavigationCommunication{

    interface Update: Communication.SuspendUpdate<NavigationStrategy>
    interface Collect: Communication.Collector<NavigationStrategy>
    interface Mutable: Update, Collect

    class Base @Inject constructor():
        Communication.SingleUiUpdate<NavigationStrategy>(), Mutable

}