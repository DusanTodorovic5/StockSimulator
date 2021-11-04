#include "parser_Podaci.h"
#include "parser.hpp"
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
JNIEXPORT void JNICALL Java_parser_Podaci_parse
(JNIEnv* j_env, jobject obj_Podaci, jstring j_simbol, jstring j_od, jstring j_do, jint jTip) {
    std::string simbol = jstring2string(j_env, j_simbol);
    std::string _do = jstring2string(j_env, j_do);
    std::string _od = jstring2string(j_env, j_od);
    int tip = (int)jTip;
	Parser parser;
	Podaci p = parser(simbol, _do, _od,tip);

    jclass j_Podaci = j_env->GetObjectClass(obj_Podaci);

    jfieldID j_valuta = j_env->GetFieldID(j_Podaci, "valuta", "Ljava/lang/String;");
    jfieldID j_simbol_field = j_env->GetFieldID(j_Podaci, "simbol", "Ljava/lang/String;");
    jfieldID j_exchangeName = j_env->GetFieldID(j_Podaci, "exchangeName", "Ljava/lang/String;");
    jfieldID j_vremenskaZona = j_env->GetFieldID(j_Podaci, "vremenskaZona", "Ljava/lang/String;");
    j_env->SetObjectField(obj_Podaci, j_valuta, (j_env)->NewStringUTF(p.vrati_valutu().c_str()));
    j_env->SetObjectField(obj_Podaci, j_simbol_field, (j_env)->NewStringUTF(p.vrati_simbol().c_str()));
    j_env->SetObjectField(obj_Podaci, j_exchangeName, (j_env)->NewStringUTF(p.vrati_exchangeName().c_str()));
    j_env->SetObjectField(obj_Podaci, j_vremenskaZona, (j_env)->NewStringUTF(p.vrati_vremenskaZona().c_str()));
    jmethodID methodId = j_env->GetMethodID(j_Podaci, "dodaj", "(Lparser/Sveca;)V");
    
    for (Sveca s : p.vrati_svece()) {
        jclass j_Sveca = j_env->FindClass("parser/Sveca");
        jobject obj_Sveca = j_env->AllocObject(j_Sveca);

        jfieldID j_tmstp = j_env->GetFieldID(j_Sveca, "timestamp", "D");
        jfieldID j_open = j_env->GetFieldID(j_Sveca, "open", "D");
        jfieldID j_close = j_env->GetFieldID(j_Sveca, "close", "D");
        jfieldID j_high = j_env->GetFieldID(j_Sveca, "high", "D");
        jfieldID j_low = j_env->GetFieldID(j_Sveca, "low", "D");

        j_env->SetDoubleField(obj_Sveca, j_tmstp, s.timestamp);
        j_env->SetDoubleField(obj_Sveca, j_open, s.open);
        j_env->SetDoubleField(obj_Sveca, j_close, s.close);
        j_env->SetDoubleField(obj_Sveca, j_high, s.high);
        j_env->SetDoubleField(obj_Sveca, j_low, s.low);
        j_env->CallObjectMethod(obj_Podaci, methodId,obj_Sveca);
    }
}