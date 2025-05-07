#include <jni.h>
#include <string>

std::string xorEncryptDecrypt(const char *string, const char *string1);

std::string decryptApiKey();

std::string encryptApiKey(const char *string, char key);

extern "C" JNIEXPORT jstring JNICALL
Java_com_el_mybasekotlin_ui_fragment_SplashScreenFragment_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
extern "C" JNIEXPORT jstring JNICALL
Java_com_el_mybasekotlin_ui_fragment_SplashScreenFragment_stringPrivate(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++2222222";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_demophotosearchapp_MainActivity_decryptDecrypt(JNIEnv *env, jobject thiz) {

    char key = 0x5A;
    std::string apiKey = decryptApiKey();
    return env->NewStringUTF(apiKey.c_str());
}

//extern "C" JNIEXPORT jstring JNICALL
//Java_com_example_demophotosearchapp_MainActivity_encryptDecrypt(
//        JNIEnv* env,
//        jobject) {
//    char key = 0x5A;
//    std::string encryptedKey = encryptApiKey("nsdhyvLOMSouramkx3nsdhyvouramkHX987X@123", key);
//    return env->NewStringUTF(encryptedKey.c_str());
//}

std::string encryptApiKey(const std::string& apiKey, char key) {

    std::string encrypted = apiKey;
    for (char& c : encrypted) {
        c ^= key; // XOR each character with the key
    }
    return encrypted;
}

std::string decryptApiKey() {
    std::string encrypted = "U29tZUVuY3J5cHRlZEtleQ=="; // Base64 or XOR encrypted key
    std::string decrypted = "";
    char key = 0x5A;
    for (char c : encrypted) {
        decrypted += c ^ key; // XOR Decryption with a simple key (example)
    }

    return decrypted;
}


