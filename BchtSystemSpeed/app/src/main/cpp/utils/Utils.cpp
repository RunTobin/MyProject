//
// Created by admin on 2017/6/9.
//

#include "Utils.h"

speed_t Utils::getBaudrate(jint baudrate)
{
    switch(baudrate) {
        case 0: return B0; break;
        case 50: return B50; break;
        case 75: return B75; break;
        case 110: return B110; break;
        case 134: return B134; break;
        case 150: return B150; break;
        case 200: return B200; break;
        case 300: return B300; break;
        case 600: return B600; break;
        case 1200: return B1200; break;
        case 1800: return B1800; break;
        case 2400: return B2400; break;
        case 4800: return B4800; break;
        case 9600: return B9600; break;
        case 19200: return B19200; break;
        case 38400: return B38400; break;
        case 57600: return B57600; break;
        case 115200: return B115200; break;
        case 230400: return B230400; break;
        case 460800: return B460800; break;
        case 500000: return B500000; break;
        case 576000: return B576000; break;
        case 921600: return B921600; break;
        case 1000000: return B1000000; break;
        case 1152000: return B1152000; break;
        case 1500000: return B1500000; break;
        case 2000000: return B2000000; break;
        case 2500000: return B2500000; break;
        case 3000000: return B3000000; break;
        case 3500000: return B3500000; break;
        case 4000000: return B4000000; break;
        default: return -1; break;
    }
}

int Utils::getDataBits(int bits)
{
    switch (bits)
    {
        case 5:   return CS5; break;
        case 6:   return CS5; break;
        case 7:   return CS5; break;
        case 8:   return CS5; break;
        default:  return -1;  break;
    }
}

int Utils::getParity(int parity)
{
    switch (parity)
    {
        case 0:return 0;break;
        case 1:return PARENB;break;
        case 2:return PARODD;break;
        case 3:return PARMRK;break;
        default:return -1;break;
    }
}

int Utils::foundFiles(std::string& path, std::vector<std::string>& files )
{
    if(path[path.size()-1] != '/')
        path.push_back('/');
    files.resize(0);
    DIR* dir = opendir(path.c_str());
    if(dir)
    {
        struct dirent* dirEnt;
        while (NULL != (dirEnt = readdir(dir)))
        {
            if(( (dirEnt->d_type&'\x02')=='\x02') && strstr(dirEnt->d_name,"ttyS"))
            {
                files.push_back(path + dirEnt->d_name);
            }
        }
    }
    return files.size();
}