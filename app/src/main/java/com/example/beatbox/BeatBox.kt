package com.example.beatbox

import android.content.res.AssetManager
import android.util.Log


private const val TAG = "BeatBox"
private const val SOUNDS_FOLDER = "sample_sounds"

class BeatBox(private val assets: AssetManager) {
    val sound: List<Sound>

    init {
        sound = loadSound()
    }

    fun loadSound(): List<Sound> {
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
            sounds.add(sound)
        }
        return sounds
    }


}