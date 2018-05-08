package com.huang.bchtsystem.rader;

/**
 * Created by admin on 2017/6/7.
 */

public class RaderCmd {

    final public static short Null = (short) 0x8310;
    final public static short STATUS_SEARCH = (short) 0x8310;
    final public static short RESET = (short) 0x8320;
    final public static short STATUS_SET = (short) 0x8350;
    final public static short SOFTWARE_VERSION_SEARCH = (short) 0x8351;
    final public static short PRODUCT_NO_SEARCH = (short) 0x8352;
    final public static short PRAMETER_SET = (short) 0x8355;
    final public static short PRAMETER_GET = (short) 0x8356;
    final public static short PRAMETER_SAVE = (short) 0x8357;
    final public static short RESTORE = (short) 0x8359;

    final public static short STATUS_RESPONSE_SUCCESS = (short) 0x853A;
    final public static short STATUS_RESPONSE_FAILURE = (short) 0x85FF;

    final public static byte HEADER1 = (byte) 0xFF;
    final public static byte HEADER2 = (byte) 0xFA;

    final public static byte POSITIVE = (byte) 0x00;
    final public static byte NEGATIVE = (byte) 0x01;

    final public static int ERROR_BASE = 0x1000;
    final public static int ERROR_UNKNOWN = (ERROR_BASE + 0x1);
    final public static int ERROR_HEADER1 = (ERROR_BASE + 0x2);
    final public static int ERROR_HEADER2 = (ERROR_BASE + 0x3);
    final public static int ERROR_DATALENGTH = (ERROR_BASE + 0x4);
    final public static int ERROR_CHECKSUM = (ERROR_BASE + 0x5);
    final public static int ERROR_RESPONSE_STATUS = (ERROR_BASE + 0x6);
    final public static int ERROR_CMD = (ERROR_BASE + 0x7);
    final public static int ERROR_FAILURE = (ERROR_BASE + 0x8);
    final public static int ERROR_NULL = (ERROR_BASE + 0x9);
    final public static int ERROR_NOT_OPENED = (ERROR_BASE + 0xA);
    final public static int ERROR_SPEED = (ERROR_BASE + 0xB);
    final public static int ERROR_CONFIG = (ERROR_BASE + 0xC);
    final public static int ERROR_PARAM_NOT_SAVED = (ERROR_BASE + 0xD);
    final public static int ERROR_WORKING_MODE = (ERROR_BASE + 0xE);
    final public static int ERROR_ANGLE = (ERROR_BASE + 0x10);
    final public static int ERROR_PARAM_OUT_OF_BOUND = (ERROR_BASE + 0x11);

    public class WorkingMode {
        final public static byte MODE_SET = 0x01;
        final public static byte MODE_SPEED_TEST = 0x02;
    };

    public class Mode {
        final public static byte METER_MODE = 0x01;
        final public static byte EXIT_MODE = 0x02;
    };

    public enum TriggerMode {
        TRIGGER_HEADER,
        TRIGGER_TAIL,
        TRIGGER_BOTH,
        TRIGGER_CONTINUE
    };

    public enum RoadMode {
        ROAD_CITY,
        ROAD_HIGHWAY,
    };

    public class OutOfBoundException extends Exception
    {
        public OutOfBoundException()
        {
            super();
        }

        public OutOfBoundException(String message)
        {
            super(message);
        }
    };

    public class Response
    {

        public Response()
        {
            status = 0;
        }

        public short getStatus()
        {
            return status;
        }

        public int isValidStatus()
        {
            return (this.status == RaderCmd.STATUS_RESPONSE_SUCCESS || this.status == RaderCmd.STATUS_RESPONSE_FAILURE ) ? 0 : ERROR_RESPONSE_STATUS;
        }
        public boolean isSuccess()
        {
            return this.status == RaderCmd.STATUS_RESPONSE_SUCCESS;
        }

        private short   status;
    };

    public class SpeedResponse
    {
        public short getSpeed() {
            return speed;
        }

        public void setSpeed(short speed) {
            this.speed = speed;
        }

        public char getLane() {
            return lane;
        }

        public void setLane(char lane) {
            this.lane = lane;
        }

        public char getDirection() {
            return direction;
        }

        public void setDirection(char direction) {
            this.direction = direction;
        }

        private short speed;
        private char lane;
        private char direction;
        //private char checksum;
    }

    class StatusSetCmd
    {

        public StatusSetCmd()
        {
            mode = 0;
        }

        public void change2SetMode()
        {
            this.mode = WorkingMode.MODE_SET;
        }
        public void change2SpeedTestMode()
        {
            this.mode = WorkingMode.MODE_SPEED_TEST;
        }
        public boolean isSetMode()
        {
            return this.mode == WorkingMode.MODE_SET;
        }
        public boolean isSpeedTestMode()
        {
            return this.mode == WorkingMode.MODE_SPEED_TEST;
        }

        private byte    mode;

    };

    public class StatusSetResponse extends Response
     {

     };


    class MeasureSetCmd
    {

        public MeasureSetCmd()
        {
            mode = 0;
        }

