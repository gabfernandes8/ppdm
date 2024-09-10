package br.senai.sp.jandira.rickandmortyapi.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.rickandmortyapi.model.Character
import br.senai.sp.jandira.rickandmortyapi.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CharacterDetails(modifier: Modifier = Modifier) {

    var id by remember {
        mutableStateOf("")
    }

    var character by remember {
        mutableStateOf(Character())
    }

    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xff303030)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
        ) {
            OutlinedTextField(
                value = id,
                onValueChange = { valor -> id = valor },
                modifier = Modifier
                    .fillMaxWidth(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                trailingIcon = {
                    IconButton(onClick = {
                        val callCharacter = RetrofitFactory()
                            .getCharacterService()
                            .getCharacterById(id.toInt())

                        callCharacter.enqueue(object : Callback<Character> {
                            override fun onResponse(
                                p0: Call<Character>,
                                resp: Response<Character>
                            ) {
                                character = resp.body()!!
                            }

                            override fun onFailure(p0: Call<Character>, p1: Throwable) {
                                TODO("Not yet implemented")
                            }

                        })
                    }) {
                        Icon(
                            imageVector = Icons.Default.Search,
                            contentDescription = ""
                        )
                    }
                }
            )

            Spacer(modifier = Modifier.height(32.dp))

            Card(
                modifier = Modifier
                    .height(250.dp)
                    .fillMaxWidth(),
                colors = CardDefaults.cardColors(Color(0x442dabc4))
            ) {
                Card(
                    modifier = Modifier
                        .size(150.dp)
                        .padding(15.dp)
                        .align(alignment = Alignment.CenterHorizontally)
                        .fillMaxSize(),
                    shape = CircleShape,
                    elevation = CardDefaults.cardElevation(
                        defaultElevation = 15.dp
                    )

                ) {
                    AsyncImage(
                        model = character.image,
                        contentDescription = "Imagem do personagem ${character.name}"
                    )
                }
                Column(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxSize()
                ) {
                    Text(
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .fillMaxWidth(),
                        text = "${character.name}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .fillMaxWidth(),
                        text = "${character.species}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                    Text(
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .fillMaxWidth(),
                        text = "${character.origin.name}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))
        }
    }
}

// funcao de desenhar card
@Composable
fun EpisodeCard(character: Character) {

    Card(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .height(100.dp),
//        .clickable {
//            controleNavegacao.navigate("details")
//        },
        colors = CardDefaults.cardColors(
            containerColor =  Color(0x442dabc4)
        )
    ) {
        Row {
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = character.episode,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.White,
                    fontFamily = FontFamily.Serif
                )
                Text(
                    text = character.species,
                    fontWeight = FontWeight.Thin,
                    color = Color.White
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun EpisodeCardPreview() {
    EpisodeCard(Character(

    ))
}

@Preview(showSystemUi = true)
@Composable
private fun CharacterDetailsPreview() {
    CharacterDetails()
}