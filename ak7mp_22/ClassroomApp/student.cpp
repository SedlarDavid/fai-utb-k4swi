#include "student.h"
#include <iostream>

Student::Student(QString name, QObject *parent)
    : QObject{parent},m_name(name)
{

}

void Student::welcomeTeacher()
{
    std::cout << "Hello teacher!" << std::endl;
}