        public void change2MeterMode()
        {
            this.mode = Mode.METER_MODE;
        }
        public void change2ExitMode()
        {
            this.mode = Mode.EXIT_MODE;
        }
        public boolean isMeterMode()
        {
            return this.mode == Mode.METER_MODE;
        }
        public boolean isExitMode()
        {
            return this.mode == Mode.EXIT_MODE;
        }

        private byte    mode;

    };

    public class MeasureSetResponse extends Response
    {

    };


    class ParameterGetCmd
    {

        public ParameterGetCmd()
        {
        }
    };

    public  class Parameter
    {

        public Parameter()
        {
            raderHeight=0;
            maxSpeed=0;
            threshold=0;
            hardThreshold=0;
            triggerMode=0;
            leftSpeed=0;
            angle=0;
            distance=0;
            laneWidth=0;
            interval=0;
            adjustedLaneCoefficient=0;
            roadMode=0;
        }

        public float getRaderHeight() {
            return raderHeight;
        }

        public void setRaderHeight(float raderHeight) throws Exception{
            if( raderHeight >= 0 && raderHeight <= 25.5)
                this.raderHeight = raderHeight;
            else
                throw new Exception("ERROR_PARAM_OUT_OF_BOUND");
        }

        public float getMaxSpeed() {
            return maxSpeed;
        }

        public void setMaxSpeed(float maxSpeed) {
            this.maxSpeed = maxSpeed;
        }

        public float getThreshold() {
            return threshold;
        }

        public void setThreshold(float threshold) {
            this.threshold = threshold;
        }

        public float getHardThreshold() {
            return hardThreshold;
        }

        public void setHardThreshold(float hardThreshold) throws Exception{

            if( hardThreshold >= 0 && hardThreshold <= 25.5)
                this.hardThreshold = hardThreshold;
            else
                throw new Exception("ERROR_PARAM_OUT_OF_BOUND");
        }

        public float getTriggerMode() {
            return triggerMode;
        }

        public void setTriggerMode(TriggerMode triggerMode) {
            switch (triggerMode) {
                case TRIGGER_HEADER:
                    this.triggerMode = 0x00;
                    break;
                case TRIGGER_TAIL:
                    this.triggerMode = 0x01;
                    break;
                case TRIGGER_BOTH:
                    this.triggerMode = 0x02;
                    break;
                case TRIGGER_CONTINUE:
                    this.triggerMode = 0x03;
                    break;
                default:
                    this.triggerMode = 0x00;
                    break;
            }
        }

        public float getLeftSpeed() {
            return leftSpeed;
        }

        public void setLeftSpeed(float leftSpeed) {
            this.leftSpeed = leftSpeed;
        }

        public float getAngle() {
            return angle;
        }

        public void setAngle(float angle) {
            this.angle = angle;
        }

        public float getDistance() {
            return distance;
        }

        public void setDistance(float distance) throws Exception {

            if( distance >= 0 && distance <= 25.5 )
                this.distance = distance;
            else
                throw new Exception("ERROR_PARAM_OUT_OF_BOUND");
        }

        public float getLaneWidth() {
            return laneWidth;
        }

        public void setLaneWidth(float laneWidth) throws Exception {
            if( laneWidth >= 0 && laneWidth <= 25.5 )
                this.laneWidth = laneWidth;
            else
                throw new Exception("ERROR_PARAM_OUT_OF_BOUND");
        }

        public float getInterval() {
            return interval;
        }

        public void setInterval(float interval) throws Exception {
            if( interval >=0 && interval <= 25.5 )
                this.interval = interval;
            else
                throw new Exception("ERROR_PARAM_OUT_OF_BOUND");
        }

        public float getAdjustedLaneCoefficient() {
            return adjustedLaneCoefficient;
        }

        public void setAdjustedLaneCoefficient(float adjustedLaneCoefficient) throws Exception {
            if( adjustedLaneCoefficient >= 0 && adjustedLaneCoefficient <= 25.5 )
                this.adjustedLaneCoefficient = adjustedLaneCoefficient;
            else
                throw new Exception("ERROR_PARAM_OUT_OF_BOUND");
        }

        public float getRoadMode() {
            return roadMode;
        }

        public void setRoadMode(RoadMode roadMode) {
            switch (roadMode)
            {
                case ROAD_CITY:
                    this.roadMode = 0x00;
                    break;
                case ROAD_HIGHWAY:
                    this.roadMode = 0x01;
                    break;
                default:
                    this.roadMode = 0x00;
                    break;
            }
        }

        private float    raderHeight;
        private float    maxSpeed;
        private float    threshold;
        private float    hardThreshold;
        private float    triggerMode;
        private float    leftSpeed;
        private float    angle;
        private float    distance; // 雷达到车道的距离
        private float    laneWidth;
        private float    interval;   //两车间的间隙
        private float    adjustedLaneCoefficient;
        private float    roadMode;
    };

    public class ParameterGetResponse
    {
        public Parameter parameter;

        public Parameter getParameter() {
            return parameter;
        }

        public void setParameter(Parameter parameter) {
            this.parameter = parameter;
        }
    };

    public class ParameterSetCmd
    {
        public Parameter parameter;

        public ParameterSetCmd()
        {

        }

        public Parameter getParameter() {
            return parameter;
        }

        public void setParameter(Parameter parameter) {
            this.parameter = parameter;
        }
    };

    class ParameterSetResponse extends Response
    {

    };
}
