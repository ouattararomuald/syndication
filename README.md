# Syndication feed

A Kotlin library for reading RSS 2.0 and ATOM 1.0 syndication feeds.

## Usage

You're willing to consume **ATOM 1.0** or **RSS 2.0** feed? Start by creating and interface

```kotlin
interface RssReader {

  fun readRss(): RssFeed

  fun readAtom(): AtomFeed
}
```

then let `Syndication` class generates an implementation of that interface `RssReader`:

```kotlin
val reader = syndicationReader.create(RssReader::class.java)
val rssFeed = reader.readRss() // this is synchronous
val atomFeed = reader.readAtom() // this is synchronous
```

each call from the created `RssReader` will make an HTTP request and return the expected syndication feed.

By default only `RssFeed` and `AtomFeed` are accepted as return type.

If you would like to do things asynchronously then take a look to existing adapters in this repo.

**kotlin-coroutines-adapter** for example let you return `Deferred<RssFeed>` or `Deferred<AtomFeed>`.

## Syndication specifications

- [Atom 1.0](https://www.ietf.org/rfc/rfc4287)
- [RSS 2.0](http://www.rssboard.org/rss-specification)

## Download

Snapshots of the development version are available in [Sonatype's snapshots repository](https://oss.sonatype.org/content/repositories/snapshots/).

## Next Steps

- Documentation
- More Tests
- RxJava Adapter

## Contributing

Contributions you say? Yes please!

**Bug report?**

If at all possible, please attach a minimal sample project or code which reproduces the bug.
Screenshots are also a huge help if the problem is visual.

**Send a pull request!**

If you're fixing a bug, please add a failing test or code that can reproduce the issue.

## License

```
Copyright 2018 Ouattara Gninlikpoho Romuald

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```