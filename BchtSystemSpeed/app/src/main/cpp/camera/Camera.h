#ifndef _Included_com_bcht_rader_Rader
#define _Included_com_bcht_rader_Rader

#include <jni.h>
#include <string>

#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_huang_bchtsystem_rader_Rader
 * Method:    openRader
 * Signature: ()Z
 */
JNIEXPORT jint JNICALL Java_com_huang_bchtsystem_rader_Rader_raderOpen
        (JNIEnv *, jobject , jstring , jint );

/*
 * Class:     com_huang_bchtsystem_rader_Rader
 * Method:    closeRader
 * Signature: ()Z
 */
JNIEXPORT jboolean JNICALL Java_com_huang_bchtsystem_rader_Rader_raderClose
        (JNIEnv *, jobject);

/*
 * Class:     com_huang_bchtsystem_rader_Rader
 * Method:    raderGetParameter
 * Signature: (Lcom/huang/bchtsystem/rader/RaderCmd/ParameterGetCmd;)I
 */
JNIEXPORT jint JNICALL Java_com_huang_bchtsystem_rader_Rader_raderGetParameter
        (JNIEnv *, jobject);

/*
 * Class:     com_huang_bchtsystem_rader_Rader
 * Method:    raderSetParameter
 * Signature: (Lcom/huang/bchtsystem/rader/RaderCmd/ParameterSetCmd;)I
 */
JNIEXPORT jint JNICALL Java_com_huang_bchtsystem_rader_Rader_raderSetParameter
        (JNIEnv *, jobject, jshort, jbyte , jbyte);

/*
 * Class:     com_huang_bchtsystem_rader_Rader
 * Method:    raderSetParameter2
 * Signature: (Lcom/huang/bchtsystem/rader/RaderCmd/ParameterSetCmd;)I
 */
JNIEXPORT jint JNICALL Java_com_huang_bchtsystem_rader_Rader_raderSetParameter2
        (JNIEnv *, jobject, jobject);

/*
 * Class:     com_huang_bchtsystem_rader_Rader
 * Method:    raderGetResponse
 * Signature: (Lcom/huang/bchtsystem/rader/RaderCmd/SpeedResponse;)I
 */
JNIEXPORT jint JNICALL Java_com_huang_bchtsystem_rader_Rader_raderGetSpeedResponse
        (JNIEnv *, jobject);

/*
 * Class:     com_huang_bchtsystem_rader_Rader
 * Method:    raderEnterMeasure
 * Signature: (Lcom/huang/bchtsystem/rader/RaderCmd/ParameterGetCmd;)I
 */
JNIEXPORT jint JNICALL Java_com_huang_bchtsystem_rader_Rader_raderEnterMeasure
        (JNIEnv *, jobject);
/*
 * Class:     com_huang_bchtsystem_rader_Rader
 * Method:    raderExitMeasure
 * Signature: (Lcom/huang/bchtsystem/rader/RaderCmd/ParameterGetCmd;)I
 */
JNIEXPORT jint JNICALL Java_com_huang_bchtsystem_rader_Rader_raderExitMeasure
        (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif // _Included_com_bcht_rader_Rader

