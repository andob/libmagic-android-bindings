## Android binding library on [file(1) command and libmagic(3) library](https://github.com/file/file)

``libmagic`` is the best way to determine the mime type of a file! This binding library enables you to determine mime type of files on Android.

### Usage

Import it with:

```
repositories {
    maven { url "https://maven.andob.info/repository/open_source" }
}
```

```
dependencies {
    implementation 'ro.andob.libmagic:libmagic-bindings:1.0.5'
}
```

Use it (Java / Kotlin):

```
File someFile = ...;
String mimetype = LibMagic.getFileMimeType(someFile);
```

In Kotlin you can also use this extension method:

```
val someFile : File = ...
val mimeType : String = someFile.getMimeType()
```

### Rationale

1. Android SDK's [MimeTypeMap.getFileExtensionFromUrl](https://developer.android.com/reference/android/webkit/MimeTypeMap#getFileExtensionFromUrl(java.lang.String)) is flawed and incomplete. Will detect few mime types based on file's extension, NOT its contents!
2. There is also [Apache Tika Core](https://tika.apache.org/) which is great. Will detect lots of mime types based on file contents. However, it's a bit hacky to make it work on Android<8 because it uses [MethodHandle API](https://developer.android.com/reference/java/lang/invoke/MethodHandle). Thanks Google for not keeping up with modern Java standard library.
3. There is also [AndroidMagic](https://github.com/huzongyao/AndroidMagic) library, another Android binding library to libmagic. However, I don't like it because it does not provide auto-update of libmagic code and database. On AndroidMagic, libmagic code and database were copy pasted right into the repository, thus the maintainer have to copy and paste when new version appears. This library, libmagic-android-bindings, uses a different approach: it clones file / libmagic repository as a git submodule and it does have an automatic update script. On each gradle build, libmagic source code is checked out again into the project and mime type database gets compiled and loaded into assets. Thus, this library is self-updating with the upstream libmagic code and database, you just need to hit build / publish a new version.
4. While using a libmagic binding library will yield to a larger APK, because it contains native code .so files and the mime type database - by using file/libmagic you will always get the right mime type! file is the standard reimplementation used on Linux systems of the Unix file command. To say that "it is widely used" would be an underestimate.

### License

```
Copyright 2022 Andrei Dobrescu

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
