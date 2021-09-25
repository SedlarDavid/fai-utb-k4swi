#include <iostream>
#include <stdlib.h>
#include "myLib/myLib.h"

int main()
{
#ifdef WINDOWS
    system("cls");
#else
    system("clear"};)
#endif

    int temp = 0;
    printHello();
    std::cout << "Hello World" << std::endl;
}
