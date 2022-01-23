package com.example.qrscanner.domain.utils

import com.example.qrscanner.data.storage.ElementEntity
import com.example.qrscanner.domain.ElementQR

fun ElementEntity.toElementQR(): ElementQR {
    return ElementQR(
        uid = uid,
        bitmap = bitmap,
        date = date,
        time = time
    )
}

fun ElementQR.toElementEntity(): ElementEntity {
    return ElementEntity(
        uid = uid,
        bitmap = bitmap,
        date = date,
        time = time
    )
}
