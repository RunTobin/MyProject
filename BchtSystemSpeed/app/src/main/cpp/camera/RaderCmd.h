//
// Created by admin on 2017/6/7.
//

#ifndef RADER_RADERCMD_H
#define RADER_RADERCMD_H

//#include "../../../../../../android-ndk-r14b/platforms/android-24/arch-x86_64/usr/include/pthread.h"
#include <sys/socket.h>

#define VERSION_LENGTH  12


#define ERROR_BASE                   (0x1000)
#define ERROR_UNKNOWN               (ERROR_BASE + 0x1)
#define ERROR_HEADER1               (ERROR_BASE + 0x2)
#define ERROR_HEADER2               (ERROR_BASE + 0x3)
#define ERROR_DATALENGTH            (ERROR_BASE + 0x4)
#define ERROR_CHECKSUM              (ERROR_BASE + 0x5)
#define ERROR_RESPONSE_STATUS      (ERROR_BASE + 0x6)
#define ERROR_CMD                    (ERROR_BASE + 0x7)
#define ERROR_FAILURE               (ERROR_BASE + 0x8)
#define ERROR_NULL                  (ERROR_BASE + 0x9)
#define ERROR_NOT_OPENED           (ERROR_BASE + 0xA)
#define ERROR_SPEED                 (ERROR_BASE + 0xB)
#define ERROR_CONFIG                (ERROR_BASE + 0xC)
#define ERROR_PARAM_NOT_SAVED     (ERROR_BASE + 0xD)
#define ERROR_WORKING_MODE         (ERROR_BASE + 0xE)
#define ERROR_ANGLE                 (ERROR_BASE + 0x10)
#define ERROR_PARAM_OUT_OF_BOUND     (ERROR_BASE + 0x11)

namespace Radar
{
    class RadarCmd
    {
    private:
        RadarCmd(){}

    public:
        const static unsigned short Null;
        const static unsigned short STATUS_SEARCH;
        const static unsigned short RESET;
        const static unsigned short STATUS_SET;
        const static unsigned short SOFTWARE_VERSION_SEARCH;
        const static unsigned short PRODUCT_NO_SEARCH;
        const static unsigned short PRAMETER_SET;
        const static unsigned short PRAMETER_GET;
        const static unsigned short PRAMETER_SAVE;
        const static unsigned short RESTORE;
        const static unsigned short SPEED;
        const static unsigned short MEASUREMENT_MODE;
        const static unsigned short TEST_MODE;

    public:
        const static unsigned short STATUS_RESPONSE_SUCCESS;
        const static unsigned short STATUS_RESPONSE_FAILURE;
    };

#pragma pack(1)
    class Header
    {
    public:
        Header();

        unsigned char getHerder1() const;

        void setHerder1(unsigned char herder1);

        unsigned char getHerder2() const;

        void setHerder2(unsigned char herder2);

        unsigned char getDataLength() const;

        void setDataLength(unsigned char dataLength);

        unsigned short getCmd() const;

        void setCmd(unsigned short cmd);

    public:
        int isValidHeader1() const;
        int isValidHeader2() const;
        int isValidLength() const;
        int isValidCmd(unsigned short cmd) const;

    protected:
        template <typename  T>
        unsigned char getChecksum(T t) const
        {
            unsigned char* begin = (unsigned char*)(&t);
            unsigned char cs = 0;
            for(int i = 0; i < sizeof(T); i++)
            {
                cs += *(begin + i);
            }
            return cs;
        }


    private:
        unsigned char       header1;
        unsigned char       header2;
        unsigned char       dataLength;
        unsigned short      cmd;
    };


    class StatusSearchCmd : public Header
    {
    public:
        StatusSearchCmd();

    public:
        unsigned char getChecksum() const;

    private:
        unsigned char    reserved;
        unsigned char    checksum;
    };

    class StatusSearchResponse : public Header
    {
    public:
        StatusSearchResponse();

    public:
        const static unsigned short STATUS_PARAMETER_SET;
        const static unsigned short STATUS_SPEED_TEST_SET;

    public:
        unsigned short getStatus() const;
        unsigned char getChecksum() const;

    public:
        int isValidStatus() const;
        int isValidChecksum() const;
        bool isSuccess() const;

    private:
        unsigned short   status;
        unsigned char    checksum;

    };


    class ResetCmd : public Header
    {
    public:
        ResetCmd();

    public:
        unsigned char getChecksum();

    public:
        int isValidChecksum() const;

    private:
        unsigned char    checksum;
    };

