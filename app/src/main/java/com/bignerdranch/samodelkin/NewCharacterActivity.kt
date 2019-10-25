package com.bignerdranch.samodelkin

import android.os.Bundle
import android.os.PersistableBundle
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_new_character.*
import kotlinx.coroutines.android.UI
import kotlinx.coroutines.launch

private const val CHARACTER_DATA_KEY = "CHARACTER_DATA_KEY"

private var Bundle.characterData
    get() = getSerializable(CHARACTER_DATA_KEY) as CharacterGenerator.CharacterData
    set(value) = putSerializable(CHARACTER_DATA_KEY, value)

class NewCharacterActivity : AppCompatActivity() {

    private var characterData = CharacterGenerator.generate()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_character)

        characterData = savedInstanceState?.characterData ?: CharacterGenerator.generate()

        btn_generate.setOnClickListener {
            launch(UI) {
                characterData = fetchCharacterData().await()
                //CharacterGenerator.fromApiData("halfing, Lars Kizzy, 14, 13, 8")
                displayCharacterData()
            }
        }
        displayCharacterData()
    }

    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.characterData = characterData
    }

    private fun displayCharacterData() {
        characterData.run {
            tv_name_input.text = name
            tv_race_input.text = race
            tv_dexterity_num.text = dex
            tv_wisdom_num.text = wis
            tv_strength_num.text = str
        }
    }
}
