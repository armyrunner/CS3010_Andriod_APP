package com.example.triviaquestions

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.os.SystemClock
import android.util.Log
import java.io.IOException


private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 1


class sounds (private val assets: AssetManager){
    val sounds: List<Sound>
    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()

    init {
        sounds = loadSounds()
    }

    fun play(sound: Sound) {
        sound.soundId?.let {
            val waitLimit = 1000
            var waitCounter = 0
            val throttle = 10
            while (soundPool.play(it, 1.0f, 1.0f, 1, 0, 1.0f) == 0 && waitCounter < waitLimit) {
                waitCounter++
                SystemClock.sleep(throttle.toLong())
            }
            //soundPool.play(it, 1.0f, 1.0f, 1, 5, 1.0f)
        }
    }

    fun release() {
        soundPool.release()
    }

    fun loadSounds(): List<Sound> {

        val soundNames: Array<String>

        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!
        } catch (e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()
        soundNames.forEach { filename ->
            val assetPath = "$SOUNDS_FOLDER/$filename"
            val sound = Sound(assetPath)

            try {
                load(sound)
                sounds.add(sound)
            } catch (ioe: IOException) {
                Log.e(TAG, "Could not load sound $filename", ioe)
            }        }
        return sounds
    }

    private fun load(sound: Sound) {
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }

}

private const val WAV = ".mp3"

class Sound(val assetPath: String, var soundId: Int? = null) {
    val name = assetPath.split("/").last().removeSuffix(WAV)
    val myPath = assetPath
}