package rus.tutby.utils;

/**
 * Created by RUS on 15.03.2016.
 */

class Time(val nanoTime: Double = System.nanoTime().toDouble()) {

    fun getTime() : Double = (System.nanoTime() - nanoTime) / 1000000000;
}
