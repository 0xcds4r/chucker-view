#ifndef _MAIN_H
#define _MAIN_H

#include <jni.h>
#include <string>
#include <android/log.h>

#define LOG_TAG "chucker"
#define LOG(...) __android_log_print(ANDROID_LOG_INFO, LOG_TAG, __VA_ARGS__);

#define INIT_HANDLE 0x95
#define INIT_SECTOR 0x98

#endif