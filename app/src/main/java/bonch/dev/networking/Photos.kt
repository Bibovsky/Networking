package bonch.dev.networking

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.RealmClass

@RealmClass
open class Photos (@PrimaryKey
                   var id: Long = 0,
                   var albumId: Int? = 1,
                   var url: String? = null):RealmObject()