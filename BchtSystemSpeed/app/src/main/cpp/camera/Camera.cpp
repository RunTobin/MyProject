//
// Created by admin on 2017/6/7.
//

#include <termios.h>
#include <unistd.h>
#include <sys/types.h>
#include <sys/stat.h>
#include <fcntl.h>
#include <string.h>
#include <jni.h>
#include <errno.h>
#include<grp.h>
#include<stdio.h>
#include <vector>
#include <string>
#include <dirent.h>
#include <sys/select.h>
#include <sys/socket.h>

#include <pthread.h>
#include <exception>


#include "Camera.h"
#include "RaderCmd.h"

#include "../utils/Utils.h"
#include "../utils/Log.h"

#define FLOAT2UCHAR(f) \
    (unsigned char)(f*10)

#define UCHAR2FLOAT(f) \
    (float)(f)/10

jint Java_com_huang_bchtsystem_rader_Rader_raderOpen(JNIEnv *env, jobject radarObj, jstring path, jint baudrate)
{
    jobject fileDescriptor = NULL;

        int fid = 0;
        int speed = Utils::getBaudrate(baudrate);
        if(speed < 0)
        {
            return ERROR_SPEED;
        }

        jboolean isCopy = 0;
        const char *devpath = env->GetStringUTFChars(path, &isCopy);
    if( !devpath)
        {
            return ERROR_NULL;
        }

        int err = 0;
        fid = open(devpath, O_RDWR|O_NOCTTY|O_NDELAY);
        if (fid > 0)
        {
            bool isConfigured = true;
            struct termios cfg;
            memset(&cfg,0,sizeof(cfg));
            do {
                if (-1 == tcgetattr(fid, &cfg))
                {
                    close(fid);
                    isConfigured = false;
                    break;
                }

                cfmakeraw(&cfg);
                if( -1 == cfsetispeed(&cfg, speed))
                {
                    close(fid);
                    isConfigured = false;
                    break;
                }
                if( -1 == cfsetospeed(&cfg, speed))
                {
                    close(fid);
                    isConfigured = false;
                    break;
                }

                cfg.c_cc[VMIN] = 2;
                cfg.c_cc[VTIME] = 0;

                if( -1 == tcflush(fid,TCIOFLUSH ))
                {
                    close(fid);
                    int error = errno;
                    isConfigured = false;
                    break;
                }
                if( -1 == tcdrain(fid))
                {
                    close(fid);
                    isConfigured = false;
                    break;
                }

                if (-1 == tcsetattr(fid, TCSAFLUSH, &cfg))
                {
                    close(fid);
                    isConfigured = false;
                    break;
                }
            } while (0);

            if (!isConfigured)
                return ERROR_CONFIG;

            if( isCopy )
                env->ReleaseStringUTFChars(path, devpath);

            jclass fileDescriptorClass = env->FindClass("java/io/FileDescriptor");
            jmethodID method = env->GetMethodID(fileDescriptorClass, "<init>", "()V");
            jfieldID field = env->GetFieldID(fileDescriptorClass, "descriptor", "I");
            fileDescriptor = env->NewObject(fileDescriptorClass, method);
            env->SetIntField(fileDescriptor, field, fid);

            jclass radarClass = env->FindClass("com/huang/bchtsystem/rader/Rader");
            jfieldID fieldFd = env->GetFieldID(radarClass, "fd", "Ljava/io/FileDescriptor;");
            env->SetObjectField(radarObj, fieldFd, fileDescriptor);

            Radar::StatusSearchCmd statusSearchCmd;
            Radar::StatusSearchResponse statusSearchResponse;
            err = Radar::Cmd::sendCmd(fid, statusSearchCmd, statusSearchResponse);
            if( 0 == err )
                err = Radar::Cmd::getError(statusSearchCmd, statusSearchResponse);
        }
        else
        {
            err = ERROR_NOT_OPENED;
        }
    return err;
}


jboolean Java_com_huang_bchtsystem_rader_Rader_raderClose(JNIEnv *env, jobject obj)
{
    jclass raderClass = env->GetObjectClass( obj );
    jclass FileDescriptorClass = env->FindClass( "java/io/FileDescriptor");

    jfieldID mFdID = env->GetFieldID( raderClass, "fd", "Ljava/io/FileDescriptor;");
    jfieldID descriptorID = env->GetFieldID( FileDescriptorClass, "descriptor", "I");

    jobject mFd = env->GetObjectField( obj, mFdID );
    jint descriptor = env->GetIntField( mFd, descriptorID );

    if( descriptor > 0)
        close(descriptor);

    return true;
}


