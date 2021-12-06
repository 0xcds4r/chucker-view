#include "main.h"
#include "JavaWrap.h"

#define INIT_HANDLE 0x95
#define INIT_SECTOR 0x98

void InitAppHandle(JNIEnv* env, jobject obj, jint handle)
{
    switch((int)handle)
    {
        default: {
            LOG("Error: invalid app handle!");
            std::terminate();
        }

        case INIT_HANDLE: {
            LOG("Initializing handle..");
            JavaWrap::Initialise(env, obj);
            break;
        }

        case INIT_SECTOR: {
            break;
        }
    }
}

extern "C"
JNIEXPORT void JNICALL
Java_net_kentandcds4r_chuckerview_MainActivity_InitApp(JNIEnv *env, jobject obj, jint handle) {
    InitAppHandle(env, obj, handle);
}