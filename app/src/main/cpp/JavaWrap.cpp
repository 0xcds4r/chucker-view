#include "main.h"
#include "JavaWrap.h"

JNIEnv* JavaWrap::g_env = nullptr;
jobject JavaWrap::g_obj = NULL;

void JavaWrap::Initialise(JNIEnv* env, jobject obj)
{
    JavaWrap::g_env = env;
    JavaWrap::g_obj = obj;
}