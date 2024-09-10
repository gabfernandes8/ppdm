package br.senai.sp.jandira.rickandmortyapi.screens

import android.widget.Toast
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.rickandmortyapi.model.Character
import br.senai.sp.jandira.rickandmortyapi.model.Results
import br.senai.sp.jandira.rickandmortyapi.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun ListAllCharacters() {

    var charactersList by remember {
        mutableStateOf(listOf<Character>())
    }

    //conectando com a api
    val charactersCall = RetrofitFactory()
        .getCharacterService()
        .getAllCharacters()

    charactersCall.enqueue(
        object : Callback<Results> {
            override fun onResponse(p0: Call<Results>, resp: Response<Results>) {
                charactersList = resp.body()!!.results
            }

            override fun onFailure(p0: Call<Results>, p1: Throwable) {
            }

        }
    )

    Surface(modifier = Modifier
        .fillMaxSize(),
        color = Color(0xff303030)
    ) {
        Column(modifier = Modifier
            .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Rick & Morty",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.Serif,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp))

            LazyColumn {
                items(charactersList){
                    //CharacterCard(it, controleNavegacao)
                }
            }
        }
    }
}

// funcao de desenhar card
@Composable
fun CharacterCard(character: Character, controleNavegacao: NavHostController) {

    Card(modifier = Modifier
        .padding(5.dp)
        .fillMaxWidth()
        .height(100.dp)
        .clickable {
            controleNavegacao.navigate("details")
        },
        colors = CardDefaults.cardColors(
            containerColor =  Color(0x442dabc4)
        )
    ) {
        Row {
            Card(modifier = Modifier
                .size(100.dp)
                .padding(10.dp),
                shape = CircleShape,
            ) {
                AsyncImage(
                    model = character.image,
                    contentDescription = ""
                )
            }
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(start = 5.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = character.name,
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

//@Preview(showSystemUi = true)
//@Composable
//private fun ListAllCharactersPreview() {
//    ListAllCharacters()
//}