jint Java_com_huang_bchtsystem_rader_Rader_raderGetParameter(JNIEnv *env, jobject objRader)
{
    jclass raderClass = env->GetObjectClass(objRader);
    jfieldID fdId = env->GetFieldID(raderClass, "fd", "Ljava/io/FileDescriptor;");
    jobject  objFd = env->GetObjectField(objRader, fdId);

    jclass fdClass = env->GetObjectClass(objFd);
    jfieldID descriptorId = env->GetFieldID(fdClass, "descriptor","I");
    jint fd = env->GetIntField(objFd, descriptorId);

    Radar::StatusSearchCmd statusSearchCmd;
    Radar::StatusSearchResponse statusSearchResponse;
    int err = Radar::Cmd::sendCmd(fd, statusSearchCmd, statusSearchResponse);
    if( 0 == err )
        err = Radar::Cmd::getError(statusSearchCmd, statusSearchResponse);

    bool statusChanged = false;
    if( 0 == err )
    {
        if(Radar::StatusSearchResponse::STATUS_PARAMETER_SET != statusSearchResponse.getStatus())
        {
            Radar::StatusSetCmd statusSetCmd;
            Radar::StatusSetResponse statusSetResponse;

            statusSetCmd.change2SetMode();

            err = Radar::Cmd::sendCmd(fd, statusSetCmd, statusSetResponse);
            if( 0 == err )
                err = Radar::Cmd::getError(statusSetCmd, statusSetResponse);
            if( 0 == err )
                statusChanged = true;
        }
    }

    Radar::ParameterGetCmd parameterGetCmd;
    Radar::ParameterGetResponse parameterGetResponse;

    err = Radar::Cmd::sendCmd(fd, parameterGetCmd, parameterGetResponse);
    if( 0 == err )
        err = Radar::Cmd::getError(parameterGetCmd, parameterGetResponse);

    if( 0 == err )
    {
        jclass responseClass = env->FindClass("com/huang/bchtsystem/rader/RaderCmd$ParameterGetResponse");
        jfieldID responseId = env->GetFieldID(raderClass, "parameterGetResponse", "Lcom/huang/bchtsystem/rader/RaderCmd$ParameterGetResponse;");
        jobject responseObj = env->GetObjectField(objRader, responseId);
        if( !responseObj )
        {
            jmethodID methodId = env->GetMethodID(responseClass,"<init>","(Lcom/huang/bchtsystem/rader/RaderCmd;)V");
            responseObj = env->NewObject(responseClass, methodId,objRader);
            env->SetObjectField(objRader, responseId, responseObj);
        }

        jclass paramClass = env->FindClass("com/huang/bchtsystem/rader/RaderCmd$Parameter");
        jfieldID paramId = env->GetFieldID(responseClass, "parameter", "Lcom/huang/bchtsystem/rader/RaderCmd$Parameter;");
        jobject paramObj = env->GetObjectField(responseObj, paramId);
        if(!paramObj)
        {
            jmethodID methodId = env->GetMethodID(paramClass,"<init>","(Lcom/huang/bchtsystem/rader/RaderCmd;)V");
            paramObj = env->NewObject(paramClass, methodId,responseObj);
            env->SetObjectField(responseObj, paramId, paramObj);
        }

#define GET_FIELD(cls,name ) \
    jfieldID name##Id = env->GetFieldID(cls,#name, "F")

        GET_FIELD(paramClass,raderHeight);
        GET_FIELD(paramClass,maxSpeed);
        GET_FIELD(paramClass,threshold);
        GET_FIELD(paramClass,hardThreshold);
        GET_FIELD(paramClass,triggerMode);
        GET_FIELD(paramClass,leftSpeed);
        GET_FIELD(paramClass,angle);
        GET_FIELD(paramClass,distance);
        GET_FIELD(paramClass,laneWidth);
        GET_FIELD(paramClass,interval);
        GET_FIELD(paramClass,adjustedLaneCoefficient);
        GET_FIELD(paramClass,roadMode);

        env->SetFloatField(paramObj, raderHeightId, UCHAR2FLOAT(parameterGetResponse.getRadarHeight()));
        env->SetFloatField(paramObj, maxSpeedId, parameterGetResponse.getMaxSpeed());
        env->SetFloatField(paramObj, thresholdId, parameterGetResponse.getThreshold());
        env->SetFloatField(paramObj, hardThresholdId, UCHAR2FLOAT(parameterGetResponse.getHardThreshold()));
        env->SetFloatField(paramObj, triggerModeId, parameterGetResponse.getTriggerMode());
        env->SetFloatField(paramObj, leftSpeedId, parameterGetResponse.getLeftSpeed());
        env->SetFloatField(paramObj, angleId, parameterGetResponse.getAngle());
        env->SetFloatField(paramObj, distanceId, UCHAR2FLOAT(parameterGetResponse.getDistance()));
        env->SetFloatField(paramObj, laneWidthId, UCHAR2FLOAT(parameterGetResponse.getLaneWidth()));
        env->SetFloatField(paramObj, intervalId, UCHAR2FLOAT(parameterGetResponse.getInterval()));
        env->SetFloatField(paramObj, adjustedLaneCoefficientId, UCHAR2FLOAT(parameterGetResponse.getAdjustedLaneCoefficient()));
        env->SetFloatField(paramObj, roadModeId, parameterGetResponse.getRoadMode());
    }

    if( statusChanged == true )
    {
        Radar::StatusSetCmd statusSetCmd;
        Radar::StatusSetResponse statusSetResponse;

        statusSetCmd.change2SpeedTestMode();

        err = Radar::Cmd::sendCmd(fd, statusSetCmd, statusSetResponse);
        if( 0 == err )
            err = Radar::Cmd::getError(statusSetCmd, statusSetResponse);
    }
    return err;
}

