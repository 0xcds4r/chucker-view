#ifndef _JAVA_WRAP_H
#define _JAVA_WRAP_H

class JavaWrap {
private:
    static JNIEnv* g_env;
    static jobject g_obj;
public:
    static void Initialise(JNIEnv* env, jobject obj);
};

#endif