    class Response : public Header
    {
    public:
        Response();

    public:
        unsigned short getStatus() const;
        unsigned char getChecksum() const;

    public:
        int isValidStatus() const;
        bool isSuccess() const;
        int isValidChecksum() const;

    public:
        unsigned short   status;
        unsigned char    checksum;
    };

    class ResetResponse : public Response
    {
    };

    class SpeedResponse : public Header
    {
    public:
        SpeedResponse();

    public:
        unsigned short getSpeed() const;

        unsigned char getLane() const;

        unsigned char getDirection() const;

        unsigned char getChecksum() const;

    public:
        bool isValidChecksum() const;

    private:
        unsigned short speed;
        unsigned char lane;
        unsigned char direction;
        unsigned char checksum;
    };

    class StatusSetCmd : public Header
    {
    public:
        StatusSetCmd();

    public:
        const static unsigned char MODE_SET;
        const static unsigned char MODE_SPEED_TEST;

    public:
        void change2SetMode();
        void change2SpeedTestMode();
        bool isSetMode() const;
        bool isSpeedTestMode() const;

    public:
        unsigned char getChecksum();

    private:
        unsigned char    mode;
        unsigned char    checksum;
    };

    class StatusSetResponse : public Response
    {

    };


    class MeasureSetCmd : public Header
    {
    public:
        MeasureSetCmd();

    public:
        const static unsigned char METER_MODE;
        const static unsigned char EXIT_MODE;

    public:
        void change2MeterMode();
        void change2ExitMode();
        bool isMeterMode() const;
        bool isExitMode() const;

    public:
        unsigned char getChecksum();

    private:
        unsigned char    mode;
        unsigned char    checksum;
    };

    class MeasureSetResponse : public Response
    {

    };


    class TestMeasureSetCmd : public Header
    {
    public:
        TestMeasureSetCmd();

    public:
        const static unsigned char DIREC_MODE;
        const static unsigned char SIMULATE_MODE;
        const static unsigned char MEATURE_MODE;

    public:
        void change2DirecMode();
        void change2SimulateMode();
        void change2MeasureMode();
        bool isDirecMode() const;
        bool isSimulateMode() const;
        bool isMeasureMode() const;

    public:
        unsigned char getChecksum();

    private:
        unsigned char    mode;
        unsigned char    checksum;
    };

    class TestMeasureSetResponse : public Response
    {

    };


    class SoftwareVersionSearchCmd : public ResetCmd
    {
    public:
        SoftwareVersionSearchCmd();
    };

    class SoftwareVersionSearchResponse : public Header
    {
    public:
        SoftwareVersionSearchResponse();

    public:
        char getChecksum() const;
        const char* getVersion(char* version, const int length) const;
        const char* getVersion(std::string& version) const;

    private:
        unsigned char    major;
        unsigned char    minor;
        unsigned char    revision;
        unsigned char    checksum;
    };

    class ProductNoSearchCmd : public ResetCmd
    {
    public:
        ProductNoSearchCmd();
    };

    class ProductNoSearchResponse : public Header
    {
    public:
        ProductNoSearchResponse();

    public:
        const char *getProductNo() const;
        const char *getMainBoardNo() const;
        char getChecksum();

    private:
        char productNo[9];
        char mainBoardNo[16];
        unsigned char checksum;
    };

    class Parameter : public Header
    {
    public:
        Parameter();

    public:
        enum TriggerMode
        {
            TRIGGER_HEADER  = 0x00,
            TRIGGER_TAIL    = 0x01,
            TRIGGER_BOTH    = 0x02,
            TRIGGER_CONTINUE    = 0x03,
        };


        enum RoadMode
        {
            ROAD_CITY    = 0x00,
            ROAD_HIGHWAY = 0x01,
        };


    public:
        unsigned char getRadarHeight() const;

        void setRadarHeight(unsigned char raderHeight);

        unsigned char getMaxSpeed() const;

        void setMaxSpeed(unsigned char maxSpeed);

        unsigned char getThreshold() const;

        void setThreshold(unsigned char threshold);

        unsigned char getHardThreshold() const;

        void setHardThreshold(unsigned char hardThreshold);

        unsigned char getLeftSpeed() const;

        void setLeftSpeed(unsigned char leftSpeed);

        unsigned char getAngle() const;

        void setAngle(unsigned char angle);

        unsigned char getDistance() const;

        void setDistance(unsigned char distance);

        unsigned char getLaneWidth() const;

        void setLaneWidth(unsigned char laneWidth);

        unsigned char getInterval() const;

