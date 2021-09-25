#include "student.h"
#include<iostream>

Student::Student(QString name, QObject *parent) : QObject(parent), m_name(name)
{
    //m_name = name;
}

void Student::riseHand()
{
    emit riseMyHand(m_name);

}


void Student::welcomeTeacher(){

    std::cout << "Hello mr. Teacher! My name is " << m_name.toStdString() << std::endl;
}
