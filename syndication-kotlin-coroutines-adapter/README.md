# Kotlin Coroutines Adapter

A [CallAdapter.Factory] for use with kotlin coroutine's `Deferred`.

## Usage

Pass `CoroutineCallAdapterFactory` as parameter of `Syndication`.

```kotlin
val syndicationReader = Syndication(
  url = "https://www.lequipe.fr/rss/actu_rss.xml",
  callFactory = CoroutineCallAdapterFactory()
)
```

## Download

