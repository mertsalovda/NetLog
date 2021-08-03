# NetLog

Library for displaying logs of network requests.

Step 1. Add the JitPack repository to your build file

Add it in your root build.gradle at the end of repositories:
```groovy
	allprojects {
		repositories {
			maven { url 'https://jitpack.io' }
		}
	}
```
Step 2. Add the dependency
```groovy
	dependencies {
	        implementation 'com.github.mertsalovda:NetLog:1.0.0-beta'
	}
```

Step 3. Create a singleton repository instance. Example:

```kotlin
import android.app.Application
import ru.mertsalovda.netlog.interceptor.INetLogRepository
import ru.mertsalovda.netlog.repository.NetLogRepositoryImpl

class App : Application() {

    companion object {
        lateinit var netLogRepository: INetLogRepository
    }

    override fun onCreate() {
        super.onCreate()
        netLogRepository = NetLogRepositoryImpl()
    }
}
```

Step 4. Create and add an interceptor to your OkHttpClient

```kotlin
   fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                   .addInterceptor(NetLogInterceptor(App.netLogRepository))
                   .build()
    }
```

Step 5. Create an instance of a `NetLogDialogFragment` and pass it a link to the repository with logs. Call the `show` method.

```kotlin
    NetLogDialogFragment.newInstance(App.netLogRepository).show(supportFragmentManager, "NetLog")
```
<p>
  <a target="_blank" rel="noopener noreferrer" href="https://github.com/mertsalovda/NetLog/blob/master/preview.gif">
    <img src="https://github.com/mertsalovda/NetLog/blob/master/preview.gif" alt="" style="max-width:100%;">
  </a>
</p>
