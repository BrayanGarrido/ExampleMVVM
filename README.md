# Room con MVVM
Aplicación desarrollada con Room y Model View ViewModel.

Almacena correo y nombre de usuario\n

Nombre de la base de datos "subscriber_data_database"\n
Nombre de la tabla "subscriber_data_table"\n
______________________________________________________\n
| subscriber_id | subscriber_name | subscriber_email |\n
|____________________________________________________|\n

```groovy
	dependences {
		...
		def lifecycle_version = "2.4.0"

    // ViewModel
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:$lifecycle_version")
    // LiveData
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:$lifecycle_version")
    // Annotation processor
    kapt("androidx.lifecycle:lifecycle-compiler:$lifecycle_version")

    def roomVersion = "2.4.1"
    implementation("androidx.room:room-runtime:$roomVersion")
    // To use Kotlin annotation processing tool (kapt)
    kapt("androidx.room:room-compiler:$roomVersion")
    // optional - Kotlin Extensions and Coroutines support for Room
    implementation("androidx.room:room-ktx:$roomVersion")

    //Coroutines
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.0"
    implementation "org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.0"
	}
```


<img src="img/example.jfif" width="40%"/>
