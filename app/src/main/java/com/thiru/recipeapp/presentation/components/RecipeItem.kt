package com.thiru.recipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.thiru.recipeapp.common.Screen
import com.thiru.recipeapp.data.dto.Recipe
import java.net.URLEncoder

@Composable
fun RecipeItem(
    navController: NavController,
    recipe: Recipe,
) {

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(recipe.image)
            .size(Size.ORIGINAL)
            .build()
    ).state

    Column(
        modifier = Modifier
            .wrapContentHeight()
            .width(200.dp)
            .padding(horizontal = 4.dp, vertical = 4.dp)
            .clickable {
                val imageUrlEncoded = URLEncoder.encode(recipe.image, "UTF-8")
                navController.navigate(
                    Screen.RecipeSummaryScreen.route + "/${recipe.id}/${imageUrlEncoded}"
                )
            }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            if (imageState is AsyncImagePainter.State.Success) {
                Image(
                    modifier = Modifier
                        .width(120.dp)
                        .height(120.dp)
                        .clip(RoundedCornerShape(8.dp)),
                    contentDescription = recipe.title,
                    painter = imageState.painter,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.Center
                )
            }
        }
        Spacer(modifier = Modifier.height(6.dp))
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(horizontal = 10.dp, vertical = 6.dp),
                textAlign = TextAlign.Center,
                maxLines = 2,
                text = recipe.title
            )
        }
    }
}