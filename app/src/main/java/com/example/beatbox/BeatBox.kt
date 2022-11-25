package com.example.beatbox

import android.content.res.AssetFileDescriptor
import android.content.res.AssetManager
import android.media.SoundPool
import android.util.Log
import java.io.IOException


private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"
private const val MAX_SOUNDS = 5
class BeatBox(private val assets: AssetManager) {

    private val soundPool = SoundPool.Builder()
        .setMaxStreams(MAX_SOUNDS)
        .build()

    val sound: List<Sound>

    init {
        sound = loadSound()
    }

    private fun loadSound(): List<Sound> {
        val soundNames: Array<String>
        try {
            soundNames = assets.list(SOUNDS_FOLDER)!!

        } catch (e: Exception) {
            Log.e(TAG, "Could not list assets", e)
            return emptyList()
        }
        val sounds = mutableListOf<Sound>()
        soundNames.forEach { fileName ->
            val assetPatch = "$SOUNDS_FOLDER/$fileName"
            val sound = Sound(assetPatch)
            try {
                load(sound)
                sounds.add(sound)
            } catch (e: IOException) {
                Log.e(TAG, "Cond not load sound$fileName", e)
            }

        }
        return sounds
    }

    private fun load(sound: Sound) {
        val afd: AssetFileDescriptor = assets.openFd(sound.assetPath)
        val soundId = soundPool.load(afd, 1)
        sound.soundId = soundId
    }

    fun play(sound: Sound) {
        sound.soundId.let {
            if (it != null) {
                soundPool.play(it, 1.0f, 1.0f, 1, 0, 1.0f)
            }
        }
    }
    fun release() {
        soundPool.release()
    }


}