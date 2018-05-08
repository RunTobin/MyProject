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

#include "RaderCmd.h"
#include <stdio.h>






const unsigned short Radar::RadarCmd::Null = 0x0;
const unsigned short Radar::RadarCmd::STATUS_SEARCH = 0x8310;
const unsigned short Radar::RadarCmd::RESET = 0x8320;
const unsigned short Radar::RadarCmd::STATUS_SET = 0x8350;
const unsigned short Radar::RadarCmd::SOFTWARE_VERSION_SEARCH = 0x8351;
const unsigned short Radar::RadarCmd::PRODUCT_NO_SEARCH = 0x8352;
const unsigned short Radar::RadarCmd::PRAMETER_SET = 0x8355;
const unsigned short Radar::RadarCmd::PRAMETER_GET = 0x8356;
const unsigned short Radar::RadarCmd::PRAMETER_SAVE = 0x8357;
const unsigned short Radar::RadarCmd::RESTORE = 0x8359;
const unsigned short Radar::RadarCmd::SPEED   = 0x8A0A;
const unsigned short Radar::RadarCmd::STATUS_RESPONSE_SUCCESS   = 0x853A;
const unsigned short Radar::RadarCmd::STATUS_RESPONSE_FAILURE   = 0x85FF;
const unsigned short Radar::RadarCmd::MEASUREMENT_MODE =0x8410;
const unsigned short Radar::RadarCmd::TEST_MODE =0x8420;

const unsigned short Radar::StatusSearchResponse::STATUS_PARAMETER_SET     = 0x8801;
const unsigned short Radar::StatusSearchResponse::STATUS_SPEED_TEST_SET    = 0x8820;

const unsigned char Radar::StatusSetCmd::MODE_SET     = 0x01;
const unsigned char Radar::StatusSetCmd::MODE_SPEED_TEST    = 0x02;

const unsigned char Radar::MeasureSetCmd::METER_MODE     = 0x01;
const unsigned char Radar::MeasureSetCmd::EXIT_MODE    = 0x02;

const unsigned char Radar::TestMeasureSetCmd::DIREC_MODE     = 0x01;
const unsigned char Radar::TestMeasureSetCmd::SIMULATE_MODE    = 0x02;
const unsigned char Radar::TestMeasureSetCmd::MEATURE_MODE    = 0x03;

Radar::Header::Header()
        : header1(0xFF)
        , header2(0xFA)
        , dataLength(0)
        , cmd(RadarCmd::Null)
{

}

unsigned char Radar::Header::getHerder1() const {
    return header1;
}

void Radar::Header::setHerder1(unsigned char herder1) {
    this->header1 = herder1;
}

unsigned char Radar::Header::getHerder2() const {
    return header2;
}

void Radar::Header::setHerder2(unsigned char herder2) {
    this->header2 = herder2;
}

unsigned char Radar::Header::getDataLength() const {
    return dataLength;
}

void Radar::Header::setDataLength(unsigned char dataLength) {
    this->dataLength = dataLength;
}

unsigned short Radar::Header::getCmd() const {
    return cmd;
}

void Radar::Header::setCmd(unsigned short cmd) {
    this->cmd = cmd;
}

int Radar::Header::isValidHeader1() const
{
    return this->header1 == 0xFF ? 0 : ERROR_HEADER1;
}

int Radar::Header::isValidHeader2() const
{
    return this->header2 == 0xFA ? 0 : ERROR_HEADER2;
}

int Radar::Header::isValidLength() const
{
    return this->dataLength > 0 ? 0 : ERROR_DATALENGTH;
}

int Radar::Header::isValidCmd(unsigned short cmd) const
{
    return this->cmd == cmd ? 0 : ERROR_CMD;
}

Radar::StatusSearchCmd::StatusSearchCmd()
        : reserved(0x0)
{
    setCmd(RadarCmd::STATUS_SEARCH);
    setDataLength(0x03);
    checksum = Header::getChecksum(getCmd()) + Header::getChecksum(getDataLength()) + Header::getChecksum(reserved);
}

unsigned char Radar::StatusSearchCmd::getChecksum() const {
    return checksum;
}


Radar::StatusSearchResponse::StatusSearchResponse()
        : status(0)
        , checksum(0)
{

}

unsigned short Radar::StatusSearchResponse::getStatus() const {
    return status;
}

unsigned char Radar::StatusSearchResponse::getChecksum() const {
    return checksum;
}

int Radar::StatusSearchResponse::isValidStatus() const
{
    return (STATUS_PARAMETER_SET == status || STATUS_SPEED_TEST_SET == status) ? 0 : ERROR_RESPONSE_STATUS;
}

