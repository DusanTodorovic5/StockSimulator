#include "sql_parser_Akcije.h"
#include "sqlBaza.hpp"
JNIEXPORT void JNICALL Java_sql_1parser_Akcije_azuriraj(JNIEnv* j_env, jobject jobj, jint j_id) {

	SQL::init();

	Akcija a = SQL::akcije(j_id);

	jclass j_ak = j_env->GetObjectClass(jobj);
	jmethodID methodId = j_env->GetMethodID(j_ak, "dodaj", "(Lsql_parser/Vrednosti;)V");

	for (Vrednosti v : a) {

		jclass j_akc = j_env->FindClass("sql_parser/Vrednosti");

		jobject obj_akc = j_env->AllocObject(j_akc);

		jfieldID j_id = j_env->GetFieldID(j_akc, "idAkcije", "I");
		jfieldID j_sim = j_env->GetFieldID(j_akc, "simbol", "Ljava/lang/String;");
		jfieldID j_kol = j_env->GetFieldID(j_akc, "kolicina", "Ljava/lang/String;");
		jfieldID j_cena = j_env->GetFieldID(j_akc, "cena", "Ljava/lang/String;");

		j_env->SetIntField(obj_akc, j_id, v.idAkc);
		j_env->SetObjectField(obj_akc, j_sim, j_env->NewStringUTF(v.simbol.c_str()));
		j_env->SetObjectField(obj_akc, j_kol, j_env->NewStringUTF(v.kolicina.c_str()));
		j_env->SetObjectField(obj_akc, j_cena, j_env->NewStringUTF(v.cena.c_str()));
		j_env->CallObjectMethod(jobj, methodId, obj_akc);
	}

	SQL::finish();
}