[![](https://img.shields.io/maven-central/v/com.ouattararomuald/syndication-kotlin-coroutines-adapter.svg)](https://search.maven.org/search?q=g:com.ouattararomuald%20a:syndication-kotlin-coroutines-adapter)
[![](https://img.shields.io/nexus/s/https/oss.sonatype.org/com.ouattararomuald/syndication-kotlin-coroutines-adapter.svg)](https://oss.sonatype.org/content/repositories/snapshots/)

# Kotlin Coroutines Adapter

A [CallAdapter.Factory] for use with kotlin coroutine's `Deferred`.

## Usage

Pass `CoroutineCallAdapterFactory` as parameter to `Syndication`.

```kotlin
val syndicationReader = Syndication(
  url = "https://www.lequipe.fr/rss/actu_rss.xml",
  callFactory = CoroutineCallAdapterFactory()
)
```

create and interface like the one below

```kotlin
interface RssReader {

  fun readRss(): Deferred<RssFeed>

  fun readAtom(): Deferred<AtomFeed>
}
```

then let `Syndication` class generates an implementation of that interface `RssReader`:

```kotlin
val reader = syndicationReader.create(RssReader::class.java)
val rssFeed = runBlocking { reader.readRss() }
val atomFeed = runBlocking { reader.readAtom() }
```

## Download

Download the [latest JAR](https://search.maven.org/search?q=g:com.ouattararomuald%20AND%20a:syndication-kotlin-coroutines-adapter) or grab via Gradle:

```gradle
implementation 'com.ouattararomuald:syndication-kotlin-coroutines-adapter:0.1.0-rc.1'
```

or Maven:

```xml
<dependency>
  <groupId>com.ouattararomuald</groupId>
  <artifactId>syndication-kotlin-coroutines-adapter</artifactId>
  <version>0.1.0-rc.1</version>
</dependency>
```

Snapshots of the development version are available in [Sonatype's snapshots repository](https://oss.sonatype.org/content/repositories/snapshots/).
