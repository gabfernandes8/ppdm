package br.senai.sp.jandira.rickandmortyapi.screens

import android.util.Log
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import br.senai.sp.jandira.rickandmortyapi.model.Character
import br.senai.sp.jandira.rickandmortyapi.model.Episode
import br.senai.sp.jandira.rickandmortyapi.service.RetrofitFactory
import coil.compose.AsyncImage
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Composable
fun CharacterDetails(navHostController: NavHostController, characterId : String) {

    var id by remember {
        mutableStateOf("")
    }

    var character by remember {
        mutableStateOf(Character())
    }

    val callCharacter = RetrofitFactory()
        .getCharacterService()
        .getCharacterById(characterId.toInt())

    callCharacter.enqueue(object : Callback<Character> {
        override fun onResponse(
            p0: Call<Character>,
            resp: Response<Character>
        ) {
            character = resp.body()!!
        }

        override fun onFailure(p0: Call<Character>, p1: Throwable) {
        }

    })


    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = Color(0xff303030)
    ) {
        Column(
            modifier = Modifier
                .padding(15.dp)
        ) {
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
                        defaultElevation = 50.dp
                    )

                ) {
                    AsyncImage(
                        model = character.image,
                        contentDescription = "Imagem do personagem ${character.name}"
                    )
                }
                Row(
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
                    Text(
                        modifier = Modifier
                            .padding(bottom = 5.dp)
                            .fillMaxWidth(),
                        text = "${character.status}",
                        fontWeight = FontWeight.Bold,
                        color = Color.White,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Spacer(modifier = Modifier.height(15.dp))

            var episodesIdList by remember {
                mutableStateOf("")
            }
            
            var episodeList by remember {
                mutableStateOf(listOf<Episode>())
            }

            character.episode.forEach(){
                episodesIdList += "${it.substringAfterLast("/")},"
            }
            Log.i("oi", "CharacterDetails: $episodesIdList")
            var callEpisodes = RetrofitFactory()
                .getEpisodeService()
                .getEpisodesById(episodesIdList.dropLast(1))
            
            callEpisodes.enqueue(
                object: Callback<List<Episode>> {
                    override fun onResponse(p0: Call<List<Episode>>, resp: Response<List<Episode>>) {
                        episodeList = resp.body()!!
                    }

                    override fun onFailure(p0: Call<List<Episode>>, p1: Throwable) {
                    }

                }
            )

            LazyColumn {
                items(episodeList){
                    Text(text = it.name)
                }
            }
        }
    }
}

// funcao de desenhar card
@Composable
fun EpisodeCard(episode: Episode) {

    Surface(color = Color(0xff303030)) {

        Card(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth()
                .height(80.dp),
            colors = CardDefaults.cardColors(
                containerColor = Color(0x442dabc4)
            ),
            elevation = CardDefaults.cardElevation(
                defaultElevation = 15.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(15.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column(
                    modifier = Modifier
                        .padding(start = 5.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "${episode.name}",
                        fontWeight = FontWeight.SemiBold,
                        color = Color.White,
                    )
                    Text(
                        text = "${episode.id}",
                        fontWeight = FontWeight.Normal,
                        color = Color.White
                    )
                }
                Column {
                    Text(
                        text = "${episode.created}",
                        fontWeight = FontWeight.Normal,
                        color = Color.White

                    )
                }
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//private fun EpisodeCardPreview() {
//    //EpisodeCard(Episode(1))
//}

//@Preview(showSystemUi = true)
//@Composable
//private fun CharacterDetailsPreview() {
//    CharacterDetails()
//}