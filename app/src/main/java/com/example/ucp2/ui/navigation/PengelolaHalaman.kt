package com.example.ucp2.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.ucp2.ui.view.HomePage
import com.example.ucp2.ui.view.barang.DetailBrgView
import com.example.ucp2.ui.view.barang.HomeBrgView
import com.example.ucp2.ui.view.barang.InsertBrgView
import com.example.ucp2.ui.view.barang.UpdateBrgView
import com.example.ucp2.ui.view.suplier.HomeSplView
import com.example.ucp2.ui.view.suplier.InsertSplView

@Composable
fun PengelolaHalaman(
    navController: NavHostController = rememberNavController(),
    modifier: Modifier = Modifier
){
    NavHost(
        navController = navController,
        startDestination = DestinasiHome.route
    ) {
        composable(
            route = DestinasiHome.route
        ){
            HomePage(
                onAddBarang = { navController.navigate(DestinasiInsertBrg.route ) },
                onAddSuplier = {navController.navigate(DestinasiInsertSpl.route )},
                onHomeBarang = {navController.navigate(DestinasiHomeBrg.route)},
                onHomeSuplier = {navController.navigate(DestinasiHomeSpl.route)},
            )
        }
        composable(
            route = DestinasiHomeBrg.route
        ) {
            HomeBrgView(
                onDetailClick = {
                        idBarang ->
                    navController.navigate("${DestinasiDetailBrg.route}/$idBarang")
                    println("PengelolaHalaman: id_barang = $idBarang")
                },
                onAddBrg = {navController.navigate(DestinasiInsertBrg.route)},
                onBack = {
                    navController.popBackStack()
                },
                modifier = Modifier
            )
        }
        composable(
            route = DestinasiHomeSpl.route
        ) {
            HomeSplView(
                onBack = {
                    navController.popBackStack()
                },
                modifier = Modifier
            )
        }
        composable(
            route = DestinasiInsertBrg.route
        ){
            InsertBrgView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {

                },
                modifier = modifier
            )
        }

        composable(
            DestinasiDetailBrg.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiDetailBrg.ID){
                    type = NavType.StringType
                }
            )
        ){
            val id = it.arguments?.getString(DestinasiDetailBrg.ID)
            id?.let { id ->
                DetailBrgView(
                    onBack = {
                        navController.popBackStack()
                    },
                    onEditClick = {
                        navController.navigate("${DestinasiUpdateBrg.route}/$it")
                    },
                    modifier = modifier,
                    onDeleteClick = {
                        navController.popBackStack()
                    }
                )
            }
        }

        composable(
            DestinasiUpdateBrg.routesWithArg,
            arguments = listOf(
                navArgument(DestinasiUpdateBrg.ID){
                    type = NavType.StringType
                }
            )
        ){
            UpdateBrgView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {
                    navController.popBackStack()
                },
                modifier = modifier,
            )
        }

        composable(
            route = DestinasiInsertSpl.route
        ){
            InsertSplView(
                onBack = {
                    navController.popBackStack()
                },
                onNavigate = {

                },
                modifier = modifier
            )
        }

    }
}