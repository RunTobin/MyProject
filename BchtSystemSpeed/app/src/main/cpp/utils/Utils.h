//
// Created by admin on 2017/6/9.
//

#ifndef RADER_UTILS_H
#define RADER_UTILS_H

#include <termios.h>
#include <jni.h>
#include <vector>
#include <string>
#include <dirent.h>

class Utils {

public:
    static speed_t getBaudrate(jint baudrate);
    static int getDataBits(int bits);
    static int getParity(int parity);
    static int foundFiles(std::string& path, std::vector<std::string>& files );
};


#endif //RADER_UTILS_H
