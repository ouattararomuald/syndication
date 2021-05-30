[![](https://img.shields.io/maven-central/v/com.ouattararomuald/syndication-rxjava3-adapter.svg)](https://search.maven.org/search?q=g:com.ouattararomuald%20a:syndication-rxjava3-adapter)
[![](https://img.shields.io/nexus/s/https/oss.sonatype.org/com.ouattararomuald/syndication-rxjava3-adapter.svg)](https://oss.sonatype.org/content/repositories/snapshots/)

# RxJava2 Adapter

An adapter for adapting [RxJava 2.x](https://github.com/ReactiveX/RxJava) types.

Available types:

- `Flowable<T>` with `T` being `AtomFeed` or `RssFeed`.
- `Maybe<T>` with `T` being `AtomFeed` or `RssFeed`.
- `Single<T>` with `T` being `AtomFeed` or `RssFeed`.

## Usage

Pass `RxJava2CallAdapterFactory` as parameter to `Syndication`.

```kotlin
val syndicationReader = Syndication(
  url = "https://www.lequipe.fr/rss/actu_rss.xml",
  callFactory = RxJava2CallAdapterFactory.create()
)
```

create and interface like the one below

```kotlin
interface RssReader {

  fun readFlowable(): Flowable<RssFeed>

  fun readMaybe(): Maybe<RssFeed>
  
  fun readSingle(): Single<RssFeed>
}
```

then let `Syndication` class generates an implementation of that interface `RssReader`:

```kotlin
val reader = syndicationReader.create(RssReader::class.java)
reader.readFlowable().subscribe { rssFeed -> /* do something */ }
reader.readMaybe().subscribe { rssFeed -> /* do something */ }
reader.readSingle().subscribe { rssFeed -> /* do something */ }
```

By default all reactive types execute their requests synchronously. There are multiple ways to control the threading on which a request occurs:

- Call `subscribeOn` on the returned reactive type with a `Scheduler` of your choice.
- Use `createAsync()` when creating the factory which will use OkHttp's internal thread pool.
- Use `createWithScheduler(Scheduler)` to supply a default subscription `Scheduler`.

## Download

Download the [latest JAR](https://search.maven.org/search?q=g:com.ouattararomuald%20AND%20a:syndication-rxjava2-adapter) or grab via Gradle:

```gradle
implementation 'com.ouattararomuald:syndication-rxjava2-adapter:2.1.1'
```

or Maven:

```xml
<dependency>
  <groupId>com.ouattararomuald</groupId>
  <artifactId>syndication-rxjava2-adapter</artifactId>
  <version>2.1.1</version>
</dependency>
```

Snapshots of the development version are available in [Sonatype's snapshots repository](https://oss.sonatype.org/content/repositories/snapshots/).
