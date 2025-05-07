package com.example.demophotosearchapp.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.demophotosearchapp.R
import com.example.demophotosearchapp.ui.theme.beVietNamRegular
import com.example.demophotosearchapp.utils.extension.Dimens
import com.example.demophotosearchapp.utils.extension.dimenDp
import com.example.demophotosearchapp.utils.extension.dimenSp

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size

@Composable
fun SimpleDropdown(
    options: List<String> = listOf("All", "Landscape", "Portrait", "Square"),
    default: String = "All",
    onSelected: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(default) }

    Box(
        modifier = Modifier
            .fillMaxHeight()
            .clickable { expanded = true }
            .clip(
                RoundedCornerShape(
                    topStart = dimensionResource(R.dimen.size_0),
                    topEnd = dimensionResource(R.dimen.size_60),
                    bottomStart = dimensionResource(R.dimen.size_0),
                    bottomEnd = dimensionResource(R.dimen.size_60)
                )
            )
            .background(colorResource(R.color.gray)), contentAlignment = Alignment.Center
    ) {

        Row {
            Text(
                text = selectedOption,
                modifier = Modifier
                    .padding(start = Dimens.Padding10)
                    .width(dimenDp(R.dimen.box_filter_size)),
                style = beVietNamRegular(dimenSp(R.dimen.text_size_12)),
                color = Color.Black,
                textAlign = TextAlign.Start,
                maxLines = 1, overflow = TextOverflow.Ellipsis,
            )
            Image(
                painter = painterResource(
                    id = if (expanded) R.drawable.arrow_up else R.drawable.ic_drop_down
                ),
                contentDescription = null,
                modifier = Modifier.size(20.dp)
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = {
                        Text(
                            option,
                            style = beVietNamRegular(dimenSp(R.dimen.text_size_12))
                        )
                    },
                    onClick = {
                        selectedOption = option
                        expanded = false
                        onSelected(option)
                    }
                )
            }
        }
    }
}
