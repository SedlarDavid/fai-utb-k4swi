#include "teacher.h"
#include <iostream>

Teacher::Teacher(QObject *parent) : QObject(parent)
{

}

void Teacher::goToClass()
{
    std::cout << "Teacher is going to the class!" << std::endl;
    emit teacherIncome();

}

void Teacher::noticeStudent(QString studentName)
{
    std::cout << "Teacher noticed student " << studentName.toStdString() << std::endl;

}
