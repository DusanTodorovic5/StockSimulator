#include "sql_parser_SQLParser.h"
#include "sqlBaza.hpp"
std::string jstring2string(JNIEnv* env, jstring jStr) {
    if (!jStr)
        return "";

    const jclass stringClass = env->GetObjectClass(jStr);
    const jmethodID getBytes = env->GetMethodID(stringClass, "getBytes", "(Ljava/lang/String;)[B");
    const jbyteArray stringJbytes = (jbyteArray)env->CallObjectMethod(jStr, getBytes, env->NewStringUTF("UTF-8"));

    size_t length = (size_t)env->GetArrayLength(stringJbytes);
    jbyte* pBytes = env->GetByteArrayElements(stringJbytes, NULL);

    std::string ret = std::string((char*)pBytes, length);
    env->ReleaseByteArrayElements(stringJbytes, pBytes, JNI_ABORT);

    env->DeleteLocalRef(stringJbytes);
    env->DeleteLocalRef(stringClass);
    return ret;
}
JNIEXPORT jint JNICALL Java_sql_1parser_SQLParser_kupi(JNIEnv* j_env, jobject jobj, jint j_id, jstring j_simbol, jint j_kol, jdouble j_cena) {
    SQL::init();

    int pov = SQL::kupi(j_id, jstring2string(j_env,j_simbol),j_kol, j_cena);

    SQL::finish();

    return pov;
}
JNIEXPORT jint JNICALL Java_sql_1parser_SQLParser_prodaj(JNIEnv* j_env, jobject jobj, jint j_id, jint j_id_akc, jdouble j_cena, jint j_kol) {
    SQL::init();

    int pov = SQL::prodaj(j_id,j_id_akc,j_cena,j_kol);

    SQL::finish();

    return pov;
}
JNIEXPORT jstring JNICALL Java_sql_1parser_SQLParser_simbol(JNIEnv* j_env, jobject, jint j_id) {
    SQL::init();
	int id = (int)j_id;
	std::string s = SQL::simbol(id);
    SQL::finish();
	return j_env->NewStringUTF(s.c_str());
}

JNIEXPORT jint JNICALL Java_sql_1parser_SQLParser_loginPass(JNIEnv* j_env, jclass, jstring j_user, jstring j_pass) {
    SQL::init();
    std::pair<std::string, std::string> par;
    par.first = jstring2string(j_env, j_user);
    par.second = jstring2string(j_env, j_pass);
    std::pair<int,std::string> pov = SQL::loginPass(par);
    SQL::finish();
    return pov.first;
}
JNIEXPORT jstring JNICALL Java_sql_1parser_SQLParser_registracije(JNIEnv* j_env, jclass, jstring j_user, jstring j_pass, jint j_novac) {
    SQL::init();

    std::string user = jstring2string(j_env, j_user);
    std::string pass = jstring2string(j_env, j_pass);

    std::string reg = SQL::registracija(user, pass, j_novac);

    SQL::finish();

    return j_env->NewStringUTF(reg.c_str());
}

JNIEXPORT jdouble JNICALL Java_sql_1parser_SQLParser_novac(JNIEnv* j_env, jobject, jint j_id) {
    SQL::init();
    std::cout << j_id << std::endl;
    double n = SQL::novac(j_id);
    std::cout << n << std::endl;
    SQL::finish();
    return n;
}