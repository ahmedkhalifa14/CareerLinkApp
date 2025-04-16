package com.ahmedkhalifa.careerlinkapp.composable

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.RectF
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.detectDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.ahmedkhalifa.careerlinkapp.utils.updateCropRect

@Composable
fun CropImageDialog(
    imageUri: Uri,
    onCrop: (Bitmap) -> Unit,
    onCancel: () -> Unit
) {
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }
    var cropRect by remember { mutableStateOf(RectF(0f, 0f, 500f, 500f)) }
    val imageSize = remember { mutableStateOf(Size(0f, 0f)) }

    LaunchedEffect(imageUri) {
        bitmap.value = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            val source = ImageDecoder.createSource(context.contentResolver, imageUri)
            ImageDecoder.decodeBitmap(source)
        } else {
            @Suppress("DEPRECATION")
            MediaStore.Images.Media.getBitmap(context.contentResolver, imageUri)
        }?.copy(Bitmap.Config.ARGB_8888, true)

        bitmap.value?.let {
            val size = minOf(it.width, it.height) * 0.8f
            val startX = (it.width - size) / 2f
            val startY = (it.height - size) / 2f
            cropRect = RectF(startX, startY, startX + size, startY + size)
            imageSize.value = Size(it.width.toFloat(), it.height.toFloat())
        }
    }

    Dialog(onDismissRequest = onCancel) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Black)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                bitmap.value?.let { bmp ->
                    var scale by remember { mutableStateOf(1f) }
                    var offset by remember { mutableStateOf(Offset.Zero) }

                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(bmp.width.toFloat() / bmp.height.toFloat())
                            .background(Color.Black)
                    ) {
                        Canvas(
                            modifier = Modifier
                                .fillMaxSize()
                                .pointerInput(Unit) {
                                    detectDragGestures { change, dragAmount ->
                                        change.consume()

                                        val scaledDrag = dragAmount / scale

                                        cropRect = updateCropRect(cropRect, scaledDrag, imageSize.value)
                                    }
                                }
                        ) {
                            val canvasWidth = size.width
                            val canvasHeight = size.height

                            val scaleX = canvasWidth / imageSize.value.width
                            val scaleY = canvasHeight / imageSize.value.height
                            scale = minOf(scaleX, scaleY)

                            val imageDrawWidth = imageSize.value.width * scale
                            val imageDrawHeight = imageSize.value.height * scale

                            val offsetX = (canvasWidth - imageDrawWidth) / 2f
                            val offsetY = (canvasHeight - imageDrawHeight) / 2f
                            offset = Offset(offsetX, offsetY)

                            drawIntoCanvas { canvas ->
                                canvas.save()
                                canvas.translate(offsetX, offsetY)
                                canvas.scale(scale, scale)
                                canvas.drawImage(bmp.asImageBitmap(), Offset.Zero, Paint())
                                canvas.restore()
                            }

                            drawRect(
                                color = Color.White,
                                topLeft = Offset(cropRect.left, cropRect.top) * scale + offset,
                                size = Size(cropRect.width(), cropRect.height()) * scale,
                                style = Stroke(width = 3f)
                            )
                        }
                    }
                } ?: CircularProgressIndicator()

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Button(onClick = onCancel, modifier = Modifier.weight(1f)) {
                        Text("Cancel")
                    }
                    Button(
                        onClick = {
                            bitmap.value?.let { bmp ->
                                val x = cropRect.left.toInt().coerceIn(0, bmp.width)
                                val y = cropRect.top.toInt().coerceIn(0, bmp.height)
                                val width = cropRect.width().toInt().coerceIn(1, bmp.width - x)
                                val height = cropRect.height().toInt().coerceIn(1, bmp.height - y)

                                val cropped = Bitmap.createBitmap(bmp, x, y, width, height)
                                onCrop(cropped)
                            }
                        },
                        modifier = Modifier.weight(1f)
                    ) {
                        Text("Crop")
                    }
                }
            }
        }
    }
}