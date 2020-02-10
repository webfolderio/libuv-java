/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_oracle_libuv_handles_UDPHandle */

#ifndef _Included_com_oracle_libuv_handles_UDPHandle
#define _Included_com_oracle_libuv_handles_UDPHandle
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _new
 * Signature: (J)J
 */
JNIEXPORT jlong JNICALL Java_com_oracle_libuv_handles_UDPHandle__1new__J
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _static_initialize
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_oracle_libuv_handles_UDPHandle__1static_1initialize
  (JNIEnv *, jclass);

/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _initialize
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_oracle_libuv_handles_UDPHandle__1initialize
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _address
 * Signature: (J)Lcom/oracle/libuv/Address;
 */
JNIEXPORT jobject JNICALL Java_com_oracle_libuv_handles_UDPHandle__1address
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _bind
 * Signature: (JILjava/lang/String;Z)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_UDPHandle__1bind
  (JNIEnv *, jobject, jlong, jint, jstring, jboolean);

/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _send
 * Signature: (JLjava/nio/ByteBuffer;[BIIILjava/lang/String;Ljava/lang/Object;Z)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_UDPHandle__1send
  (JNIEnv *, jobject, jlong, jobject, jbyteArray, jint, jint, jint, jstring, jobject, jboolean);

/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _recv_start
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_UDPHandle__1recv_1start
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _recv_stop
 * Signature: (J)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_UDPHandle__1recv_1stop
  (JNIEnv *, jobject, jlong);

/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _set_ttl
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_UDPHandle__1set_1ttl
  (JNIEnv *, jobject, jlong, jint);

/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _set_membership
 * Signature: (JLjava/lang/String;Ljava/lang/String;I)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_UDPHandle__1set_1membership
  (JNIEnv *, jobject, jlong, jstring, jstring, jint);

/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _set_multicast_loop
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_UDPHandle__1set_1multicast_1loop
  (JNIEnv *, jobject, jlong, jint);

/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _set_multicast_ttl
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_UDPHandle__1set_1multicast_1ttl
  (JNIEnv *, jobject, jlong, jint);

/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _set_broadcast
 * Signature: (JI)I
 */
JNIEXPORT jint JNICALL Java_com_oracle_libuv_handles_UDPHandle__1set_1broadcast
  (JNIEnv *, jobject, jlong, jint);

/*
 * Class:     com_oracle_libuv_handles_UDPHandle
 * Method:    _close
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_oracle_libuv_handles_UDPHandle__1close
  (JNIEnv *, jobject, jlong);

#ifdef __cplusplus
}
#endif
#endif