int Radar::StatusSearchResponse::isValidChecksum() const
{
    unsigned char validChecksum = Header::getChecksum(getCmd()) + Header::getChecksum(getDataLength()) + Header::getChecksum(status);
    return checksum == validChecksum ? 0 : ERROR_CHECKSUM;
}

bool Radar::StatusSearchResponse::isSuccess() const
{
    return 0 == isValidStatus();
}



Radar::ResetCmd::ResetCmd()
        : checksum(0)
{
    setCmd(RadarCmd::RESET);
    setDataLength(0x02);
    getChecksum();
}

unsigned char Radar::ResetCmd::getChecksum() {
    return checksum = Header::getChecksum(getCmd()) + Header::getChecksum(getDataLength());
}

int Radar::ResetCmd::isValidChecksum() const
{
    unsigned char validChecksum = Header::getChecksum(getCmd()) + Header::getChecksum(getDataLength());
    return checksum == (unsigned char)validChecksum ? 0 : ERROR_CHECKSUM;
}

Radar::Response::Response()
        : status(0)
        , checksum(0)
{}

unsigned short Radar::Response::getStatus() const {
    return status;
}

unsigned char Radar::Response::getChecksum() const {
    return checksum;
}

int Radar::Response::isValidStatus() const
{
    return (this->status == RadarCmd::STATUS_RESPONSE_SUCCESS || this->status == RadarCmd::STATUS_RESPONSE_FAILURE ) ? 0 : ERROR_RESPONSE_STATUS;
}

bool Radar::Response::isSuccess() const
{
    return this->status == RadarCmd::STATUS_RESPONSE_SUCCESS;
}

int Radar::Response::isValidChecksum() const
{
    unsigned char validChecksum = Header::getChecksum(getDataLength()) + Header::getChecksum(getCmd()) +  Header::getChecksum(status);
    return checksum == validChecksum ? 0 : ERROR_CHECKSUM;
}


Radar::StatusSetCmd::StatusSetCmd()
        : mode(0)
        , checksum(0)
{
    setCmd(RadarCmd::STATUS_SET);
    setDataLength(0x3);
}

unsigned char Radar::StatusSetCmd::getChecksum() {
    unsigned char validChecksum = Header::getChecksum(getDataLength()) + Header::getChecksum(getCmd()) +  Header::getChecksum(mode);
    checksum = validChecksum;
    return checksum;
}

void Radar::StatusSetCmd::change2SetMode()
{
    this->mode = MODE_SET;
    getChecksum();
}

void Radar::StatusSetCmd::change2SpeedTestMode()
{
    this->mode = MODE_SPEED_TEST;
    getChecksum();
}
bool Radar::StatusSetCmd::isSetMode() const
{
    return this->mode == MODE_SET;
}

bool Radar::StatusSetCmd::isSpeedTestMode() const
{
    return this->mode == MODE_SPEED_TEST;
}



Radar::MeasureSetCmd::MeasureSetCmd()
        : mode(0)
        , checksum(0)
{
    setCmd(RadarCmd::MEASUREMENT_MODE);
    setDataLength(0x3);
}

unsigned char Radar::MeasureSetCmd::getChecksum() {
    unsigned char validChecksum = Header::getChecksum(getDataLength()) + Header::getChecksum(getCmd()) +  Header::getChecksum(mode);
    checksum = validChecksum;
    return checksum;
}

void Radar::MeasureSetCmd::change2MeterMode()
{
    this->mode = METER_MODE;
    getChecksum();
}

void Radar::MeasureSetCmd::change2ExitMode()
{
    this->mode = EXIT_MODE;
    getChecksum();
}
bool Radar::MeasureSetCmd::isMeterMode() const
{
    return this->mode == METER_MODE;
}

bool Radar::MeasureSetCmd::isExitMode() const
{
    return this->mode == EXIT_MODE;
}


Radar::TestMeasureSetCmd::TestMeasureSetCmd()
        : mode(0)
        , checksum(0)
{
    setCmd(RadarCmd::TEST_MODE);
    setDataLength(0x3);
}

unsigned char Radar::TestMeasureSetCmd::getChecksum() {
    unsigned char validChecksum = Header::getChecksum(getDataLength()) + Header::getChecksum(getCmd()) +  Header::getChecksum(mode);
    checksum = validChecksum;
    return checksum;
}

void Radar::TestMeasureSetCmd::change2DirecMode()
{
    this->mode = DIREC_MODE;
    getChecksum();
}

