#include "sql_parser_Transakcije.h"
#include "sqlBaza.hpp"
JNIEXPORT void JNICALL Java_sql_1parser_Transakcije_azuriraj(JNIEnv* j_env, jobject jobj, jint j_id) {

	SQL::init();

	Transakcije t = SQL::transakcije(j_id);
	
	jclass j_trans = j_env->GetObjectClass(jobj);
	jmethodID methodId = j_env->GetMethodID(j_trans, "dodaj", "(Lsql_parser/Transakcija;)V");
	for (TransStruktura ts : t) {

        jclass j_transakcija = j_env->FindClass("sql_parser/Transakcija");

        jobject obj_transakcija = j_env->AllocObject(j_transakcija);

		jfieldID j_id = j_env->GetFieldID(j_transakcija, "id", "Ljava/lang/String;");
		jfieldID j_simbol = j_env->GetFieldID(j_transakcija, "simbol", "Ljava/lang/String;");
		jfieldID j_kolicina = j_env->GetFieldID(j_transakcija, "kolicina", "Ljava/lang/String;");
		jfieldID j_cena = j_env->GetFieldID(j_transakcija, "cena", "Ljava/lang/String;");
		jfieldID j_operacija = j_env->GetFieldID(j_transakcija, "operacija", "Ljava/lang/String;");

		j_env->SetObjectField(obj_transakcija,j_id, j_env->NewStringUTF(ts.id.c_str()));
		j_env->SetObjectField(obj_transakcija, j_simbol, j_env->NewStringUTF(ts.simbol.c_str()));
		j_env->SetObjectField(obj_transakcija, j_kolicina, j_env->NewStringUTF(ts.kolicina.c_str()));
		j_env->SetObjectField(obj_transakcija, j_cena, j_env->NewStringUTF(ts.cena.c_str()));
		j_env->SetObjectField(obj_transakcija, j_operacija, j_env->NewStringUTF(ts.operacija.c_str()));

        j_env->CallObjectMethod(jobj, methodId, obj_transakcija);
	}

	SQL::finish();
}