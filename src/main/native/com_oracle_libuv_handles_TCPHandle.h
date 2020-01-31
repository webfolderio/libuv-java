/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_oracle_libuv_handles_TCPHandle */

#ifndef _Included_com_oracle_libuv_handles_TCPHandle
#define _Included_com_oracle_libuv_handles_TCPHandle
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_oracle_libuv_handles_TCPHandle
 * Method:    _new
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_oracle_libuv_handles_TCPHandle__1new__J
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_oracle_libuv_handles_TCPHandle
 * Method:    _new
 * Signature: (JJ)J
 */
JNIEXPORT jlong JNICALL Java_com_oracle_libuv_handles_TCPHandle__1new__JJ
  (JNIEnv *, jclass, jlong, jlong);

/*
 * Class:     com_oracle_libuv_handles_TCPHandle
 * Method:    _bind
 * Signature: (JLjava/lang/String;IZ)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_TCPHandle__1bind
  (JNIEnv *, jobject, jlong, jstring, jint, jboolean);

/*
 * Class:     com_oracle_libuv_handles_TCPHandle
 * Method:    _connect
 * Signature: (JLjava/lang/String;ILjava/lang/Object;Z)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_TCPHandle__1connect
  (JNIEnv *, jobject, jlong, jstring, jint, jobject, jboolean);

/*
 * Class:     com_oracle_libuv_handles_TCPHandle
 * Method:    _open
 * Signature: (JJ)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_TCPHandle__1open
  (JNIEnv *, jobject, jlong, jlong);

/*
 * Class:     com_oracle_libuv_handles_TCPHandle
 * Method:    _socket_name
 * Signature: (J)Lcom/oracle/libuv/Address;
 */
JNIEXPORT jobject JNICALL Java_com_oracle_libuv_handles_TCPHandle__1socket_1name
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_oracle_libuv_handles_TCPHandle
 * Method:    _peer_name
 * Signature: (J)Lcom/oracle/libuv/Address;
 */
JNIEXPORT jobject JNICALL Java_com_oracle_libuv_handles_TCPHandle__1peer_1name
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_oracle_libuv_handles_TCPHandle
 * Method:    _no_delay
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_TCPHandle__1no_1delay
  (JNIEnv *, jobject, jlong, jint);

/*
 * Class:     com_oracle_libuv_handles_TCPHandle
 * Method:    _keep_alive
 * Signature: (JII)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_TCPHandle__1keep_1alive
  (JNIEnv *, jobject, jlong, jint, jint);

/*
 * Class:     com_oracle_libuv_handles_TCPHandle
 * Method:    _simultaneous_accepts
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_TCPHandle__1simultaneous_1accepts
  (JNIEnv *, jobject, jlong, jint);

#ifdef __cplusplus
}
#endif
#endif