jint Java_com_huang_bchtsystem_rader_Rader_raderEnterMeasure(JNIEnv *env, jobject objRader)
{
    jclass raderClass = env->GetObjectClass(objRader);
    jfieldID fdId = env->GetFieldID(raderClass, "fd", "Ljava/io/FileDescriptor;");
    jobject  objFd = env->GetObjectField(objRader, fdId);

    jclass fdClass = env->GetObjectClass(objFd);
    jfieldID descriptorId = env->GetFieldID(fdClass, "descriptor","I");
    jint fd = env->GetIntField(objFd, descriptorId);

    Radar::MeasureSetCmd statusSetCmd;
    Radar::MeasureSetResponse statusSetResponse;

    statusSetCmd.change2MeterMode();

    int err = Radar::Cmd::sendCmd(fd, statusSetCmd, statusSetResponse);
    if( 0 == err )
        err = Radar::Cmd::getError(statusSetCmd, statusSetResponse);

    if( 0 == err)
    {
        Radar::TestMeasureSetCmd measureSetCmd;
        Radar::TestMeasureSetResponse measureSetResponse;

        measureSetCmd.change2SimulateMode();

        err = Radar::Cmd::sendCmd(fd, measureSetCmd, measureSetResponse);
        if( 0 == err )
            err = Radar::Cmd::getError(measureSetCmd, measureSetResponse);
    }
    return err;
}
jint Java_com_huang_bchtsystem_rader_Rader_raderExitMeasure(JNIEnv *env, jobject objRader)
{
    jclass raderClass = env->GetObjectClass(objRader);
    jfieldID fdId = env->GetFieldID(raderClass, "fd", "Ljava/io/FileDescriptor;");
    jobject  objFd = env->GetObjectField(objRader, fdId);

    jclass fdClass = env->GetObjectClass(objFd);
    jfieldID descriptorId = env->GetFieldID(fdClass, "descriptor","I");
    jint fd = env->GetIntField(objFd, descriptorId);

    Radar::MeasureSetCmd measureSetCmd;
    Radar::MeasureSetResponse measureSetResponse;

    measureSetCmd.change2ExitMode();

    int err = Radar::Cmd::sendCmd(fd, measureSetCmd, measureSetResponse);
    if( 0 == err )
        err = Radar::Cmd::getError(measureSetCmd, measureSetResponse);

    return err;
}

