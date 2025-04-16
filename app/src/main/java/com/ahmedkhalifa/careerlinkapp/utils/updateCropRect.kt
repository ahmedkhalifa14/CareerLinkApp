package com.ahmedkhalifa.careerlinkapp.utils

import android.graphics.RectF
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size

 fun updateCropRect(rect: RectF, dragAmount: Offset, imageSize: Size): RectF {
    val width = rect.width()
    val height = rect.height()

    // التأكد من أن منطقة القص لا تخرج من حدود الصورة في الاتجاهين الأفقي والعمودي
    val newLeft = (rect.left + dragAmount.x).coerceIn(0f, imageSize.width - width)
    val newTop = (rect.top + dragAmount.y).coerceIn(0f, imageSize.height - height)

    // حساب الحدود الجديدة للمنطقة
    val newRight = newLeft + width
    val newBottom = newTop + height

    // التأكد أن المنطقة لا تتجاوز حدود الصورة
    val coerceRight = newRight.coerceAtMost(imageSize.width)
    val coerceBottom = newBottom.coerceAtMost(imageSize.height)

    // إعادة إنشاء مستطيل القص بناءً على القيم المعدلة
    return RectF(newLeft, newTop, coerceRight, coerceBottom)
}