        void setInterval(unsigned char interval);

        unsigned char getAdjustedLaneCoefficient() const;

        void setAdjustedLaneCoefficient(unsigned char adjustedLaneCoefficient);

        unsigned char getChecksum() const;

        TriggerMode getTriggerMode() const;

        void setTriggerMode(TriggerMode triggerMode);
        void setTriggerMode(unsigned char triggerMode);

        RoadMode getRoadMode() const;

        void setRoadMode(RoadMode roadMode);
        void setRoadMode(unsigned char roadMode);

    public:
        int isValidChecksum() const;
        unsigned char getChecksum();
        int isValidStatus() const;
        bool isSuccess() const;
        static bool isValidTriggerMode(int mode);
        static bool isValidRoadMode(int mode);

    private:
        unsigned char    radarHeight;
        unsigned char    maxSpeed;
        unsigned char    threshold;
        unsigned char    hardThreshold;
        unsigned char    triggerMode;
        unsigned char    leftSpeed;
        unsigned char    angle;
        unsigned char    distance; // 雷达到车道的距离
        unsigned char    laneWidth;
        unsigned char    interval;   //两车间的间隙
        unsigned char    adjustedLaneCoefficient;
        unsigned char    roadMode;
        unsigned char    checksum;
    };

    class ParameterSetCmd : public Parameter
    {
    public:
        ParameterSetCmd();

    public:
        void setParameter(const Parameter* parameter);
    };

    class ParameterSetResponse : public Response
    {

    };

    class ParameterSaveCmd : public ResetCmd
    {
    public:
        ParameterSaveCmd();
    };

    class ParameterSaveResponse : public Response
    {

    };

    class ParameterGetCmd : public ResetCmd
    {
    public:
        ParameterGetCmd();
    };

    class ParameterGetResponse : public Parameter
    {

    };

#pragma pack()

    class Cmd
    {
    public:

        template <typename S,typename R>
        static int sendCmd(int fd, const S& s, R& r)
        {
            int err = 0;
            err = send(fd,s);
            if( 0 == err )
            {
                err = recv(fd,r);
            }
            return  err;
        }

        template <typename S>
        static int send(int fd, const S& s)
        {
            const int writeSize = sizeof(S);
            int writeCount = write(fd,&s,writeSize);
            int err = 0;
            if(writeCount != writeSize)
                err = errno;
            return err;
        }

        template <typename R>
        static int recv(int fd, R& r)
        {
            const int readSize = sizeof(R);
            int readCount = 0;
            const int MAXTIMES = 10;
            int count = 0;
            int err = 0;

            do {
                readCount = read(fd,&r,readSize);
                if(readCount != readSize)
                {
                    usleep(10000);
                }
            } while (readCount <= 0 && count++ < MAXTIMES);

            if(readCount != readSize)
                err = errno;
            return err;
        }

        template <typename S, typename R>
        static int getError(S& s, R& r)
        {
            int err = 0;
            if( 0 == r.isValidHeader1() )
            {
                if( 0 == r.isValidHeader2() )
                {
                    if( 0 == s.isValidCmd( r.getCmd()) )
                    {
                        if( 0 == r.isValidLength() )
                        {
                            if( 0 == r.isValidChecksum() )
                            {
                                err = 0;
                            }
                            else
                            {
                                err = ERROR_CHECKSUM;
                            }
                        }
                        else
                        {
                            err = ERROR_DATALENGTH;
                        }
                    }
                    else
                    {
                        err = ERROR_CMD;
                    }
                }
                else
                {
                    err = ERROR_HEADER2;
                }
            }
            else
            {
                err = ERROR_HEADER1;
            }
            return err;
        }

        template <typename R>
        static int getError(int cmd, R& r)
        {
            int err = 0;
            if( 0 == r.isValidHeader1() )
            {
                if( 0 == r.isValidHeader2() )
                {
                    if( 0 == r.isValidCmd( cmd ) )
                    {
                        if( 0 == r.isValidLength() )
                        {
                            if( 0 == r.isValidChecksum() )
                            {
                                err = 0;
                            }
                            else
                            {
                                err = ERROR_CHECKSUM;
                            }
                        }
                        else
                        {
                            err = ERROR_DATALENGTH;
                        }
                    }
                    else
                    {
                        err = ERROR_CMD;
                    }
                }
                else
                {
                    err = ERROR_HEADER1;
                }
            }
            else
            {
                err = ERROR_HEADER2;
            }
            return err;
        }
    };

}
#endif //RADER_RADERCMD_H