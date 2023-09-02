#include <jni.h>
#include <stdio.h>
#include <unistd.h>
#include <stdlib.h>
#include <ctype.h>

#include "../../../../file/src/magic.h"

#define JAVA_LANG_EXCEPTION "java/lang/Exception"

JNIEXPORT jstring JNICALL Java_ro_andob_libmagic_LibMagic_getFileMimeType(JNIEnv* env, jclass clazz, jstring mgc_file_path_java, jstring file_path_java)
{
    if (mgc_file_path_java == NULL)
    {
        const char* error = "Please send non null MGC file path!";
        (*env)->ThrowNew(env, (*env)->FindClass(env, JAVA_LANG_EXCEPTION), error);
        return NULL;
    }

    if (file_path_java == NULL)
    {
        const char* error = "Please send non null file path!";
        (*env)->ThrowNew(env, (*env)->FindClass(env, JAVA_LANG_EXCEPTION), error);
        return NULL;
    }

    const char* mgc_file_path = (*env)->GetStringUTFChars(env, mgc_file_path_java, 0);
    const char* file_path = (*env)->GetStringUTFChars(env, file_path_java, 0);

    magic_t magic_cookie = magic_open(MAGIC_MIME_TYPE);

    #define ReleaseMemory if (magic_cookie == NULL) magic_close(magic_cookie); \
        (*env)->ReleaseStringUTFChars(env, mgc_file_path_java, mgc_file_path); \
        (*env)->ReleaseStringUTFChars(env, file_path_java, file_path);

    if (magic_cookie == NULL)
    {
        ReleaseMemory;
        const char* error = "magic_open failed! Cannot load libmagic!";
        (*env)->ThrowNew(env, (*env)->FindClass(env, JAVA_LANG_EXCEPTION), error);
        return NULL;
    }

    if (magic_load(magic_cookie, mgc_file_path) != 0)
    {
        ReleaseMemory;
        char error[1024];
        sprintf(error, "cannot load libmagic database - %s", magic_error(magic_cookie));
        (*env)->ThrowNew(env, (*env)->FindClass(env, JAVA_LANG_EXCEPTION), error);
        return NULL;
    }

    const char* mime_type = magic_file(magic_cookie, file_path);
    if (mime_type == NULL)
    {
        ReleaseMemory;
        char error[1024];
        sprintf(error, "cannot determine mime type - %s", magic_error(magic_cookie));
        (*env)->ThrowNew(env, (*env)->FindClass(env, JAVA_LANG_EXCEPTION), error);
        return NULL;
    }

    ReleaseMemory;
    #undef ReleaseMemory

    return (*env)->NewStringUTF(env, mime_type);
}

#undef JAVA_LANG_EXCEPTION
