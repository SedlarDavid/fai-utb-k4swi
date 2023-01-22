package cz.sedlardavid.eventorr.components.eventDetail

import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextDecoration
import cz.sedlardavid.eventorr.entities.Event

@Composable
fun EventLink(event: Event) {
    val annotatedLinkString: AnnotatedString = buildAnnotatedString {

        val str = event.url ?: ""
        val startIndex = 0
        val endIndex = str.length - 1
        append(str)
        addStyle(
            style = SpanStyle(
                color = Color.White, textDecoration = TextDecoration.Underline,
            ), start = startIndex, end = endIndex
        )

        // attach a string annotation that stores a URL to the text "link"
        addStringAnnotation(
            tag = "URL",
            annotation = str,
            start = startIndex,
            end = endIndex
        )

    }
    val uriHandler = LocalUriHandler.current

    ClickableText(
        text = annotatedLinkString,
        onClick = {
            annotatedLinkString
                .getStringAnnotations("URL", it, it)
                .firstOrNull()?.let { stringAnnotation ->
                    uriHandler.openUri(stringAnnotation.item)
                }
        }
    )
}