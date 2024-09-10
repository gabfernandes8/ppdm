package br.senai.sp.jandira.rickandmortyapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import br.senai.sp.jandira.rickandmortyapi.screens.CharacterDetails
import br.senai.sp.jandira.rickandmortyapi.screens.ListAllCharacters
import br.senai.sp.jandira.rickandmortyapi.ui.theme.RickAndMortyApiTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            RickAndMortyApiTheme {
                // lembrar das telas que passou anteriormente
                val controleNavegacao = rememberNavController()

                // rota de navegação
                NavHost(
                    navController = controleNavegacao,
                    startDestination = "listAll"
                ) {
                    // cria a rota
                    composable(route = "listAll"){ ListAllCharacters() }
                    composable(route = "details"){ CharacterDetails() }
                }
            }
        }
    }
}