jint Java_com_huang_bchtsystem_rader_Rader_raderSetParameter2(JNIEnv *env, jobject objRadar, jobject objParams)
{
    if(NULL == objParams)
    {
        return ERROR_NULL;
    }

    jclass paramsSetClass = env->FindClass("com/huang/bchtsystem/rader/RaderCmd$ParameterSetCmd");
    jfieldID paramId = env->GetFieldID(paramsSetClass,"parameter","Lcom/huang/bchtsystem/rader/RaderCmd$Parameter;");
    jobject params = env->GetObjectField(objParams,paramId);
    if( NULL == params  )
    {
        return ERROR_NULL;
    }

    jclass paramsClass = env->FindClass("com/huang/bchtsystem/rader/RaderCmd$Parameter");
    GET_FIELD(paramsClass,raderHeight);
    GET_FIELD(paramsClass,maxSpeed);
    GET_FIELD(paramsClass,threshold);
    GET_FIELD(paramsClass,hardThreshold);
    GET_FIELD(paramsClass,triggerMode);
    GET_FIELD(paramsClass,leftSpeed);
    GET_FIELD(paramsClass,angle);
    GET_FIELD(paramsClass,distance);
    GET_FIELD(paramsClass,laneWidth);
    GET_FIELD(paramsClass,interval);
    GET_FIELD(paramsClass,adjustedLaneCoefficient);
    GET_FIELD(paramsClass,roadMode);

#define GET_FIELD_VAL(cls,name) \
    jfloat name = env->GetFloatField(params, name##Id)

    GET_FIELD_VAL(paramsClass,raderHeight);
    GET_FIELD_VAL(paramsClass,maxSpeed);
    GET_FIELD_VAL(paramsClass,threshold);
    GET_FIELD_VAL(paramsClass,hardThreshold);
    GET_FIELD_VAL(paramsClass,triggerMode);
    GET_FIELD_VAL(paramsClass,leftSpeed);
    GET_FIELD_VAL(paramsClass,angle);
    GET_FIELD_VAL(paramsClass,distance);
    GET_FIELD_VAL(paramsClass,laneWidth);
    GET_FIELD_VAL(paramsClass,interval);
    GET_FIELD_VAL(paramsClass,adjustedLaneCoefficient);
    GET_FIELD_VAL(paramsClass,roadMode);

    Radar::ParameterSetCmd parameterSetCmd;
    Radar::ParameterSetResponse parameterSetResponse;

    if( raderHeight > 0 && raderHeight <= 25.5)
    {
        parameterSetCmd.setRadarHeight(FLOAT2UCHAR(raderHeight));
    }
    else
    {
        return ERROR_PARAM_OUT_OF_BOUND;
    }
    parameterSetCmd.setMaxSpeed(maxSpeed);
    parameterSetCmd.setThreshold(threshold);
    if( hardThreshold > 0 && hardThreshold <= 25.5)
    {
        parameterSetCmd.setHardThreshold(FLOAT2UCHAR(hardThreshold));
    }
    else
    {
        return ERROR_PARAM_OUT_OF_BOUND;
    }

    unsigned char triggermode = (unsigned char)triggerMode;
    if( triggermode >=Radar::Parameter::TRIGGER_HEADER && triggermode <= Radar::Parameter::TRIGGER_CONTINUE )
    {
        parameterSetCmd.setTriggerMode(triggerMode);
    }
    else
    {
        return ERROR_PARAM_OUT_OF_BOUND;
    }
    parameterSetCmd.setLeftSpeed(leftSpeed);
    parameterSetCmd.setAngle(angle);
    if( distance > 0 && distance <= 25.5)
    {
        parameterSetCmd.setDistance(FLOAT2UCHAR(distance));
    }
    else
    {
        return ERROR_PARAM_OUT_OF_BOUND;
    }

    if( laneWidth > 0 && laneWidth <= 25.5)
    {
        parameterSetCmd.setLaneWidth(FLOAT2UCHAR(laneWidth));
    }
    else
    {
        return ERROR_PARAM_OUT_OF_BOUND;
    }

    if( interval > 0 && interval <= 25.5)
    {
        parameterSetCmd.setInterval(FLOAT2UCHAR(interval));
    }
    else
    {
        return ERROR_PARAM_OUT_OF_BOUND;
    }

    if( adjustedLaneCoefficient > 0 && adjustedLaneCoefficient <= 25.5)
    {
        parameterSetCmd.setAdjustedLaneCoefficient(FLOAT2UCHAR(adjustedLaneCoefficient));
    }
    else
    {
        return ERROR_PARAM_OUT_OF_BOUND;
    }

    unsigned char roadmode = (unsigned char)roadMode;
    if( roadmode >=Radar::Parameter::TRIGGER_HEADER && roadmode <= Radar::Parameter::TRIGGER_CONTINUE )
    {
         parameterSetCmd.setRoadMode(roadMode);
    }
    else
    {
        return ERROR_PARAM_OUT_OF_BOUND;
    }
    parameterSetCmd.getChecksum();

    ////////////////////////////////////////////////////////////////////////////////////////////
    jclass radarClass = env->GetObjectClass(objRadar);
        jfieldID fdId = env->GetFieldID(radarClass, "fd", "Ljava/io/FileDescriptor;");
        jobject  objFd = env->GetObjectField(objRadar, fdId);

        jclass fdClass = env->GetObjectClass(objFd);
        jfieldID descriptorId = env->GetFieldID(fdClass, "descriptor","I");
        jint fd = env->GetIntField(objFd, descriptorId);

        Radar::StatusSearchCmd statusSearchCmd;
        Radar::StatusSearchResponse statusSearchResponse;
        int err = Radar::Cmd::sendCmd(fd, statusSearchCmd, statusSearchResponse);
        if( 0 == err )
            err = Radar::Cmd::getError(statusSearchCmd, statusSearchResponse);

        bool statusChanged = false;
        if( 0 == err )
        {
            if(Radar::StatusSearchResponse::STATUS_PARAMETER_SET != statusSearchResponse.getStatus())
            {
                Radar::StatusSetCmd statusSetCmd;
                Radar::StatusSetResponse statusSetResponse;

                statusSetCmd.change2SetMode();

                err = Radar::Cmd::sendCmd(fd, statusSetCmd, statusSetResponse);
                if( 0 == err )
                    err = Radar::Cmd::getError(statusSetCmd, statusSetResponse);
                if( 0 == err )
                    statusChanged = true;
            }
        }


        if( 0 == err )
        {
            err = Radar::Cmd::sendCmd(fd, parameterSetCmd, parameterSetResponse);
            if( 0 == err )
                err = Radar::Cmd::getError(parameterSetCmd, parameterSetResponse);
        }

        if( 0 == err )
        {
            Radar::ParameterSaveCmd parameterSaveCmd;
            Radar::ParameterSaveResponse parameterSaveResponse;

            parameterSaveCmd.getChecksum();

            err = Radar::Cmd::sendCmd(fd, parameterSaveCmd, parameterSaveResponse);
            if( 0 == err )
                err = Radar::Cmd::getError(parameterSaveCmd, parameterSaveResponse);

        }
        if( statusChanged == true )
        {
            Radar::StatusSetCmd statusSetCmd;
            Radar::StatusSetResponse statusSetResponse;

            statusSetCmd.change2SpeedTestMode();

            err = Radar::Cmd::sendCmd(fd, statusSetCmd, statusSetResponse);
            if( 0 == err )
                err = Radar::Cmd::getError(statusSetCmd, statusSetResponse);
        }

        return err;
}

jint Java_com_huang_bchtsystem_rader_Rader_raderSetParameter
        (JNIEnv *env, jobject objRader, jshort maxSpeed, jbyte angle, jbyte workingMode)
{

    if( maxSpeed < 0 || maxSpeed > 255)
    {
        return ERROR_SPEED;
    }
    if( (unsigned char)angle < 0 || (unsigned char)angle > 180 )
    {
        return ERROR_ANGLE;
    }
    if(!Radar::Parameter::isValidTriggerMode(workingMode))
    {
        return ERROR_WORKING_MODE;
    }
    jclass radarClass = env->GetObjectClass(objRader);
    jfieldID fdId = env->GetFieldID(radarClass, "fd", "Ljava/io/FileDescriptor;");
    jobject  objFd = env->GetObjectField(objRader, fdId);

    jclass fdClass = env->GetObjectClass(objFd);
    jfieldID descriptorId = env->GetFieldID(fdClass, "descriptor","I");
    jint fd = env->GetIntField(objFd, descriptorId);

    Radar::StatusSearchCmd statusSearchCmd;
    Radar::StatusSearchResponse statusSearchResponse;
    int err = Radar::Cmd::sendCmd(fd, statusSearchCmd, statusSearchResponse);
    if( 0 == err )
        err = Radar::Cmd::getError(statusSearchCmd, statusSearchResponse);

    bool statusChanged = false;
    if( 0 == err )
    {
        if(Radar::StatusSearchResponse::STATUS_PARAMETER_SET != statusSearchResponse.getStatus())
        {
            Radar::StatusSetCmd statusSetCmd;
            Radar::StatusSetResponse statusSetResponse;

            statusSetCmd.change2SetMode();

            err = Radar::Cmd::sendCmd(fd, statusSetCmd, statusSetResponse);
            if( 0 == err )
                err = Radar::Cmd::getError(statusSetCmd, statusSetResponse);
            if( 0 == err )
                statusChanged = true;
        }
    }

    Radar::ParameterGetCmd parameterGetCmd;
    Radar::ParameterGetResponse parameterGetResponse;
    if( err == 0 )
    {
        err = Radar::Cmd::sendCmd(fd, parameterGetCmd, parameterGetResponse);
        if( 0 == err )
            err = Radar::Cmd::getError(parameterGetCmd, parameterGetResponse);

    }
    if( 0 == err )
    {
        Radar::ParameterSetCmd parameterSetCmd;
        Radar::ParameterSetResponse parameterSetResponse;

        parameterSetCmd.setParameter(&parameterGetResponse);
        parameterSetCmd.setMaxSpeed(maxSpeed);
        parameterSetCmd.setAngle(angle);
        parameterSetCmd.setTriggerMode(workingMode);
        parameterSetCmd.getChecksum();

        err = Radar::Cmd::sendCmd(fd, parameterSetCmd, parameterSetResponse);
        if( 0 == err )
            err = Radar::Cmd::getError(parameterSetCmd, parameterSetResponse);
    }

    if( 0 == err )
    {
        Radar::ParameterSaveCmd parameterSaveCmd;
        Radar::ParameterSaveResponse parameterSaveResponse;

        parameterSaveCmd.getChecksum();

        err = Radar::Cmd::sendCmd(fd, parameterSaveCmd, parameterSaveResponse);
        if( 0 == err )
            err = Radar::Cmd::getError(parameterSaveCmd, parameterSaveResponse);

    }
    if( statusChanged == true )
    {
        Radar::StatusSetCmd statusSetCmd;
        Radar::StatusSetResponse statusSetResponse;

        statusSetCmd.change2SpeedTestMode();

        err = Radar::Cmd::sendCmd(fd, statusSetCmd, statusSetResponse);
        if( 0 == err )
            err = Radar::Cmd::getError(statusSetCmd, statusSetResponse);
    }

    return err;

}

jint Java_com_huang_bchtsystem_rader_Rader_raderGetSpeedResponse(JNIEnv *env, jobject raderObj)
{
    jclass raderClass = env->GetObjectClass( raderObj );
    jclass FileDescriptorClass = env->FindClass( "java/io/FileDescriptor");

    jfieldID mFdID = env->GetFieldID( raderClass, "fd", "Ljava/io/FileDescriptor;");
    jfieldID descriptorID = env->GetFieldID( FileDescriptorClass, "descriptor", "I");

    jobject mFd = env->GetObjectField( raderObj, mFdID );
    jint fd = env->GetIntField( mFd, descriptorID );

    jclass speedResponseClass = env->FindClass("com/huang/bchtsystem/rader/RaderCmd$SpeedResponse");
    jfieldID speedField = env->GetFieldID(speedResponseClass,"speed","S");
    jfieldID laneField = env->GetFieldID(speedResponseClass,"lane","C");
    jfieldID directionField = env->GetFieldID(speedResponseClass,"direction","C");
    jfieldID responseField = env->GetFieldID( raderClass, "speedResponse","Lcom/huang/bchtsystem/rader/RaderCmd$SpeedResponse;");
    jobject responseObj = env->GetObjectField(raderObj, responseField);
    if( !responseObj )
    {
        jmethodID init = env->GetMethodID( speedResponseClass, "<init>","(Lcom/huang/bchtsystem/rader/RaderCmd;)V");
        responseObj = env->NewObject( speedResponseClass, init, raderObj);
        env->SetObjectField(raderObj, responseField, responseObj);
    }

    Radar::SpeedResponse speedResponse;
    int err = Radar::Cmd::recv( fd, speedResponse);
    if( 0 == err )
        err = Radar::Cmd::getError<Radar::SpeedResponse>( Radar::RadarCmd::SPEED, speedResponse);
    if( 0 == err )
    {
        env->SetShortField(responseObj, speedField, speedResponse.getSpeed());
        env->SetCharField(responseObj, laneField, speedResponse.getLane());
        env->SetCharField(responseObj, directionField, speedResponse.getDirection());
    }

    return err;
}
