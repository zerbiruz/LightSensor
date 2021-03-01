package com.example.lightsensor

import android.app.Application
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorManager
import android.hardware.SensorEventListener
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData

class SensorViewModel(application: Application): AndroidViewModel(application), SensorEventListener {

    private lateinit var sensorManager: SensorManager
    private lateinit var sensor: Sensor
    var brightness = MutableLiveData<Float>()
    var lightText = MutableLiveData<String>()

    fun registerSensor() {
        this.sensorManager = getApplication<Application>().getSystemService(AppCompatActivity.SENSOR_SERVICE) as SensorManager
        this.sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT)
        this.sensorManager.registerListener(this, this.sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }

    fun unregisterSensor() {
        this.sensorManager.unregisterListener(this)
    }

    private fun brightness(brightness: Float): String {

        return when (brightness.toInt()) {
            0 -> "Pitch black"
            in 1..10 -> "Dark"
            in 11..50 -> "Grey"
            in 51..5000 -> "Normal"
            in 5001..25000 -> "Incredibly bright"
            else -> "This light will blind you"
        }
    }

    override fun onSensorChanged(event: SensorEvent?) {
        if (event?.sensor?.type == Sensor.TYPE_LIGHT) {
            val light1 = event.values[0]
            this.brightness.value = light1
            this.lightText.value = "Sensor: $light1\n${this.brightness(light1)}"
        }
    }

    override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {

    }
}