void Radar::TestMeasureSetCmd::change2SimulateMode()
{
    this->mode = SIMULATE_MODE;
    getChecksum();
}
void Radar::TestMeasureSetCmd::change2MeasureMode()
{
    this->mode = MEATURE_MODE;
    getChecksum();
}
bool Radar::TestMeasureSetCmd::isDirecMode() const
{
    return this->mode == DIREC_MODE;
}

bool Radar::TestMeasureSetCmd::isSimulateMode() const
{
    return this->mode == SIMULATE_MODE;
}
bool Radar::TestMeasureSetCmd::isMeasureMode() const
{
    return this->mode == MEATURE_MODE;
}



Radar::SoftwareVersionSearchCmd::SoftwareVersionSearchCmd()
{
    setCmd(RadarCmd::SOFTWARE_VERSION_SEARCH);
    setDataLength(0x2);
}

Radar::SoftwareVersionSearchResponse::SoftwareVersionSearchResponse()
        : major(0)
        , minor(0)
        , revision(0)
        , checksum(0)
{}

char Radar::SoftwareVersionSearchResponse::getChecksum() const {
    return checksum;
}
const char* Radar::SoftwareVersionSearchResponse::getVersion(char* version, const int length) const
{
    if(version && length >= 12)
    {
        snprintf(version, length,"%d.%d.%d", major, minor, revision);
    }
    return version;
}

const char* Radar::SoftwareVersionSearchResponse::getVersion(std::string& version) const
{
    version.reserve(12);
    sprintf((char*)version.c_str(),"%d.%d.%d", major, minor, revision);
    return version.c_str();
}

Radar::ProductNoSearchCmd::ProductNoSearchCmd()
{
    setCmd(RadarCmd::PRODUCT_NO_SEARCH);
    setDataLength(0x2);
}


Radar::ProductNoSearchResponse::ProductNoSearchResponse() {}

const char *Radar::ProductNoSearchResponse::getProductNo() const {
    return productNo;
}

const char *Radar::ProductNoSearchResponse::getMainBoardNo() const {
    return mainBoardNo;
}

char Radar::ProductNoSearchResponse::getChecksum() {
    return checksum;
}

Radar::Parameter::Parameter()
        : radarHeight(0)
        , maxSpeed(0)
        , threshold(0)
        , hardThreshold(0)
        , triggerMode(0)
        , leftSpeed(0)
        , angle(0)
        , distance(0)
        , laneWidth(0)
        , interval(0)
        , adjustedLaneCoefficient(0)
        , roadMode(0)
        , checksum(0)
{

}

unsigned char Radar::Parameter::getRadarHeight() const {
    return radarHeight;
}

void Radar::Parameter::setRadarHeight(unsigned char radarHeight) {
    Parameter::radarHeight = radarHeight;
}

unsigned char Radar::Parameter::getMaxSpeed() const {
    return maxSpeed;
}

void Radar::Parameter::setMaxSpeed(unsigned char maxSpeed) {
    Parameter::maxSpeed = maxSpeed;
}


unsigned char Radar::Parameter::getThreshold() const {
    return threshold;
}

void Radar::Parameter::setThreshold(unsigned char threshold) {
    Parameter::threshold = threshold;
}


unsigned char Radar::Parameter::getHardThreshold() const {
    return hardThreshold;
}

void Radar::Parameter::setHardThreshold(unsigned char hardThreshold) {
    Parameter::hardThreshold = hardThreshold;
}


unsigned char Radar::Parameter::getLeftSpeed() const {
    return leftSpeed;
}

void Radar::Parameter::setLeftSpeed(unsigned char leftSpeed) {
    Parameter::leftSpeed = leftSpeed;
}

unsigned char Radar::Parameter::getAngle() const {
    return angle;
}

void Radar::Parameter::setAngle(unsigned char angle) {
    Parameter::angle = angle;
}

unsigned char Radar::Parameter::getDistance() const {
    return distance;
}

void Radar::Parameter::setDistance(unsigned char distance) {
    Parameter::distance = distance;
}

unsigned char Radar::Parameter::getLaneWidth() const {
    return laneWidth;
}

void Radar::Parameter::setLaneWidth(unsigned char laneWidth) {
    Parameter::laneWidth = laneWidth;
}


unsigned char Radar::Parameter::getInterval() const {
    return interval;
}

void Radar::Parameter::setInterval(unsigned char interval) {
    Parameter::interval = interval;
}


unsigned char Radar::Parameter::getAdjustedLaneCoefficient() const {
    return adjustedLaneCoefficient;
}

void Radar::Parameter::setAdjustedLaneCoefficient(unsigned char adjustedLaneCoefficient) {
    Parameter::adjustedLaneCoefficient = adjustedLaneCoefficient;
}


