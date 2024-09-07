package com.thiru.recipeapp.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.thiru.recipeapp.data.dto.RecipeSummary
import com.thiru.recipeapp.presentation.viewmodel.RecipeSummaryViewModel

@Composable
fun RecipeSummaryScreen(viewModel: RecipeSummaryViewModel = hiltViewModel<RecipeSummaryViewModel>()) {

    val recipeSummaryState = viewModel.recipeSummary.collectAsState().value
    val recipeSummary: RecipeSummary? = recipeSummaryState.recipeSummary

    val imageState = rememberAsyncImagePainter(
        model = ImageRequest.Builder(LocalContext.current)
            .data(recipeSummary?.imageUrl)
            .size(Size.ORIGINAL)
            .build()
    ).state

    recipeSummary?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            if (imageState is AsyncImagePainter.State.Success) {
                Image(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(220.dp),
                    painter = imageState.painter,
                    contentDescription = recipeSummary.title,
                    contentScale = ContentScale.Crop
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                modifier = Modifier
                    .wrapContentWidth(),
                fontSize = 19.sp,
                fontWeight = FontWeight.Bold,

                text = recipeSummary.title,
            )
            Text(text = recipeSummary.summary)
        }
    }
}