unsigned char Radar::Parameter::getChecksum() const {
    return checksum;
}

Radar::Parameter::TriggerMode Radar::Parameter::getTriggerMode() const {
    return (Radar::Parameter::TriggerMode)triggerMode;
}

void Radar::Parameter::setTriggerMode(TriggerMode triggerMode) {
    Parameter::triggerMode = triggerMode;
}

void Radar::Parameter::setTriggerMode(unsigned char triggerMode)
{
    if( triggerMode >=TRIGGER_HEADER && triggerMode <= TRIGGER_CONTINUE )
        Parameter::triggerMode = (Radar::Parameter::TriggerMode)triggerMode;
    else
        Parameter::triggerMode = TRIGGER_HEADER;
}



Radar::Parameter::RoadMode Radar::Parameter::getRoadMode() const {
    return (Radar::Parameter::RoadMode)roadMode;
}

void Radar::Parameter::setRoadMode(RoadMode roadMode) {
    Parameter::roadMode = roadMode;
}

void Radar::Parameter::setRoadMode(unsigned char roadMode)
{
    if( roadMode >= ROAD_CITY && roadMode <= ROAD_HIGHWAY )
        Parameter::roadMode = (Radar::Parameter::RoadMode)roadMode;
    else
        Parameter::roadMode = ROAD_CITY;
}


int Radar::Parameter::isValidChecksum() const
{
    unsigned char validChecksum = getChecksum();

    return checksum == (unsigned char)validChecksum ? 0 : ERROR_CHECKSUM;
}

unsigned char Radar::Parameter::getChecksum()
{
    short validChecksum = Header::getChecksum(getCmd())
                          + getDataLength()
                          + radarHeight
                          + maxSpeed
                          + threshold
                          + hardThreshold
                          + triggerMode
                          + leftSpeed
                          + angle
                          + distance // 雷达到车道的距离
                          + laneWidth
                          + interval   //两车间的间隙
                          + adjustedLaneCoefficient
                          + roadMode;

    return (checksum = (unsigned char)validChecksum);
}

int Radar::Parameter::isValidStatus() const
{
    return 0;
}
bool Radar::Parameter::isSuccess() const
{
    return 0 == isValidStatus();
}

bool Radar::Parameter::isValidTriggerMode(int mode)
{
    return mode >= TRIGGER_HEADER && mode <= TRIGGER_CONTINUE;
}

bool Radar::Parameter::isValidRoadMode(int mode)
{
    return ROAD_CITY   <= mode && ROAD_HIGHWAY >= mode;
}
Radar::ParameterSaveCmd::ParameterSaveCmd()
{
    setCmd(RadarCmd::PRAMETER_SAVE);
    setDataLength(0x2);
}

Radar::ParameterGetCmd::ParameterGetCmd()
{
    setCmd(RadarCmd::PRAMETER_GET);
    setDataLength(0x2);
    getChecksum();
}

Radar::ParameterSetCmd::ParameterSetCmd()
{
    setCmd(RadarCmd::PRAMETER_SET);
    setDataLength(0x0E);

}

void Radar::ParameterSetCmd::setParameter(const Radar::Parameter* parameter)
{
    if(parameter)
    {
        this->setTriggerMode( parameter->getTriggerMode() );
        this->setRadarHeight(parameter->getRadarHeight());
        this->setMaxSpeed(parameter->getMaxSpeed());
        this->setThreshold(parameter->getThreshold());
        this->setHardThreshold(parameter->getHardThreshold());
        this->setLeftSpeed(parameter->getLeftSpeed());
        this->setAngle(parameter->getAngle());
        this->setDistance(parameter->getDistance());
        this->setLaneWidth(parameter->getLaneWidth());
        this->setInterval(parameter->getInterval());
        this->setAdjustedLaneCoefficient(this->getAdjustedLaneCoefficient());
        this->setRoadMode(parameter->getRoadMode());
    }
}

Radar::SpeedResponse::SpeedResponse()
    : speed(0)
    , lane(0)
    , direction(0)
    , checksum(0)
{}

unsigned short Radar::SpeedResponse::getSpeed() const {
    return speed;
}

unsigned char Radar::SpeedResponse::getLane() const {
    return lane;
}

unsigned char Radar::SpeedResponse::getDirection() const {
    return direction;
}

unsigned char Radar::SpeedResponse::getChecksum() const {
    return checksum;
}

bool Radar::SpeedResponse::isValidChecksum() const
{
    unsigned char validChecksum = Header::getChecksum(getCmd())
                                    + getDataLength()
                                    + Header::getChecksum(speed)
                                    + lane
                                    + direction;

    return checksum == validChecksum;
}

